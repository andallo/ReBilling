package controllers;


import core.PaymentsCore;
import models.exceptions.ValidationException;
import models.mongo.ConfigDS;
import models.mongo.CustomerDS;
import models.users.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * Created by andrey on 20.10.15.
 */
@Security.Authenticated(Secured.class)
public class PaymentsController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result paymentGatewaysPage() {
        return paymentGatewaysPageTab("");
    }

    public Result paymentGatewaysPageTab(String tab) {
        if (tab == null) {
            tab = "";
        }
        return ok(views.html.payment_gateways.render(ConfigDS.getConfigParams(), tab));
    }

    public Result topupBalance() {
        try {
            DynamicForm df = Form.form().bindFromRequest();
            String customerId = df.get("customerId");
            Integer sum = Integer.valueOf(df.get("sum"));
            String currency = df.get("currency");

            Customer customer = CustomerDS.getCustomerByID(customerId);
            PaymentsCore.topupBalance(customer, sum, currency);

            flash("success", "Баланс «"+ customer.getUsername() +"» пополнен, " + customer.getBalance() + " " + customer.getCurrency() + "");
            return redirect(routes.CustomerController.customers());
        } catch (ValidationException v) {
            flash("error", v.getMessage());
        } catch (Exception e) {
            flash("error", "Ошибка при пополнении баланса.");
        }

        return redirect(routes.CustomerController.customers());
    }
}
