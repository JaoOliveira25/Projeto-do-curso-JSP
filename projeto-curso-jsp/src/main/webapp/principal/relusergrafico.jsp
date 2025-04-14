<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<!DOCTYPE html>
		<html lang="en">

		<jsp:include page="header.jsp"></jsp:include>

		<body>
			<!-- Pre-loader start -->
			<jsp:include page="theme-loader.jsp"></jsp:include>
			<!-- Pre-loader end -->
			<div id="pcoded" class="pcoded">
				<div class="pcoded-overlay-box"></div>
				<div class="pcoded-container navbar-wrapper">

					<jsp:include page="navbar.jsp"></jsp:include>

					<div class="pcoded-main-container">
						<div class="pcoded-wrapper">

							<jsp:include page="navbar-main-menu.jsp"></jsp:include>

							<div class="pcoded-content">
								<!-- Page-header start -->
								<jsp:include page="page-header.jsp"></jsp:include>
								<!-- Page-header end -->
								<div class="pcoded-inner-content">
									<!-- Main-body start -->
									<div class="main-body">
										<div class="page-wrapper">
											<!-- Page-body start -->
											<div class="page-body">
												<div class="card">
													<div class="card-block">
														<h5>Gráfico Salário</h5>

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="get" id="formUser">

															<div class="row">
																<div class="col">
																	<input type="text" id="dataInicio" name="dataInicio" class="form-control" placeholder="Data Inicio" aria-label="Last name" value="${dataInicio}">
																</div>
																<div class="col">
																  <input type="text" id="dataFim" name="dataFim" class="form-control" placeholder="Data Fim" aria-label="Last name" value="${dataFim}">
																</div>

																<div class="col-auto">
																	<button type="button" onclick="gerarGrafico()" class="btn btn-primary mb-2">Gerar Gráfico</button>
	
																</div>
															</div>
															
														</form>



														<div style="height: 700px; overflow: scroll;">
															<div>
																<canvas id="myChart"></canvas>
															</div>
														</div>

													</div>
												</div>
											</div>
											<!-- Page-body end -->
										</div>
										<div id="styleSelector"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<jsp:include page="js.jsp"></jsp:include>
			<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

			<script type="text/javascript">



				function gerarGrafico() {
					let urlAction = document.getElementById("formUser").action;
					let dataInicio = document.getElementById("dataInicio").value;
					let dataFim = document.getElementById("dataFim").value;
					
					$.ajax({
						method: "get",
						url: urlAction,
						data: {
							dataInicio : dataInicio,
							dataFim: dataFim, 
							acao: "gerarDadosGrafico"
						},

						success: function (response) {
							let json = JSON.parse(response);
							

							const ctx = document.getElementById('myChart');

							new Chart(ctx, {
								type: 'line',
								data: {
									labels: json.perfis,
									datasets: [{
										label: 'Gráfico de média salárial por tipo',
										data: json.salarios,
										fill: false,
										borderColor: 'rgb(75, 192, 192)',
										tension: 0.1
									}]
								}
							});
						}


					}).fail(function (xhr, status, errorThrown) {
						alert('Erro ao buscar dados para o gráfico :' + xhr.responseText);
					});

					

					


				}

				

				$(function () {
					$("#dataInicio").datepicker({
						dateFormat: 'dd/mm/yy',
						dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
						dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
						dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
						monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
						monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
						nextText: 'Próximo',
						prevText: 'Anterior'
					})
					$("#dataFim").datepicker({
						dateFormat: 'dd/mm/yy',
						dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
						dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
						dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
						monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
						monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
						nextText: 'Próximo',
						prevText: 'Anterior'
					})
				});
			</script>
		</body>

		</html>