package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	//não importa de onde eu chamar/instanciar essa classe vou obter uma conexão
	//precisamos da url do bd , usuario, senha e Objeto Connection do pacote java sql
	private static String urlBanco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "admin";
	private static Connection connection = null;
	
	static {
		conectar();

	}
	
	public SingleConnectionBanco() {
		conectar();
	}



	private static void conectar() {
		try {
			//conexão
			if(connection == null) {
				//carrega o Drive de conexão do banco 
				Class.forName("org.postresql.Driver");
				connection = DriverManager.getConnection(urlBanco,user,password);
				connection.setAutoCommit(false);//para não efetuar alterações no banco sem nosso comando
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();//mostrar qualquer erro de conexão
		}
		
	}



	public static Connection getConnection() {
		return connection;
	}


}
