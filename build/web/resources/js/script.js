document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formFilme");

    form.addEventListener("submit", function(event) {
        const nomeFilme = document.getElementById("nomeFilme").value;
        const notaFilme = document.getElementById("notaFilme").value;
        const comentarioFilme = document.getElementById("comentarioFilme").value;

        if (!nomeFilme || !notaFilme || !comentarioFilme) {
            alert("Por favor, preencha todos os campos.");
            event.preventDefault();  // Impede o envio do formulário
        }

        if (notaFilme < 1 || notaFilme > 5) {
            alert("A nota deve ser entre 1 e 5.");
            event.preventDefault();  // Impede o envio do formulário
        }
    });
});
