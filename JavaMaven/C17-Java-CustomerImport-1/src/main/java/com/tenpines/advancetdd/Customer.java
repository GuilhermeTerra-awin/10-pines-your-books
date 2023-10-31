package com.tenpines.advancetdd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table( name = "CUSTOMERS" )
public class Customer {

	@Id
	@GeneratedValue
	private long id; 
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Pattern(regexp="D|C")
	private String identificationType;
	@NotEmpty
	private String identificationNumber;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Address> addresses;
	
	public Customer()
	{
		addresses = new HashSet<Address>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public void addAddress(Address anAddress){
		addresses.add(anAddress);
	}

	public static void importCustomers() throws IOException{
		
		FileReader reader = new FileReader("src/main/resources/input.txt");
		LineNumberReader lineReader = new LineNumberReader(reader);
		
		File file = new File("src/main/resources/hibernate.cfg.xml");
		
		Configuration configuration = new Configuration();
	    configuration.configure(file);
	    configuration.addAnnotatedClass(Customer.class);
	    configuration.addAnnotatedClass(Address.class);

	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Customer newCustomer = null;
		String line = lineReader.readLine(); 
		while (line!=null) {
			if (line.startsWith("C")){
				String[] customerData = line.split(",");
				newCustomer = new Customer();
				newCustomer.setFirstName(customerData[1]);
				newCustomer.setLastName(customerData[2]);
				newCustomer.setIdentificationType(customerData[3]);
				newCustomer.setIdentificationNumber(customerData[3]);
				session.persist(newCustomer);
			}
			else if (line.startsWith("A")) {
				String[] addressData = line.split(",");
				Address newAddress = new Address();
	
				newCustomer.addAddress(newAddress);
				newAddress.setStreetName(addressData[1]);
				newAddress.setStreetNumber(Integer.parseInt(addressData[2]));
				newAddress.setTown(addressData[3]);
				newAddress.setZipCode(Integer.parseInt(addressData[4]));
				newAddress.setProvince(addressData[3]);
			}
			
			line = lineReader.readLine();
		}
			
		session.getTransaction().commit();
		session.close();
		
		reader.close();
	}
	
	public static void main(String[] args){
		try {
			Customer.importCustomers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
