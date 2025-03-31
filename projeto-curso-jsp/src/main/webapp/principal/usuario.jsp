<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="model.ModelLogin"%>

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
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cad. Usuário</h4>

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser"
															enctype="multipart/form-data">
															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																	<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">ID:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																	<input type="text" name="dataNascimento" id="dataNascimento"
																	class="form-control" required="required"
																	value="${modelLogin.dataNascimento}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Dat. Nascimento:</label>
															</div>

															
															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	<c:if test="${not empty modelLogin.fotoUser}">
																		<a href="<%= request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																			<img src="${modelLogin.fotoUser}" alt="Imagem User" width="70px" id="fotoEmBase64" style=" margin-right: 10px ;">
																		</a>																	
																	</c:if>
																	<c:if test="${empty modelLogin.fotoUser}">
																		<img src="${pageContext.request.contextPath}/assets/images/free-user.png" alt="Imagem User" width="70px" id="fotoEmBase64" style=" margin-right: 10px ;">
																	</c:if>
																	
																</div>
																
																<input type="file" accept="image/*" onchange ="visualizarImg('fotoEmBase64', 'fileFoto')" class="form-control-file" style="margin-top: 15px; margin-right: 15px;" id="fileFoto" name="fileFoto">	
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	value="${modelLogin.email}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">E-mail:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option disabled="disabled"
																	<%ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																			if (modelLogin != null ) {
																				out.print("selected=\"selected\"");
																			}%> >[Selecione o
																		Perfil]</option>
																	<option value="ADMIN"
																		<% modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																			if (modelLogin != null && "ADMIN".equals(modelLogin.getPerfil())) {
																				out.print("selected=\"selected\"");
																			}%>>Admin</option>

																	<option value="SECRETARIA"
																		<%if (modelLogin != null && "SECRETARIA".equals(modelLogin.getPerfil())) {
																				out.print("selected=\"selected\"");
																			}%>>Secretária</option>

																	<option value="AUXILIAR"
																		<%if (modelLogin != null && "AUXILIAR".equals(modelLogin.getPerfil())) {
																			out.print("selected=\"selected\"");
																		}%>>Auxiliar</option>
																</select> <span class="form-bar"></span> <label
																	class="float-label">Perfil:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input onblur="pesquisaCep()" type="text" name="cep" id="cep"
																	class="form-control" required="required"
																	value="${modelLogin.cep}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">CEP</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro"
																	class="form-control" required="required"
																	value="${modelLogin.logradouro}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Logradouro</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro"
																	class="form-control" required="required"
																	value="${modelLogin.bairro}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Bairro</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade"
																	class="form-control" required="required"
																	value="${modelLogin.localidade}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Localidade</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf"
																	class="form-control" required="required"
																	value="${modelLogin.uf}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">UF</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	value="${modelLogin.numero}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Número</label>
															</div>


															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	value="${modelLogin.login}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Login</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	value="${modelLogin.senha}" autocomplete="off">
																<span class="form-bar"></span> <label
																	class="float-label">Senha</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="radio" name="sexo" value="MASCULINO"
																<%
																	if(modelLogin != null && "MASCULINO".equals(modelLogin.getSexo())){
																		out.print("checked=\"checked\"");
																	}
																%>
																>MASCULINO</>
																<input type="radio" value="FEMININO" name="sexo"
																<%
																	if(modelLogin != null && "FEMININO".equals(modelLogin.getSexo())){
																		out.print("checked=\"checked\"");
																	}
																%>
																>FEMININO</>
															</div>

															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm()">Novo</button>
															<button class="btn btn-success waves-effect waves-light">salvar</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="criarDelete()">Deletar</button>
																<c:if test="${modelLogin.id > 0}">
																	<a href="<%= request.getContextPath()%>/ServletTelefone?idUser=${modelLogin.id}" class="btn btn-primary waves-effect waves-light">Telefone</a>
																</c:if>
															<button type="button" class="btn btn-secondary"
															data-toggle="modal" data-target="#exampleModalUsuario">
															Buscar</button>

														</form>

													</div>
												</div>
											</div>
										</div>

										<span>${msg}</span>
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaResultadosView">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
														<th scope="col">Remover</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="mL">
														<tr>
															<td><c:out value="${mL.id}"></c:out></td>
															<td><c:out value="${mL.nome}"></c:out></td>
															<td><a
																href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${mL.id}"
																class="btn btn-success" type="button">Ver</a></td>
															<td><button onclick="btnDeletar(${mL.id})"
																	class="btn btn-danger" type="button">Remover</button></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<nav aria-label="Page navigation example">
											<ul class="pagination">
												<%
													int totalPaginas = (int) request.getAttribute("totalPaginas");

													for(int i=0; i < totalPaginas; i++){
														String url = request.getContextPath()+"/ServletUsuarioController?acao=paginar&pagina="+(i*5);
														out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url +"\">"+(i+1)+"</a></li>");
													}
												%>
											 
											</ul>
									  </nav>

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
	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
						Usuário</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome"
							aria-label="Recipient's username" aria-describedby="basic-addon2"
							id="nomeBusca">
						<div class="input-group-append">
							<button class="btn btn-success" type="button"
								onclick="buscarUsuario()">Buscar</button>
						</div>
					</div>
					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaResultados">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">Nome</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</div>
				
				<nav aria-label="Page navigation example">
					<ul class="pagination" id="ulPaginacaoUserAjax">
																
					</ul>
				</nav>	
				
				
				<span id="totalResultados"></span>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>

				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		$(function(){
			$("#dataNascimento").datepicker({
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
	
	
		$("#numero,#cep").keypress(function(event){
			let char = String.fromCharCode(event.which||event.keyCode);
			if(!/\d/.test(char)){
				event.preventDefault();
			}
		})
	
		function pesquisaCep(){
			let cep = $("#cep").val(); 

			 $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados){
				if (!("erro" in dados)) {
                                $("#cep").val(dados.cep);
                                $("#logradouro").val(dados.logradouro);
                                $("#bairro").val(dados.bairro);
                                $("#localidade").val(dados.localidade);
                                $("#uf").val(dados.uf);
                               
                } 
			 })
		}


		function visualizarImg(fotoEmBase64, fileFoto){

			
			let preview = document.getElementById(fotoEmBase64);//campo img 
			let fileUser = document.getElementById(fileFoto).files[0];
			let reader = new FileReader();

			reader.onloadend = function () {/*aqui apenas criamos o evento não significa que ele sera executado imediatamente onload = "carregamento" end="fim" fim do carregamento*/
											//O evento onloadend só será chamado depois que a leitura do arquivo for concluída.
				preview.src = reader.result;/*Pega o resultado da leitura e atribui a imagem (<img>) como essa versão Base64, permitindo que ela seja exibida na tela.*/
			};

			if(fileUser){//aqui é feito a leitura do arquivo 
				reader.readAsDataURL(fileUser);//Isso inicia a leitura do arquivo e, quando ela terminar, o evento onloadend será disparado.
				//readAsDataURL(file) lê o arquivo e converte seu conteúdo para uma string Base64
			}else{
				preview.src = '';
			}
		
		}

		function verEditar(id){
			let urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction + '?acao=buscarEditar&id='+id;

		}
		
		function buscarUserPageAjax(data){
			let urlAction = document.getElementById('formUser').action;
			let nomeBusca = document.getElementById('nomeBusca').value;


			$.ajax({
				method: "get",
				url: urlAction,
				data: data,
				
				success: function(response, textStatus, xhr){
					
					const json = JSON.parse(response);
					
					$('#tabelaResultados > tbody > tr').remove();
					$("#ulPaginacaoUserAjax > li ").remove();

					for(var i=0; i<json.length; i++){
						$('#tabelaResultados > tbody').append('<tr> <td>'+json[i].id+'</td> <td>'+json[i].nome+'</td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[i].id+')">Ver</button></td> </tr>');
						
					}

					document.getElementById('totalResultados').textContent = '	Resultados: '+json.length;
					let totalPagina = xhr.getResponseHeader("totalPagina");
					
					for(var p=0; p < totalPagina; p++){
						var data = "nomeBusca="+nomeBusca+"&acao=buscarUserAjaxPage&pagina="+(p*5);
						
						$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPageAjax(\''+data+'\')">'+(p+1)+'</a></li>');
					}
				}
			}).fail(function(xhr, status, errorThrown) {
						alert('Erro ao buscar usuário :'+ xhr.responseText);
			});
		}

		function buscarUsuario() {
			let nomeBusca = document.getElementById("nomeBusca").value;
			

			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim()!= ''){

				let urlAction = document.getElementById("formUser").action;//pega a url que o form esta direcionando
				

				$.ajax({
					method: "get",
					url: urlAction,
					data:{
						nomeBusca: nomeBusca,
						acao: "buscarUserAjax"
					},
					success: function(response, textStatus, xhr){
						
						const json = JSON.parse(response);
						
						$('#tabelaResultados > tbody > tr').remove();
						$("#ulPaginacaoUserAjax > li ").remove();

						for(var i=0; i<json.length; i++){
							$('#tabelaResultados > tbody').append('<tr> <td>'+json[i].id+'</td> <td>'+json[i].nome+'</td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[i].id+')">Ver</button></td> </tr>');
							
						}

						
						document.getElementById('totalResultados').textContent = '	Resultados: '+json.length;
						
						let totalPagina = xhr.getResponseHeader("totalPagina");
						
						for(var p=0; p < totalPagina; p++){
							var data = "nomeBusca="+nomeBusca+"&acao=buscarUserAjaxPage&pagina="+(p*5);
							
							$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUserPageAjax(\''+data+'\')">'+(p+1)+'</a></li>');
						}
					}
				}).fail(function(xhr, status, errorThrown) {
							alert('Erro ao buscar usuário :'+ xhr.responseText);
				});
			}
			
			
		}

		function criarDeleteComAjax() {
			if (confirm("Deseja mesmo deletar esses dados?")) {
				let urlAction = document.getElementById("formUser").action;
				let idUser = document.getElementById("id").value;

				$.ajax({
					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=deletarAjax",
					success : function(response) {
						limparForm();
						document.getElementById('msg').textContent = response;
					}
				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por id:'
									+ xhr.responseText);
						});
			}
		}

		function btnDeletar(id){
			if (confirm("Deseja mesmo deletar esses dados?")) {
				let urlAction = document.getElementById("formUser").action;
				let idUser = id;

				$.ajax({
					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=btnDeletar",
					success : function(response) {
						
						window.location.href = "ServletUsuarioController?acao=ListarUsers";
						
					}
				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por id:'
									+ xhr.responseText);
						});
			}
		}

		function limparForm() {
			let elementos = document.getElementById("formUser").elements;
			for (let i = 0; i < elementos.length; i++) {
				elementos[i].value = '';
			}

		}

		function criarDelete() {
			if (confirm("Deseja mesmo deletar esses dados?")) {
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}
	</script>
</body>

</html>