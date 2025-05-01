package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String urlBanco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "seu-usuario";
	private static String password = "sua-senha";
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
			if(connection == null|| connection.isClosed()) {
				//carrega o Drive de conexão do banco 
				
				Class.forName("org.postgresql.Driver");
				
				 
				connection = DriverManager.getConnection(urlBanco,user,password);
				connection.setAutoCommit(false);//para não efetuar alterações no banco sem nosso comando
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();//mostrar qualquer erro de conexão
			throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage());
		}
		
	}



	public static Connection getConnection() {
		return connection;
	}


}
