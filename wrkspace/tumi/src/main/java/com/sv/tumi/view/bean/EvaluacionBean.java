package com.sv.tumi.view.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.faces.util.CollectionsUtils;
import com.sun.org.glassfish.gmbal.ManagedAttribute;
import com.sv.tumi.db.dao.CursoCapacitacionDAO;
import com.sv.tumi.db.dao.CursoEvaluacionDAO;
import com.sv.tumi.db.dao.CursoNivelDAO;
import com.sv.tumi.db.dao.CursoevaluacionPreguntaRespuestaDAO;
import com.sv.tumi.db.dao.CursoevaluacionpreguntaDAO;
import com.sv.tumi.db.dao.EstadoDAO;
import com.sv.tumi.db.dao.EvaluacionDAO;
import com.sv.tumi.db.dao.NivelDAO;
import com.sv.tumi.db.dao.ParametroGeneracionCapacitacionDAO;
import com.sv.tumi.db.dao.PersonalCapacitacionDAO;
import com.sv.tumi.db.dao.RespuestaDAO;
import com.sv.tumi.db.dao.ResultadoEvaluacionDAO;
import com.sv.tumi.db.dao.SolicitudCapacitacionDAO;
import com.sv.tumi.db.dao.SubtemaDAO;
import com.sv.tumi.db.dao.TemaDAO;
import com.sv.tumi.db.entity.Curso;
import com.sv.tumi.db.entity.Cursocapacitacion;
import com.sv.tumi.db.entity.Cursoevaluacion;
import com.sv.tumi.db.entity.Cursoevaluacionpregunta;
import com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta;
import com.sv.tumi.db.entity.Cursonivel;
import com.sv.tumi.db.entity.Estado;
import com.sv.tumi.db.entity.Evaluacion;
import com.sv.tumi.db.entity.Nivel;
import com.sv.tumi.db.entity.ParametroGeneracionCapacitacion;
import com.sv.tumi.db.entity.Personal;
import com.sv.tumi.db.entity.PersonalCapacitacion;
import com.sv.tumi.db.entity.Pregunta;
import com.sv.tumi.db.entity.Respuesta;
import com.sv.tumi.db.entity.Resultadoevaluacion;
import com.sv.tumi.db.entity.Solicitudcapacitacion;
import com.sv.tumi.db.entity.Subtema;
import com.sv.tumi.db.entity.Tema;
import com.sv.tumi.db.entity.Tiempodesarrollo;
import com.sv.tumi.neuroph.TumiMultiLayerPerceptron;
import com.sv.tumi.neuroph.util.NormalizacionUtil;
import com.sv.tumi.view.CursoView;
import com.sv.tumi.view.SubtemaView;
import com.sv.tumi.view.TemaView;

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
	private int number = 10000;

	List<Cursoevaluacion> cursoEvaluacionList = new ArrayList<Cursoevaluacion>();
	List<Cursocapacitacion> cursoCapacitacion = new ArrayList<Cursocapacitacion>();
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
	PersonalCapacitacionDAO personalCapacitacionDAO = new PersonalCapacitacionDAO();
	ParametroGeneracionCapacitacionDAO parametroGeneracionCapacitacionDAO = new ParametroGeneracionCapacitacionDAO();
	NivelDAO nivelDAO = new NivelDAO();
	CursoNivelDAO cursoNivelDAO = new CursoNivelDAO();
	CursoCapacitacionDAO cursoCapacitacionDAO = new CursoCapacitacionDAO();
	TemaDAO temaDAO = new TemaDAO();
	SubtemaDAO subtemaDAO = new SubtemaDAO();
	SolicitudCapacitacionDAO solicitudCapacitacionDAO = new SolicitudCapacitacionDAO();
	List<CursoView> cursosTemario = new ArrayList<CursoView>();
	List<CursoView> cursosGeneradosCapacitacion = new ArrayList<CursoView>();
	
	private static Integer EVALUACION_ID = 1;

	@PostConstruct
	public void inicializar() {
		cargaEvaluacion();
	}

	public void cargaEvaluacion() {
		filter.put("codigo", EVALUACION_ID);
		filter.put("codigoEstado.codigo", 3);//registrado
		List<Evaluacion> evaluaciones = evaluacionDAO.findByProperty(filter);
		
		if(evaluaciones!=null && evaluaciones.size()==1){
			selectedEvaluacion = evaluaciones.get(0);
			
			if(selectedEvaluacion.getCodigoPersonalCapacitacion().getCodigoEstado().getCodigo() == 1){//aprobado
				
				selectedEvaluacion.setFechaInicio(new Date());
				number= selectedEvaluacion.getMinutosDuracion();
				
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
				
				cursosTemario = new ArrayList<CursoView>();
				getCursosEvaluacion(cursosTemario, true);
				
			} else {
				selectedEvaluacion = null;
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

		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {

			Double puntajeObtenido = 0.0;
			Double maximoPuntaje = 0.0;
			Double porcentajeAprobacion = 0.0;
			for (Cursoevaluacionpregunta cursoevaluacionpregunta : preguntasEvaluacion) {

				if (cursoevaluacionpregunta.getCodigoCursoEvaluacion()
						.getCodigo().compareTo(cursoevaluacion.getCodigo()) != 0) {
					continue;
				}

				maximoPuntaje = maximoPuntaje
						+ cursoevaluacionpregunta.getCodigoPregunta()
								.getValor();

				if (cursoevaluacionpregunta.getPuntajeObtenido() != null
						&& cursoevaluacionpregunta.getPuntajeObtenido()
								.compareTo(new Double(0.0)) != 0) {
					puntajeObtenido = puntajeObtenido
							+ cursoevaluacionpregunta.getPuntajeObtenido();
				}
				cursoevaluacionpreguntaDAO.edit(cursoevaluacionpregunta);
			}

			System.out.println(puntajeObtenido);
			System.out.println(maximoPuntaje);
			porcentajeAprobacion = (puntajeObtenido * 100) / maximoPuntaje;

			Nivel bajo = nivelDAO.find(1);
			Nivel intermedio = nivelDAO.find(2);
			Nivel alto = nivelDAO.find(3);

			for (Cursoevaluacionpreguntarespuesta cursoevaluacionpreguntarespuesta : cursoevaluacionpreguntarespuestas) {
				
				if(cursoevaluacionpreguntarespuesta.getCodigoCursoEvaluacionPregunta().getCodigoCursoEvaluacion().getCodigo().
						compareTo(cursoevaluacion.getCodigo())!=0)
					continue;
				
				cursoevaluacionPreguntaRespuestaDAO
						.create(cursoevaluacionpreguntarespuesta);
			}

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

		Estado estadoEvaluacion = estadoDAO.find(4);
		selectedEvaluacion.setCodigoEstado(estadoEvaluacion);
		selectedEvaluacion.setFechaFin(new Date());
		selectedEvaluacion.setFechaModificacion(new Date());
		selectedEvaluacion.setUsuarioModificacion("1");
		evaluacionDAO.edit(selectedEvaluacion);
		
		Estado estadoPersonalCapacitacion = estadoDAO.find(6);
		PersonalCapacitacion personalCapacitacion =selectedEvaluacion.getCodigoPersonalCapacitacion();
		personalCapacitacion.setFechaModificacion(new Date());
		personalCapacitacion.setUsuarioModificacion("1");
		personalCapacitacion.setCodigoEstado(estadoPersonalCapacitacion);
		personalCapacitacionDAO.edit(personalCapacitacion);

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("evaluacionFinal.xhtml");
	}
	
	private PersonalCapacitacion personalCapacitacion;
	
	public PersonalCapacitacion getPersonalCapacitacion() {
		return personalCapacitacion;
	}
	
	public void setPersonalCapacitacion(
			PersonalCapacitacion personalCapacitacion) {
		this.personalCapacitacion = personalCapacitacion;
	}
	
	public String gogenerarcapacitacion() {
		personalCapacitacion = personalCapacitacionDAO.find(1);
		
		//if(cursoEvaluacionList.isEmpty()){
			filter.clear();
			filter.put("codigoEvaluacion.codigo", EVALUACION_ID);
			cursoEvaluacionList = cursoEvaluacionDAO.findByProperty(filter);
			
		//}
		
		if (personalCapacitacion.getCodigoEstado().getCodigo() == 6
				|| personalCapacitacion.getCodigoEstado().getCodigo() == 5) {
			cursosTemario = new ArrayList<CursoView>();
			getCursosEvaluacion(cursosTemario, false);
		}
				
		
		return "/app/generarCapacitacion";
	}

	private void getCursosEvaluacion(List<CursoView> cursos, boolean evaluacionInicial) {
		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {

			Curso curso = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema().getCodigoTema().getCodigoCurso();
			Tema tema = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema().getCodigoTema();
			Subtema subtema = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema();
			
			String nivel = null;
			Double puntaje= null;
			
			if(evaluacionInicial){
				nivel = cursoevaluacion.getCodigoCursoNivel()
				.getCodigoNivel().getNombre();
				puntaje=0.0;
			} else {
				
				if(cursoevaluacion.getResultadoevaluacion()!=null){
					nivel = cursoevaluacion.getResultadoevaluacion().getCodigoNivel().getNombre();
					puntaje=cursoevaluacion
							.getResultadoevaluacion().getPuntaje();
				} else {
					nivel = "";
					puntaje=0.0;
				}
			
			}

			getCurso(cursos, curso, tema, subtema, nivel, puntaje);

		}

	}

	private void getCursosCapacitacion(List<CursoView> cursos) {
		for (Cursocapacitacion cursoevaluacion : cursoCapacitacion) {

			Curso curso = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema().getCodigoTema().getCodigoCurso();
			Tema tema = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema().getCodigoTema();
			Subtema subtema = cursoevaluacion.getCodigoCursoNivel()
					.getCodigoSubtema();

			getCurso(cursos, curso, tema, subtema, cursoevaluacion.getCodigoCursoNivel()
					.getCodigoNivel().getNombre(), 0.0);

		}

	}

	private void getCurso(List<CursoView> cursos, Curso curso, Tema tema, Subtema subtema, String nombreNivel, double puntaje) {
		CursoView cursoView = null;
		TemaView temaView = null;
		SubtemaView subtemaView = null;
		
		//CURSO
		
		for (CursoView cursoViewItem : cursos) {
			
			if(curso.getNombre().equalsIgnoreCase(cursoViewItem.getNombre())){
				cursoView = cursoViewItem;
			}
			
		}
		
		if(cursoView == null){
			cursoView = new CursoView();
			cursoView.setNombre(curso.getNombre());
			cursoView.setAlcance(curso.getAlcance());
			cursos.add(cursoView);
		}
		
		//TEMA
		
		for (TemaView temaViewItem : cursoView.getTema()) {
			
			if(tema.getNombre().equalsIgnoreCase(temaViewItem.getNombre())){
				temaView = temaViewItem;
			}
			
		}
		
		if(temaView == null){
			temaView = new TemaView();
			temaView.setNombre(tema.getNombre());
			temaView.setAlcance(tema.getAlcance());
			temaView.setOrden(tema.getOrden());
			cursoView.getTema().add(temaView);
		}
		
		//SUB TEMA
		
		for (SubtemaView subtemaViewItem : temaView.getSubtemas()) {
			
			if(subtema.getNombre().equalsIgnoreCase(subtemaViewItem.getNombre()) && 
					nombreNivel.equalsIgnoreCase(subtemaViewItem.getNivel())){
				subtemaView = subtemaViewItem;
			}
			
		}
		
		if(subtemaView == null){
			subtemaView = new SubtemaView();
			subtemaView.setNombre(subtema.getNombre());
			subtemaView.setNivel(nombreNivel);
			subtemaView.setOrder(subtema.getOrden());
			subtemaView.setPorcentajeAprobacion(puntaje);
			temaView.getSubtemas().add(subtemaView);
		}
	}

	public void generarCapacitacion() throws IOException {
		cursosGenerados = new ArrayList<Cursonivel>();
		
		Personal personal = personalCapacitacion.getCodigoPersonal();
		int nivelDesempeñoId = personal.getCodigoNivelDesempeño().getCodigo();
		int nivelPersonalId = personal.getCodigoNivelPersonal().getCodigo();

		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {

			filter.clear();
			filter.put("codigoCursoEvaluacion.codigo",
					cursoevaluacion.getCodigo());
			Resultadoevaluacion resultadoEvalacion = resultadoEvaluacionDAO
					.findByProperty(filter).get(0);

			System.out.println("cargo "
					+ personalCapacitacion.getCodigoPersonal().getCodigoCargo()
							.getCodigo());

			System.out.println("sub tema "
					+ cursoevaluacion.getCodigoCursoNivel().getCodigoSubtema()
							.getCodigo());

			System.out.println("resultado nivel (nivel actual) "
					+ resultadoEvalacion.getCodigoNivel().getCodigo());

			System.out.println("nivel depemseño " + nivelDesempeñoId);

			System.out.println("nivel personal " + nivelPersonalId);

			TumiMultiLayerPerceptron rn = new TumiMultiLayerPerceptron();
			//usar red neuronal. input cargo, area, curso, nivel actual. output nivel selecionado
			double[] nivelSelecionadoArray = rn.getPrediction(NormalizacionUtil
					.getCargoValue(personalCapacitacion.getCodigoPersonal()
							.getCodigoCargo().getCodigo()), NormalizacionUtil
					.getSubtemaValue(cursoevaluacion.getCodigoCursoNivel()
							.getCodigoSubtema().getCodigo()), NormalizacionUtil
					.getNivelValue(resultadoEvalacion.getCodigoNivel()
							.getCodigo()), NormalizacionUtil.getNivelDesempeñoValue(nivelDesempeñoId),
							NormalizacionUtil.getNivelPersonalValue(nivelPersonalId));
			
			int nivelSelecionadoId = getNivelId(nivelSelecionadoArray);
			System.out.println("nivel selecionado "+ nivelSelecionadoId);
			
			//for (int i = nivelSelecionadoId; i <= cursoevaluacion.getCodigoCursoNivel()
				//	.getCodigoNivel().getCodigo(); i++) {
				
				filter.clear();
				filter.put("codigoNivel.codigo", nivelSelecionadoId);
				filter.put("codigoSubtema.codigo", cursoevaluacion
						.getCodigoCursoNivel().getCodigoSubtema().getCodigo());
				
				Cursonivel cursonivelSeleccionado = cursoNivelDAO.findByProperty(
						filter).get(0);

				Cursocapacitacion cursocapacitacion = new Cursocapacitacion();
				cursocapacitacion.setCodigoPersonalCapacitacion(personalCapacitacion);
				cursocapacitacion.setCodigoCursoNivel(cursonivelSeleccionado);
				cursocapacitacion.setFechaRegistro(new Date());
				cursocapacitacion.setUsuarioRegistro("1");
				cursoCapacitacionDAO.create(cursocapacitacion);
				cursoCapacitacion.add(cursocapacitacion);
				
			//}
			
		}

		Estado estado = estadoDAO.find(5);//generado caacitacion
		personalCapacitacion.setCodigoEstado(estado);
		personalCapacitacionDAO.edit(personalCapacitacion);
		
		getCursosCapacitacion(cursosGeneradosCapacitacion);
		//return "/app/generarCapacitacion?faces-redirect=true";
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("/tumi/app/generarCapacitacion.xhtml");

	}

	private int getNivelId(double[] nivelSelecionado) {
		int maxAt = 0;

		for (int i = 0; i < nivelSelecionado.length; i++) {
		    maxAt = nivelSelecionado[i] > nivelSelecionado[maxAt] ? i : maxAt;
		}
		
		return maxAt+1;
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

	public int getNumber() throws IOException {
		return number;
	}

	public String getNumberFormat() {
		return getDateFromMillis(number);
	}

	public void increment() throws IOException {
		if (number == 0) {
			finalizarEvaluacion();
		}
		number = number - 1000;
	}

	public List<CursoView> getCursosTemario() {
		return cursosTemario;
	}

	public void setCursosTemario(List<CursoView> cursosTemario) {
		this.cursosTemario = cursosTemario;
	}

	public List<CursoView> getCursosGeneradosCapacitacion() {
		return cursosGeneradosCapacitacion;
	}

	public void setCursosGeneradosCapacitacion(
			List<CursoView> cursosGeneradosCapacitacion) {
		this.cursosGeneradosCapacitacion = cursosGeneradosCapacitacion;
	}

	public static String getDateFromMillis(long millis) {
		return new SimpleDateFormat("mm:ss").format(new Date(millis));
	}
}
