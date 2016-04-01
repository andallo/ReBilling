package controllers.utils;

import controllers.FileStoreController;
import models.FileStore;
import models.documents.Requisites;
import models.mongo.ConfigDS;
import models.tariffs.Tariff;
import models.users.Customer;

import java.util.*;

/**
 * Created by andrey on 25.10.15.
 */
public class InvoiceUtils {
    public static Requisites customer2requisites(Customer customer) {
        Requisites requisites = new Requisites();
        requisites.setAddress(customer.getOooAddress());
        requisites.setCompanyName(customer.getOooName());
        requisites.setInn(customer.getOooInn());
        requisites.setKpp(customer.getOooKpp());
        return requisites;
    }

    public static Requisites config2requisites() {
        Requisites requisites = new Requisites();
        Map<String, String> configParams = ConfigDS.getConfigParams();
        requisites.setAddress(configParams.get("ooo_address"));
        requisites.setBankAccount(configParams.get("ooo_bank_account"));
        requisites.setBankName(configParams.get("ooo_bank_name"));
        requisites.setBik(configParams.get("ooo_bank_bik"));
        requisites.setCeoFio(configParams.get("ooo_ceo_fio"));
        requisites.setCeoJobTitle(configParams.get("ooo_ceo_position"));
        requisites.setCompanyName(configParams.get("ooo_name"));
        requisites.setCorrAccount(configParams.get("ooo_bank_corr"));
        requisites.setInn(configParams.get("ooo_inn"));
        requisites.setKpp(configParams.get("ooo_kpp"));
        requisites.setTelephone(configParams.get("ooo_telephone"));
        requisites.setWebSite(configParams.get("ooo_site"));
        requisites.setBuhFio(configParams.get("ooo_buh_fio"));
        requisites.setCeoSignURL(FileStoreController.getVersionURL("ooo_ceo_sign_image"));
        requisites.setBuhSignURL(FileStoreController.getVersionURL("ooo_buh_sign_image"));
        requisites.setStampURL(FileStoreController.getVersionURL("ooo_stamp_image"));
        return requisites;
    }

    public static int calculateEndsum(List<Tariff> tariffs){
        int endsum = 0;
        for (Tariff tariff : tariffs) {
            endsum = endsum + tariff.getEndsum();
        }
        return endsum;
    }

    public static String getCurrencyId(String currency) {
        if (currency.equals("RUB")) {
            return "643";
        } else if (currency.equals("USD")) {
            return "810";
        } else {
            return currency;
        }
    }

    public static String getCurrencyName(String currencyId) {
        if (Arrays.asList("643", "10643").contains(currencyId)) {
            return "RUB";
        } else if (Arrays.asList("810", "10810").contains(currencyId)) {
            return "USD";
        } else {
            return currencyId;
        }
    }

}
