package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;

import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

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
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				
				
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(dadosJsonUser);
				
				response.getWriter().write(json);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String idBusca = request.getParameter("id");
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(idBusca, super.getUserLogado(request));
				
				request.setAttribute("msg", "Editar Usuário");
				request.setAttribute("modelLogin", modelLogin);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsers")) {
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
					
					request.setAttribute("msg", "Usuários carregados");
					request.setAttribute("modelLogins", modelLogins);
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			}
			else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
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
			
			
			 
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			
			if(request.getPart("fileFoto")!= null) {
				Part part = request.getPart("fileFoto"); // Obtemos o arquivo enviado
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

			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

		}

	}

}
