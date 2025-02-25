package servlets;

import java.io.IOException;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String acao = request.getParameter("acao");
			
			try {
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					String idUser = request.getParameter("id");
					System.out.println(idUser);
					daoUsuarioRepository.deletarUser(idUser);
					request.setAttribute("msg", "Excluido com sucesso!");
					
				}
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}
	}

	// vamos interceptar os parametros da requisição de cadastro de usuario
	// minuto da aula 10:50
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String msg = "Operação realizada com sucesso";
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id): null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		
		/*se o id for null e o validaLogin retornar true  significa que está sendo feito um novo registro então não será permitido 
		login igual mas se o id já existir permite atualizar */
		if(daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId()==null) {
			msg = "Já existe usuário com o mesmo login, informe outro login";
		} else {
			if(modelLogin.isNovo()) {
				msg = "Cadastrado com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
			
		}
		
		
		request.setAttribute("msg", msg);
		
		request.setAttribute("modelLogin", modelLogin);
		
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}catch(Exception e ){
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
				
		}
		
	}

}
