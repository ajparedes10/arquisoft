package controllers;

import models.EmergenciaEntity;
import models.LecturaEntity;
import models.MedicoEntity;
import models.PacienteEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by aj.paredes10 on 28/04/2017.
 */
public class MedicoObserver extends Observador {

    public MedicoObserver(PacienteEntity p){
        this.paciente = p;
    }

    @Override
    public void update() {
        String mensaje = "";

        if(paciente.getEstado().equals(LecturaEntity.ROJO) || paciente.getEstado().equals(LecturaEntity.AMARILLO)) {
            EmergenciaEntity em = new EmergenciaEntity();
            if(paciente.getEstado().equals(LecturaEntity.ROJO)){
                mensaje = "Alerta Roja! El paciente " + paciente.getNombre() + " se encuentra muy grave";
            }
            else if (paciente.getEstado().equals(LecturaEntity.AMARILLO)) {
                mensaje = "Cuidado! El paciente " + paciente.getNombre() + " se encuentra en estado amarillo";
            }

            em.setDescripcion(mensaje);
            em.setFecha(new Date());
            em.setUbicacion("Calle falsa123");
            em.setPaciente(paciente);
            em.save();

            paciente.getHistorialClinico().addEmergencia(em);

            List<MedicoEntity> medicos = paciente.getMedicos();
            for (int i = 0; i < medicos.size(); i++) {
                MedicoEntity med = medicos.get(i);
                med.addEmergencia(em);
                med.save();
            }
        }
    }
}
