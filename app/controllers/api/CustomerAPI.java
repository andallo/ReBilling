package controllers.api;

import models.mongo.CustomerDS;
import models.users.Customer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by andrey on 27.03.16.
 */
public class CustomerAPI extends Controller {

    public Result getCustomerInfo(String username) {
        Customer customer = CustomerDS.getCustomerByUsername(username);
        if (customer == null) {return badRequest();}
        return ok(Json.toJson(customer));
    }
}
