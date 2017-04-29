package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Nivel;


/**
 * @author Hector Santos
 */
public class NivelDAO extends AbstractDAO<Nivel, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(NivelDAO.class);

	public NivelDAO() {
		super(Nivel.class);
	}

}
