package com.sv.tumi.view.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.dao.CursoCapacitacionDAO;
import com.sv.tumi.db.dao.CursoEvaluacionDAO;
import com.sv.tumi.db.dao.CursoNivelDAO;
import com.sv.tumi.db.dao.CursoevaluacionPreguntaRespuestaDAO;
import com.sv.tumi.db.dao.CursoevaluacionpreguntaDAO;
import com.sv.tumi.db.dao.EstadoDAO;
import com.sv.tumi.db.dao.EvaluacionDAO;
import com.sv.tumi.db.dao.NivelDAO;
import com.sv.tumi.db.dao.ParametroGeneracionCapacitacionDAO;
import com.sv.tumi.db.dao.RespuestaDAO;
import com.sv.tumi.db.dao.ResultadoEvaluacionDAO;
import com.sv.tumi.db.dao.SolicitudCapacitacionDAO;
import com.sv.tumi.db.dao.SubtemaDAO;
import com.sv.tumi.db.dao.TemaDAO;
import com.sv.tumi.db.entity.Cursocapacitacion;
import com.sv.tumi.db.entity.Cursoevaluacion;
import com.sv.tumi.db.entity.Cursoevaluacionpregunta;
import com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta;
import com.sv.tumi.db.entity.Cursonivel;
import com.sv.tumi.db.entity.Estado;
import com.sv.tumi.db.entity.Evaluacion;
import com.sv.tumi.db.entity.Nivel;
import com.sv.tumi.db.entity.ParametroGeneracionCapacitacion;
import com.sv.tumi.db.entity.Pregunta;
import com.sv.tumi.db.entity.Respuesta;
import com.sv.tumi.db.entity.Resultadoevaluacion;
import com.sv.tumi.db.entity.Solicitudcapacitacion;
import com.sv.tumi.db.entity.Subtema;
import com.sv.tumi.db.entity.Tema;
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
	List<Cursonivel> cursosGenerados = new ArrayList<Cursonivel>();
	Map<String, Object> filter = new HashMap<String, Object>();

	private String selectedItem;
	private List<String> selectedItems = new ArrayList();

	CursoevaluacionpreguntaDAO cursoevaluacionpreguntaDAO = new CursoevaluacionpreguntaDAO();
	CursoEvaluacionDAO cursoEvaluacionDAO = new CursoEvaluacionDAO();
	RespuestaDAO respuestaDAO = new RespuestaDAO();
	ResultadoEvaluacionDAO resultadoEvaluacionDAO = new ResultadoEvaluacionDAO();
	CursoevaluacionPreguntaRespuestaDAO cursoevaluacionPreguntaRespuestaDAO = new CursoevaluacionPreguntaRespuestaDAO();
	EstadoDAO estadoDAO = new EstadoDAO();
	EvaluacionDAO evaluacionDAO = new EvaluacionDAO();
	ParametroGeneracionCapacitacionDAO parametroGeneracionCapacitacionDAO = new ParametroGeneracionCapacitacionDAO();
	NivelDAO nivelDAO = new NivelDAO();
	CursoNivelDAO cursoNivelDAO = new CursoNivelDAO();
	CursoCapacitacionDAO cursoCapacitacionDAO = new CursoCapacitacionDAO();
	TemaDAO temaDAO = new TemaDAO();
	SubtemaDAO subtemaDAO = new SubtemaDAO();
	SolicitudCapacitacionDAO solicitudCapacitacionDAO = new SolicitudCapacitacionDAO();

	@PostConstruct
	public void inicializar() {
		cargaEvaluacion();
	}

	public void cargaEvaluacion() {
		selectedEvaluacion = evaluacionDAO.find(1);
		selectedEvaluacion.setFechaInicio(new Date());

		filter.clear();
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

		if (showfinalizar) {
			finalizarEvaluacion();
		} else if (preguntasEvaluacion.size() - 1 == getPreguntaIndex()) {
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

		limpiarRespuesta();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("pregunta.xhtml");
	}

	private void limpiarRespuesta() {
		selectedItem = null;
		selectedItems = new ArrayList();
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
				preguntasEvaluacion.get(getPreguntaIndex() - 1)
						.setPuntajeObtenido(0.0);
			}
		}
	}

	public void finalizarEvaluacion() throws IOException {
		agregarPregunta();

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

		Nivel bajo = nivelDAO.find(1);
		Nivel intermedio = nivelDAO.find(2);
		Nivel alto = nivelDAO.find(3);

		for (Cursoevaluacionpregunta cursoevaluacionpregunta : preguntasEvaluacion) {
			cursoevaluacionpreguntaDAO.edit(cursoevaluacionpregunta);
		}

		for (Cursoevaluacionpreguntarespuesta cursoevaluacionpreguntarespuestas : cursoevaluacionpreguntarespuestas) {
			cursoevaluacionPreguntaRespuestaDAO
					.create(cursoevaluacionpreguntarespuestas);
		}

		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {
			Resultadoevaluacion resultadoevaluacion = new Resultadoevaluacion();
			resultadoevaluacion.setCodigoCursoEvaluacion(cursoevaluacion);
			resultadoevaluacion.setFechaRegistro(new Date());
			resultadoevaluacion.setUsuarioRegistro("1");
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

		Estado estado = estadoDAO.find(4);
		selectedEvaluacion.setCodigoEstado(estado);
		selectedEvaluacion.setFechaFin(new Date());
		evaluacionDAO.edit(selectedEvaluacion);

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("evaluacionFinal.xhtml");
	}

	public void generarCapacitacion() throws IOException {
		// selectedEvaluacion
		System.out.println(cursoEvaluacionList);
		cursosGenerados = new ArrayList<Cursonivel>();
		
		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {
			
			filter.clear();
			filter.put("codigoCursoEvaluacion.codigo",
					cursoevaluacion.getCodigo());
			Resultadoevaluacion resultadoEvalacion = resultadoEvaluacionDAO
					.findByProperty(filter).get(0);

			System.out.println("nivel esperado "
					+ cursoevaluacion.getCodigoCursoNivel().getCodigoNivel()
							.getNombre());
			System.out.println("nivel esperado "
					+ resultadoEvalacion.getCodigoNivel().getNombre());

			filter.clear();
			filter.put("codigoNivelEsperado", cursoevaluacion
					.getCodigoCursoNivel().getCodigoNivel().getCodigo());
			filter.put("codigoNivelReal", resultadoEvalacion.getCodigoNivel()
					.getCodigo());
			ParametroGeneracionCapacitacion parametroGeneracionCapacitacion = parametroGeneracionCapacitacionDAO
					.findByProperty(filter).get(0);
			Nivel nivelSeleccionado = nivelDAO
					.find(parametroGeneracionCapacitacion
							.getCodigoNivelSeleccionado());
			System.out.println("nivel selecionado: "
					+ nivelSeleccionado.getNombre());

			filter.clear();
			filter.put("codigoNivel.codigo", nivelSeleccionado.getCodigo());
			filter.put("codigoCurso.codigo", cursoevaluacion
					.getCodigoCursoNivel().getCodigoCurso().getCodigo());
			Cursonivel cursonivelSeleccionado = cursoNivelDAO.findByProperty(
					filter).get(0);

			Cursocapacitacion cursocapacitacion = new Cursocapacitacion();
			cursocapacitacion.setCodigoCapacitacion(cursoevaluacion
					.getCodigoEvaluacion().getCodigoSolicitudCapacitacion());
			cursocapacitacion.setCodigoCursoNivel(cursonivelSeleccionado);
			cursocapacitacion.setFechaRegistro(new Date());
			cursocapacitacion.setUsuarioRegistro("1");
			cursoCapacitacionDAO.create(cursocapacitacion);

			filter.clear();
			filter.put("codigoCursoNivel.codigo",
					cursonivelSeleccionado.getCodigo());
			List<Tema> temas = temaDAO.findByProperty(filter);
			for (Tema tema : temas) {
				filter.clear();
				filter.put("codigoTema.codigo", tema.getCodigo());
				List<Subtema> subtemas = subtemaDAO.findByProperty(filter);
				tema.setSubtemaList(subtemas);
			}

			cursonivelSeleccionado.setTemaList(temas);

			cursosGenerados.add(cursonivelSeleccionado);
			System.out.println(cursocapacitacion);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("generarCapacitacion.xhtml");
		}
		
		Estado estado = estadoDAO.find(5);
		Solicitudcapacitacion solicitudcapacitacion = selectedEvaluacion.getCodigoSolicitudCapacitacion();
		solicitudcapacitacion.setCodigoEstado(estado);
		
		solicitudCapacitacionDAO.edit(solicitudcapacitacion);

	}

	private void valorarPregunta(String codigoRespuesta) {

		Cursoevaluacionpregunta pregunta = preguntasEvaluacion
				.get(getPreguntaIndex() - 1);

		if (codigoRespuesta != null) {
			Respuesta respuesta = respuestaDAO.find(Integer
					.valueOf(codigoRespuesta));

			if (respuesta.getVerdadero() == 1) {
				pregunta.setPuntajeObtenido(pregunta.getCodigoPregunta()
						.getValor());
			} else {
				pregunta.setPuntajeObtenido(0.0);
			}
		} else {
			pregunta.setPuntajeObtenido(0.0);
		}

	}

	private void valorarPregunta(List<String> codigoRespuestas) {
		Cursoevaluacionpregunta pregunta = preguntasEvaluacion
				.get(getPreguntaIndex() - 1);

		if (codigoRespuestas != null && codigoRespuestas.size() > 0) {
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
				pregunta.setPuntajeObtenido(pregunta.getCodigoPregunta()
						.getValor());
			} else {
				pregunta.setPuntajeObtenido(0.0);
			}
		} else {
			pregunta.setPuntajeObtenido(0.0);
		}

	}

	private void crearRespuesta(String codigoRespuesta) {

		if (codigoRespuesta != null) {
			Cursoevaluacionpreguntarespuesta cursoevaluacionpreguntarespuesta = new Cursoevaluacionpreguntarespuesta();
			cursoevaluacionpreguntarespuesta
					.setCodigoCursoEvaluacionPregunta(preguntasEvaluacion
							.get(getPreguntaIndex() - 1));
			cursoevaluacionpreguntarespuesta.setCodigoRespuesta(respuestaDAO
					.find(Integer.valueOf(codigoRespuesta)));
			cursoevaluacionpreguntarespuesta.setFechaRegistro(new Date());
			cursoevaluacionpreguntarespuesta.setUsuarioRegistro("1");
			cursoevaluacionpreguntarespuestas
					.add(cursoevaluacionpreguntarespuesta);
		}

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

	public List<Cursonivel> getCursosGenerados() {
		return cursosGenerados;
	}

	public void setCursosGenerados(List<Cursonivel> cursosGenerados) {
		this.cursosGenerados = cursosGenerados;
	}

	public List<Cursoevaluacion> getCursoEvaluacionList() {
		return cursoEvaluacionList;
	}
	
	private int number = 10000;
	 
	public int getNumber() throws IOException {
        return number;
    }
    
    public String getNumberFormat() {
        return getDateFromMillis(number);
    }
 
    public void increment() throws IOException {
    	if(number==0){
    		finalizarEvaluacion();
    	} 
        number=number-1000;
    }
    
    public static String getDateFromMillis(long millis) {
        return new SimpleDateFormat("mm:ss:SSS").format(new Date(millis));
    }
}
