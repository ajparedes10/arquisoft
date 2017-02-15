package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="historialDeMedicionEntity")
public class HistorialDeMedicionEntity extends Model
{
    public static Finder<Long, HistorialDeMedicionEntity> FINDER = new Finder<>(HistorialDeMedicionEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "HistorialDeMedicion")
    private Long id;

    @OneToMany(mappedBy = "historialDeMedicion")
    @JsonManagedReference(value="r1")
    private List<LecturaEntity> lecturas;

    @OneToOne
    @JsonBackReference(value="r6")
    private PacienteEntity paciente;

    public HistorialDeMedicionEntity()
    {
        this.lecturas = new ArrayList<LecturaEntity>();
        this.id=null;
    }
    public HistorialDeMedicionEntity(Long id)
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

    public List<LecturaEntity> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<LecturaEntity> lecturas) {
        this.lecturas = lecturas;
    }

    public void setPaciente(PacienteEntity paciente) {

        this.paciente = paciente;
    }

    public void addLectura(LecturaEntity lectura)
    {
        this.lecturas.add(lectura);
    }
}
