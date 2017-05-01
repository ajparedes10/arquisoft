package controllers;


import models.MedicoEntity;
import play.data.*;
import play.mvc.*;
import views.html.*;

import static play.data.Form.form;

public class Application extends Controller{

    public static class Login {

        public String email;
        public String password;

        public String validate() {

            if (MedicoEntity.authenticate(email, password)== false) {
                return "Invalid user or password";
            }
            return null;
        }

    }
    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.HomeController.index()
            );
        }
    }

    public  Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }





}