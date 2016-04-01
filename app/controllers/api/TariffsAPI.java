package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.TariffsCore;
import models.exceptions.ValidationException;
import models.mongo.CustomerDS;
import models.mongo.TariffsDS;
import models.tariffs.Tariff;
import models.users.Customer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

/**
 * Created by andrey on 25.03.16.
 */
public class TariffsAPI extends Controller {

    public Result getTariffTemplates(String username) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        List<Tariff> tariffs = new ArrayList<>();
        if (customer != null && customer.getTariffsCollectionList() != null && customer.getTariffsCollectionList().size() > 0) {
            tariffs = TariffsDS.getTariffs(customer.getTariffsCollectionList(), Collections.singletonList("template-active"));
        }

        return ok(Json.toJson(tariffs));
    }

    public Result getActiveTariff(String username, String project) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {return badRequest();}
        Tariff tariff = TariffsDS.getActiveTariff(customer.getId(), project);
        if (tariff == null) {return badRequest();}
        JsonNode jsonNode = Json.toJson(tariff);
        return ok(jsonNode);
    }

    public Result getActiveTariffs(String username) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {return badRequest();}
        List<Tariff> tariffs = TariffsDS.getActiveTariffs(customer.getId());
        if (tariffs == null) {return badRequest();}
        ArrayNode arrayNode = Json.newArray();
        for (Tariff tariff : tariffs) {
            JsonNode tariffJson = Json.toJson(tariff);
            arrayNode.add(tariffJson);
        }
        return ok(arrayNode);
    }

    public Result getSharedTariff(String username) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {return badRequest();}
        Tariff tariff = TariffsDS.getSharedTariff(customer.getId());
        if (tariff == null) {return badRequest();}
        JsonNode jsonNode = Json.toJson(tariff);
        return ok(jsonNode);
    }

    public Result buyTariff(String username, String project, String tariffId, Boolean shared) {
        try {
            TariffsCore.buyTariff(username, project, tariffId, shared);
            ObjectNode jsonNode = Json.newObject();
            jsonNode.put("status", "ok");
            jsonNode.put("error_message", "ok");
            return ok(jsonNode);
        } catch (ValidationException v) {
            ObjectNode jsonNode = Json.newObject();
            jsonNode.put("status", "error");
            jsonNode.put("error_message", v.getMessage());
            return ok(jsonNode);
        }
    }

    public Result check_LimitsPackets(String username, String project) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "user-not-found");
            responseNode.put("error_message", "Пользователь не найден в биллинге");
            return ok(responseNode);
        }

        JsonNode jsonNode = request().body().asJson();
        Map<String, Integer> packets = new HashMap<>();
        Map<String, Integer> limits = new HashMap<>();

        JsonNode limitsJsonNode = jsonNode.get("limits");
        JsonNode packetsJsonNode = jsonNode.get("packets");
        Iterator<String> limitsIterator = limitsJsonNode.fieldNames();
        Iterator<String> packetsIterator = packetsJsonNode.fieldNames();

        while (limitsIterator.hasNext()) {
            String limitName = limitsIterator.next();
            limits.put(limitName, limitsJsonNode.get(limitName).asInt());
        }
        while (packetsIterator.hasNext()) {
            String packetName = packetsIterator.next();
            packets.put(packetName, packetsJsonNode.get(packetName).asInt());
        }

        if (TariffsCore.check_LimitsPackets(customer, project, limits, packets)) {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "ok");
            responseNode.put("error_message", "ok");
            return ok(responseNode);
        } else {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "check-failed");
            responseNode.put("error_message", "Подключенный тариф не позволяет провести операцию");
            return ok(responseNode);
        }
    }

    public Result decrementPackets(String username, String project) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "user-not-found");
            responseNode.put("error_message", "Пользователь не найден в биллинге");
            return ok(responseNode);
        }
        JsonNode jsonNode = request().body().asJson();
        Map<String, Integer> packets = new HashMap<>();
        Map<String, Integer> limits = new HashMap<>();

        JsonNode limitsJsonNode = jsonNode.get("limits");
        JsonNode packetsJsonNode = jsonNode.get("packets");
        Iterator<String> limitsIterator = limitsJsonNode.fieldNames();
        Iterator<String> packetsIterator = packetsJsonNode.fieldNames();

        while (limitsIterator.hasNext()) {
            String limitName = limitsIterator.next();
            limits.put(limitName, limitsJsonNode.get(limitName).asInt());
        }
        while (packetsIterator.hasNext()) {
            String packetName = packetsIterator.next();
            packets.put(packetName, packetsJsonNode.get(packetName).asInt());
        }

        if (TariffsCore.decrementPackets(customer, project, limits, packets)) {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "ok");
            responseNode.put("error_message", "ok");
            return ok(responseNode);
        } else {
            ObjectNode responseNode = Json.newObject();
            responseNode.put("status", "check-failed");
            responseNode.put("error_message", "Подключенный тариф не позволяет провести операцию");
            return ok(responseNode);
        }
    }
}
