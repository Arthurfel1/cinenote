<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Filmes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f8f8f8;
        }
        h1 {
            color: #333;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin-top: 15px;
            background-color: #fff;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
            vertical-align: top;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        p {
            font-style: italic;
            color: #666;
        }
    </style>
</head>
<body>
    <h1>Filmes cadastrados</h1>

    <c:if test="${empty filmes}">
        <p>Nenhum filme cadastrado.</p>
    </c:if>

    <c:if test="${not empty filmes}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Atores Principais</th>
                    <th>Gênero</th>
                    <th>Sinopse</th>
                    <th>Nota</th>
                    <th>Comentário</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="filme" items="${filmes}">
                    <tr>
                        <td>${filme.id}</td>
                        <td>${filme.nome}</td>
                        <td>${filme.atores}</td>
                        <td>${filme.genero}</td>
                        <td>${filme.sinopse}</td>
                        <td>${filme.nota}</td>
                        <td>${filme.comentario}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
