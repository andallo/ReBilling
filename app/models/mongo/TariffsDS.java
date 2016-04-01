package models.mongo;

import models.tariffs.Tariff;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

import static models.mongo.MongoDS.ds;
/**
 * Created by andrey on 15.10.15.
 */
public class TariffsDS {

    public static String saveTariff(Tariff tariff) {
        Object id = ds().save(tariff);
        return id.toString();
    }

    public static void removeTariff(Tariff tariff) {
        ds().delete(tariff);
    }

    public static Tariff getTariff(String tariffId) {
        return ds().get(Tariff.class, new ObjectId(tariffId));
    }

    public static List<Tariff> getTariffs(List<String> tariffs_collections, List<String> statuses) {
        Query<Tariff> query = ds().createQuery(Tariff.class);
        if (statuses != null) {
            query.field("status").in(statuses);
        }
        if (tariffs_collections != null) {
            query.field("tariffsCollection").in(tariffs_collections);
        }

        List<Tariff> tariffs = query.asList();
        if (tariffs == null) {
            return new ArrayList<Tariff>();
        } else {
            return tariffs;
        }
    }

    public static List<Tariff> getTariffs(List<String> statuses) {
        Query<Tariff> query = ds().createQuery(Tariff.class);
        if (statuses != null) {
            query.field("status").in(statuses);
        }

        List<Tariff> tariffs = query.asList();

        if (tariffs != null) {
            return tariffs;
        } else {
            return new ArrayList<Tariff>();
        }
    }

    public static Tariff getActiveTariff(String customerId, String project) {
        Tariff tariff = ds().createQuery(Tariff.class)
                .field("status").equal("active")
                .field("customerID").equal(customerId)
                .field("project").equal(project).get();
        return tariff;
    }


    public static Tariff getSharedTariff(String customerId) {
        Tariff tariff = ds().createQuery(Tariff.class)
                .field("status").equal("active")
                .field("customerID").equal(customerId)
                .field("shared").equal(true).get();
        return tariff;
    }

    public static List<Tariff> getActiveTariffs(String customerId) {
        List<Tariff> tariffs = ds().createQuery(Tariff.class)
                .field("status").equal("active")
                .field("customerID").equal(customerId).asList();

        return tariffs == null ? new ArrayList<>() : tariffs;
    }


    public static List<String> getTariffsCollections() {
        List<String> distinct_tariffsCollections = ds().getDB().getCollection("Tariff").distinct("tariffsCollection");
        if (distinct_tariffsCollections != null) {
            return distinct_tariffsCollections;
        } else {
            return new ArrayList<>();
        }
    }
}
