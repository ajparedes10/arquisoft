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
        System.out.println("entró update medico observador");
        String mensaje = "";

        if(paciente.getEstado().equals(LecturaEntity.ROJO) || paciente.getEstado().equals(LecturaEntity.AMARILLO)) {
            System.out.println("entró rojo o amarillo");

            if(paciente.getEstado().equals(LecturaEntity.ROJO)){
                System.out.println("entró rojo");
                mensaje = "Alerta Roja! El paciente " + paciente.getNombre() + " se encuentra muy grave";
            }
            else if (paciente.getEstado().equals(LecturaEntity.AMARILLO)) {
                mensaje = "Cuidado! El paciente " + paciente.getNombre() + " se encuentra en estado amarillo";
            }

            List<MedicoEntity> medicos = paciente.getMedicos();
            for (int i = 0; i < medicos.size(); i++) {

                MedicoEntity med = medicos.get(i);

                EmergenciaEntity em = new EmergenciaEntity();
                em.setDescripcion(mensaje);
                em.setFecha(new Date());
                em.setUbicacion("Calle falsa123");
                em.setPaciente(paciente);
                em.setMedico(med);
                em.save();
                System.out.println("guardó emergencia id "+ em.getId());

                med.addEmergencia(em);
                med.update();
            }
        }
    }
}
