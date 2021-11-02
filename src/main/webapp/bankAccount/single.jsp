<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bank_account" scope="request" type="domain.BankAccount"/>

<html>
<head>
    <title>${bank_account.iban}</title>
</head>
<body>
<h1>Bank account</h1>
<h3>${bank_account.iban}</h3>
</body>
</html>
