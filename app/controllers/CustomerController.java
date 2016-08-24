package controllers;

import models.mongo.CustomerDS;
import models.users.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;
/**
 * Created by andrey on 08.11.15.
 */

@Security.Authenticated(Secured.class)
public class CustomerController extends Controller {

    public Result customers() {
        return ok(views.html.customers.render(CustomerDS.getCustomers()));
    }

    public Result creteEditCustomer() {
        try {
            DynamicForm df = Form.form().bindFromRequest();
            String action = df.get("action");
            Customer _customer = customerFromParams(df.data());

            if (isEmpty(_customer.getUsername())) {
                flash("error", "Поле «уникальный логин» обязательно для заполнения.");
                return redirect(routes.CustomerController.customers());
            }

            if (action.equals("create")) {
                _customer.setBalance(0);
                String id = CustomerDS.saveIfUsernameUnique(_customer);
                if (id == null) {
                    flash("error", "Покупатель с логином «" + _customer.getUsername() + "» уже существует.");
                } else {
                    flash("success", "Покупатель «" + _customer.getUsername() + "» создан.");
                }
                return redirect(routes.CustomerController.customers());
            } else {
                String customerId = df.get("customerId");
                Customer customer = CustomerDS.getCustomerByID(customerId);
                customer.setUsername(_customer.getUsername());
                customer.setOooName(_customer.getOooName());
                customer.setOooInn(_customer.getOooInn());
                customer.setOooKpp(_customer.getOooKpp());
                customer.setOooAddress(_customer.getOooAddress());
                customer.setEmail(_customer.getEmail());
                customer.setCreditLimit(_customer.getCreditLimit());
                customer.setAllowSharedPack(_customer.getAllowSharedPack());
                customer.setAllowSharedSubscription(_customer.getAllowSharedSubscription());
                customer.setPostpayMode(_customer.getPostpayMode());
                customer.setTariffsCollectionList(_customer.getTariffsCollectionList());

                CustomerDS.save(customer);
                flash("success", "Покупатель «" + customer.getUsername() + "» отредактирован.");
                return redirect(routes.CustomerController.customers());
            }
        } catch (Throwable t) {
            flash("error", "Ошибка при создании/редактировании.");
            return redirect(routes.CustomerController.customers());
        }
    }

    public Result deleteCustomer(String customerId) {
        Customer customer = CustomerDS.getCustomerByID(customerId);
        CustomerDS.delete(customer);

        flash("success", "Покупатель «" + customer.getUsername() + "» удалён.");
        return redirect(routes.CustomerController.customers());
    }
    
    private Customer customerFromParams(Map<String, String> params) {
        String username = params.get("username");
        String oooName = params.get("oooName");
        String oooInn = params.get("oooInn");
        String oooKpp = params.get("oooKpp");
        String oooAddress = params.get("oooAddress");
        String email = params.get("email");
        String creditLimit = params.get("creditLimit");
        String allowSharedPack = params.get("allowSharedPack");
        String allowSharedSubscription = params.get("allowSharedSubscription");
        String postpay = params.get("postpay");
        List<String> tariffsCollectionList = new ArrayList<>();

        boolean stop = false;
        for (int i = 0; !stop; i++) {
            String tariffsCollection = params.get("tariffsCollectionList["+ i +"]");
            if (tariffsCollection != null) {
                tariffsCollectionList.add(tariffsCollection);
            } else {
                stop = true;
            }
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setOooName(oooName);
        customer.setOooInn(oooInn);
        customer.setOooKpp(oooKpp);
        customer.setOooAddress(oooAddress);
        customer.setEmail(email);
        customer.setCreditLimit(isEmpty(creditLimit) ? 0 : Integer.valueOf(creditLimit));
        customer.setAllowSharedPack(isEmpty(allowSharedPack) || !allowSharedPack.equals("on") ? false : true);
        customer.setAllowSharedSubscription(
                isEmpty(allowSharedSubscription) || !allowSharedSubscription.equals("on") ? false : true);
        customer.setPostpayMode(isEmpty(postpay) || !postpay.equals("on") ? false : true);
        customer.setTariffsCollectionList(tariffsCollectionList);

        return customer;
    }
}
