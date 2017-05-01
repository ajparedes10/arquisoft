package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.libs.Json.toJson;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class MedicoController extends Controller{

    public CompletionStage<Result> getMedicoPacientes(Long id) {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return MedicoEntity.FINDER.byId(id).getPacientes(); } ,jdbcDispatcher)
                .thenApply(pacientes -> {return ok(toJson(pacientes));}
                );
    }

    public CompletionStage<Result> getMedicos()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return MedicoEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(medicoEntities -> {return ok(toJson(medicoEntities));}
                );
    }

    public CompletionStage<Result> getMedico(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return MedicoEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                .thenApply(medicos -> {return ok(toJson(medicos));}
                );
    }

    public CompletionStage<Result> getMedicoCorreo(String correo)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        return CompletableFuture.
               supplyAsync(() -> { return MedicoEntity.FINDER.where().ilike("correo", "%"+correo+"%").findList(); } ,jdbcDispatcher)
                .thenApply(medicos -> {return ok(toJson(medicos));}
                );
    }

    public CompletionStage<Result> createMedico()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode n = request().body().asJson();

        MedicoEntity medico = Json.fromJson( n , MedicoEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    medico.save();
                    return medico;
                }
        ).thenApply(
                medicos -> {
                    return ok(Json.toJson(medicos));
                }
        );
    }

    public CompletionStage<Result> deleteMedico(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    MedicoEntity medico = MedicoEntity.FINDER.byId(idE);
                    medico.delete();
                    return medico;
                }
        ).thenApply(
                medicos -> {
                    return ok(Json.toJson(medicos));
                }
        );
    }
    public CompletionStage<Result> updateMedico( Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode n = request().body().asJson();
        MedicoEntity m = Json.fromJson( n , MedicoEntity.class ) ;
        MedicoEntity antiguo = MedicoEntity.FINDER.byId(idE);

        return CompletableFuture.supplyAsync(
                ()->{
                    antiguo.setId(m.getId());
                    antiguo.setNombre(m.getNombre());
                    antiguo.setTipo(m.getTipo());

                    antiguo.update();
                    return antiguo;
                }
        ).thenApply(
                medicos -> {
                    return ok(Json.toJson(medicos));
                }
        );
    }

    public CompletionStage<Result> agregarPacienteAMedico(Long idPaciente, Long idMedico)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                   // Long id = Long.getLong(idPaciente.split("=")[1]);
                    MedicoEntity medico = MedicoEntity.FINDER.byId(idMedico);
                    PacienteEntity paciente = PacienteEntity.FINDER.byId(idPaciente);
                    medico.addPaciente(paciente);
                    paciente.addMedico(medico);
                    medico.update();
                    paciente.update();
                    return medico;
                }
        ).thenApply(
                CampoEntity -> {
                    return ok(Json.toJson(CampoEntity));
                }
        );
    }
//    public CompletionStage<Result> agregarConsejoAMedico(Long idConsejo, Long idMedico)
//    {
//        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
//
//        return CompletableFuture.supplyAsync(
//                ()->{
//                    MedicoEntity medico = MedicoEntity.FINDER.byId(idMedico);
//                    ConsejoEntity cons = ConsejoEntity.FINDER.byId(idConsejo);
//                    medico.addConsejo(cons);
//                    cons.setMedico(medico);
//                    medico.update();
//                    return medico;
//                }
//        ).thenApply(
//                CampoEntity -> {
//                    return ok(Json.toJson(CampoEntity));
//                }
//        );
//    }
//
}
