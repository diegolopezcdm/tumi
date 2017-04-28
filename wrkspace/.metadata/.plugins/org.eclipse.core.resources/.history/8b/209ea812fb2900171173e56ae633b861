package com.sv.tumi.view.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.dao.EvaluacionDAO;
import com.sv.tumi.db.entity.Evaluacion;
import com.sv.tumi.db.entity.Tiempodesarrollo;


@ManagedBean
@ViewScoped
public class EvaluacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EvaluacionBean.class);

	private Evaluacion selectedEvaluacion;
	private Tiempodesarrollo selectedTiempo;

	@PostConstruct
	public void inicializar() {
		cargaEvaluacion();
	}

	public void cargaEvaluacion() {
		EvaluacionDAO evaDao = new EvaluacionDAO();
		selectedEvaluacion = evaDao.find(1);
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

}
