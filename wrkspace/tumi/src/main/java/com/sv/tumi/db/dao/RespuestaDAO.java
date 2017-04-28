package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Pregunta;
import com.sv.tumi.db.entity.Respuesta;


/**
 * @author Hector Santos
 */
public class RespuestaDAO extends AbstractDAO<Respuesta, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(RespuestaDAO.class);

	public RespuestaDAO() {
		super(Respuesta.class);
	}

}
