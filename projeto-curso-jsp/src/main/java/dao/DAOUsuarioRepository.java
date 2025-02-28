package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {
		if (objeto.isNovo()) {// grava um novo usuario
			String sql = "INSERT INTO model_login(login, password, nome, email) VALUES (?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());

			statement.execute();// executa a instrução sql
			connection.commit();// salva no banco de dados
		} else {
			String sql = "UPDATE model_login SET login=?, password=?, nome=?, email=? WHERE id=" + objeto.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());

			statement.executeUpdate();
			connection.commit();

		}

		return this.consultaUsuario(objeto.getLogin());

	}
	
	public List<ModelLogin> consultaUsuarioList(String nome) throws SQLException{
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE nome ILIKE ? ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			//modelLogin.setSenha(result.getString("password"));
			retornoList.add(modelLogin);
		}
		
		return retornoList;
	}
	
	public List<ModelLogin> consultaUsuarioList() throws SQLException{
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet result = statement.executeQuery();
		while(result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			//modelLogin.setSenha(result.getString("password"));
			retornoList.add(modelLogin);
		}
		
		return retornoList;
	}
	

	public ModelLogin consultaUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

		while (result.next()) {// se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}

	public ModelLogin consultaUsuarioId(String id) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? ;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

		while (result.next()) {// se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}
	
	public boolean validaLogin(String login) throws Exception {
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE upper(login) = upper(?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet result = statement.executeQuery();
		result.next();// vai entrar nos valores
		return result.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM public.model_login WHERE id=?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
	}

}
