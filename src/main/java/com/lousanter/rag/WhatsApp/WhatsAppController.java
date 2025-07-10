package com.lousanter.rag.WhatsApp;

import com.lousanter.rag.AI.IAService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class WhatsAppController {

    private final WasapiService wasapiService;
    private final IAService iaService;


    public WhatsAppController(WasapiService wasapiService, IAService iaService) {
        this.wasapiService = wasapiService;
        this.iaService = iaService;
    }
    private final Set<String> mensajesProcesados = ConcurrentHashMap.newKeySet();


    @PostMapping
    public ResponseEntity<String> recibirSms(@RequestBody Map<String, Object> payload) {
        System.out.println("======= MENSAJE LLEGO AL BACKEND =======");
        System.out.println(payload);


        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        if (data == null) return ResponseEntity.badRequest().body("Sin data");

        String wamId = (String) data.get("wam_id");
        if (wamId == null || !mensajesProcesados.add(wamId)) {
            System.out.println("Mensaje duplicado ignorado: " + wamId);
            return ResponseEntity.ok("Ya procesado");
        }


        if (data == null) return ResponseEntity.badRequest().body("Sin data");

        String mensaje = (String) data.get("message");
        String numero = (String) data.get("wa_id");
        String fromId = String.valueOf(data.get("from_id"));
        System.out.println("MENSAJE: " + mensaje);

        if (mensaje == null || numero == null) return ResponseEntity.ok("Mensaje vacio");


        Prompt prompt = iaService.construirPrompt(mensaje);
        String respuestaIA = iaService.procesar(prompt);

        System.out.println("Respuesta IA: " + respuestaIA);

        if (respuestaIA.trim().equalsIgnoreCase("usarVector")) {
            String respuesta = iaService.procesarConVectorStore(mensaje);
            System.out.println("Respuesta vector: " + respuesta);
            wasapiService.enviarMensaje(numero, fromId, respuesta);
        } else {
            wasapiService.enviarMensaje(numero, fromId, respuestaIA);
        }

        return ResponseEntity.ok("Procesado");
    }

}
