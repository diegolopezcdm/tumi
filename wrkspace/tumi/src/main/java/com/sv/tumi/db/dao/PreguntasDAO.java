package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Pregunta;


/**
 * @author Hector Santos
 */
public class PreguntasDAO extends AbstractDAO<Pregunta, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PreguntasDAO.class);

	public PreguntasDAO() {
		super(Pregunta.class);
	}

}
