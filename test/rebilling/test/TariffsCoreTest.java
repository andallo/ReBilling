package rebilling.test;

import core.TariffsCore;
import models.mongo.CustomerDS;
import models.users.Customer;
import org.junit.Test;
import play.test.WithApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 01.04.16.
 */
public class TariffsCoreTest extends WithApplication {

    @Test
    public void test() {
        String username = "test";
        String project = "1825063450";
        Map<String, Integer> limits = new HashMap<>();
        Map<String, Integer> packets = new HashMap<>();
        limits.put("кол-во актуальных видео", 0);
        packets.put("создание/обновление видео", 1);
        Customer customer = CustomerDS.getCustomerByUsername(username);

        boolean check = TariffsCore.check_LimitsPackets(customer, project, limits, packets);
        System.out.println("check_LimitsPackets = " + check);
    }
}
