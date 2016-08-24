package controllers;

import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

    public Result login() {
        return ok(views.html.login_form.login_form.render());
    }

    @Security.Authenticated(Secured.class)
    public Result index() {
        return redirect(routes.CustomerController.customers());
    }

    public static class Login {
        public String login;
        public String password;
    }

    public Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.get().login.equals("ovido") && loginForm.get().password.equals("ab271088")) {
            session().clear();
            session("email", loginForm.get().login);
            return redirect(routes.Application.index());
        } else {
            return badRequest(views.html.login_form.login_form.render());
        }
    }

    public Result logout() {
        session().clear();
        return redirect(routes.Application.login());
    }
}

