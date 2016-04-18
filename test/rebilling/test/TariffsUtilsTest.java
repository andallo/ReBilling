package rebilling.test;

import controllers.utils.TariffUtils;
import models.tariffs.Tariff;
import org.junit.Test;

/**
 * Created by andrey on 18.04.16.
 */
public class TariffsUtilsTest {

    @Test
    public void test() {
        Tariff tariff = new Tariff();
        tariff.setTariffsCollection("adf-sdfs %20 $10 sdjflk_sss ");
        TariffUtils.validateTariffCollection(tariff);
        System.out.println(tariff.getTariffsCollection());
    }
}
