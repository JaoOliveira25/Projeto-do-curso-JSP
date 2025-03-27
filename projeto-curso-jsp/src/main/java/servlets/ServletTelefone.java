package servlets;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet("/ServletTelefone") 
public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
      
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
	
	
    public ServletTelefone() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUser = request.getParameter("idUser");
		try {
			if(idUser != null && !idUser.isEmpty()) {
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(idUser));
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
	
		
			}else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		try {
			String usuario_pai_id = request.getParameter("id");//usuario pai do telefone
			String numero = request.getParameter("numero");
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id)));
			modelTelefone.setNumero(numero);
			modelTelefone.setUsuario_cad_id(super.getUserLogadoObjt(request));
			daoTelefoneRepository.gravarTelefone(modelTelefone);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
