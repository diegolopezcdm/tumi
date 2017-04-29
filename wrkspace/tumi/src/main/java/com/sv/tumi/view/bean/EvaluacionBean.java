package com.sv.tumi.view.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.dao.CursoEvaluacionDAO;
import com.sv.tumi.db.dao.CursoevaluacionPreguntaRespuestaDAO;
import com.sv.tumi.db.dao.CursoevaluacionpreguntaDAO;
import com.sv.tumi.db.dao.EvaluacionDAO;
import com.sv.tumi.db.dao.NivelDAO;
import com.sv.tumi.db.dao.RespuestaDAO;
import com.sv.tumi.db.dao.ResultadoEvaluacionDAO;
import com.sv.tumi.db.entity.Cursoevaluacion;
import com.sv.tumi.db.entity.Cursoevaluacionpregunta;
import com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta;
import com.sv.tumi.db.entity.Evaluacion;
import com.sv.tumi.db.entity.Nivel;
import com.sv.tumi.db.entity.Pregunta;
import com.sv.tumi.db.entity.Respuesta;
import com.sv.tumi.db.entity.Resultadoevaluacion;
import com.sv.tumi.db.entity.Tiempodesarrollo;

@ManagedBean(name = "evaluacionBean", eager = true)
@SessionScoped
public class EvaluacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(EvaluacionBean.class);
	private Pregunta pregunta;
	private Evaluacion selectedEvaluacion;
	private Tiempodesarrollo selectedTiempo;

	@ManagedProperty(name = "preguntaIndex", value = "0")
	private Integer preguntaIndex = 0;
	private Boolean showfinalizar = false;

	List<Cursoevaluacion> cursoEvaluacionList = new ArrayList<Cursoevaluacion>();
	List<Cursoevaluacionpregunta> preguntasEvaluacion = new ArrayList<Cursoevaluacionpregunta>();
	List<Cursoevaluacionpreguntarespuesta> cursoevaluacionpreguntarespuestas = new ArrayList<Cursoevaluacionpreguntarespuesta>();
	Map<String, Object> filter = new HashMap<String, Object>();

	private String selectedItem;
	private List<String> selectedItems = new ArrayList();

	CursoevaluacionpreguntaDAO cursoevaluacionpreguntaDAO = new CursoevaluacionpreguntaDAO();
	CursoEvaluacionDAO cursoEvaluacionDAO = new CursoEvaluacionDAO();
	RespuestaDAO respuestaDAO = new RespuestaDAO();
	ResultadoEvaluacionDAO resultadoEvaluacionDAO = new ResultadoEvaluacionDAO();
	CursoevaluacionPreguntaRespuestaDAO cursoevaluacionPreguntaRespuestaDAO = new CursoevaluacionPreguntaRespuestaDAO();

	@PostConstruct
	public void inicializar() {
		cargaEvaluacion();
	}

	public void cargaEvaluacion() {
		EvaluacionDAO evaDao = new EvaluacionDAO();
		selectedEvaluacion = evaDao.find(1);

		filter.put("codigoEvaluacion.codigo", selectedEvaluacion.getCodigo());
		cursoEvaluacionList = cursoEvaluacionDAO.findByProperty(filter);

		filter.clear();
		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {
			filter.put("codigoCursoEvaluacion.codigo",
					cursoevaluacion.getCodigo());
			List<Cursoevaluacionpregunta> preguntasCursos = cursoevaluacionpreguntaDAO
					.findByProperty(filter);

			for (Cursoevaluacionpregunta preguntasCurso : preguntasCursos) {
				preguntasEvaluacion.add(preguntasCurso);
			}

		}

	}

	public void goPreguntas() throws IOException {

		if (preguntasEvaluacion.size() - 1 == getPreguntaIndex()) {
			showfinalizar = true;
		}

		agregarPregunta();

		pregunta = preguntasEvaluacion.get(getPreguntaIndex())
				.getCodigoPregunta();
		setPreguntaIndex(getPreguntaIndex() + 1);

		filter.clear();
		filter.put("codigoPregunta.codigo", pregunta.getCodigo());
		List<Respuesta> respuestas = respuestaDAO.findByProperty(filter);
		pregunta.setRespuestaList(respuestas);

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("pregunta.xhtml");
	}

	private void agregarPregunta() {
		if (pregunta != null) {
			if (pregunta.getCodigoTipoPregunta().getCodigo() == 1) {
				valorarPregunta(selectedItem);
				crearRespuesta(selectedItem);
			}

			if (pregunta.getCodigoTipoPregunta().getCodigo() == 2) {
				valorarPregunta(selectedItems);
				for (String selectedItem : selectedItems) {
					crearRespuesta(selectedItem);
				}

			}
			if (pregunta.getCodigoTipoPregunta().getCodigo() == 3) {
				// crearRespuesta(null);
				preguntasEvaluacion
				.get(getPreguntaIndex() - 1).setPuntajeObtenido(0.0);
			}
		}
	}

	public void finalizarEvaluacion() {
		agregarPregunta();

		System.out.println(cursoEvaluacionList);
		System.out.println(preguntasEvaluacion);
		System.out.println(cursoevaluacionpreguntarespuestas);

		Double puntajeObtenido = 0.0;
		Double maximoPuntaje = 0.0;
		Double porcentajeAprobacion = 0.0;
		for (Cursoevaluacionpregunta cursoevaluacionpregunta : preguntasEvaluacion) {
			maximoPuntaje = maximoPuntaje
					+ cursoevaluacionpregunta.getCodigoPregunta().getValor();

			if (cursoevaluacionpregunta.getPuntajeObtenido() != null
					&& cursoevaluacionpregunta.getPuntajeObtenido().compareTo(
							new Double(0.0)) != 0) {
				puntajeObtenido = puntajeObtenido
						+ cursoevaluacionpregunta.getPuntajeObtenido();
			}
		}

		System.out.println(puntajeObtenido);
		System.out.println(maximoPuntaje);
		porcentajeAprobacion = (puntajeObtenido * 100) / maximoPuntaje;

		NivelDAO nivelDAO = new NivelDAO();
		Nivel bajo = nivelDAO.find(1);
		Nivel intermedio = nivelDAO.find(2);
		Nivel alto = nivelDAO.find(3);
		
		for (Cursoevaluacionpregunta cursoevaluacionpregunta : preguntasEvaluacion) {
			cursoevaluacionpreguntaDAO.edit(cursoevaluacionpregunta);
		}
		
		for (Cursoevaluacionpreguntarespuesta cursoevaluacionpreguntarespuestas : cursoevaluacionpreguntarespuestas) {
			cursoevaluacionPreguntaRespuestaDAO.create(cursoevaluacionpreguntarespuestas);
		}

		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {
			Resultadoevaluacion resultadoevaluacion = new Resultadoevaluacion();
			resultadoevaluacion.setCodigoCursoEvaluacion(cursoevaluacion);
			resultadoevaluacion.setFechaRegistro(new Date());
			resultadoevaluacion.setUsuarioRegistro("");
			resultadoevaluacion.setPuntaje(porcentajeAprobacion);

			if (porcentajeAprobacion < 70) {
				resultadoevaluacion.setCodigoNivel(bajo);
			}
			if (porcentajeAprobacion > 70 && porcentajeAprobacion < 90) {
				resultadoevaluacion.setCodigoNivel(intermedio);
			}
			if (porcentajeAprobacion > 90) {
				resultadoevaluacion.setCodigoNivel(alto);
			}
			resultadoEvaluacionDAO.create(resultadoevaluacion);
		}
	}

	private void valorarPregunta(String codigoRespuesta) {
		Cursoevaluacionpregunta pregunta = preguntasEvaluacion
				.get(getPreguntaIndex() - 1);
		Respuesta respuesta = respuestaDAO.find(Integer
				.valueOf(codigoRespuesta));

		if (respuesta.getVerdadero() == 1) {
			pregunta.setPuntajeObtenido(pregunta.getCodigoPregunta().getValor());
		} else {
			pregunta.setPuntajeObtenido(0.0);
		}
	}

	private void valorarPregunta(List<String> codigoRespuestas) {
		Cursoevaluacionpregunta pregunta = preguntasEvaluacion
				.get(getPreguntaIndex() - 1);
		boolean exito = true;

		filter.clear();
		filter.put("codigoPregunta.codigo", pregunta.getCodigo());
		filter.put("verdadero", new Short("1"));
		List<Respuesta> respuestas = respuestaDAO.findByProperty(filter);

		if (Integer.valueOf(respuestas.size()).compareTo(
				Integer.valueOf(codigoRespuestas.size())) != 0) {
			exito = false;
		} else {
			for (Respuesta respuesta : respuestas) {

				boolean found = false;

				for (String codigoRespuesta : codigoRespuestas) {

					if (Integer.valueOf(codigoRespuesta).compareTo(
							respuesta.getCodigo()) == 0) {
						found = true;
						break;
					}

				}

				if (!found) {
					exito = false;
					break;
				}

			}
		}

		if (exito) {
			pregunta.setPuntajeObtenido(pregunta.getCodigoPregunta().getValor());
		} else {
			pregunta.setPuntajeObtenido(0.0);
		}
	}

	private void crearRespuesta(String codigoRespuesta) {
		Cursoevaluacionpreguntarespuesta cursoevaluacionpreguntarespuesta = new Cursoevaluacionpreguntarespuesta();
		cursoevaluacionpreguntarespuesta
				.setCodigoCursoEvaluacionPregunta(preguntasEvaluacion
						.get(getPreguntaIndex() - 1));
		cursoevaluacionpreguntarespuesta.setCodigoRespuesta(respuestaDAO
				.find(Integer.valueOf(codigoRespuesta)));
		cursoevaluacionpreguntarespuesta.setFechaRegistro(new Date());
		cursoevaluacionpreguntarespuesta.setUsuarioRegistro("");
		cursoevaluacionpreguntarespuestas.add(cursoevaluacionpreguntarespuesta);
	}

	public String goEmpleadoMovin() throws IOException {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		// flash.put("selectedEmpl", selectedEmpleado);
		return "empleado_movimiento.xhtml?faces-redirect=true";
	}

	public Evaluacion getSelectedEvaluacion() {
		return selectedEvaluacion;
	}

	public void setSelectedEvaluacion(Evaluacion selectedEvaluacion) {
		this.selectedEvaluacion = selectedEvaluacion;
	}

	public Tiempodesarrollo getSelectedTiempo() {
		return selectedTiempo;
	}

	public void setSelectedTiempo(Tiempodesarrollo selectedTiempo) {
		this.selectedTiempo = selectedTiempo;
	}

	public Integer getPreguntaIndex() {
		return preguntaIndex;
	}

	public void setPreguntaIndex(Integer preguntaIndex) {
		this.preguntaIndex = preguntaIndex;
	}

	public List<Cursoevaluacionpregunta> getPreguntasEvaluacion() {
		return preguntasEvaluacion;
	}

	public void setPreguntasEvaluacion(
			List<Cursoevaluacionpregunta> preguntasEvaluacion) {
		this.preguntasEvaluacion = preguntasEvaluacion;
	}

	public Pregunta getPregunta() {
		// this.pregunta=getPreguntasEvaluacion().get(preguntaIndex);
		// this.pregunta.setRespuestaList(getPreguntasEvaluacion().get(preguntaIndex).getRespuestaList());
		// preguntaIndex++;
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<String> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<String> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public Boolean getShowfinalizar() {
		return showfinalizar;
	}

}
