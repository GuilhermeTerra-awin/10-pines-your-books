import static org.junit.jupiter.api.Assertions.*;

import com.tenpines.advancetdd.Address;
import com.tenpines.advancetdd.Customer;
import com.tenpines.advancetdd.CustomerImporter;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CustomerImportTest {

    private Session session;

    @AfterEach
    public void TearDown() {
        session.getTransaction().commit();
        session.close();
    }

    @BeforeEach
    public void SetUp() {
        File file = new File("src/main/resources/hibernate.cfg.xml");

        Configuration configuration = new Configuration();
        configuration.configure(file);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Address.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void TestVerifyOneCustomerWasImported(){


        assertDoesNotThrow(() -> importCustomers(new StringReader(
            "C,Pepe,Sanchez,D,22333444\n" +
            "A,San Martin,3322,Olivos,1636,BsAs\n" +
            "A,Maipu,888,Florida,1122,Buenos Aires\n" +
            "C,Juan,Perez,C,23-25666777-9\n" +
            "A,Alem,1122,CABA,1001,CABA"), session));

        String id = "22333444";
        String d = "D";

        var customer = getCustomer(id, d);

        assertTrue(customer.isPresent());
        assertEquals("Pepe", customer.get().getFirstName());
        assertEquals("Sanchez", customer.get().getLastName());
    }

    @Test
    public void TestVerifyOneCustomerHasCorrectAddress() {


        assertDoesNotThrow(() -> importCustomers(new StringReader(
            "C,Pepe,Sanchez,D,22333444\n" +
                "A,San Martin,3322,Olivos,1636,BsAs\n" +
                "A,Maipu,888,Florida,1122,Buenos Aires\n" +
                "C,Juan,Perez,C,23-25666777-9\n" +
                "A,Alem,1122,CABA,1001,CABA"), session));

        var customer = getCustomer("23-25666777-9", "C");

        assertCostumerHasAddress(customer, "Alem", 1122, "CABA", 1001, "CABA");
    }

    private void assertCostumerHasAddress(Customer customer, String street, int number, String city, int zipcode, String province) {
        var address = customer.getAddresses();
        assertEquals(street, address.getStreetName());
        assertEquals(number, address.getStreetNumber());
        assertEquals(city, address.getTown());
        assertEquals(zipcode, address.getZipCode());
        assertEquals(province, address.getProvince());

    }

    private Optional<Customer> getCustomer(String id, String d) {
        Query<Customer> query = session.createQuery("FROM Customer WHERE identificationNumber = :id AND identificationType =: type ", Customer.class);
        query.setParameter("id", id);
        query.setParameter("type", d);

        return Optional.ofNullable(query.list().get(0));
    }

    @Test
    public void TestTwoCostumersWereImported(){

        assertDoesNotThrow(() -> importCustomers(new StringReader(
            "C,Pepe,Sanchez,D,22333444\n" +
                "A,San Martin,3322,Olivos,1636,BsAs\n" +
                "A,Maipu,888,Florida,1122,Buenos Aires\n" +
                "C,Juan,Perez,C,23-25666777-9\n" +
                "A,Alem,1122,CABA,1001,CABA"), session));

        var customers = session.createQuery("FROM Customer", Customer.class).list();
        assertEquals(2, customers.size());

    }


    public void importCustomers(Reader reader, Session session) throws IOException {

        new CustomerImporter(reader, session).invoke();
    }


}
