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
 * Created by Usuario on 15/02/2017.
 */
public class MarcapasosController extends Controller {

    public CompletionStage<Result> getMarcapasoss()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return MarcapasosEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(marcapasosEntities -> {return ok(toJson(marcapasosEntities));}
                );
    }

    public CompletionStage<Result> getMarcapasos(Long idM)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return MarcapasosEntity.FINDER.byId(idM); } ,jdbcDispatcher)
                .thenApply(marcapasoss -> {return ok(toJson(marcapasoss));}
                );
    }

    public CompletionStage<Result> createMarcapasos()
    {

        JsonNode n = request().body().asJson();

        MarcapasosEntity marcapasos = Json.fromJson( n , MarcapasosEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    marcapasos.save();
                    return marcapasos;
                }
        ).thenApply(
                marcapasoss -> {
                    return ok(Json.toJson(marcapasoss));
                }
        );
    }

    public CompletionStage<Result> deleteMarcapasos(Long idE)
    {


        return CompletableFuture.supplyAsync(
                ()->{
                    MarcapasosEntity marcapasos = MarcapasosEntity.FINDER.byId(idE);
                    marcapasos.delete();
                    return marcapasos;
                }
        ).thenApply(
                marcapasoss -> {
                    return ok(Json.toJson(marcapasoss));
                }
        );
    }
    public CompletionStage<Result> updateMarcapasos( Long idP, Long idM)
    {

        JsonNode n = request().body().asJson();
        MarcapasosEntity m = Json.fromJson( n , MarcapasosEntity.class ) ;
        PacienteEntity paciente = PacienteEntity.FINDER.byId(idP);
        MedicoEntity medico = MedicoEntity.FINDER.byId(idM);

        return CompletableFuture.supplyAsync(
                ()->{
                    if(medico != null && medico.getTipo().equals("Cardiologia"))
                    {
                        MarcapasosEntity antiguo = paciente.getMarcapasos();
                        antiguo.setId(m.getId());
                        antiguo.setIntervaloAV(m.getIntervaloAV());
                        antiguo.setFrecuencia(m.getFrecuencia());
                        antiguo.update();
                        return antiguo;
                    }
                    else
                    {
                        return "No tiene autorizacion de cambiar la configuracion del marcapasos";
                    }
                }
        ).thenApply(
                marcapasoss -> {
                    return ok(Json.toJson(marcapasoss));
                }
        );
    }

    public CompletionStage<Result> createMarcapasosPaciente(Long idPaciente){

        JsonNode n = request().body().asJson();

        MarcapasosEntity marcapasos = Json.fromJson( n , MarcapasosEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    PacienteEntity paciente = PacienteEntity.FINDER.byId(idPaciente);
                    paciente.setMarcapasos(marcapasos);

                    marcapasos.save();
                    paciente.update();
                    return marcapasos;
                }
        ).thenApply(
                marcapasoss -> {
                    return ok(Json.toJson(marcapasoss));
                }
        );
    }
}
