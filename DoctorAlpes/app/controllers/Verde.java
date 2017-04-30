package controllers;

import models.LecturaEntity;
import models.PacienteEntity;

/**
 * Created by Andrea on 29/04/2017.
 */
public class Verde implements State{

    @Override
    public boolean establecerEstado(PacienteEntity paciente, LecturaEntity lectura) {

            paciente.setEstado(this);
            paciente.update();
            return true;
    }

    @Override
    public String darMotivo() {
        return "todo está en orden";
    }

    @Override
    public String toString(){
        return State.VERDE;
    }
}
