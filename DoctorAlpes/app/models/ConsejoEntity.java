package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="consejoEntity")
public class ConsejoEntity extends Model
{
    public static Finder<Long, ConsejoEntity> FINDER = new Finder<>(ConsejoEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Consejo")
    private Long id;
    private String tipo;
    private Date fecha;
    private String mensaje;


    @ManyToOne
    @JsonBackReference(value="r2")
    private MedicoEntity medico;

    @ManyToOne
    @JsonBackReference(value="r9")
    private HistorialClinicoEntity historialClinico;

    public ConsejoEntity()
    {
        id=null;
        mensaje="NO NAME";
        fecha=null;
        tipo="NO NAME";
    }
    public ConsejoEntity(Long id)
    {
        this();
        setId(id);
    }
    public ConsejoEntity(Long id, String tipo, Date fecha, String mensaje)
    {
        this();
        setId(id);
        setTipo(tipo);
        setFecha(fecha);
        setMensaje(mensaje);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

    public HistorialClinicoEntity getHistorialClinico() {
        return historialClinico;
    }

    public void setHistorialClinico(HistorialClinicoEntity historialClinico) {
        this.historialClinico = historialClinico;
    }
}
