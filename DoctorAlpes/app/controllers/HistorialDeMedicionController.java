package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class HistorialDeMedicionController extends Controller
{
    public CompletionStage<Result> getHistorialesDeMedicion()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return HistorialDeMedicionEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(historiales -> {return ok(toJson(historiales));}
                );
    }

    public CompletionStage<Result> getHistorialDeMedicion(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return HistorialDeMedicionEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                .thenApply(historial -> {return ok(toJson(historial));}
                );
    }

    public CompletionStage<Result> createHistorialDeMedicion(Long idPaciente)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode n = request().body().asJson();

        HistorialDeMedicionEntity historial = Json.fromJson( n , HistorialDeMedicionEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    PacienteEntity paciente = PacienteEntity.FINDER.byId(idPaciente);
                    historial.setPaciente(paciente);
                    paciente.setHistorialMediciones(historial);
                    historial.save();
                    paciente.update();
                    return historial;
                }
        ).thenApply(
                historiales -> {
                    return ok(Json.toJson(historiales));
                }
        );
    }

    public CompletionStage<Result> deleteHistorialDeMedicion(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    HistorialDeMedicionEntity historial = HistorialDeMedicionEntity.FINDER.byId(idE);
                    historial.delete();
                    return historial;
                }
        ).thenApply(
                historiales -> {
                    return ok(Json.toJson(historiales));
                }
        );
    }
    public CompletionStage<Result> updateHistorialDeMedicion( Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
        JsonNode n = request().body().asJson();
        HistorialDeMedicionEntity m = Json.fromJson( n , HistorialDeMedicionEntity.class ) ;
        PacienteEntity paciente = PacienteEntity.FINDER.byId(idE);

        return CompletableFuture.supplyAsync(
                ()->{
                    HistorialDeMedicionEntity antiguo = paciente.getHistorialMediciones();
                    antiguo.update();
                    return antiguo;
                }
        ).thenApply(
                historiales -> {
                    return ok(Json.toJson(historiales));
                }
        );
    }

}
