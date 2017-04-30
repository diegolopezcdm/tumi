package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.ParametroGeneracionCapacitacion;


/**
 * @author Hector Santos
 */
public class ParametroGeneracionCapacitacionDAO extends AbstractDAO<ParametroGeneracionCapacitacion, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ParametroGeneracionCapacitacionDAO.class);

	public ParametroGeneracionCapacitacionDAO() {
		super(ParametroGeneracionCapacitacion.class);
	}

}
