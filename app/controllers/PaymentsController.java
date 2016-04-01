package controllers;


import models.mongo.ConfigDS;
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

}
