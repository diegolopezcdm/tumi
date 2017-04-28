package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Cursoevaluacionpreguntarespuesta;


/**
 * @author Hector Santos
 */
public class CursoevaluacionPreguntaRespuestaDAO extends AbstractDAO<Cursoevaluacionpreguntarespuesta, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CursoevaluacionPreguntaRespuestaDAO.class);

	public CursoevaluacionPreguntaRespuestaDAO() {
		super(Cursoevaluacionpreguntarespuesta.class);
	}

}
