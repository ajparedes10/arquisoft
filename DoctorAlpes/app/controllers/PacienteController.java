package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;
import models.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class PacienteController extends Controller {

        public CompletionStage<Result> getPacientes()
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.
                    supplyAsync(() -> { return PacienteEntity.FINDER.all(); } ,jdbcDispatcher)
                    .thenApply(pacienteEntities -> {return ok(toJson(pacienteEntities));}
                    );
        }

        public CompletionStage<Result> getPaciente(Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.
                    supplyAsync(() -> { return PacienteEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                    .thenApply(pacientes -> {return ok(toJson(pacientes));}
                    );
        }

        public CompletionStage<Result> createPaciente()
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
            JsonNode n = request().body().asJson();

            PacienteEntity paciente = Json.fromJson( n , PacienteEntity.class ) ;
            return CompletableFuture.supplyAsync(
                    ()->{
                        paciente.save();
                        return paciente;
                    }
            ).thenApply(
                    pacientes -> {
                        return ok(Json.toJson(pacientes));
                    }
            );
        }

        public CompletionStage<Result> deletePaciente(Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.supplyAsync(
                    ()->{
                        PacienteEntity paciente = PacienteEntity.FINDER.byId(idE);
                        paciente.delete();
                        return paciente;
                    }
            ).thenApply(
                    pacientes -> {
                        return ok(Json.toJson(pacientes));
                    }
            );
        }
        public CompletionStage<Result> updatePaciente( Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
            JsonNode n = request().body().asJson();
            PacienteEntity m = Json.fromJson( n , PacienteEntity.class ) ;
            PacienteEntity antiguo = PacienteEntity.FINDER.byId(idE);

            return CompletableFuture.supplyAsync(
                    ()->{
                        antiguo.setId(m.getId());
                        antiguo.setNombre(m.getNombre());
                        antiguo.setEps(m.getEps());
                        antiguo.setEstado(m.getEstado());

                        antiguo.update();
                        return antiguo;
                    }
            ).thenApply(
                    pacientes -> {
                        return ok(Json.toJson(pacientes));
                    }
            );
        }
    }


