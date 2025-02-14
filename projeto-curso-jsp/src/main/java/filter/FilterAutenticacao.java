package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//intercepta todas as requisições que vierem do projeto ou mapeamento
//primeiro o index não pode passar por esse filtro 
@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
       

    public FilterAutenticacao() {
        super();
      
    }

    //Encerra os processo quando o servido é parado
    //ex encerraria os processo de conexão com o banco 
	public void destroy() {
	
	}

	//Intercepta todas requisições e as respostas no sistema 
	//tudo que for feito no sistema vai passar por ele 
	//validação de autentição 
	//dar commit e rolback de transações no bd
	//validar e fazer redirecionamento de páginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		String urlParaAutenticar = req.getServletPath();//url que está sendo acessada
		
		//validar se o usuario está ligado caso contrário redirecionar para a pagina de login
		//ele está tentando acessar qualquer parte do sistema diferente da servlet login
		if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
			
			RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url="+urlParaAutenticar);
			request.setAttribute("msg", "Por favor realize o login!");
			redireciona.forward(request, response);
			return;//para a execução e retorna pro login 
			
		}else {
			//caso esteja logado 
			chain.doFilter(request, response);
		}
		
	}

	//Inicia os processos ou recurso quando o servido sobe o projeto
	//ex iniciar conexão com o bd 
	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
