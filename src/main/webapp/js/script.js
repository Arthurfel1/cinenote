document.addEventListener("DOMContentLoaded", function () {
    carregarFilmes();

    const formulario = document.getElementById("formularioFilme");
    const listaFilmes = document.getElementById("listaFilmes");

    // Referência ou criação da mensagem de sucesso
    let mensagemSucesso = document.getElementById("mensagemSucesso");
    if (!mensagemSucesso) {
        mensagemSucesso = document.createElement("p");
        mensagemSucesso.id = "mensagemSucesso";
        formulario.parentNode.insertBefore(mensagemSucesso, listaFilmes);
    }

    formulario.addEventListener("submit", function (e) {
        e.preventDefault();

        const nome = document.getElementById("nomeFilme").value.trim();
        const nota = parseInt(document.getElementById("notaFilme").value, 10);
        const comentario = document.getElementById("comentarioFilme").value.trim();

        if (!nome) {
            alert("O nome do filme é obrigatório.");
            return;
        }

        if (isNaN(nota) || nota < 1 || nota > 10) {
            alert("Informe uma nota válida entre 1 e 10.");
            return;
        }

        const formData = new URLSearchParams();
        formData.append("nome", nome);
        formData.append("nota", nota);
        formData.append("comentario", comentario);

        fetch("adicionarFilme", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData.toString()
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Erro ao salvar filme no servidor.");
            }
        })
        .then(filme => {
            adicionarFilmeNaLista(filme);
            formulario.reset();

            // Mostrar mensagem de sucesso usando classe .show
            mensagemSucesso.textContent = "Filme adicionado com sucesso!";
            mensagemSucesso.classList.add("show");

            setTimeout(() => {
                mensagemSucesso.classList.remove("show");
            }, 3000);
        })
        .catch(error => {
            console.error("Erro na requisição:", error);
            alert(error.message);
        });
    });
});

function carregarFilmes() {
    fetch("api/filmes")
        .then(response => response.json())
        .then(filmes => {
            filmes.forEach(filme => {
                adicionarFilmeNaLista(filme);
            });
        })
        .catch(error => {
            console.error("Erro ao carregar filmes:", error);
            alert("Erro ao carregar lista de filmes.");
        });
}

function adicionarFilmeNaLista(filme) {
    const item = document.createElement("li");
    item.innerHTML = `
        <strong>${filme.nome}</strong> – Nota: ${filme.nota}<br>
        ${filme.comentario ? `<p>${filme.comentario}</p>` : ""}
    `;

    const botaoExcluir = document.createElement("button");
    botaoExcluir.textContent = "Excluir";
    botaoExcluir.addEventListener("click", () => item.remove());

    item.appendChild(botaoExcluir);
    document.getElementById("listaFilmes").appendChild(item);
}
