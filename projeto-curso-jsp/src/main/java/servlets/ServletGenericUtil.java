package servlets;

import java.io.Serializable;

import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



public class ServletGenericUtil extends HttpServlet implements  Serializable {
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		
		//esse método retornar o id do usuario logado 
		HttpSession session = request.getSession();
		
		String usarioLogado = (String)session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultaUsuarioLogado(usarioLogado).getId();
	}

}
