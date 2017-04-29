package controllers;

import models.*;

import java.util.Date;

/**
 * Created by aj.paredes10 on 28/04/2017.
 */
public class EmergenciaObserver extends Observador {

    public EmergenciaObserver(PacienteEntity p){
        this.paciente = p;
    }

    @Override
    public void update() {
        String mensaje = "";

        if(paciente.getEstado().equals(LecturaEntity.ROJO)){
            EmergenciaEntity em = new EmergenciaEntity();
            mensaje = "Alerta Roja! su frecuencia cardiaca está fuera de rango";
            HistorialClinicoEntity hc =paciente.getHistorialClinico();

            em.setDescripcion(mensaje);
            em.setFecha(new Date());
            em.setUbicacion("Calle falsa123");
            em.setPaciente(paciente);
            em.setHistorialClinico(hc);
            em.save();

            hc.addEmergencia(em);
            hc.save();
        }
    }
}
