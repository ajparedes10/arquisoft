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
            System.out.println("--------------------entró rojo");
            EmergenciaEntity em = new EmergenciaEntity();
            mensaje = "Alerta Roja! su frecuencia cardiaca está fuera de rango";
            HistorialClinicoEntity hc =paciente.getHistorialClinico();
            System.out.println("******************"+paciente.getNombre());
            if(hc!=null)
             System.out.println("--------------------buco historial clinico "+ hc.getId());
            else
                System.out.println("********el histiraial es null**********");

            em.setDescripcion(mensaje);
            em.setFecha(new Date());
            em.setUbicacion("Calle falsa123");
            em.setPaciente(paciente);
            em.setHistorialClinico(hc);
            em.save();

            System.out.println("--------------------guardo emergencia "+ em.getId());

            hc.addEmergencia(em);
            hc.update();
        }
    }
}
