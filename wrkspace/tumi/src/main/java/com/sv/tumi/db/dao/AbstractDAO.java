package com.sv.tumi.db.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sv.tumi.db.connection.SessionFactoryTumi;

/**
 * @author Hector Santos
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDAO<T, I extends java.io.Serializable> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

	private Class<T> entityClass;

	public AbstractDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public AbstractDAO() {
	}

	public void create(T entity) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			session.save(entity);
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al guardar registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
	}

	public void edit(T entity) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			session.merge(entity);
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al modificar registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
	}

	public void remove(Object id) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			session.delete(find(id));
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al eliminar registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
	}

	public T find(Object id) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		T entity = null;
		try {
			session.getTransaction().begin();
			entity = (T) session.get(entityClass, (I) id);
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al obtener registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
		return entity;
	}

	// matchMode the match mode: EXACT, START, END, ANYWHERE.
	public List<T> findByProperty(String propertyName, String value, MatchMode matchMode) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		List<T> list = null;
		try {
			Criteria criteria = session.createCriteria(entityClass);
			if (matchMode != null) {
				criteria.add(Restrictions.ilike(propertyName, value, matchMode));
			} else {
				criteria.add(Restrictions.ilike(propertyName, value, MatchMode.EXACT));
			}
			session.getTransaction().begin();
			list = criteria.list();
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al obtener registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
		return list;
	}

	public List<T> findByProperty(Map<String, Object> properties) {
		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
		Session session = sf.openSession();
		List<T> list = null;
		try {
			Criteria criteria = session.createCriteria(entityClass);
			Iterator<Entry<String, Object>> ite = properties.entrySet().iterator();
			while (ite.hasNext()) {
				Map.Entry<String, Object> e = (Map.Entry<String, Object>) ite.next();
				criteria.add(Restrictions.eq(e.getKey(), e.getValue()));
			}
			session.getTransaction().begin();
			list = criteria.list();
			session.getTransaction().commit();
		} catch (Throwable ex) {
			logger.error("Error al obtener registro : ", ex);
			if (session != null && session.isOpen()) {
				if (session.getTransaction().isActive()) {
					try {
						session.getTransaction().rollback();
					} catch (HibernateException he) {
						logger.error("Error al realizar rollback a la base de datos : ", he);
					}
				}
			}
		} finally {
			session.flush();
			session.clear();
			session.close();
		}
		return list;
	}

//	public List<T> findAll(Map<String, Object> filters, 
//			               String sortField, String order, 
//			               Integer firstResult, Integer maxResult) {
//		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
//		ClassMetadata entityMetaData = sf.getClassMetadata(entityClass);
//		Session session = sf.openSession();
//		List<T> list = null;
//		try {
//			Criteria criteria = null;
//			if (filters != null) {
//				Iterator<Entry<String, Object>> ite = filters.entrySet().iterator();
//				while (ite.hasNext()) {
//					Map.Entry<String, Object> e = (Map.Entry<String, Object>) ite.next();
//					String key = e.getKey();
//					String value = e.getValue().toString();
//					if (key.contains(".")) {
//						List<String> values = new ArrayList<String>();
//						StringTokenizer str = new StringTokenizer(key.toString(), ".");
//						while (str.hasMoreTokens()) {
//							values.add(str.nextToken());
//						}
//						String alias = "a" + Math.round((Math.random() * 50));
//						StringBuilder association = new StringBuilder();
//						for (int i = 0; i < values.size() - 1; i++) {
//							association.append(values.get(i)).append(".");
//						}
//						criteria = session.createCriteria(entityClass)
//					               .createAlias(association.toString()
//					            		        .substring(0,association.length()-1), alias);
//						key = alias + "." + values.get(values.size() - 1);
//						sortField = values.get(0);
//					}
//					if(criteria == null) criteria = session.createCriteria(entityClass);
//					if (value.matches("[a-zA-Z ]+")) {
//						criteria.add(Restrictions.ilike(key, value, MatchMode.ANYWHERE));
//					} else {
//						criteria.add(Restrictions.eq(key, e.getValue()));
//					}
//				}
//			}
//			criteria = (criteria == null) ?  session.createCriteria(entityClass) : criteria;
//			criteria = (firstResult == null) ? criteria : criteria.setFirstResult(firstResult);
//			criteria = (maxResult == null) ? criteria : criteria.setMaxResults(maxResult);
//			if (sortField == null) sortField = entityMetaData.getIdentifierPropertyName();
//			if(sortField.contains(".")) sortField = sortField.substring(0, sortField.indexOf("."));
//			if (Order.isAscOrder(order)) {
//				criteria.addOrder(org.hibernate.criterion.Order.asc(sortField));
//			} else {
//				criteria.addOrder(org.hibernate.criterion.Order.desc(sortField));
//			}
//			session.getTransaction().begin();
//			list = criteria.list();
//			session.getTransaction().commit();
//		} catch (Throwable ex) {
//			logger.error("Error al obtener registros : ", ex);
//			if (session != null && session.isOpen()) {
//				if (session.getTransaction().isActive()) {
//					try {
//						session.getTransaction().rollback();
//					} catch (HibernateException he) {
//						logger.error("Error al realizar rollback a la base de datos : ", he);
//					}
//				}
//			}
//		} finally {
//			session.flush();
//			session.clear();
//			session.close();
//		}
//		return list;
//	}
//
//	public Integer count() {
//		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
//		Session session = sf.openSession();
//		Integer count = null;
//		try {
//			Criteria criteria = session.createCriteria(entityClass);
//			criteria.setProjection(Projections.rowCount());
//			session.getTransaction().begin();
//			count = ((Long) criteria.uniqueResult()).intValue();
//			session.getTransaction().commit();
//		} catch (Throwable ex) {
//			logger.error("Error al contar los registros : ", ex);
//			if (session != null && session.isOpen()) {
//				if (session.getTransaction().isActive()) {
//					try {
//						session.getTransaction().rollback();
//					} catch (HibernateException he) {
//						logger.error("Error al realizar rollback a la base de datos : ", he);
//					}
//				}
//			}
//		} finally {
//			session.flush();
//			session.clear();
//			session.close();
//		}
//		return count;
//	}
//
//	public Integer findCountAll(Map<String, Object> filters, String sortField, String order) {
//		SessionFactory sf = SessionFactoryTumi.getSessionAnnotationFactory();
//		ClassMetadata entityMetaData = sf.getClassMetadata(entityClass);
//		Session session = sf.openSession();
//		Integer list = null;
//		try {
//			Criteria criteria = session.createCriteria(entityClass);
//			criteria.setProjection(Projections.rowCount());
//			if (filters != null) {
//				Iterator<Entry<String, Object>> ite = filters.entrySet().iterator();
//				while (ite.hasNext()) {
//					Map.Entry<String, Object> e = (Map.Entry<String, Object>) ite.next();
//					String key = e.getKey();
//					String value = e.getValue().toString();
//					if (key.contains(".")) {
//						List<String> values = new ArrayList<String>();
//						StringTokenizer str = new StringTokenizer(key.toString(), ".");
//						while (str.hasMoreTokens()) {
//							values.add(str.nextToken());
//						}
//						String alias = "a" + Math.round((Math.random() * 50));
//						StringBuilder association = new StringBuilder();
//						for (int i = 0; i < values.size() - 1; i++) {
//							association.append(values.get(i)).append(".");
//						}
//						criteria = session.createCriteria(entityClass)
//					               .createAlias(association.toString()
//					            		        .substring(0,association.length()-1), alias);
//						key = alias + "." + values.get(values.size() - 1);
//						sortField = values.get(0);
//					}
//					if(criteria == null) criteria = session.createCriteria(entityClass);
//					if (value.matches("[a-zA-Z ]+")) {
//						criteria.add(Restrictions.ilike(key, value, MatchMode.ANYWHERE));
//					} else {
//						criteria.add(Restrictions.eq(key, e.getValue()));
//					}
//				}
//			}
//			criteria = (criteria == null) ?  session.createCriteria(entityClass) : criteria;
//			if (sortField == null) sortField = entityMetaData.getIdentifierPropertyName();
//			if(sortField.contains(".")) sortField = sortField.substring(0, sortField.indexOf("."));
//			if (Order.isAscOrder(order)) {
//				criteria.addOrder(org.hibernate.criterion.Order.asc(sortField));
//			} else {
//				criteria.addOrder(org.hibernate.criterion.Order.desc(sortField));
//			}
//			criteria.setProjection(Projections.rowCount());
//			session.getTransaction().begin();
//			list = ((Long) criteria.uniqueResult()).intValue();
//			session.getTransaction().commit();
//		} catch (Throwable ex) {
//			logger.error("Error al obtener registros : ", ex);
//			if (session != null && session.isOpen()) {
//				if (session.getTransaction().isActive()) {
//					try {
//						session.getTransaction().rollback();
//					} catch (HibernateException he) {
//						logger.error("Error al realizar rollback a la base de datos : ", he);
//					}
//				}
//			}
//		} finally {
//			session.flush();
//			session.clear();
//			session.close();
//		}
//		return list;
//	}
	
}
