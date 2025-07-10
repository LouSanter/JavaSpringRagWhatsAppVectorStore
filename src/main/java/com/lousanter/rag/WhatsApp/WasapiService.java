package com.lousanter.rag.WhatsApp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Service
public class WasapiService {

    private final String API_KEY = "52472|qDBPitIPygY7Z0Sr8RlLlZUNX6O3b5T53NMtyDjg";
    private final String URL = "https://api-ws.wasapi.io/api/v1/whatsapp-messages";

    public void enviarMensaje(String waId, String fromId, String mensaje) {
        System.out.println("ENVIANDO MENSAJE DESDE WASAPISERVICE");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("wa_id", waId);
        body.put("device", fromId);
        body.put("message", mensaje);
        body.put("message_type", "text");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            System.out.println("Respuesta: " + response.getBody());

        } catch (HttpStatusCodeException e) {
            System.out.println("Error HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}



