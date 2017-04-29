package controllers;

/**
 * Created by aj.paredes10 on 28/04/2017.
 */

import models.PacienteEntity;

public abstract class Observador {

    protected PacienteEntity paciente;

    public abstract void update();
}
