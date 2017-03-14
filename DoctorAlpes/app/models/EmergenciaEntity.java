package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aj.paredes10 on 14/02/2017.
 */

@Entity
@Table(name="emergenciaEntity")
public class EmergenciaEntity extends Model {

    public static Finder<Long, EmergenciaEntity> FINDER = new Finder<>(EmergenciaEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Emergencia")
    private Long id;
    private String descripcion;
    private Date fecha;
    private String ubicacion;


    @ManyToOne
    @JsonBackReference(value="r8")
    private PacienteEntity paciente;

    @ManyToOne
    @JsonBackReference(value="r10")
    private HistorialClinicoEntity historialClinico;

    public EmergenciaEntity()
    {
        id=null;
        descripcion="NO NAME";
        fecha=null;
        ubicacion="NO NAME";
    }
    public EmergenciaEntity(Long id)
    {
        this();
        setId(id);
    }
    public EmergenciaEntity(Long id, String descripcion, String ubicacion)
    {
        this();
        setId(id);
        setDescripcion(descripcion);
        setFecha(new Date());
        setUbicacion(ubicacion);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }

    public HistorialClinicoEntity getHistorialClinico() {
        return historialClinico;
    }

    public void setHistorialClinico(HistorialClinicoEntity historialClinico) {
        this.historialClinico = historialClinico;
    }
}
