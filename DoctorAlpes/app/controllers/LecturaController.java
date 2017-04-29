package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import play.libs.Json;
import play.libs.concurrent.Futures;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class LecturaController extends Controller{

    public CompletionStage<Result> getLecturas()
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return LecturaEntity.FINDER.all(); } ,jdbcDispatcher)
                .thenApply(lecturas -> {return ok(toJson(lecturas));}
                );
    }

    public CompletionStage<Result> getLectura(Long idE)
    {
        MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

        return CompletableFuture.
                supplyAsync(() -> { return LecturaEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                .thenApply(lecturas -> {return ok(toJson(lecturas));}
                );
    }

    public CompletionStage<Result> createLectura(Long idHist)
    {
        JsonNode n = request().body().asJson();

        LecturaEntity lectura = Json.fromJson( n , LecturaEntity.class ) ;
        return CompletableFuture.supplyAsync(
                ()->{
                    HistorialDeMedicionEntity historial = HistorialDeMedicionEntity.FINDER.byId(idHist);
                    lectura.setFecha(new Date());
                    lectura.setHistorialMedicion(historial);
                    PacienteEntity p = historial.getPaciente();
                    historial.addLectura(lectura);
                    lectura.save();
                    p.setEstado(lectura.verificardatos());
                    historial.update();
                    p.update();
                    return lectura;
                }
        ).thenApply(
                lecturas -> {
                    return ok(Json.toJson(lecturas));
                }
        );
    }

    public CompletionStage<Result> deleteLectura(Long idE)
    {

        return CompletableFuture.supplyAsync(
                ()->{
                    LecturaEntity lec = LecturaEntity.FINDER.byId(idE);
                    lec.delete();
                    return lec;
                }
        ).thenApply(
                lecturas -> {
                    return ok(Json.toJson(lecturas));
                }
        );
    }


    public CompletionStage<Result> updateLectura( Long idE)
    {
        JsonNode n = request().body().asJson();
        LecturaEntity m = Json.fromJson( n , LecturaEntity.class ) ;
        LecturaEntity antiguo = LecturaEntity.FINDER.byId(idE);

        return CompletableFuture.supplyAsync(
                ()->{
                    antiguo.setId(m.getId());
                    antiguo.setFrecuenciaCardiaca(m.getFrecuenciaCardiaca());
                    antiguo.setNivelEstres(m.getNivelEstres());
                    antiguo.setPresionDiastolica(m.getPresionDiastolica());
                    antiguo.setPresionSistolica(m.getPresionSistolica());
                    //antiguo.setFecha(m.getFecha());

                    antiguo.update();
                    return antiguo;
                }
        ).thenApply(
                lecturas -> {
                    return ok(Json.toJson(lecturas));
                }
        );
    }

    public CompletionStage<Result> createLecturaCode(Long idHist){

        JsonNode n = request().body().asJson();
        String j = n.toString();
        EncriptadorEntity lectura = Json.fromJson( n , EncriptadorEntity.class ) ;
        String encriptado = lectura.encriptar(j);
        System.out.println(encriptado);

        if(lectura.validar()) {
            System.out.println("EYYYYYYYYYYYY");

            System.out.println(lectura.desencriptar(encriptado));
            LecturaEntity lecturaDesencriptada = Json.fromJson(n, LecturaEntity.class);

            return CompletableFuture.supplyAsync(
                    () -> {
                        HistorialDeMedicionEntity historial = HistorialDeMedicionEntity.FINDER.byId(idHist);

                        lecturaDesencriptada.setFecha(new Date());
                        lecturaDesencriptada.setHistorialMedicion(historial);

                        historial.addLectura(lecturaDesencriptada);
                        lecturaDesencriptada.save();
                        historial.update();
                        return lecturaDesencriptada;

                    }
            ).thenApply(
                    lecturas -> {
                        return ok(Json.toJson(lecturas));
                    }
            );
        }
        else
        {
            return CompletableFuture.supplyAsync(
                    () ->
                    {
                        return "Error con integridad";
                    }
            ).thenApply(
                    lecturas ->
                    {
                        return ok(Json.toJson(lecturas));
                    }
            );
        }
    }


}
