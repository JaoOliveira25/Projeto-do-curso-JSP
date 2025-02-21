package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravarUsuario(ModelLogin objeto) throws Exception{
		
			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			
			statement.execute();//executa a instrução sql
			connection.commit();//salva no banco de dados 
				
	}
	
	
	
	
	
}
