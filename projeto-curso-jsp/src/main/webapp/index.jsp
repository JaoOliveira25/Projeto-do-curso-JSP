<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<!-- Bootstrap CSS -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
			integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

		<title>Página inicial</title>

		<style type="text/css">
			form {
				position: absolute;
				top: 35%;
				right: 35%;
				left: 35%;
			}

			.msg {
				text-align: center;
				color: #664d03;
				background-color: #fff3cd;
				border-color: #ffecb5;
			}
		</style>
	</head>

	<body>


		<form action="ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
			<input type="hidden" value="<%= request.getParameter(" url")%>"
			name="url">
			<h5 class="msg">${msg}</h5>
			<h1>Login</h1>
			<div class="mb-3">
				<label class="form-label">Login</label>
				<input type="text" name="login" class="form-control" required>
				<div class="invalid-feedback">
					Informe o login!
				</div>
				<div class="valid-feedback">
					ok!
				</div>
			</div>
			<div class="mb-3">
				<label class="form-label">Senha</label>
				<input type="password" name="senha" class="form-control" required>
				<div class="invalid-feedback">
					Informe a senha!
				</div>
				<div class="valid-feedback">
					ok!
				</div>
			</div>


			<button type="submit" name="btnEnviar" class="btn btn-primary">Acessar</button>


		</form>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
			crossorigin="anonymous"></script>


		<script>
			// Example starter JavaScript for disabling form submissions if there are invalid fields
			(function () {
				'use strict'

				// Fetch all the forms we want to apply custom Bootstrap validation styles to
				var forms = document.querySelectorAll('.needs-validation')

				// Loop over them and prevent submission
				Array.prototype.slice.call(forms)
					.forEach(function (form) {
						form.addEventListener('submit', function (event) {
							if (!form.checkValidity()) {
								event.preventDefault()
								event.stopPropagation()
							}

							form.classList.add('was-validated')
						}, false)
					})
			})()
		</script>
	</body>

	</html>