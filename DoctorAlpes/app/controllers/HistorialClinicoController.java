package controllers;

import akka.dispatch.MessageDispatcher;
import com.fasterxml.jackson.databind.JsonNode;
import dispatchers.AkkaDispatcher;
import models.EmergenciaEntity;
import models.HistorialClinicoEntity;
import models.PacienteEntity;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.libs.Json.toJson;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;



/**
 * Created by aj.paredes10 on 15/02/2017.
 */
public class HistorialClinicoController extends Controller
    {


        public CompletionStage<Result> getHistorialesClinicos()
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.
                    supplyAsync(() -> { return HistorialClinicoEntity.FINDER.all(); } ,jdbcDispatcher)
                    .thenApply(historiales -> {return ok(toJson(historiales));}
                    );
        }

        public CompletionStage<Result> getHistorialClinico(Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.
                    supplyAsync(() -> { return HistorialClinicoEntity.FINDER.byId(idE); } ,jdbcDispatcher)
                    .thenApply(historial -> {return ok(toJson(historial));}
                    );
        }

        public CompletionStage<Result> createHistorialClinico(Long idPaciente)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
            JsonNode n = request().body().asJson();

            HistorialClinicoEntity historial = Json.fromJson( n , HistorialClinicoEntity.class ) ;
            return CompletableFuture.supplyAsync(
                    ()->{
                        PacienteEntity paciente = PacienteEntity.FINDER.byId(idPaciente);
                        historial.setPaciente(paciente);
                        paciente.setHistorialClinico(historial);
                        System.out.print("AAAAAAA");

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

        public CompletionStage<Result> deleteHistorialClinico(Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;

            return CompletableFuture.supplyAsync(
                    ()->{
                        HistorialClinicoEntity historial = HistorialClinicoEntity.FINDER.byId(idE);
                        historial.delete();
                        return historial;
                    }
            ).thenApply(
                    historiales -> {
                        return ok(Json.toJson(historiales));
                    }
            );
        }
        public CompletionStage<Result> updateHistorialClinico( Long idE)
        {
            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
            JsonNode n = request().body().asJson();
            HistorialClinicoEntity m = Json.fromJson( n , HistorialClinicoEntity.class ) ;
            PacienteEntity paciente = PacienteEntity.FINDER.byId(idE);

            return CompletableFuture.supplyAsync(
                    ()->{
                        HistorialClinicoEntity antiguo = paciente.getHistorialClinico();
                        antiguo.update();
                        return antiguo;
                    }
            ).thenApply(
                    historiales -> {
                        return ok(Json.toJson(historiales));
                    }
            );
        }

//        public CompletionStage<Result> agregarEmergenciaAHistorial(Long idEmergencia, Long idHistorial)
//        {
//            MessageDispatcher jdbcDispatcher = AkkaDispatcher.jdbcDispatcher;
//
//            return CompletableFuture.supplyAsync(
//                    ()->{
//                        HistorialClinicoEntity historial = HistorialClinicoEntity.FINDER.byId(idHistorial);
//                        EmergenciaEntity em = EmergenciaEntity.FINDER.byId(idEmergencia);
//                        historial.addEmergencia(em);
//                        historial.
//                        em.setHistorialClinico(historial);
//                        historial.update();
//                        return historial;
//                    }
//            ).thenApply(
//                    CampoEntity -> {
//                        return ok(Json.toJson(CampoEntity));
//                    }
//            );
//        }



}
