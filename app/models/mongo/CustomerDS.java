package models.mongo;

/**
 * Created by andrey on 24.10.15.
 */

import models.users.Customer;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static models.mongo.MongoDS.ds;

public class CustomerDS {
    public static String save(Customer customer) {
        Object id = ds().save(customer);
        return id.toString();
    }

    public static void delete(Customer customer) {
        if (customer != null) {
            ds().delete(customer);
        }
    }

    public static Customer getCustomerByID (String customerID){
        return ds().get(Customer.class, new ObjectId(customerID));
    }

    public static Customer getCustomerByUsername (String username){
        return ds().createQuery(Customer.class).field("username").equal(username).get();
    }

    public static synchronized String saveIfUsernameUnique (Customer customer) {
        if (customer != null && customer.getUsername() != null) {
            Customer _customer = ds().createQuery(Customer.class).field("username").equal(customer.getUsername()).get();
            if (_customer == null) {
                return save(customer);
            }
        }
        return null;
    }

    public static List<Customer> getCustomers() {
        List<Customer> customers = ds().createQuery(Customer.class).asList();
        return customers == null ? new ArrayList<>() : customers;
    }
}
