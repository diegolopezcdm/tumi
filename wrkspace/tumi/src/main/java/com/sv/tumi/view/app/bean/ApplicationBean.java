package com.sv.tumi.view.app.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Edwin Bonilla
 */
public class ApplicationBean  implements Serializable{

	private static final long serialVersionUID = 1L; 
	private static final Logger logger = LoggerFactory.getLogger(ApplicationBean.class);
	
	public ApplicationBean(){}
	
	@PostConstruct
	public void init(){
		logger.info("saen-web iniciado...");
		
	}
}
