package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Subtema;


/**
 * @author Hector Santos
 */
public class SubtemaDAO extends AbstractDAO<Subtema, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SubtemaDAO.class);

	public SubtemaDAO() {
		super(Subtema.class);
	}

}
