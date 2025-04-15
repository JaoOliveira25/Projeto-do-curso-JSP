package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;
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
@WebFilter(urlPatterns = { "/principal/*" })
public class FilterAutenticacao extends HttpFilter implements Filter {
	private static Connection connection;

	public FilterAutenticacao() {
		super();

	}

	// Encerra os processo quando o servido é parado
	// ex encerraria os processo de conexão com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Intercepta todas requisições e as respostas no sistema
	// tudo que for feito no sistema vai passar por ele
	// validação de autentição
	// dar commit e rolback de transações no bd
	// validar e fazer redirecionamento de páginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath();// url que está sendo acessada

			// validar se o usuario está ligado caso contrário redirecionar para a pagina de
			// login
			// ele está tentando acessar qualquer parte do sistema diferente da servlet

			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;// para a execução e retorna pro login

			} else {
				// caso esteja logado
				chain.doFilter(request, response);
			}

			connection.commit();// deu tudo certo comita as alterações no banco de dados

		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Inicia os processos ou recurso quando o servido sobe o projeto
	// ex iniciar conexão com o bd
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();

		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();

		String caminhoPastaSql = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;

		File[] filesSql = new File(caminhoPastaSql).listFiles();
		try {
			for (File file : filesSql) {

				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				try {
					if (!arquivoJaRodado) {
						FileInputStream entradaArquivo = new FileInputStream(file);

						Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
						StringBuilder sql = new StringBuilder();

						while (lerArquivo.hasNext()) {
							sql.append(lerArquivo.nextLine());
							sql.append("\n");
						}

						connection.prepareStatement(sql.toString()).execute();
						
						try {
							daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						connection.commit();
						lerArquivo.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
