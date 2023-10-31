package com.tenpines.advancetdd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Map;
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



	public static void main(String[] args){
//		try {
//			//Customer.importCustomers();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public Set<Address> getAddresses() {
		return addresses;
	}
}
