    const chatToggle = document.getElementById('chatToggle');
    const chatBox = document.getElementById('chatBox');
    const input = document.getElementById("userInput");
    const chatMessages = document.getElementById("chatMessages");

    let logicalQueue = [];
    let typingTimeout = null;
    let iaTimeout = null;
    let typingIndicator = null;

    // Mostrar/ocultar chat
    chatToggle.addEventListener('click', () => {
    chatBox.style.display = (chatBox.style.display === 'flex') ? 'none' : 'flex';
});

    // Capturar cada tecla
    input.addEventListener('input', () => {
    resetTimers();
});

    // Enter → agregar mensaje visible y lógico
    input.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
    e.preventDefault();
    handleUserMessage();
}
});

function sendMessage() {
    handleUserMessage();
}

function handleUserMessage() {
    const mensaje = input.value.trim();
    if (mensaje === "") return;

    chatMessages.innerHTML += `<div class="user-msg">🧑 ${mensaje}</div>`;
    chatMessages.scrollTop = chatMessages.scrollHeight;

    logicalQueue.push(mensaje);
    input.value = "";

    resetTimers();
}

    function resetTimers() {
    if (typingTimeout) clearTimeout(typingTimeout);
    if (iaTimeout) clearTimeout(iaTimeout);

    typingTimeout = setTimeout(() => {
    showTyping();
}, 3000);

    iaTimeout = setTimeout(() => {
    if (logicalQueue.length > 0) {
    sendToIA(logicalQueue.join(' '));
    logicalQueue = [];
}
}, 3000);
}

    function showTyping() {
    if (!typingIndicator) {
    typingIndicator = document.createElement('div');
    typingIndicator.classList.add('ia-msg', 'typing');
    typingIndicator.innerText = '🤖 escribiendo...';
    chatMessages.appendChild(typingIndicator);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}
}

    function removeTyping() {
    if (typingIndicator) {
    typingIndicator.remove();
    typingIndicator = null;
}
}

    function sendToIA(mensajeConcatenado) {
        fetch(`/conversar?sms=${encodeURIComponent(mensajeConcatenado)}`)
            .then(response => response.text())
            .then(data => {
                removeTyping();
                typeWriter(data);
            })
            .catch(error => {
                removeTyping();
                chatMessages.innerHTML += `<div class="ia-msg error">⚠️ Error al conectar</div>`;
            });
    }
    function typeWriter(text) {
        const iaBubble = document.createElement('div');
        iaBubble.classList.add('ia-msg');
        iaBubble.innerHTML = '🤖 ';
        chatMessages.appendChild(iaBubble);

        let index = 0;
        const speed = 30;

        function writeChar() {
            if (index < text.length) {
                iaBubble.innerHTML += text.charAt(index);
                index++;
                chatMessages.scrollTop = chatMessages.scrollHeight;
                setTimeout(writeChar, speed);
            }
        }

        writeChar();
    }
