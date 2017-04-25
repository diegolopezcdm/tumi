package com.sv.tumi.db.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hector Santos
 */
public class SessionFactoryTumi {

	private static Logger logger = LoggerFactory.getLogger(SessionFactoryTumi.class);

	static SessionFactory sessionAnnotationFactory;
	
	static SessionFactory buildSessionAnnotationFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			return sessionFactory;		
		} catch (Throwable ex) {
			logger.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionAnnotationFactory() {
		if (sessionAnnotationFactory == null)
			sessionAnnotationFactory = buildSessionAnnotationFactory();
		return sessionAnnotationFactory;
	}
}
