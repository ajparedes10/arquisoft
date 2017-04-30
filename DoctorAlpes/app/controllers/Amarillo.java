package controllers;

import models.LecturaEntity;
import models.PacienteEntity;

/**
 * Created by Andrea on 29/04/2017.
 */
public class Amarillo implements State {

    private String motivo = null;

    @Override
    public boolean establecerEstado(PacienteEntity paciente, LecturaEntity lectura) {
        motivo = verificar(lectura);
        if(motivo!=null){
            paciente.setEstado(this);
            paciente.update();
            return true;
        }
        return false;
    }

    @Override
    public String darMotivo() {
        return motivo;
    }

    public String verificar(LecturaEntity lectura){

        int frec = lectura.getFrecuenciaCardiaca();
        int sist= lectura.getPresionSistolica();
        int dia= lectura.getPresionDiastolica();
        double estres = lectura.getNivelEstres();

        if((frec > 55 && frec < 70) || (frec > 95 && frec < 120)){
            return "La frecuencia cardiaca esta un poco fuera del limite.\n La frecuencia es de: "+frec+" bpm";
        }
        if(sist >120 && sist < 140){
            return "La presión sistolica está un poco fuera del limite.\nLa presión arterial es: "+ sist+"/"+dia;
        }
        if(dia >80 || dia < 90){
            return "La presión diastolica está un poco fuera del limite.\nLa presión arterial es: "+ sist+"/"+dia;
        }
        if(estres >=4 || estres <= 7) {
            return "El nivel de estres es un poco alto.\nEl nivel de estres es de: " + estres + " en la escala de 1 a 10";
        }
        else
            return null;
    }

    @Override
    public String toString(){
        return State.AMARILLO;
    }
}
