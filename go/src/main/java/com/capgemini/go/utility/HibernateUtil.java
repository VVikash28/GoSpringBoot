package com.capgemini.go.utility;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.capgemini.go.dto.OrderDTO;
import com.capgemini.go.dto.OrderProductMapDTO;
import com.capgemini.go.dto.ProductDTO;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.dto.ViewDetailedSalesReportByProductDTO;
import com.capgemini.go.dto.ViewSalesReportByUserDTO;
//import com.capgemini.go.entity.AddressEntity;
//import com.capgemini.go.entity.CartItemEntity;
//import com.capgemini.go.entity.OrderCancelEntity;
//import com.capgemini.go.entity.OrderEntity;
//import com.capgemini.go.entity.OrderReturnEntity;
//import com.capgemini.go.entity.ProductEntity;
//import com.capgemini.go.entity.ProductUinMapEntity;
//import com.capgemini.go.entity.WishlistEntity;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://remotemysql.com:3306/iMIuEwHYH8");
				settings.put(Environment.USER, "iMIuEwHYH8");
				settings.put(Environment.PASS, "QXFKvh2Ni9");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				configuration.setProperties(settings);
				// Add all classes that need to be mapped here
				configuration.addAnnotatedClass(RetailerInventoryDTO.class);
//                configuration.addAnnotatedClass(AddressEntity.class);
//                configuration.addAnnotatedClass(WishlistEntity.class);
//                configuration.addAnnotatedClass(OrderCancelEntity.class);
//                configuration.addAnnotatedClass(OrderReturnEntity.class);
//                configuration.addAnnotatedClass(OrderEntity.class);
//                configuration.addAnnotatedClass(CartItemEntity.class);
//                configuration.addAnnotatedClass(ProductEntity.class);
//                configuration.addAnnotatedClass(ProductUinMapEntity.class);
				// end of this
				configuration.addAnnotatedClass(ProductDTO.class);
				configuration.addAnnotatedClass(OrderDTO.class);
				configuration.addAnnotatedClass(OrderProductMapDTO.class);
				configuration.addAnnotatedClass(ViewDetailedSalesReportByProductDTO.class);
				configuration.addAnnotatedClass(ViewSalesReportByUserDTO.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
