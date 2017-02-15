package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aj.paredes10 on 14/02/2017.
 */
@Entity
@Table(name="marcapasosEntity")
public class MarcapasosEntity extends Model {

    public static Finder<Long, MarcapasosEntity> FINDER = new Finder<>(MarcapasosEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Marcapasos")
    private Long id;
    private String modo;
    private int intervaloAV;
    private int nivelBateria;
    private int frecuencia;


    @ManyToOne
    @JsonBackReference(value="r4")
    private MedicoEntity medico;

    @OneToOne
    @JsonBackReference(value="r5")
    private PacienteEntity paciente;

    public MarcapasosEntity()
    {
        id=null;
        modo="NO NAME";
        intervaloAV=-1;
        nivelBateria=-1;
        frecuencia =-1;
    }
    public MarcapasosEntity(Long id)
    {
        this();
        setId(id);
    }
    public MarcapasosEntity(Long id, String modo, int intervaloAV, int nivelBateria, int frec)
    {
        this();
        setId(id);
        setModo(modo);
        setIntervaloAV(intervaloAV);
        setNivelBateria(nivelBateria);
        setFrecuencia(frec);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public int getIntervaloAV() {
        return intervaloAV;
    }

    public void setIntervaloAV(int intervaloAV) {
        this.intervaloAV = intervaloAV;
    }

    public int getNivelBateria() {
        return nivelBateria;
    }

    public void setNivelBateria(int nivelBateria) {
        this.nivelBateria = nivelBateria;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
}
