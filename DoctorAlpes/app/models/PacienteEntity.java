package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pacienteEntity")
public class PacienteEntity extends Model
{
    public static Finder<Long, PacienteEntity> FINDER = new Finder<>(PacienteEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Paciente")
    private Long id;
    private String nombre;
    private String eps;
    private String estado;


    @ManyToMany
    @JsonBackReference (value = "r3")
    private List<MedicoEntity> medicos;

    @OneToOne
    @JsonBackReference(value="r5")
    private MarcapasosEntity marcapasos;

    @OneToOne
    @JsonBackReference(value="r6")
    private HistorialDeMedicionEntity historialMediciones;

    @OneToOne
    @JsonBackReference(value="r7")
    private HistorialClinicoEntity historialClinico;

    @OneToMany
    @JsonManagedReference(value="r8")
    private List<EmergenciaEntity> emergencias;

    public PacienteEntity()
    {
        id=null;
        nombre="NO NAME";
        eps="NO NAME";
        estado = "NO NAME";
        medicos = new ArrayList<MedicoEntity>();
        emergencias = new ArrayList<EmergenciaEntity>();

    }
    public PacienteEntity(Long id)
    {
        this();
        setId(id);
    }
    public PacienteEntity(Long id, String nombre, String eps, String estado )
    {
        this();
        setId(id);
        setNombre(nombre);
        setEps(eps);
        setEstado(estado);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<MedicoEntity> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<MedicoEntity> medicos) {
        this.medicos = medicos;
    }

    public MarcapasosEntity getMarcapasos() {
        return marcapasos;
    }

    public void setMarcapasos(MarcapasosEntity marcapasos) {
        this.marcapasos = marcapasos;
    }

    public HistorialDeMedicionEntity getHistorialMediciones() {
        return historialMediciones;
    }

    public void setHistorialMediciones(HistorialDeMedicionEntity historialMediciones) {
        this.historialMediciones = historialMediciones;
    }

    public HistorialClinicoEntity getHistorialClinico() {
        return historialClinico;
    }

    public void setHistorialClinico(HistorialClinicoEntity historialClinico) {
        this.historialClinico = historialClinico;
    }

    public List<EmergenciaEntity> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<EmergenciaEntity> emergencias) {
        this.emergencias = emergencias;
    }

    public void addEmergencia(EmergenciaEntity emergencia)
    {
        this.emergencias.add(emergencia);
    }

    public void addMedico(MedicoEntity medico)
    {
        this.medicos.add(medico);
    }
}
