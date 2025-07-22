
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Burbuja de Chat</title>
    <link rel="stylesheet" href="style.css" />
    <!-- Prism.js CSS para tema oscuro -->
    <link href="https://cdn.jsdelivr.net/npm/prismjs@1.29.0/themes/prism-okaidia.min.css" rel="stylesheet">
    <!-- Prism.js tema oscuro (okaidia) -->
    <link href="https://cdn.jsdelivr.net/npm/prismjs@1.29.0/themes/prism-okaidia.min.css" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">


</head>
<body>
<!-- Esquema Principal -->
<nav>
    <h1 id="titulo">Agente AI para Institución</h1>
</nav>

<main>
    <section>
        <h2>DESARROLLO DE AGENTE AI USANDO LA TECNICA RAG (Recuperación Aumentada de Generación)</h2>

        <h3>Autores: Ing. Teofilo Roberto Correa Calle y Estudiante de Ingeneria de Sistemas e informatica Lucas Edu Sanchez Calle</h3>


        <h2>¿Cómo funciona este Agente?</h2>
        <p>
            Primero que nada, entendamos la diferencia entre un ChatBot convencional y un Agente AI. Mientras que un ChatBot sigue un conjunto de reglas predefinidas y tiene un conocimiento limitado, un Agente AI es capaz de aprender y adaptarse a nuevas situaciones, proporcionando respuestas más relevantes y contextuales.
        </p>
        <br>
        <p>El contexto de este agente (Informacion con la que alimentaremos) se llena a travez de informacion que vamos a vectorizar, es por ello que usaremos embeddings. para posteriormente aplicar RAG (Recuperación Aumentada de Generación). que es una tecnica avanzada de procesamiento de lenguaje natural.</p>

        <h2>Datos para el Agente</h2>
        <p>
            En esta demo estamos usando datos de ejemplo para realizar consultas y ver cómo el agente acierta en cada una de sus respuestas. ¿De dónde sacaremos la información? Utilizaremos contenido simulado desde una pequeña base de datos.
        </p>

        <h2>Tecnologías usadas</h2>
        <ul>
            <li>Java</li>
            <li>Spring Boot</li>
            <li>Spring Data JPA</li>
            <li>OpenAI API</li>
            <img width="800" src="${pageContext.request.contextPath}/rsrc/price.png" alt="">
            <li>Spring AI</li>
            <li>PostgreSQL</li>
            <li>pgVector</li>
        </ul>

        <h3>Para la demostración visual</h3>
        <ul>
            <li>Spring Web</li>
            <li>JSP</li>
            <li>Tomcat Jasper</li>
            <li>html, css, js</li>
        </ul>
        <br>
        <h4>NOTAS</h4>
        <p>Para tema de produccion se recomienda usar tecnicas avanzadas en Base de Datos como la fragmentacion.</p>
        <h4>El proyecto primero fue presentado usando una api de WhatsApp, pero por costes, se migro a un chat en html</h4>

        <h2>PREPARACION DE ENTORNO</h2>
        <p>Bien. como primer paso tenemos que indexar la informacion. Claro si es que comenzamos con la base de datos llena, si comenzaramos con una base de datos vacia, bastaria con un que la logica de negocio de encargue de indexarla a medida que la hacemos crecer</p>
        <h3>ENTIDADES RELACIONALES</h3>
        <ul>
            <li>Carrera</li>
            <li>Curso</li>
            <li>Profesor</li>
            <li>Estudiante</li>
            <li>Ciclo</li>
            <li>Estudiante</li>
        </ul>
        <img src="${pageContext.request.contextPath}/rsrc/ERD.png" alt="">
        <h5>Se puede observar una tabla sin relaciones. es la VectorStore la tabla vectorial, ya hablaremos de ello mas adelante</h5>
        <p></p>

        <h3>ARQUITECTURA</h3>
        <img src="${pageContext.request.contextPath}/rsrc/rag.png" alt="">

        <h2>INFORMACION PARA SIMULAR</h2>
        <pre><code class="language-sql">
-- DATOS PARA Carrera (con ID explícito)
INSERT INTO carrera (id, nombre, duracion) VALUES
(1, 'Ingeniería de Sistemas', 10),
(2, 'Administración de Empresas', 8),
(3, 'Contabilidad', 8),
(4, 'Ingeniería Industrial', 10),
(5, 'Psicología', 10),
(6, 'Derecho', 12),
(7, 'Medicina', 14),
(8, 'Arquitectura', 12),
(9, 'Diseño Gráfico', 8),
(10, 'Marketing', 8);

-- DATOS PARA Ciclo
INSERT INTO ciclo (nombre, fechainicio, fechafin) VALUES
('2021-I', '2021-03-01', '2021-07-31'),
('2021-II', '2021-08-01', '2021-12-31'),
('2022-I', '2022-03-01', '2022-07-31'),
('2022-II', '2022-08-01', '2022-12-31'),
('2023-I', '2023-03-01', '2023-07-31'),
('2023-II', '2023-08-01', '2023-12-31'),
('2024-I', '2024-03-01', '2024-07-31'),
('2024-II', '2024-08-01', '2024-12-31'),
('2025-I', '2025-03-01', '2025-07-31'),
('2025-II', '2025-08-01', '2025-12-31');

-- DATOS PARA Curso
INSERT INTO curso (id, nombre, creditos) VALUES
(1, 'Matemática Básica', '4'),
(2, 'Fundamentos de Programación', '4'),
(3, 'Estadística I', '3'),
(4, 'Contabilidad General', '3'),
(5, 'Derecho Civil', '4'),
(6, 'Psicología del Desarrollo', '3'),
(7, 'Diseño Web', '3'),
(8, 'Marketing Digital', '3'),
(9, 'Administración General', '4'),
(10, 'Redes de Computadoras', '4');

-- DATOS PARA Profesor
INSERT INTO profesor (id, nombrecompleto, email, especialidad) VALUES
(1, 'Juan Pérez', 'juan.perez@uni.edu.pe', 'Matemáticas'),
(2, 'Laura Sánchez', 'laura.sanchez@uni.edu.pe', 'Psicología'),
(3, 'Carlos Torres', 'carlos.torres@uni.edu.pe', 'Derecho'),
(4, 'María Ramos', 'maria.ramos@uni.edu.pe', 'Diseño Gráfico'),
(5, 'Luis Aguilar', 'luis.aguilar@uni.edu.pe', 'Contabilidad'),
(6, 'Patricia Díaz', 'patricia.diaz@uni.edu.pe', 'Administración'),
(7, 'Pedro Romero', 'pedro.romero@uni.edu.pe', 'Sistemas'),
(8, 'Andrea Vega', 'andrea.vega@uni.edu.pe', 'Redes'),
(9, 'Sergio Castro', 'sergio.castro@uni.edu.pe', 'Estadística'),
(10, 'Mónica Salas', 'monica.salas@uni.edu.pe', 'Marketing');

-- DATOS PARA Estudiante (usaremos 10 carreras)
DO $$
DECLARE
    i INT;
    nombre TEXT;
    email TEXT;
    fecha TEXT;
    carreraid INT;
BEGIN
    FOR i IN 1..100 LOOP
        nombre := 'Estudiante ' || i;
        email := 'estudiante' || i || '@gmail.com';
        fecha := '2021-03-01';
        carreraid := ((i - 1) % 10) + 1;
        INSERT INTO estudiante (nombrecompleto, email, fechaingreso, idcarrera)
        VALUES (nombre, email, fecha, carreraid);
    END LOOP;
END $$;

-- DATOS PARA HistorialAcademico (cada estudiante tiene 3 registros)
DO $$
DECLARE
    i INT;
    curso_id INT;
    ciclo_id INT;
    profesor_id INT;
    nota NUMERIC;
BEGIN
    FOR i IN 1..100 LOOP
        FOR curso_id IN 1..3 LOOP
            ciclo_id := ((i - 1) % 10) + 1;
            profesor_id := ((curso_id - 1) % 10) + 1;
            nota := ROUND((random()*10)::NUMERIC, 2);
            INSERT INTO historialacademico (idestudiante, idcurso, idciclo, idprofesor, nota)
            VALUES (i, curso_id, ciclo_id, profesor_id, nota);
        END LOOP;
    END LOOP;
END $$;
</code></pre>



        <h2>SERVICIO JAVA PARA INDEXACION</h2>
        <p>Desarrollamos un metodo para indexar todo el contenido de la tabla, una sola vez.</p>

        <pre><code class="language-java">
&#64;Service
public class RagService {

    &#64;Autowired
    private CarreraRepo carreraRepo;
    &#64;Autowired
    private CicloRepo cicloRepo;
    &#64;Autowired
    private CursoRepo cursoRepo;
    &#64;Autowired
    private EstudianteRepo estudianteRepo;
    &#64;Autowired
    private ProfesorRepo profesorRepo;
    &#64;Autowired
    private VectorStore vectorStore;
    &#64;Autowired
    private HistorialAcademicoRepo historialRepo;

    public void indexarTodo() {
        indexarHistorialAcademicoExistente();
    }

    public void indexarHistorialAcademicoExistente() {
        List&lt;HistorialAcademico&gt; historiales = historialRepo.findAll();

        List&lt;Document&gt; documentos = historiales.stream().map(ha -&gt; {

            Estudiante estudiante = estudianteRepo.findById(ha.getEstudiante().getIdEstudiante()).orElse(null);
            Curso curso = cursoRepo.findById(ha.getCurso().getIdCurso()).orElse(null);
            Ciclo ciclo = cicloRepo.findById(ha.getCiclo().getIdCiclo()).orElse(null);
            Profesor profesor = profesorRepo.findById(ha.getProfesor().getIdProfesor()).orElse(null);

            ha.setEstudiante(estudiante);
            ha.setCurso(curso);
            ha.setCiclo(ciclo);
            ha.setProfesor(profesor);

            String contenido = String.format("""
            Historial Académico:
            ID: %s
            Estudiante: %s
            Curso: %s
            Ciclo: %s
            Profesor: %s
            Nota: %s
            """,
                    ha.getIdHistorialAcademico(),
                    estudiante != null ? estudiante.getNombreCompleto() : "N/A",
                    curso != null ? curso.getNombre() : "N/A",
                    ciclo != null ? ciclo.getNombre() : "N/A",
                    profesor != null ? profesor.getNombreCompleto() : "N/A",
                    ha.getNota() != null ? ha.getNota().toString() : "Sin nota"
            );

            return Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(contenido)
                    .metadata(Map.of(
                            "tipo", "historial",
                            "idHistorialAcademico", ha.getIdHistorialAcademico()
                    ))
                    .build();
        }).toList();

        vectorStore.add(documentos);
    }

    public void indexarCarreras() {
        List&lt;Carrera&gt; carreras = carreraRepo.findAll();

        for (Carrera c : carreras) {
            String texto = String.format(
                    "idCarrera: %s%nNombre: %s%nDuración: %d ciclos",
                    c.getIdCarrera(), c.getNombre(), c.getDuracion()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "carrera",
                            "idCarrera", c.getIdCarrera()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarCiclos() {
        List&lt;Ciclo&gt; ciclos = cicloRepo.findAll();

        for (Ciclo c : ciclos) {
            String texto = String.format(
                    "idCiclo: %s%nnombre: %s%nfechaInicio: %s%nfechaFin: %s",
                    c.getIdCiclo(), c.getNombre(), c.getFechaInicio(), c.getFechaFin()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "ciclo",
                            "idCiclo", c.getIdCiclo()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarCursos() {
        List&lt;Curso&gt; cursos = cursoRepo.findAll();

        for (Curso c : cursos) {
            String texto = String.format(
                    "idCurso: %s%nnombre: %s%ncreditos: %s",
                    c.getIdCurso(), c.getNombre(), c.getCreditos()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "curso",
                            "idCurso", c.getIdCurso()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarProfesores() {
        List&lt;Profesor&gt; profesores = profesorRepo.findAll();

        for (Profesor p : profesores) {
            String texto = String.format("""
                Profesor:
                ID: %s
                Nombre completo: %s
                Email: %s
                Especialidad: %s
                """,
                    p.getIdProfesor(), p.getNombreCompleto(), p.getEmail(), p.getEspecialidad()
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "profesor",
                            "idProfesor", p.getIdProfesor()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }

    public void indexarEstudiantes() {
        List&lt;Estudiante&gt; estudiantes = estudianteRepo.findAll();

        for (Estudiante e : estudiantes) {
            Carrera carrera = e.getCarrera();

            String texto = String.format(
                    "idEstudiante: %s%nNombre: %s%nEmail: %s%nFechaIngreso: %s%nCarrera: %s",
                    e.getIdEstudiante(), e.getNombreCompleto(), e.getEmail(),
                    e.getFechaIngreso(), carrera != null ? carrera.getNombre() : "Sin carrera"
            );

            Document doc = Document.builder()
                    .id(UUID.randomUUID().toString())
                    .text(texto)
                    .metadata(Map.of(
                            "tipo", "estudiante",
                            "idEstudiante", e.getIdEstudiante()
                    ))
                    .build();

            vectorStore.add(List.of(doc));
        }
    }
}
</code></pre>

        <h2>CONFIGURACION DE LA LOGICA DE LA IA</h2>
        <p>Esta sección se encarga de la configuración y gestión de la lógica de la inteligencia artificial en la aplicación.
            Esta clase utiliza el cliente de chat para interactuar con la IA y procesar los mensajes, ya sea directamente o utilizando un almacén de vectores para mejorar las respuestas.</p>
        </p>


        <pre> <code class="language-java">
    @Service
public class IAService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatMemory memoria = MessageWindowChatMemory.builder().build();
    @Autowired
    private AiTools aiTools;

    public IAService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(memoria).build()).build();
        this.vectorStore = vectorStore;
    }

    public String procesar(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    public String procesarConVectorStore(String mensaje) {

        return chatClient.prompt(mensaje)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .tools(aiTools)
                .call()
                .content();

    }
}

</code></pre>
        <h2>TOOLS</h2>
        <p>Las tools son una herramienta importante para poder acceder a codigo sin nesecidad de interaccion humana
        en este caso tenemos las iatools
        </p>
        <pre>
  <code class="language-java">
@Component
public class AiTools &#123;

    @Autowired
    EstudianteRepo estudianteRepo;
    @Autowired
    CicloRepo cicloRepo;
    @Autowired
    CursoRepo cursoRepo;
    @Autowired
    ProfesorRepo profesorRepo;
    @Autowired
    HistorialAcademicoRepo historialAcademicoRepo;

    @Tool(description = &quot;Contar todos los estudiantes disponibles&quot;)
    long contarEstudiantes() &#123;
        return estudianteRepo.count();
    &#125;

    @Tool(description = &quot;Contar todos los cursos registrados&quot;)
    long contarCursos() &#123;
        return cursoRepo.count();
    &#125;

    @Tool(description = &quot;Contar todos los profesores registrados&quot;)
    long contarProfesores() &#123;
        return profesorRepo.count();
    &#125;

    @Tool(description = &quot;Listar todos los ciclos académicos disponibles&quot;)
    List&lt;Ciclo&gt; listarCiclos() &#123;
        return cicloRepo.findAll();
    &#125;

    @Tool(description = &quot;Listar estudiantes de un ciclo específico. Requiere el nombre del ciclo como parámetro.&quot;)
    List&lt;Estudiante&gt; estudiantesPorCiclo(String nombreCiclo) &#123;
        return estudianteRepo.findEstudiantesPorCiclo(nombreCiclo);
    &#125;

    @Tool(description = &quot;Buscar cursos que coincidan con un nombre parcial. Requiere parte del nombre como parámetro.&quot;)
    List&lt;Curso&gt; buscarCursosPorNombre(String nombreParcial) &#123;
        return cursoRepo.buscarPorNombre(nombreParcial);
    &#125;

    @Tool(description = &quot;Listar el historial académico de un estudiante. Requiere el ID del estudiante.&quot;)
    List&lt;HistorialAcademico&gt; historialDeEstudiante(Long estudianteId) &#123;
        return historialAcademicoRepo.findByEstudianteIdEstudiante(estudianteId);
    &#125;

    @Tool(description = &quot;Calcular el promedio de notas de un estudiante. Requiere el ID del estudiante.&quot;)
    double promedioNotas(Long estudianteId) &#123;
        List&lt;HistorialAcademico&gt; historial = historialAcademicoRepo.findByEstudianteIdEstudiante(estudianteId);
        if (historial.isEmpty()) return 0.0;
        double suma = historial.stream()
                .mapToDouble(h -&gt; h.getNota().doubleValue())
                .sum();
        return Math.round((suma / historial.size()) * 100.0) / 100.0;
    &#125;

&#125;
  </code>
</pre>


    <h2>BIEN, ESO ES LO PRINCIPAL, YA PUEDES USAR EL AGENTE</h2>
        <div class="bg-gray-900 min-h-screen text-white p-6">
            <h3 class="text-2xl font-bold mb-6 text-center text-green-400">ALGUNOS DATOS...</h3>

            <!-- Tabla de Carreras -->
            <div class="overflow-x-auto mb-8">
                <h3 class="text-xl mb-2 text-white">🎓 Carreras</h3>
                <table class="min-w-full bg-gray-800 shadow-md rounded-lg">
                    <thead class="bg-gray-700 text-green-300">
                    <tr>
                        <th class="py-2 px-4">ID</th>
                        <th class="py-2 px-4">Nombre</th>
                        <th class="py-2 px-4">Duración</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="carrera" items="${carreras}">
                        <tr class="border-b border-gray-600 hover:bg-gray-700">
                            <td class="py-2 px-4">${carrera.idCarrera}</td>
                            <td class="py-2 px-4">${carrera.nombre}</td>
                            <td class="py-2 px-4">${carrera.duracion}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Tabla de Cursos -->
            <div class="overflow-x-auto mb-8">
                <h3 class="text-xl mb-2 text-white">📘 Cursos</h3>
                <table class="min-w-full bg-gray-800 shadow-md rounded-lg">
                    <thead class="bg-gray-700 text-blue-300">
                    <tr>
                        <th class="py-2 px-4">ID</th>
                        <th class="py-2 px-4">Nombre</th>
                        <th class="py-2 px-4">Créditos</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="curso" items="${cursos}">
                        <tr class="border-b border-gray-600 hover:bg-gray-700">
                            <td class="py-2 px-4">${curso.idCurso}</td>
                            <td class="py-2 px-4">${curso.nombre}</td>
                            <td class="py-2 px-4">${curso.creditos}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Tabla de Profesores -->
            <div class="overflow-x-auto mb-8">
                <h3 class="text-xl mb-2 text-white">👨‍🏫 Profesores</h3>
                <table class="min-w-full bg-gray-800 shadow-md rounded-lg">
                    <thead class="bg-gray-700 text-purple-300">
                    <tr>
                        <th class="py-2 px-4">ID</th>
                        <th class="py-2 px-4">Nombre</th>
                        <th class="py-2 px-4">Email</th>
                        <th class="py-2 px-4">Especialidad</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="profesor" items="${profesores}">
                        <tr class="border-b border-gray-600 hover:bg-gray-700">
                            <td class="py-2 px-4">${profesor.idProfesor}</td>
                            <td class="py-2 px-4">${profesor.nombreCompleto}</td>
                            <td class="py-2 px-4">${profesor.email}</td>
                            <td class="py-2 px-4">${profesor.especialidad}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Tabla de Historial Académico -->
            <div class="overflow-x-auto mb-8">
                <h3 class="text-xl mb-2 text-white">📑 Historial Académico</h3>
                <table class="min-w-full bg-gray-800 shadow-md rounded-lg">
                    <thead class="bg-gray-700 text-yellow-300">
                    <tr>
                        <th class="py-2 px-4">Estudiante</th>
                        <th class="py-2 px-4">Curso</th>
                        <th class="py-2 px-4">Ciclo</th>
                        <th class="py-2 px-4">Profesor</th>
                        <th class="py-2 px-4">Nota</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="h" items="${historiales}">
                        <tr class="border-b border-gray-600 hover:bg-gray-700">
                            <td class="py-2 px-4">${h.estudiante.nombreCompleto}</td>
                            <td class="py-2 px-4">${h.curso.nombre}</td>
                            <td class="py-2 px-4">${h.ciclo.nombre}</td>
                            <td class="py-2 px-4">${h.profesor.nombreCompleto}</td>
                            <td class="py-2 px-4">${h.nota}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


        </div>




    </section>
</main>



<!-- Burbuja flotante -->
<div class="chat-bubble" id="chatToggle">
    <svg viewBox="0 0 24 24">
        <path d="M12 3C7.03 3 3 6.85 3 11.3c0 2.3 1.2 4.4 3.1 5.9L5 21l4.3-1.5c.9.2 1.9.3 2.7.3 4.97 0 9-3.85 9-8.3S16.97 3 12 3z"/>
    </svg>
</div>

<!-- Caja de chat -->
<div class="chat-box" id="chatBox">
    <div class="chat-box-header">Asistente Virtual</div>
    <div class="chat-box-body" id="chatMessages">
        <p><em>Hola 👋 ¿En qué puedo ayudarte?</em></p>
    </div>
    <div class="chat-box-input">
        <input type="text" id="userInput" placeholder="Escribe tu mensaje..." />
        <button onclick="sendMessage()">Enviar</button>
    </div>
</div>



<script src="${pageContext.request.contextPath}/rsrc/script.js"></script>
<!-- Prism.js Script -->
<script src="https://cdn.jsdelivr.net/npm/prismjs@1.29.0/prism.min.js"></script>
<!-- Carga el lenguaje SQL -->
<script src="https://cdn.jsdelivr.net/npm/prismjs@1.29.0/components/prism-sql.min.js"></script>
<!-- Prism.js y componente Java -->
<script src="https://cdn.jsdelivr.net/npm/prismjs@1.29.0/components/prism-java.min.js"></script>



</body>
</html>
