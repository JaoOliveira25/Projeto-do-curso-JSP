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

	private static final long serialVersionUID = 1L;



	
	private static Connection connection;

	public FilterAutenticacao() {
		super();

	}

	
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuarioLogado");

			String urlParaAutenticar = req.getServletPath();// 

			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;

			} else {
				
				chain.doFilter(request, response);
			}

			connection.commit();

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
