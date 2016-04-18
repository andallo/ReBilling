package controllers;

import controllers.utils.TariffUtils;
import models.mongo.TariffsDS;
import models.tariffs.Operation;
import models.tariffs.Tariff;
import org.apache.commons.lang3.StringUtils;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

/**
 * Created by andrey on 15.10.15.
 */

@Security.Authenticated(Secured.class)
public class TariffController extends Controller {

    public Result tariffsPage() {
        return tariffsCollectionPage("");
    }

    public Result tariffsCollectionPage(String tariffs_collection) {
        List<Tariff> tariffs = TariffsDS.getTariffs(Collections.singletonList(tariffs_collection), Arrays.asList("template-active",
                "template-inactive"));

        tariffs.sort(new Comparator<Tariff>() {
            @Override
            public int compare(Tariff o1, Tariff o2) {
                int type_compare = o1.getType().compareTo(o2.getType());
                if (type_compare == 0) {
                    return o1.getEndsum().compareTo(o2.getEndsum());
                } else {
                    return type_compare;
                }
            }
        });
        return ok(views.html.tariffs.render(tariffs_collection, tariffs));
    }

    public Result createEditTariff() {
        DynamicForm df = Form.form().bindFromRequest();
        String action = df.get("action");
        Tariff _tariff = tariffFromParams(df.data());

        if (StringUtils.isEmpty(_tariff.getTariffsCollection())){
            flash("error", "Поле «коллекция тарифов» обязательно для заполнения.");
            return redirect(routes.TariffController.tariffsPage());
        }
        TariffUtils.validateTariffCollection(_tariff);

        if (action.equals("create")) {
            _tariff.setCreated(new Date());
            TariffsDS.saveTariff(_tariff);
            return redirect(routes.TariffController.tariffsCollectionPage(_tariff.getTariffsCollection()));
        } else {
            String tariff_id = df.get("tariff_id");
            Tariff tariff = TariffsDS.getTariff(tariff_id);

            tariff.setStatus(_tariff.getStatus());
            tariff.setCustomerID(_tariff.getCustomerID());
            tariff.setCustomerName(_tariff.getCustomerName());
            tariff.setName(_tariff.getName());
            tariff.setType(_tariff.getType());
            tariff.setCurrency(_tariff.getCurrency());
            tariff.setSum(_tariff.getSum());
            tariff.setDiscount(_tariff.getDiscount());
            tariff.setEndsum(_tariff.getEndsum());
            tariff.setSubscriptionPeriod(_tariff.getSubscriptionPeriod());
            tariff.setOperations(_tariff.getOperations());
            tariff.setTariffsCollection(_tariff.getTariffsCollection());

            flash("success", "Тариф «" + _tariff.getName() + "» отредактирован.");
            return redirect(routes.TariffController.tariffsCollectionPage(_tariff.getTariffsCollection()));
        }
    }

    public Result setStatus(String tariffId, String status) {
        Tariff tariff = TariffsDS.getTariff(tariffId);
        tariff.setStatus(status);
        TariffsDS.saveTariff(tariff);
        return redirect("/tariffs/" + tariff.getTariffsCollection());
    }

    public Result deleteTariff(String tariffId) {
        Tariff tariff = TariffsDS.getTariff(tariffId);
        TariffsDS.removeTariff(tariff);
        return redirect("/tariffs/" + tariff.getTariffsCollection());
    }

    public static List<String> getTariffsCollections() {
        return TariffsDS.getTariffsCollections();
    }

    private Tariff tariffFromParams(Map<String, String> params) {
        String status = params.get("status");
        String customer_id = params.get("customer_id");
        String customer_name = params.get("customer_name");
        String name = params.get("name");
        String type = params.get("type");
        String currency = params.get("currency");
        Integer sum = Integer.valueOf(params.get("sum"));
        String discount = params.get("discount");
        Integer endsum = Integer.valueOf(params.get("endsum"));
        String subscriptionPeriod = params.get("subscription_period");
        String tariffsCollection = params.get("tariffsCollection");

        List<Operation> operations = new ArrayList<>();
        boolean stop = false;
        for (int i = 0; !stop; i++) {
            String op_name = params.get("op_name["+i+"]");
            if (!StringUtils.isEmpty(op_name)) {
                String op_limit = params.get("op_limit["+i+"]");
                String op_counter = params.get("op_counter["+i+"]");
                String op_allow_over_limit = params.get("op_allow_over_limit["+i+"]");
                String op_type = params.get("op_type["+i+"]");

                Integer op_limit_int = StringUtils.isEmpty(op_limit) ? 0 : Integer.valueOf(op_limit);
                Integer op_counter_int = StringUtils.isEmpty(op_counter) ? 0 : Integer.valueOf(op_counter);
                Boolean op_allow_over_limit_bool = op_allow_over_limit != null && op_allow_over_limit.equals("on");

                if (op_limit_int > 0 || op_allow_over_limit_bool) {
                    Operation operation = new Operation();
                    operation.setName(op_name);
                    operation.setType(op_type);
                    operation.setLimit(op_limit_int);
                    operation.setCounter(op_counter_int);
                    operation.setAllow_over_limit(op_allow_over_limit_bool);
                    operations.add(operation);
                }
            } else {
                stop = true;
            }
        }

        Tariff tariff = new Tariff();
        tariff.setStatus(status);
        tariff.setCustomerID(customer_id);
        tariff.setCustomerName(customer_name);
        tariff.setName(name);
        tariff.setType(type);
        tariff.setCurrency(currency);
        tariff.setSum(sum);
        tariff.setDiscount(StringUtils.isEmpty(discount) ? 0 : Integer.valueOf(discount));
        tariff.setEndsum(endsum);
        tariff.setTariffsCollection(tariffsCollection);
        if (type.equals("subscription")) {
            tariff.setSubscriptionPeriod(Integer.valueOf(subscriptionPeriod));
        }
        if (type.equals("subscription") || type.equals("packet")) {
            tariff.setOperations(operations);
        }
        tariff.setCreated(null);
        tariff.setExpired(null);

        return tariff;
    }
}
