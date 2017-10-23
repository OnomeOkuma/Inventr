/*
 * This class initialises and hold the SessionFactory used to create Sessions.
 * This was done because of how expensive it is, resources-wise, to create a SessionFactory.  
 */
package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import models.CurrentProductList;
import models.Product;
import models.ProductPurchased;
import models.ProductSold;


public class DataAccess {

	private SessionFactory sessionFactory;
	
	public DataAccess(){
		
		StandardServiceRegistry serviceBuilder = new StandardServiceRegistryBuilder()
														.configure(this.getClass().getResource("hibernate.cfg.xml"))
														.build();
		
		Metadata metadata = new MetadataSources(serviceBuilder)
							.addAnnotatedClass(ProductPurchased.class)
							.addAnnotatedClass(ProductSold.class)
							.addAnnotatedClass(Product.class)
							.addAnnotatedClass(CurrentProductList.class)
							.getMetadataBuilder()
							.build();
		
		this.sessionFactory = metadata.getSessionFactoryBuilder()
							  	.build();
		
	}

	public Session getSession() {
		return this.sessionFactory.openSession();
	}
	
	public void close(){
		this.sessionFactory.close();
	}
	
}
