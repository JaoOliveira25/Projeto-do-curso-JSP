<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página inicial</title>
</head>
<body>
	<h4>${msg}</h4>
	
	<form action="ServletLogin" method="post"> 
		<table>
			<tr>
				<td><h1>Login</h1></td>
			</tr>
			<tr>
				<td><label>Login</label></td>
				<td><input type="text" name="login"></td>
			</tr>
			<tr>
				<td><label>Senha</label></td>
				<td><input type="password" name="senha"></td>
			</tr>
			<tr>
				<td><button type="submit" name="btnEnviar">Enviar</button></td>
			</tr>
		</table>
	</form>

</body>
</html>