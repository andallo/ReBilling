package models.mongo;

import models.payments.Payment;

import static models.mongo.MongoDS.*;

/**
 * Created by andrey on 08.11.15.
 */


public class PaymentDS {

    public static String save(Payment payment) {
        Object id = ds().save(payment);
        return id.toString();
    }

    public static Payment getPaymentByExtTransactionID(String extTransactionID) {
        Payment payment = ds().createQuery(Payment.class).field("extTransactionId").equal(extTransactionID).get();
        return payment;
    }
}
