package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Resultadoevaluacion;


/**
 * @author Hector Santos
 */
public class ResultadoEvaluacionDAO extends AbstractDAO<Resultadoevaluacion, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ResultadoEvaluacionDAO.class);

	public ResultadoEvaluacionDAO() {
		super(Resultadoevaluacion.class);
	}

}
