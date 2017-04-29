package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="lecturaEntity")
public class LecturaEntity extends Model
{
    public static Finder<Long, LecturaEntity> FINDER = new Finder<>(LecturaEntity.class);

    public final static String VERDE = "verde"; // entre 70 y 95
    public final static String AMARILLO = "amarilla"; // entre 55 y 70 - entre 95 y 120
    public final static String ROJO = "roja"; // mas de 120 - menos de 55

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Lectura")
    private Long id;
    private Date fecha;
    private int frecuenciaCardiaca;
    private int presionSistolica;
    private int presionDiastolica;
    private double nivelEstres;

    @ManyToOne
    @JsonBackReference (value = "r1")
    private HistorialDeMedicionEntity historialMedicion;

    public LecturaEntity()
    {
        id=null;
        fecha=null;
        frecuenciaCardiaca=-1;
        presionSistolica=-1;
        presionDiastolica=-1;
        nivelEstres=-1;
    }
    public LecturaEntity(Long id)
    {
        this();
        setId(id);
    }

    public LecturaEntity(Long id, int frecuenciaCardiaca, int presionSistolica, int presionDiastolica, double nivelEstres) {
        this.id = id;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionSistolica = presionSistolica;
        this.presionDiastolica = presionDiastolica;
        this.nivelEstres = nivelEstres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public int getPresionSistolica() {
        return presionSistolica;
    }

    public void setPresionSistolica(int presionSistolica) {
        this.presionSistolica = presionSistolica;
    }

    public int getPresionDiastolica() {
        return presionDiastolica;
    }

    public void setPresionDiastolica(int presionDiastolica) {
        this.presionDiastolica = presionDiastolica;
    }

    public double getNivelEstres() {
        return nivelEstres;
    }

    public void setNivelEstres(double nivelEstres) {
        this.nivelEstres = nivelEstres;
    }

    public HistorialDeMedicionEntity getHistorialMedicion() {
        return historialMedicion;
    }

    public void setHistorialMedicion(HistorialDeMedicionEntity historialMedicion) {
        this.historialMedicion = historialMedicion;
    }

    public String verificardatos(){

        if(frecuenciaCardiaca > 70 && frecuenciaCardiaca < 95){
            return VERDE;
        }
        else if(frecuenciaCardiaca > 120 || frecuenciaCardiaca < 55){
            return ROJO;
        }
        else
            return AMARILLO;

    }
}
