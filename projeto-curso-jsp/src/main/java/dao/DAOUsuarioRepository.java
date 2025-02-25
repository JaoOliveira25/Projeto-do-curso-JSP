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
	
	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception{
		
			String sql = "INSERT INTO model_login(login, password, nome, email) VALUES (?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			
			statement.execute();//executa a instrução sql
			connection.commit();//salva no banco de dados 
			
			return this.consultaUsuario(objeto.getLogin());
				
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet result= statement.executeQuery(); //esse método retorna um objeto ResultSet
		
		while(result.next()) {//se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
		}
		
		return modelLogin;//se não entrar no loop while o retorno vai ser null
	}
	

	
	
}
