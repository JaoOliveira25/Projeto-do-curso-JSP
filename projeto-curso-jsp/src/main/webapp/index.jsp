<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página inicial</title>
</head>
<body>
	<h1>Formulário</h1>
	<!-- lembresse que no action ao colocar ServletLogin não precisa do / antes -->
	<form action="ServletLogin" method="post"> 
		<input type="text" name="nome">
		<input type="text" name="email">
		<button type="submit" >Enviar</button>
	</form>
</body>
</html>