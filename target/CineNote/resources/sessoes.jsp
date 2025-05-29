<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Sessões</title>
</head>
<body>
    <h1>Lista de Sessões</h1>
    <c:if test="${empty sessoes}">
        <p>Nenhuma sessão encontrada.</p>
    </c:if>
    <c:if test="${not empty sessoes}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Filme</th>
                    <th>Sala</th>
                    <th>Horário</th>
                    <th>Capacidade</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sessao" items="${sessoes}">
                    <tr>
                        <td>${sessao.id}</td>
                        <td>${sessao.filme.nome}</td>
                        <td>${sessao.sala}</td>
                        <td>${sessao.horario}</td>
                        <td>${sessao.capacidade}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
