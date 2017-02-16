package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;
import java.util.Date;
import play.libs.Json;
import static play.libs.Json.toJson;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class ConsejoController extends Controller{

    public CompletionStage<Result> getConsejos()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return ConsejoEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(consejos -> {return ok(toJson(consejos));}
                );
    }

    public CompletionStage<Result> getConsejo(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return ConsejoEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                .thenApply(consejos -> {return ok(toJson(consejos));}
                );
    }

    public CompletionStage<Result> createConsejo(Long idMedico, Long idHist)
    {
        JsonNode n = request().body().asJson();

        ConsejoEntity consejo = Json.fromJson( n , ConsejoEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    MedicoEntity medico = MedicoEntity.FINDER.byId(idMedico);
                    HistorialClinicoEntity historial = HistorialClinicoEntity.FINDER.byId(idHist);
                    consejo.setFecha(new Date());
                    consejo.setMedico(medico);
                    consejo.setHistorialClinico(historial);
                    medico.addConsejo(consejo);
                    historial.addConsejo(consejo);
                    consejo.save();
                    medico.update();
                    return consejo;
                }
        ).thenApply(
                consejos -> {
                    return ok(Json.toJson(consejos));
                }
        );
    }

    public CompletionStage<Result> deleteConsejo(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.supplyAsync(
                ()->{
                    ConsejoEntity consejo = ConsejoEntity.FINDER.byId(idE);
                    consejo.delete();
                    return consejo;
                }
        ).thenApply(
                consejos -> {
                    return ok(Json.toJson(consejos));
                }
        );
    }
    public CompletionStage<Result> updateConsejo( Long idE)
    {
        JsonNode n = request().body().asJson();
        ConsejoEntity m = Json.fromJson( n , ConsejoEntity.class ) ;
        ConsejoEntity antiguo = ConsejoEntity.FINDER.byId(idE);

        return CompletableFuture.supplyAsync(
                ()->{
                    antiguo.setId(m.getId());
                    antiguo.setMensaje(m.getMensaje());
                    //antiguo.setFecha(m.getFecha());
                    antiguo.setTipo(m.getTipo());

                    antiguo.update();
                    return antiguo;
                }
        ).thenApply(
                consejos -> {
                    return ok(Json.toJson(consejos));
                }
        );
    }
}
