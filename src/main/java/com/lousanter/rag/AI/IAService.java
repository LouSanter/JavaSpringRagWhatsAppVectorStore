package com.lousanter.rag.AI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IAService {

    private final ChatClient.Builder builder;
    private final VectorStore vectorStore;
    private final ChatMemory memoria = MessageWindowChatMemory.builder().build(); // Memoria única

    public IAService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.builder = builder;
        this.vectorStore = vectorStore;
    }

    public String procesar(Prompt prompt) {
        ChatClient client = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memoria).build())
                .build();

        return client.prompt(prompt).call().content();
    }

    public String procesarConVectorStore(String mensaje) {
        ChatClient client = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memoria).build(),
                        new QuestionAnswerAdvisor(vectorStore)
                )
                .build();

        return client.prompt(new Prompt(mensaje)).call().content();
    }



    public Prompt construirPrompt(String sms) {
        String instrucciones = """
        Analiza la intención del usuario.

        Si se requiere razonamiento no estructurado (como búsqueda semántica, análisis de texto o preguntas complejas), responde solo con "usarVector".

        En cualquier otro caso, responde normalmente como un asistente conversacional.

        NO EXPLIQUES lo que harás, solo da la respuesta o "usarVector".
        """;

        PromptTemplate pt = new PromptTemplate("""
        Usuario: "{sms}"

        {instrucciones}
        """);

        return pt.create(Map.of(
                "sms", sms,
                "instrucciones", instrucciones
        ));
    }
}
