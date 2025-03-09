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

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		if (objeto.isNovo()) {// grava um novo usuario
			String sql = "INSERT INTO model_login(login, password, nome, email, usuario_id, perfil, sexo) VALUES (?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setLong(5, userLogado);
			statement.setString(6, objeto.getPerfil());
			statement.setString(7, objeto.getSexo());

			statement.execute();// executa a instrução sql
			connection.commit();// salva no banco de dados
			
			if(objeto.getFotoUser()!= null && !objeto.getFotoUser().isEmpty()) {
				 sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE login = ? ";
				 statement = connection.prepareStatement(sql);
				 statement.setString(1, objeto.getFotoUser());
				 statement.setString(2, objeto.getExtesaoFotoUser());
				 statement.setString(3, objeto.getLogin());
				 statement.executeUpdate();
				 connection.commit();
			}
			
		} else {
			
			
			String sql = "UPDATE model_login SET login=?, password=?, nome=?, email=?, perfil=?, sexo=? WHERE id=" + objeto.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setString(5, objeto.getPerfil());
			statement.setString(6, objeto.getSexo());

			statement.executeUpdate();
			connection.commit();
			//atualiza os dados no BD
			
			if(objeto.getFotoUser()!= null && !objeto.getFotoUser().isEmpty()) {
				 sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE id = ? ";
				 statement = connection.prepareStatement(sql);
				 statement.setString(1, objeto.getFotoUser());
				 statement.setString(2, objeto.getExtesaoFotoUser());
				 statement.setLong(3, objeto.getId());
				 statement.executeUpdate();
				 connection.commit();
			}

		}

		return this.consultaUsuario(objeto.getLogin(), userLogado);

	}
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException{
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE nome ILIKE ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
			//modelLogin.setSenha(result.getString("password"));
			retornoList.add(modelLogin);
		}
		
		return retornoList;
	}
	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException{
		/*com o parametro user logado garantimos que só 
		será consultado somente cadastros que o usuario logado registrou*/
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin = false and usuario_id="+userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet result = statement.executeQuery();
		while(result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
			//modelLogin.setSenha(result.getString("password"));
			retornoList.add(modelLogin);
		}
		
		return retornoList;
	}
	

	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		//passasmos o userLogado como parametro para a hora da consulta o usuario n buscar registro de outros usuários
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) and useradmin = false and usuario_id=?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		statement.setLong(2, userLogado);
		ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

		while (result.next()) {// se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception {
		//passasmos o userLogado como parametro para a hora da consulta o usuario n buscar registro de outros usuários
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) and useradmin = false ; ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		
		ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

		while (result.next()) {// se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setUserAdmin(result.getBoolean("useradmin"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
		}
		return modelLogin;
		
	}
		
		public ModelLogin consultaUsuarioLogado(String login) throws Exception {
			//passasmos o userLogado como parametro para a hora da consulta o usuario n buscar registro de outros usuários
			ModelLogin modelLogin = new ModelLogin();

			String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?)  ; ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			
			ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

			while (result.next()) {// se tem resultado/retorno
				modelLogin.setId(result.getLong("id"));
				modelLogin.setEmail(result.getString("email"));
				modelLogin.setLogin(result.getString("login"));
				modelLogin.setSenha(result.getString("password"));
				modelLogin.setNome(result.getString("nome"));
				modelLogin.setUserAdmin(result.getBoolean("useradmin"));
				modelLogin.setPerfil(result.getString("perfil"));
				modelLogin.setSexo(result.getString("sexo"));
			}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}

	public ModelLogin consultaUsuarioId(String id, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? and useradmin = false and usuario_id=?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		ResultSet result = statement.executeQuery(); // esse método retorna um objeto ResultSet

		while (result.next()) {// se tem resultado/retorno
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setSenha(result.getString("password"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}
	
	public boolean validaLogin(String login) throws Exception {
		//aqui não precisa passar o userLogado como parametro pq quando vamos validar n sabemos o usuario
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE upper(login) = upper(?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet result = statement.executeQuery();
		result.next();// vai entrar nos valores
		return result.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {
		//se só será exibidos os usuarios cadastrados pelo usuario logado não precisamos passar o userLogado como parametro
		String sql = "DELETE FROM public.model_login WHERE id=? and useradmin = false ;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
	}

}
