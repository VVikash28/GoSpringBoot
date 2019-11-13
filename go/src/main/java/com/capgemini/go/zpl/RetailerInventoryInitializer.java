package com.capgemini.go.zpl;

import com.capgemini.go.dao.RetailerInventoryDaoImpl;
import com.capgemini.go.utility.DummyDataGenerator;
import com.capgemini.go.utility.HibernateUtil;

public class RetailerInventoryInitializer {
		
	public static void main (String args []) {
		DummyDataGenerator.retailerInventoryDao = new RetailerInventoryDaoImpl ();
		((RetailerInventoryDaoImpl) DummyDataGenerator.retailerInventoryDao).setSessionFactory(HibernateUtil.getSessionFactory());
		DummyDataGenerator.initializeRetailerInventory(600);
	}
}
