package core;

import models.exceptions.ValidationException;
import models.mongo.CustomerDS;
import models.mongo.PaymentDS;
import models.payments.Payment;
import models.users.Customer;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by andrey on 17.04.16.
 */
public class PaymentsCore {

    public static synchronized void topupBalance(Customer customer, Integer sum, String currency) throws ValidationException {
        Payment payment = new Payment();
        payment.setCreatedAt(new Date());
        payment.setPaidAt(new Date());
        payment.setStatus("paid");
        payment.setCustomerId(customer.getId());
        payment.setCustomerName(customer.getUsername());
        payment.setSum(sum.doubleValue());
        payment.setSumCurrency(!StringUtils.isEmpty(currency) ? currency : "RUB");
        if (!StringUtils.isEmpty(customer.getCurrency()) && !customer.getCurrency().equals(currency)) {
            throw new ValidationException("Не верная валюта для пополнения баланса «" + customer.getUsername() + "».");
        }

        PaymentDS.save(payment);
        customer.setBalance(customer.getBalance() + sum);
        if (StringUtils.isEmpty(customer.getCurrency())) {
            customer.setCurrency("RUB");
        }
        CustomerDS.save(customer);
    }
}
