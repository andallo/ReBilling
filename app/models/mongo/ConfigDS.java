package models.mongo;

/**
 * Created by andrey on 21.10.15.
 */

import models.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.mongo.MongoDS.ds;

public class ConfigDS {

    public static void saveConfigParams(Map<String, String> parameters) {
        for (String parameterName : parameters.keySet()) {
            Config config = ds().createQuery(Config.class).field("parameterName").equal(parameterName).get();
            if (config == null) {
                config = new Config();
                config.setParameterName(parameterName);
            }

            config.setParameterValue(parameters.get(parameterName));
            ds().save(config);
        }
    }

    public static Map<String, String> getConfigParams() {
        List<Config> configs = ds().createQuery(Config.class).asList();

        Map<String, String> configsMap = new HashMap<>();
        if (configs != null) {
            for (Config config : configs) {
                configsMap.put(config.getParameterName(), config.getParameterValue());
            }
        }
        return configsMap;
    }

    public static Config getConfig(String param) {
        Config config = ds().createQuery(Config.class).field("parameterName").equal(param).get();
        return config;
    }

    public static synchronized Integer getNextInvoiceNumber() {
        Config config = ds().createQuery(Config.class).field("parameterName").equal("invoice_counter").get();

        if (config == null) {
            config = new Config();
            config.setParameterName("invoice_counter");
            config.setParameterValue("1");
            ds().save(config);
            return 1;
        } else {
            int i = Integer.valueOf(config.getParameterValue());
            config.setParameterValue(String.valueOf(i + 1));
            ds().save(config);
            return i;
        }
    }
}
