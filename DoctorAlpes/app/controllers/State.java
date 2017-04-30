package controllers;

import models.LecturaEntity;
import models.PacienteEntity;

/**
 * Created by Andrea on 29/04/2017.
 */
public interface State {

    // frecuencia entre 70 y 95
    // presion sistolica 90-120 y diastolica 60-80
    // estres -4
    public final static String VERDE = "verde";
    // frecuencia entre 55 y 70 o entre 95 y 120
    // sistolica entre 120-140 o diastolica entre 80-90
    // estres 4-7
    public final static String AMARILLO = "amarillo";
    // frecuencia -55 o +120
    // sistolica +140 o -90, o diastolica +90 o -60
    // estres +7
    public final static String ROJO = "rojo";

    public boolean establecerEstado(PacienteEntity paciente, LecturaEntity lectura);

    public String darMotivo();

    public String toString();
}
