<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adicionar Filme</title>
</head>
<body>
    <h1>Adicionar Novo Filme</h1>
    <form action="adicionarFilme" method="post">
        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="nota">Nota (1-10):</label><br>
        <input type="number" id="nota" name="nota" min="1" max="10" required><br><br>

        <label for="comentario">Comentário:</label><br>
        <textarea id="comentario" name="comentario" rows="4" cols="50"></textarea><br><br>

        <input type="submit" value="Salvar Filme">
    </form>

    <p><a href="filmes">Voltar para a lista de filmes</a></p>
</body>
</html>
