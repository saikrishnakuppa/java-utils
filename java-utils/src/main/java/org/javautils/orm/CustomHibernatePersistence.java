package org.javautils.orm;

import java.util.Map;

import org.hibernate.ejb.AvailableSettings;
import org.hibernate.ejb.util.PersistenceUtilHelper;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

public class CustomHibernatePersistence extends AvailableSettings implements PersistenceProvider {
	private final PersistenceUtilHelper.MetadataCache cache = new PersistenceUtilHelper.MetadataCache();
	
	
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(
			PersistenceUnitInfo arg0, Map arg1) {
		CustomEjb3Configuration cfg = new CustomEjb3Configuration();
		
		return null;
	}

	@Override
	public EntityManagerFactory createEntityManagerFactory(String arg0, Map arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateSchema(PersistenceUnitInfo arg0, Map arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean generateSchema(String arg0, Map arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProviderUtil getProviderUtil() {
		// TODO Auto-generated method stub
		return null;
	}

}
