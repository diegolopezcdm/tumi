package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.PersonalCapacitacion;


/**
 * @author Hector Santos
 */
public class PersonalCapacitacionDAO extends AbstractDAO<PersonalCapacitacion, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PersonalCapacitacionDAO.class);

	public PersonalCapacitacionDAO() {
		super(PersonalCapacitacion.class);
	}

}
