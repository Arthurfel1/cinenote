document.addEventListener("DOMContentLoaded", function () { 
    carregarFilmesDoServidor();
    carregarSessoesDoServidor();

    const formulario = document.getElementById("formularioFilme");
    const mensagemSucesso = document.getElementById("mensagemSucesso");
    const formularioSessao = document.getElementById("formularioSessao");
    const mensagemSucessoSessao = document.getElementById("mensagemSucessoSessao");

    if (formulario) {
        formulario.addEventListener("submit", function (e) {
            e.preventDefault();

            const nome = document.getElementById("nomeFilme")?.value.trim() || "";
            const atores = document.getElementById("atoresFilme")?.value.trim() || "";
            const genero = document.getElementById("generoFilme")?.value.trim() || "";
            const sinopse = document.getElementById("sinopseFilme")?.value.trim() || "";
            const notaRaw = document.getElementById("notaFilme")?.value;
            const nota = parseInt(notaRaw, 10);
            const comentario = document.getElementById("comentarioFilme")?.value.trim() || "";

            if (!nome || !atores || !genero || isNaN(nota) || nota < 1 || nota > 10) {
                alert("Preencha todos os campos obrigatórios corretamente.");
                return;
            }

            const formData = new URLSearchParams();
            formData.append("nome", nome);
            formData.append("atores", atores);
            formData.append("genero", genero);
            formData.append("sinopse", sinopse);
            formData.append("nota", nota);
            formData.append("comentario", comentario);

            fetch("adicionarFilme", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: formData.toString()
            })
            .then(response => {
                if (!response.ok) throw new Error("Erro ao salvar filme no servidor.");
                return response.json();
            })
            .then(filmeSalvo => {
                salvarFilmeNaSessao(filmeSalvo);
                adicionarFilmeNaLista(filmeSalvo);
                carregarFilmesParaSessao();
                formulario.reset();

                if (mensagemSucesso) {
                    mensagemSucesso.textContent = "Filme adicionado com sucesso!";
                    mensagemSucesso.style.display = "block";
                    setTimeout(() => mensagemSucesso.style.display = "none", 3000);
                }
            })
            .catch(error => alert(error.message));
        });
    }

    if (formularioSessao) {
        formularioSessao.addEventListener("submit", function (e) {
            e.preventDefault();

            let filmeId = document.getElementById("filmeSessao")?.value || "";
            const sala = document.getElementById("salaSessao")?.value.trim() || "";
            const horario = document.getElementById("horarioSessao")?.value || "";
            const capacidadeRaw = document.getElementById("capacidadeSessao")?.value;
            const capacidade = parseInt(capacidadeRaw, 10);

            if (!filmeId || !sala || !horario || isNaN(capacidade) || capacidade < 1) {
                alert("Preencha todos os campos obrigatórios da sessão corretamente.");
                return;
            }

            if (!isNaN(filmeId)) {
                filmeId = parseInt(filmeId, 10);
            }

            const filmes = JSON.parse(sessionStorage.getItem("filmes")) || [];
            const filme = filmes.find(f => f.id === filmeId);

            if (!filme) {
                alert("Filme selecionado não encontrado.");
                return;
            }

            const formData = new URLSearchParams();
            formData.append("filmeId", filmeId);
            formData.append("sala", sala);
            formData.append("horario", horario);
            formData.append("capacidade", capacidade);

            fetch("adicionarSessao", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: formData.toString()
            })
            .then(response => {
                if (!response.ok) throw new Error("Erro ao salvar sessão no servidor.");
                return response.json();
            })
            .then(sessaoSalva => {
                salvarSessaoNaSessao(sessaoSalva);
                adicionarSessaoNaLista(sessaoSalva);
                formularioSessao.reset();

                if (mensagemSucessoSessao) {
                    mensagemSucessoSessao.style.display = "block";
                    setTimeout(() => mensagemSucessoSessao.style.display = "none", 3000);
                }
            })
            .catch(error => {
                console.error("Erro ao salvar sessão:", error);
                alert("Erro ao salvar sessão. Verifique os dados e tente novamente.");
            });
        });
    }
});

function carregarFilmesDoServidor() {
    fetch("api/filmes")
    .then(resp => {
        if (!resp.ok) throw new Error("Erro na resposta do servidor ao carregar filmes.");
        return resp.json();
    })
    .then(filmes => {
        sessionStorage.setItem("filmes", JSON.stringify(filmes));
        atualizarListaFilmes(filmes);
        carregarFilmesParaSessao();
    })
    .catch(err => alert("Erro ao carregar filmes: " + err.message));
}

function atualizarListaFilmes(filmes) {
    const lista = document.getElementById("listaFilmes");
    if (!lista) return;

    lista.innerHTML = "";
    filmes.forEach(adicionarFilmeNaLista);
}

function carregarSessoesDoServidor() {
    fetch("api/sessoes")
    .then(resp => {
        if (!resp.ok) throw new Error("Erro na resposta do servidor ao carregar sessões.");
        return resp.json();
    })
    .then(sessoes => {
        sessionStorage.setItem("sessoes", JSON.stringify(sessoes));
        const lista = document.getElementById("listaSessoes");
        if (lista) lista.innerHTML = "";
        sessoes.forEach(adicionarSessaoNaLista);
    })
    .catch(err => alert("Erro ao carregar sessões: " + err.message));
}

function adicionarFilmeNaLista(filme) {
    const lista = document.getElementById("listaFilmes");
    if (!lista) return;
    if (lista.querySelector(`li[data-id="${filme.id}"]`)) return;

    const item = document.createElement("li");
    item.dataset.id = filme.id;
    item.innerHTML = `
        <strong>${filme.nome}</strong> – Nota: ${filme.nota}<br>
        <strong>Atores:</strong> ${filme.atores}<br>
        <strong>Gênero:</strong> ${filme.genero}<br>
        ${filme.sinopse ? `<p><strong>Sinopse:</strong> ${filme.sinopse}</p>` : ""}
        ${filme.comentario ? `<p><strong>Comentário:</strong> ${filme.comentario}</p>` : ""}
    `;

    const botaoExcluir = document.createElement("button");
    botaoExcluir.textContent = "Excluir";
    botaoExcluir.addEventListener("click", () => {
        if (!confirm(`Confirma excluir o filme "${filme.nome}"?`)) return;

        fetch(`excluirFilme/${filme.id}`, { method: "DELETE" })
        .then(resp => {
            if (!resp.ok) throw new Error("Erro ao excluir filme no servidor.");
            removerFilmeDaSessao(filme.id);
            removerSessoesPorFilmeBackend(filme.id, filme.nome);
            item.remove();
            carregarFilmesParaSessao();
        })
        .catch(err => alert(err.message));
    });

    item.appendChild(botaoExcluir);
    lista.appendChild(item);
}

function salvarFilmeNaSessao(filme) {
    const filmes = JSON.parse(sessionStorage.getItem("filmes")) || [];
    if (!filmes.find(f => f.id === filme.id)) {
        filmes.push(filme);
        sessionStorage.setItem("filmes", JSON.stringify(filmes));
    }
}

function removerFilmeDaSessao(id) {
    let filmes = JSON.parse(sessionStorage.getItem("filmes")) || [];
    filmes = filmes.filter(f => f.id !== id);
    sessionStorage.setItem("filmes", JSON.stringify(filmes));
}

function adicionarSessaoNaLista(sessao) {
    const lista = document.getElementById("listaSessoes");
    if (!lista) return;
    if (sessao.id === undefined || lista.querySelector(`li[data-id="${sessao.id}"]`)) return;

    const item = document.createElement("li");
    item.dataset.id = sessao.id;

    const nomeFilme = sessao.filmeNome || "(Filme não informado)";

    let horarioFormatado = "Horário inválido";
    if (sessao.horario) {
        const data = new Date(sessao.horario);
        if (!isNaN(data.getTime())) {
            horarioFormatado = data.toLocaleString('pt-BR', {
                day: '2-digit', month: '2-digit', year: 'numeric',
                hour: '2-digit', minute: '2-digit'
            });
        }
    }

    const sala = sessao.sala || "(Sala não informada)";
    const capacidade = (typeof sessao.capacidade === "number") ? sessao.capacidade : "(Capacidade não informada)";

    item.innerHTML = `
        <strong>Filme:</strong> ${nomeFilme}<br>
        <strong>Sala:</strong> ${sala}<br>
        <strong>Horário:</strong> ${horarioFormatado}<br>
        <strong>Capacidade:</strong> ${capacidade}
    `;

    const botaoExcluir = document.createElement("button");
    botaoExcluir.textContent = "Excluir Sessão";
    botaoExcluir.addEventListener("click", () => {
        if (!confirm("Confirma excluir esta sessão?")) return;

        fetch(`excluirSessao/${sessao.id}`, { method: "DELETE" })
        .then(resp => {
            if (!resp.ok) throw new Error("Erro ao excluir sessão no servidor.");
            removerSessaoDaSessao(sessao.id);
            item.remove();
        })
        .catch(error => alert(error.message));
    });

    item.appendChild(botaoExcluir);
    lista.appendChild(item);
}

function salvarSessaoNaSessao(sessao) {
    const sessoes = JSON.parse(sessionStorage.getItem("sessoes")) || [];
    if (!sessoes.find(s => s.id === sessao.id)) {
        sessoes.push(sessao);
        sessionStorage.setItem("sessoes", JSON.stringify(sessoes));
    }
}

function removerSessaoDaSessao(id) {
    let sessoes = JSON.parse(sessionStorage.getItem("sessoes")) || [];
    sessoes = sessoes.filter(s => s.id !== id);
    sessionStorage.setItem("sessoes", JSON.stringify(sessoes));
}

function carregarFilmesParaSessao() {
    const selectFilme = document.getElementById("filmeSessao");
    if (!selectFilme) return;

    const filmes = JSON.parse(sessionStorage.getItem("filmes")) || [];
    selectFilme.innerHTML = '<option value="">Selecione um filme</option>';

    filmes.forEach(filme => {
        const option = document.createElement("option");
        option.value = filme.id;
        option.textContent = filme.nome;
        selectFilme.appendChild(option);
    });
}

function removerSessoesPorFilmeBackend(filmeId, filmeNome) {
    if (!confirm(`Ao excluir o filme "${filmeNome}", todas as sessões relacionadas também serão excluídas. Confirma?`)) return;

    fetch(`excluirSessoesPorFilme/${filmeId}`, { method: "DELETE" })
    .then(resp => {
        if (!resp.ok) throw new Error("Erro ao excluir sessões relacionadas no servidor.");
        let sessoes = JSON.parse(sessionStorage.getItem("sessoes")) || [];
        sessoes = sessoes.filter(s => s.filmeId !== filmeId);
        sessionStorage.setItem("sessoes", JSON.stringify(sessoes));

        const listaSessoes = document.getElementById("listaSessoes");
        if (listaSessoes) {
            Array.from(listaSessoes.children).forEach(li => {
                const sessaoId = li.dataset.id;
                const sessao = sessoes.find(s => s.id == sessaoId);
                if (!sessao) li.remove();
            });
        }
    })
    .catch(err => alert(err.message));
}
