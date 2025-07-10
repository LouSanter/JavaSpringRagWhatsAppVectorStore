# WhatsApp Integration with AI Services

Este proyecto integra un servicio de WhatsApp con capacidades de inteligencia artificial utilizando JAVA Spring Boot, Spring AI, y un sistema de gestión de memoria y vectorización de datos. Permite recibir mensajes de WhatsApp, procesarlos mediante un modelo de inteligencia artificial y devolver respuestas en función del análisis de los mensajes o mediante búsqueda semántica usando un **Vector Store**.

para la instalacion de PgVectorStore leer la guia oficial o cargar la imagen docker que deja SpringAI
---

## Características

- **Recibe mensajes desde WhatsApp** y los procesa a través de un modelo de IA.
- **Usa un Vector Store** para realizar búsquedas semánticas cuando sea necesario.
- **Memoria de chat** para almacenar el contexto de la conversación y generar respuestas más naturales.
- **Responde dinámicamente** según el tipo de mensaje recibido detecta la intencion del usuario y se manda intrucciones de manera autonoma
- **Importante:** Para el levantamiento local, usar ngrok y actualizar el webhook de tu api
  
## Tecnologías Utilizadas

- **Spring Boot** para el backend.
- **PostgreSQL** para la BD
- **Spring AI** para la integración con modelos de inteligencia artificial y procesamiento de datos semánticos.
- **VectorStore** para almacenar y recuperar vectores de información para preguntas complejas.
- **WasapiService** para enviar respuestas de vuelta a los usuarios de WhatsApp.


