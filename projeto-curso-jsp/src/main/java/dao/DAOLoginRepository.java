package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {
	//precisamos de um objeto de conexão com o BD 
	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//Agora validamos nosso login passamos o objeto Model como parametro
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception{
		//crie o sql de consulta "select" com o uso de parametros para evitar sql injection
		String sql = "select * from model_login where upper(login) = upper(?) and upper(password) = upper(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return true;//autenticado
		}
		return false;// não autenticado 
		
	}
	
}
