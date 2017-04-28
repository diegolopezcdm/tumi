package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Cursoevaluacionpregunta;


/**
 * @author Hector Santos
 */
public class CursoevaluacionpreguntaDAO extends AbstractDAO<Cursoevaluacionpregunta, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CursoevaluacionpreguntaDAO.class);

	public CursoevaluacionpreguntaDAO() {
		super(Cursoevaluacionpregunta.class);
	}

}
