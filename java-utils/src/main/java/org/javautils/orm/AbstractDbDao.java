package org.javautils.orm;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDbDao {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate sampleJdbcTemplate;
	
	protected <T> T getSingleResult(Query q, Class<T> type) {
		try {
			return type.cast(q.getSingleResult());
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public void clearPersistentContext() {
		entityManager.clear();
	}
}
