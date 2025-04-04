package servlets;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
	
	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com sucesso!");
				
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);

				response.getWriter().write("Excluido com sucesso!");

			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("btnDeletar")) {
				String idUser = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUser);
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuário excluido com sucesso!");
				
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));

				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
			
				ObjectMapper objectMapper = new ObjectMapper();
				
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca,  super.getUserLogado(request)));				
				response.getWriter().write(json);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), pagina);
			
				ObjectMapper objectMapper = new ObjectMapper();
				
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca,  super.getUserLogado(request)));				
				response.getWriter().write(json);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String idBusca = request.getParameter("id");
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(idBusca, super.getUserLogado(request));
				
				request.setAttribute("msg", "Editar Usuário");
				request.setAttribute("modelLogin", modelLogin);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));

				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsers")) {
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					
					request.setAttribute("msg", "Usuários carregados");
					request.setAttribute("modelLogins", modelLogins);
					request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));

					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(idUser, super.getUserLogado(request));
				
				if(modelLogin.getFotoUser()!=null && !modelLogin.getFotoUser().isEmpty() ) {
					response.setHeader("Content-Disposition", "attachment;filename=foto_usuario_"+idUser+"."+modelLogin.getExtesaoFotoUser());	
					response.getOutputStream().write(Base64.getDecoder().decode(modelLogin.getFotoUser().split(",")[1]));
				}
				
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
						
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
				String dataInicio = request.getParameter("dataInicio");
				String dataFim = request.getParameter("dataFim");
				
				if(dataInicio==null||dataInicio.isEmpty()&& dataFim==null||dataFim.isEmpty()) {
					request.setAttribute("listaUser",daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				}else {
					
					request.setAttribute("listaUser",daoUsuarioRepository.consultaUsuarioListRelData(super.getUserLogado(request),dataInicio,dataFim)) ;					
				}
				
				request.setAttribute("dataInicio", dataInicio);
				request.setAttribute("dataFim", dataFim);
				request.getRequestDispatcher("principal/rel.user.jsp").forward(request, response);
						
			}else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			

			String msg = "Operação realizada com sucesso";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String dataNascimento = request.getParameter("dataNascimento");
			String rendaMensal = request.getParameter("rendaMensal");
			rendaMensal = rendaMensal.replaceAll("R\\$ ", "").replaceAll("\\.", "").replaceAll(",", ".");
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			modelLogin.setDataNascimento(dataNascimento);
			modelLogin.setRendaMensal(Double.parseDouble(rendaMensal));
			modelLogin.setTelefones(daoTelefoneRepository.listFone(modelLogin.getId()));
			
			if(request.getPart("fileFoto")!= null) {
				Part part = request.getPart("fileFoto"); // Obtemos o arquivo enviado
				if(part.getSize()>0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream());
					/* Essa concatenação gera uma string no formato correto para ser usada diretamente 
					 em atributos src de imagens no HTML. Esse formato é ideal para armazenar ou exibir
					  imagens diretamente no navegador */
					String imagemBase64 = "data:image/"+part.getContentType().split("\\/")[1]+";base64,"+Base64.getEncoder().encodeToString(foto);
					
					
					modelLogin.setFotoUser(imagemBase64);
					/*O método part.getContentType() retorna algo como "image/jpeg" ou "image/png".
					O código split("\\/")[1] divide essa string pelo caractere '/' e pega a segunda parte*/
					modelLogin.setExtesaoFotoUser(part.getContentType().split("\\/")[1]);
					//Na hora de gravar no BD queremos que o parametro da imagem seja opcional 
				}
				
			}

			/*
			 * se o id for null e o validaLogin retornar true significa que está sendo feito
			 * um novo registro então não será permitido login igual mas se o id já existir
			 * permite atualizar
			 */
			if (daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro login";
			} else {
				if (modelLogin.isNovo()) {
					msg = "Cadastrado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";
				}
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));

			}
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);

			request.setAttribute("msg", msg);

			request.setAttribute("modelLogin", modelLogin);
			
			request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));

			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}

	}

}
