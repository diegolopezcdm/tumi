package com.sv.tumi.db.dao;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.entity.Solicitudcapacitacion;


/**
 * @author Hector Santos
 */
public class SolicitudCapacitacionDAO extends AbstractDAO<Solicitudcapacitacion, Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SolicitudCapacitacionDAO.class);

	public SolicitudCapacitacionDAO() {
		super(Solicitudcapacitacion.class);
	}

}
