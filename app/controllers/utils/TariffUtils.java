package controllers.utils;

import models.tariffs.Operation;
import models.tariffs.Tariff;
import models.users.Customer;

import java.util.Date;

/**
 * Created by andrey on 18.10.15.
 */
public class TariffUtils {
    public static String getRuTariffType(String tariffType) {
        if (tariffType.equals("oneoff")) {return "единоразовый";}
        if (tariffType.equals("packet")) {return "пакетный";}
        if (tariffType.equals("subscription")) {return "подписка";}
        return tariffType;
    }

    public static String getRuOperationType(String operationType) {
        if (operationType.equals("limit")) {return "лимит";}
        if (operationType.equals("packet")) {return "пакет";}
        return operationType;
    }

    public static Tariff createActiveTariff(Tariff from_template, Customer customer, String project, String status, Boolean shared) {
        Tariff active_tariff = new Tariff();
        active_tariff.setCreated(new Date());
        active_tariff.setExpired(null);

        active_tariff.setCustomerID(customer.getId());
        active_tariff.setCustomerName(customer.getUsername());
        active_tariff.setProject(project);
        active_tariff.setStatus(status);
        active_tariff.setShared(shared);

        active_tariff.setSum(from_template.getSum());
        active_tariff.setCurrency(from_template.getCurrency());
        active_tariff.setDiscount(from_template.getDiscount());
        active_tariff.setEndsum(from_template.getEndsum());

        active_tariff.setName(from_template.getName());
        active_tariff.setType(from_template.getType());
        active_tariff.setSubscriptionPeriod(from_template.getSubscriptionPeriod());
        active_tariff.setTariffsCollection(from_template.getTariffsCollection());
        active_tariff.setOperations(from_template.getOperations());
        return active_tariff;
    }
}
