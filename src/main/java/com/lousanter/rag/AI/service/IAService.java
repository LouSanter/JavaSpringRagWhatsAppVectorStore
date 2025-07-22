package com.lousanter.rag.AI.service;

import com.lousanter.rag.AI.tools.AiTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IAService {


    private ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;

    private final ChatMemory memoria;
    @Autowired
    private AiTools aiTools;


    public IAService(ChatClient.Builder builder, VectorStore vectorStore) {

        this.vectorStore = vectorStore;

        this.memoria = MessageWindowChatMemory.builder().build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .similarityThreshold(0.8d)
                        .topK(20)
                        .build())
                .build();

        this.chatClient = builder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memoria).build(),
                        qaAdvisor
                )
                .build();
    }


    public String procesar(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    public String procesarConVectorStore(String mensaje) {

        String contenido = """
        Eres un agente IA institucional, responde de manera adecuada a la siguiente petición del usuario:
        {mensaje}
        no uses markdown
        """;

        PromptTemplate promptTemplate = new PromptTemplate(contenido);

        Prompt pr = promptTemplate.create(Map.of("mensaje", mensaje));

        return chatClient.prompt(pr)
                .tools(aiTools)
                .call()
                .content();
    }

}
