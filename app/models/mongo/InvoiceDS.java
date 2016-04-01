package models.mongo;

/**
 * Created by andrey on 25.10.15.
 */

import models.documents.Invoice;
import models.tariffs.Tariff;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static models.mongo.MongoDS.*;

public class InvoiceDS {

    public static String save(Invoice invoice) {
        if (invoice.getTariffs() != null) {
            if(invoice.getTariffIDs() == null) {
                invoice.setTariffIDs(new ArrayList<>());
            }
            for (Tariff tariff : invoice.getTariffs()) {
                if (!invoice.getTariffIDs().contains(tariff.getId())) {
                    invoice.getTariffIDs().add(tariff.getId());
                }
            }
        }

        Object id = ds().save(invoice);
        return id.toString();
    }

    public static Invoice getInvoice(String invoiceID) {
        Invoice invoice = ds().get(Invoice.class, new ObjectId(invoiceID));
        List<Tariff> tariffs = new ArrayList<>();
        for (String tariffID : invoice.getTariffIDs()) {
            tariffs.add(TariffsDS.getTariff(tariffID));
        }

        invoice.setTariffs(tariffs);
        return invoice;
    }

    public static Invoice getInvoice(Integer invoiceNumber, int year) {
        DateTime dateTime = new DateTime(year, 1, 1, 0, 0);
        Date startDate = dateTime.toDate();
        dateTime = new DateTime(year+1, 1, 1, 0, 0);
        Date endDate = dateTime.toDate();

        Invoice invoice = ds().createQuery(Invoice.class)
                .field("invoiceNumber").equal(invoiceNumber)
                .field("invoiceDate").greaterThanOrEq(startDate)
                .field("invoiceDate").lessThan(endDate).get();

        if (invoice != null) {
            List<Tariff> tariffs = new ArrayList<>();
            for (String tariffID : invoice.getTariffIDs()) {
                tariffs.add(TariffsDS.getTariff(tariffID));
            }

            invoice.setTariffs(tariffs);
        }
        return invoice;
    }

    public static List<Invoice> getInvoices(String CustomerID) {
        return null;
    }
}
