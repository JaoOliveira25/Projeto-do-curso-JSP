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
		
		
		try {
			String acao = request.getParameter("acao");
			String idUser = request.getParameter("idUser");
			
			
			if(acao!=null && !acao.isEmpty() && acao.equalsIgnoreCase("excluirTelefone")) {
				String idFone = request.getParameter("id");
				String idUserPai = request.getParameter("idUserPai");
				daoTelefoneRepository.deleteFone(Long.parseLong(idFone));
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(idUserPai));
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(idUserPai));
				
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("msg", "Excluido com sucesso");
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
			}else if(idUser != null && !idUser.isEmpty()) {
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(Long.parseLong(idUser));
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
				
				
				request.setAttribute("modelTelefones", modelTelefones);
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
		
		try {
			String usuario_pai_id = request.getParameter("id");//usuario pai do telefone
			String numero = request.getParameter("numero");
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id)));
			modelTelefone.setUsuario_cad_id(super.getUserLogadoObjt(request));
			modelTelefone.setNumero(numero);
			
			ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(modelTelefone.getUsuario_pai_id().getId());

			
			
			if(!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) {
				
				
				daoTelefoneRepository.gravarTelefone(modelTelefone);
				
				
				request.setAttribute("msg", "Salvo com sucesso");

			}else {
				request.setAttribute("msg", "Telefone já existe");
			}
			
			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelTelefone.getUsuario_pai_id().getId());
			
			
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
