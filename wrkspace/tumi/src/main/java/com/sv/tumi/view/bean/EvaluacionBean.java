package com.sv.tumi.view.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.dao.CursoEvaluacionDAO;
import com.sv.tumi.db.dao.CursoevaluacionPreguntaRespuestaDAO;
import com.sv.tumi.db.dao.CursoevaluacionpreguntaDAO;
import com.sv.tumi.db.dao.EvaluacionDAO;
import com.sv.tumi.db.dao.PreguntasDAO;
import com.sv.tumi.db.dao.RespuestaDAO;
import com.sv.tumi.db.entity.Cursoevaluacion;
import com.sv.tumi.db.entity.Cursoevaluacionpregunta;
import com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta;
import com.sv.tumi.db.entity.Evaluacion;
import com.sv.tumi.db.entity.Pregunta;
import com.sv.tumi.db.entity.Respuesta;
import com.sv.tumi.db.entity.Tiempodesarrollo;


@ManagedBean
@ViewScoped
public class EvaluacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EvaluacionBean.class);

	private Pregunta selectedPregunta;
	private Evaluacion selectedEvaluacion;
	private Tiempodesarrollo selectedTiempo;
	private Integer preguntaIndex=0;
	List<Pregunta> preguntasEvaluacion = new ArrayList<Pregunta>();

	@PostConstruct
	public void inicializar() {
		cargaEvaluacion();
	}

	public void cargaEvaluacion() {
		EvaluacionDAO evaDao = new EvaluacionDAO();
		selectedEvaluacion = evaDao.find(1);
		CursoEvaluacionDAO cursoEvaluacionDAO = new CursoEvaluacionDAO();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("codigoEvaluacion.codigo", selectedEvaluacion.getCodigo());		
		List<Cursoevaluacion> cursoEvaluacionList = cursoEvaluacionDAO.findByProperty(filter);
		CursoevaluacionpreguntaDAO cursoevaluacionpreguntaDAO = new CursoevaluacionpreguntaDAO();
		PreguntasDAO preguntasDAO = new PreguntasDAO();
		RespuestaDAO respuestaDAO = new RespuestaDAO();
		
		filter.clear();
		for (Cursoevaluacion cursoevaluacion : cursoEvaluacionList) {	
			filter.put("codigoCursoEvaluacion.codigo", cursoevaluacion.getCodigo());		
			List<Cursoevaluacionpregunta> preguntasCursos = cursoevaluacionpreguntaDAO.findByProperty(filter);
			
			for (Cursoevaluacionpregunta preguntasCurso : preguntasCursos) {
				//preguntasEvaluacion.add(preguntasDAO.find(preguntasCurso.getCodigoPregunta()));
				preguntasEvaluacion.add(preguntasCurso.getCodigoPregunta());
			}
						
		}
		
		filter.clear();
		for (Pregunta pregunta : preguntasEvaluacion) {
			filter.put("codigoPregunta.codigo", pregunta.getCodigo());
			List<Respuesta> respuestas = respuestaDAO.findByProperty(filter);
			pregunta.setRespuestaList(respuestas);
		}
		
		System.out.println(preguntasEvaluacion);
		
		
	}
	
	public void goPreguntas() throws IOException {
		EvaluacionDAO evaDao = new EvaluacionDAO();
		selectedEvaluacion = evaDao.find(1);
		
		selectedPregunta = preguntasEvaluacion.get(preguntaIndex);
		preguntaIndex = preguntaIndex+1;
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("selectedPregunta", selectedPregunta);
		FacesContext.getCurrentInstance().getExternalContext().redirect("pregunta.xhtml");
		//return "pregunta";
	}

	public String goEmpleadoMovin() throws IOException {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		//flash.put("selectedEmpl", selectedEmpleado);
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

	public Pregunta getSelectedPregunta() {
		return selectedPregunta;
	}

	public void setSelectedPregunta(Pregunta selectedPregunta) {
		this.selectedPregunta = selectedPregunta;
	}

	public List<Pregunta> getPreguntasEvaluacion() {
		return preguntasEvaluacion;
	}

	public void setPreguntasEvaluacion(List<Pregunta> preguntasEvaluacion) {
		this.preguntasEvaluacion = preguntasEvaluacion;
	}
	
	

}
