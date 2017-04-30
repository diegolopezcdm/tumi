package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Tema;


/**
 * @author Hector Santos
 */
public class TemaDAO extends AbstractDAO<Tema, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(TemaDAO.class);

	public TemaDAO() {
		super(Tema.class);
	}

}
