package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.hibernate.Session;


public class CustomerImporter {

    private Reader reader;
    private Session session;

    public CustomerImporter(Reader reader, Session session) {
        this.reader = reader;
        this.session = session;
    }

    public void invoke() throws IOException {
        LineNumberReader lineReader = new LineNumberReader(reader);

        Customer newCustomer = null;
        String line = lineReader.readLine();
        while (line != null) {
            if (line.startsWith("C")) {
                String[] customerData = line.split(",");
                newCustomer = new Customer();
                newCustomer.setFirstName(customerData[1]);
                newCustomer.setLastName(customerData[2]);
                newCustomer.setIdentificationType(customerData[3]);
                newCustomer.setIdentificationNumber(customerData[3]);
                session.persist(newCustomer);
            } else if (line.startsWith("A")) {
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

        reader.close();
    }
}
