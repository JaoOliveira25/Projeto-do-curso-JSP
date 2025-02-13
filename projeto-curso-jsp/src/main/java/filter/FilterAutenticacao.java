package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import java.io.IOException;

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
	//validação de autentição 
	//dar commit e rolback de transações no bd
	//validar e fazer redirecionamento de páginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		chain.doFilter(request, response);
	}

	//Inicia os processos ou recurso quando o servido sobe o projeto
	//ex iniciar conexão com o bd 
	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
