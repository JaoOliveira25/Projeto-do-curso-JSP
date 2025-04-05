<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
										<div class="row">
											<div class="row">
												<div class="col-sm-12">
													<!-- Basic Form Inputs card start -->
													<div class="card">
														<div class="card-block">
															<h5>Rel. Usuário</h5>
															<form class="form-material"
																action="<%=request.getContextPath()%>/ServletUsuarioController?acao=imprimirRelatorioUser"
																method="get" id="formUser">
																<input type="hidden" id="acaoImprimirRelatorio" name="acao" value="imprimirRelatorioHTML">
																<div class="row">
																	<div class="col">
																		<input type="text" id="dataInicio" name="dataInicio" class="form-control" placeholder="Data Inicio" aria-label="Last name" value="${dataInicio}">
																	</div>
																	<div class="col">
																	  <input type="text" id="dataFim" name="dataFim" class="form-control" placeholder="Data Fim" aria-label="Last name" value="${dataFim}">
																	</div>

																	<div class="col-auto">
																		<button type="button" onclick="imprimirRelatorioHtml()" class="btn btn-primary mb-2">Imprimir relatório</button>
																		<button type="button" onclick="imprimirRelatorioPDF()" class="btn btn-primary mb-2">Baixar PDF</button>
																	</div>
																  </div>
															</form>

															<div style="height: 300px; overflow: scroll;">
																<table class="table" id="tabelaResultadosView">
																	<thead>
																		<tr>
																			<th scope="col">ID</th>
																			<th scope="col">Nome</th>
																			<th scope="col">Telefone</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${listaUser}" var="mL">
																			<tr>
																				<td><c:out value="${mL.id}"></c:out></td>
																				<td><c:out value="${mL.nome}"></c:out></td>
																				<td>
																					<c:forEach items="${mL.telefones}" var="tel">
																						<c:out value="${tel.numero}"></c:out>
																					<br>
																					</c:forEach>
																				
																				</td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>

														</div>
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

	<script type="text/javascript">

		function imprimirRelatorioHtml(){
			document.getElementById("acaoImprimirRelatorio").value = "imprimirRelatorioHTML";
			$("#formUser").submit();
		}

		function imprimirRelatorioPDF(){
			document.getElementById("acaoImprimirRelatorio").value = "imprimirRelatorioPDF";
			
			$("#formUser").submit();
		}

		$(function(){
			$("#dataInicio").datepicker({
				dateFormat: 'dd/mm/yy',
				dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
				dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
				dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
				monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
				monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
				nextText: 'Próximo',
			    prevText: 'Anterior'
			})
			$("#dataFim").datepicker({
				dateFormat: 'dd/mm/yy',
				dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
				dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
				dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
				monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
				monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
				nextText: 'Próximo',
			    prevText: 'Anterior'
			})
		});
	</script>
</body>

</html>
