package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="medicoEntity")
public class MedicoEntity extends Model
{
    public static Finder<Long, MedicoEntity> FINDER = new Finder<>(MedicoEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "Medico")
    private Long id;
    private String nombre;
    private String tipo;
    @Column(unique=true)
    private  String correo;
    private  String clave;

    @OneToMany (mappedBy = "medico")
    @JsonManagedReference(value="r2")
    private List<ConsejoEntity> consejos;

    @ManyToMany
    @JsonBackReference(value="r3")
    private List<PacienteEntity> pacientes;

    @OneToMany (mappedBy = "medico")
    @JsonManagedReference(value="r4")
    private List<MarcapasosEntity> marcapasos;

    @OneToMany(mappedBy = "medico")
    @JsonManagedReference(value="r20")
    private List<EmergenciaEntity> emergencias;

    public MedicoEntity()
    {
        id=null;
        tipo="NO NAME";
        nombre="NO NAME";
        consejos = new ArrayList<ConsejoEntity>();
        pacientes = new ArrayList<PacienteEntity>();
        marcapasos = new ArrayList<MarcapasosEntity>();
    }
    public MedicoEntity(Long id)
    {
        this();
        setId(id);
    }
    public MedicoEntity(Long id, String nombre, String tipo)
    {
        this();
        setId(id);
        setNombre(nombre);
        setTipo(tipo);
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ConsejoEntity> getConsejos() {
        return consejos;
    }

    public void setConsejos(List<ConsejoEntity> consejos) {
        this.consejos = consejos;
    }

    public List<PacienteEntity> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PacienteEntity> pacientes) {
        this.pacientes = pacientes;
    }

    public List<MarcapasosEntity> getMarcapasos() {
        return marcapasos;
    }

    public void setMarcapasos(List<MarcapasosEntity> marcapasos) {
        this.marcapasos = marcapasos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void addConsejo(ConsejoEntity consejo)
    {
        this.consejos.add(consejo);
    }

    public void addPaciente(PacienteEntity paciente)
    {
        this.pacientes.add(paciente);
    }

    public void addEmergencia(EmergenciaEntity emergencia)
    {
        this.emergencias.add(emergencia);
    }

    public List<EmergenciaEntity> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<EmergenciaEntity> emergencias) {
        this.emergencias = emergencias;
    }
    public  boolean  authenticate(String pCorreo, String pClave){
            return (correo.equals(pCorreo) && clave.equals(pClave));
    }
}
