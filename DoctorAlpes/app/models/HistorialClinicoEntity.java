package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aj.paredes10 on 14/02/2017.
 */
@Entity
@Table(name="historialMedicoEntity")
public class HistorialClinicoEntity extends Model {

    public static Finder<Long, HistorialClinicoEntity> FINDER = new Finder<>(HistorialClinicoEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "HistorialClinico")
    private Long id;

    @OneToMany(mappedBy = "historialClinico")
    @JsonManagedReference(value="r9")
    private List<ConsejoEntity> consejos;

    @OneToMany(mappedBy = "historialClinico")
    @JsonManagedReference(value="r10")
    private List<EmergenciaEntity> emergencias;

    @OneToOne
    @JsonManagedReference(value="r7")
    private PacienteEntity paciente;

    public HistorialClinicoEntity()
    {
        this.consejos = new ArrayList<ConsejoEntity>();
        this.emergencias = new ArrayList<EmergenciaEntity>();
        this.id=null;
    }
    public HistorialClinicoEntity(Long id)
    {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConsejoEntity> getConsejos() {
        return consejos;
    }

    public void setConsejos(List<ConsejoEntity> consejos) {
        this.consejos = consejos;
    }

    public List<EmergenciaEntity> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<EmergenciaEntity> emergencias) {
        this.emergencias = emergencias;
    }

    public PacienteEntity getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }

    public void addConsejo(ConsejoEntity consejo)
    {
        this.consejos.add(consejo);
    }

    public void addEmergencia(EmergenciaEntity emergencia)
    {
        this.emergencias.add(emergencia);
    }
}
