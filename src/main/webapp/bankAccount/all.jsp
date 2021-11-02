<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>All bank accounts</title>
</head>
<body>
<h1>All bank accounts</h1>
<table>
    <jsp:useBean id="bank_accounts" scope="request" type="java.util.List"/>
    <c:forEach items="${bank_accounts}" var="bank_account">
        <tr>
            <td>
                <a href="${bank_account.iban}">
                        ${bank_account.iban}
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
