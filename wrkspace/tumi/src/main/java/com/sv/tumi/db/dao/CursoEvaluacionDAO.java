package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Cursoevaluacion;
import com.sv.tumi.db.entity.Evaluacion;


/**
 * @author Hector Santos
 */
public class CursoEvaluacionDAO extends AbstractDAO<Cursoevaluacion, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CursoEvaluacionDAO.class);

	public CursoEvaluacionDAO() {
		super(Cursoevaluacion.class);
	}

}
