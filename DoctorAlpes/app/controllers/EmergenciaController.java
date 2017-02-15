package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class EmergenciaController extends Controller{

    public CompletionStage<Result> getEmergencias()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return EmergenciaEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(emergencias -> {return ok(toJson(emergencias));}
                );
    }

    public CompletionStage<Result> getEmergencia(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return EmergenciaEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                .thenApply(emergencias -> {return ok(toJson(emergencias));}
                );
    }

    public CompletionStage<Result> createEmergencia(Long idHist, Long idPaci)
    {
        JsonNode n = request().body().asJson();

        EmergenciaEntity emer = Json.fromJson( n , EmergenciaEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    HistorialClinicoEntity historial = HistorialClinicoEntity.FINDER.byId(idHist);
                    PacienteEntity paciente = PacienteEntity.FINDER.byId(idPaci);
                    emer.setFecha(new Date());
                    emer.setHistorialClinico(historial);
                    emer.setPaciente(paciente);
                    historial.addEmergencia(emer);
                    paciente.addEmergencia(emer);
                    emer.save();
                    return emer;
                }
        ).thenApply(
                emergencias -> {
                    return ok(Json.toJson(emergencias));
                }
        );
    }

    public CompletionStage<Result> deleteEmergencia(Long idE)
    {
        return CompletableFuture.supplyAsync(
                ()->{
                    EmergenciaEntity eme = EmergenciaEntity.FINDER.byId(idE);
                    eme.delete();
                    return eme;
                }
        ).thenApply(
                emergencias -> {
                    return ok(Json.toJson(emergencias));
                }
        );
    }
    public CompletionStage<Result> updateEmergencia( Long idE)
    {
        JsonNode n = request().body().asJson();
        EmergenciaEntity m = Json.fromJson( n , EmergenciaEntity.class ) ;
        EmergenciaEntity antiguo = EmergenciaEntity.FINDER.byId(idE);

        return CompletableFuture.supplyAsync(
                ()->{
                    antiguo.setId(m.getId());
                    antiguo.setDescripcion(m.getDescripcion());
                    antiguo.setUbicacion(m.getUbicacion());
                    //antiguo.setFecha(m.getFecha());

                    antiguo.update();
                    return antiguo;
                }
        ).thenApply(
                emergencias -> {
                    return ok(Json.toJson(emergencias));
                }
        );
    }
}
