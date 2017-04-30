package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Cursonivel;
import com.sv.tumi.db.entity.Nivel;


/**
 * @author Hector Santos
 */
public class CursoNivelDAO extends AbstractDAO<Cursonivel, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CursoNivelDAO.class);

	public CursoNivelDAO() {
		super(Cursonivel.class);
	}

}
