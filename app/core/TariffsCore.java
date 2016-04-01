package core;

import controllers.utils.TariffUtils;
import models.exceptions.ValidationException;
import models.mongo.CustomerDS;
import models.mongo.TariffsDS;
import models.tariffs.Operation;
import models.tariffs.Tariff;
import models.users.Customer;

import java.util.Map;

/**
 * Created by andrey on 29.03.16.
 */
public class TariffsCore {

    public static Tariff buyTariff(String username, String project, String tariffId, Boolean shared) throws ValidationException {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        Tariff tariff_template = TariffsDS.getTariff(tariffId);
        Tariff active_tariff = TariffsDS.getActiveTariff(customer.getId(), project);

        if ((customer.getBalance() + customer.getCreditLimit() - tariff_template.getEndsum()) < 0) {
            throw new ValidationException("Недостаточно денег на балансе.");
        }

        customer.setBalance(customer.getBalance() - tariff_template.getEndsum());
        Tariff tariff = TariffUtils.createActiveTariff(tariff_template, customer, project,
                active_tariff == null ? "active" : "wait", shared);
        CustomerDS.save(customer);
        TariffsDS.saveTariff(tariff);

        return tariff;
    }

    public static boolean check_LimitsPackets(Customer customer, String project, Map<String, Integer> limits, Map<String, Integer> packs) {
        Tariff active_tariff = TariffsDS.getActiveTariff(customer.getId(), project);
        Tariff shared_tariff = TariffsDS.getSharedTariff(customer.getId());

        if (customer.getPostpayMode()) {return true;}
        if (active_tariff != null && check_LimitsPackets(active_tariff, limits, packs)) {return true;}
        if (shared_tariff != null && check_LimitsPackets(shared_tariff, limits, packs)) {
            if (customer.getAllowSharedSubscription() && shared_tariff.getType().equals("subscription")) {return true;}
            if (customer.getAllowSharedPack() && shared_tariff.getType().equals("packet")) {return true;}
        }

        return false;
    }

    public static boolean decrementPackets(Customer customer, String project, Map<String, Integer> limits, Map<String, Integer> packs) {
        Tariff active_tariff = TariffsDS.getActiveTariff(customer.getId(), project);
        Tariff shared_tariff = TariffsDS.getSharedTariff(customer.getId());

        if (customer.getPostpayMode()) {return true;}
        if (active_tariff != null && decrementPackets(active_tariff, limits, packs)) {return true;}
        if (shared_tariff != null && (
                        (customer.getAllowSharedSubscription() && shared_tariff.getType().equals("subscription")) ||
                        (customer.getAllowSharedPack() && shared_tariff.getType().equals("packet"))
                )) {
            if (decrementPackets(shared_tariff, limits, packs)) {return true;};
        }

        return false;
    }

    public static synchronized boolean decrementPackets(Tariff tariff, Map<String, Integer> limits, Map<String, Integer> packs) {
        boolean all_packs_approved = true;
        for (Operation operation : tariff.getOperations()) {
            if (!operation.getType().equals("packet")) continue;
            boolean pack_approved = true;
            for (String packName : packs.keySet()) {
                Integer packLimit = packs.get(packName);
                if (operation.getName().equals(packName)) {
                    if ((operation.getLimit() - operation.getCounter() - packLimit) >= 0){
                        operation.setCounter(operation.getCounter() + packLimit);
                        pack_approved = true;
                        break;
                    } else {
                        pack_approved = false;
                        break;
                    }
                }
            }
            if (!pack_approved) {
                all_packs_approved = false;
                break;
            }
        }

        boolean all_limits_approved = true;
        for (Operation operation : tariff.getOperations()) {
            if (!operation.getType().equals("limit")) continue;
            boolean limit_approved = true;
            for (String limitName : limits.keySet()) {
                Integer limit = limits.get(limitName);
                if (operation.getName().equals(limitName)) {
                    if (operation.isAllow_over_limit() || (operation.getLimit() - limit) >= 0){
                        limit_approved = true;
                        break;
                    } else {
                        limit_approved = false;
                        break;
                    }
                }
            }
            if (!limit_approved) {
                all_limits_approved = false;
                break;
            }
        }

        if (all_packs_approved && all_limits_approved) {
            TariffsDS.saveTariff(tariff);
            return true;
        } else {
            return false;
        }
    }

    private static boolean check_LimitsPackets(Tariff tariff, Map<String, Integer> limits, Map<String, Integer> packs) {
        boolean all_packs_approved = true;
        for (Operation operation : tariff.getOperations()) {
            if (!operation.getType().equals("packet")) continue;
            boolean pack_approved = true;
            for (String packName : packs.keySet()) {
                Integer packLimit = packs.get(packName);
                if (operation.getName().equals(packName)) {
                    if ((operation.getLimit() - operation.getCounter() - packLimit) >= 0){
                        pack_approved = true;
                        break;
                    } else {
                        pack_approved = false;
                        break;
                    }
                }
            }
            if (!pack_approved) {
                all_packs_approved = false;
                break;
            }
        }

        boolean all_limits_approved = true;
        for (Operation operation : tariff.getOperations()) {
            if (!operation.getType().equals("limit")) continue;
            boolean limit_approved = true;
            for (String limitName : limits.keySet()) {
                Integer limit = limits.get(limitName);
                if (operation.getName().equals(limitName)) {
                    if (operation.isAllow_over_limit() || (operation.getLimit() - limit) >= 0){
                        limit_approved = true;
                        break;
                    } else {
                        limit_approved = false;
                        break;
                    }
                }
            }
            if (!limit_approved) {
                all_limits_approved = false;
                break;
            }
        }

        if (all_packs_approved && all_limits_approved) {
            return true;
        } else {
            return false;
        }
    }
}
