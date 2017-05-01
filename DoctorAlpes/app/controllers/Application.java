package controllers;


import models.MedicoEntity;
import play.data.*;
import play.mvc.*;
import views.html.*;

import java.util.List;

import static play.data.Form.form;

public class Application extends Controller{

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            List<MedicoEntity> meds = MedicoEntity.FINDER.where().ilike("correo", "%"+email+"%").findList();
            if (meds==null || meds.size()==0 || meds.get(0)==null){
                return "Invalid user";
            }
            if (meds.get(0)!=null && meds.get(0).authenticate(email, password)== false) {
                return "Invalid password";
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
            MedicoEntity med = MedicoEntity.FINDER.where().ilike("correo", "%"+loginForm.get().email+"%").findList().get(0);
            return ok(
                    medico.render(med)
            );
        }
    }

    public  Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }


}