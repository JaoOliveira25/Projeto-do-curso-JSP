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
			String sql = "INSERT INTO model_login(login, password, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setLong(5, userLogado);
			statement.setString(6, objeto.getPerfil());
			statement.setString(7, objeto.getSexo());
			statement.setString(8, objeto.getCep());
			statement.setString(9, objeto.getLogradouro());
			statement.setString(10, objeto.getBairro());
			statement.setString(11, objeto.getLocalidade());
			statement.setString(12, objeto.getUf());
			statement.setString(13, objeto.getNumero());
			statement.setDate(14, objeto.getDataNascimento());
			
			statement.execute();// executa a instrução sql
			connection.commit();// salva no banco de dados

			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser = ?, extensaofotouser = ? WHERE login = ? ";
				statement = connection.prepareStatement(sql);
				statement.setString(1, objeto.getFotoUser());
				statement.setString(2, objeto.getExtesaoFotoUser());
				statement.setString(3, objeto.getLogin());
				statement.executeUpdate();
				connection.commit();
			}

		} else {

			String sql = "UPDATE model_login SET login=?, password=?, nome=?, email=?, perfil=?, sexo=?,  cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=? WHERE id="
					+ objeto.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.setString(5, objeto.getPerfil());
			statement.setString(6, objeto.getSexo());
			statement.setString(7, objeto.getCep());
			statement.setString(8, objeto.getLogradouro());
			statement.setString(9, objeto.getBairro());
			statement.setString(10, objeto.getLocalidade());
			statement.setString(11, objeto.getUf());
			statement.setString(12, objeto.getNumero());
			statement.setDate(13, objeto.getDataNascimento());


			statement.executeUpdate();
			connection.commit();
			// atualiza os dados no BD

			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
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

public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {
		
		
		String sql = "SELECT COUNT(1) AS total FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin IS FALSE AND usuario_id = ?;";
	
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1,  nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		
		
		return pagina.intValue();
		
	}

public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, String offset) throws SQLException {
	List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
	
	String sql = "SELECT * FROM model_login WHERE nome ILIKE ? and useradmin is false and usuario_id = ? order by nome offset "+offset+" limit 5";
	PreparedStatement statement = connection.prepareStatement(sql);
	statement.setString(1, "%" + nome + "%");
	statement.setLong(2, userLogado);
	ResultSet result = statement.executeQuery();
	while (result.next()) {
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(result.getLong("id"));
		modelLogin.setEmail(result.getString("email"));
		modelLogin.setLogin(result.getString("login"));
		modelLogin.setNome(result.getString("nome"));
		modelLogin.setPerfil(result.getString("perfil"));
		modelLogin.setSexo(result.getString("sexo"));
		// modelLogin.setSenha(result.getString("password"));
		retornoList.add(modelLogin);
	}

	return retornoList;
}

	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException {
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE nome ILIKE ? and useradmin is false and usuario_id = ? order by nome limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
			// modelLogin.setSenha(result.getString("password"));
			retornoList.add(modelLogin);
		}

		return retornoList;
	}

	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		/*
		 * com o parametro user logado garantimos que só será consultado somente
		 * cadastros que o usuario logado registrou
		 */
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin = false and usuario_id=" + userLogado
				+ " order by nome offset " + offset + " limit 5;";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet result = statement.executeQuery();
		while (result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
			modelLogin.setFotoUser(result.getString("fotouser"));
			modelLogin.setCep(result.getString("cep"));
			modelLogin.setLogradouro(result.getString("logradouro"));
			modelLogin.setBairro(result.getString("bairro"));
			modelLogin.setLocalidade(result.getString("localidade"));
			modelLogin.setUf(result.getString("uf"));
			modelLogin.setNumero(result.getString("numero"));

			retornoList.add(modelLogin);
		}
		return retornoList;
	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {
		/*
		 * com o parametro user logado garantimos que só será consultado somente
		 * cadastros que o usuario logado registrou
		 */
		List<ModelLogin> retornoList = new ArrayList<ModelLogin>();
		String sql = "SELECT * FROM model_login WHERE useradmin = false and usuario_id=" + userLogado
				+ " order by nome limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet result = statement.executeQuery();
		while (result.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(result.getLong("id"));
			modelLogin.setEmail(result.getString("email"));
			modelLogin.setLogin(result.getString("login"));
			modelLogin.setNome(result.getString("nome"));
			modelLogin.setPerfil(result.getString("perfil"));
			modelLogin.setSexo(result.getString("sexo"));
			modelLogin.setFotoUser(result.getString("fotouser"));
			modelLogin.setCep(result.getString("cep"));
			modelLogin.setLogradouro(result.getString("logradouro"));
			modelLogin.setBairro(result.getString("bairro"));
			modelLogin.setLocalidade(result.getString("localidade"));
			modelLogin.setUf(result.getString("uf"));
			modelLogin.setNumero(result.getString("numero"));

			retornoList.add(modelLogin);
		}

		return retornoList;
	}

	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		// passasmos o userLogado como parametro para a hora da consulta o usuario n
		// buscar registro de outros usuários
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) and useradmin = false and usuario_id=? order by nome ";
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
			modelLogin.setFotoUser(result.getString("fotouser"));
			modelLogin.setExtesaoFotoUser(result.getString("extensaofotouser"));
			modelLogin.setCep(result.getString("cep"));
			modelLogin.setLogradouro(result.getString("logradouro"));
			modelLogin.setBairro(result.getString("bairro"));
			modelLogin.setLocalidade(result.getString("localidade"));
			modelLogin.setUf(result.getString("uf"));
			modelLogin.setNumero(result.getString("numero"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}

	public ModelLogin consultaUsuario(String login) throws Exception {
		// passasmos o userLogado como parametro para a hora da consulta o usuario n
		// buscar registro de outros usuários
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) and useradmin = false order by nome ; ";
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
			modelLogin.setFotoUser(result.getString("fotouser"));
			modelLogin.setCep(result.getString("cep"));
			modelLogin.setLogradouro(result.getString("logradouro"));
			modelLogin.setBairro(result.getString("bairro"));
			modelLogin.setLocalidade(result.getString("localidade"));
			modelLogin.setUf(result.getString("uf"));
			modelLogin.setNumero(result.getString("numero"));
		}
		return modelLogin;

	}

	public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		// passasmos o userLogado como parametro para a hora da consulta o usuario n
		// buscar registro de outros usuários
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
	
	public ModelLogin consultaUsuarioID(Long id) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) /*Se tem resultado*/ {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("password"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotoUser(resutlado.getString("fotouser"));
			modelLogin.setExtesaoFotoUser(resutlado.getString("extensaofotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setLocalidade(resutlado.getString("localidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
		}
		
		
		return modelLogin;
		
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
			modelLogin.setFotoUser(result.getString("fotouser"));
			modelLogin.setExtesaoFotoUser(result.getString("extensaofotouser"));
			modelLogin.setCep(result.getString("cep"));
			modelLogin.setLogradouro(result.getString("logradouro"));
			modelLogin.setBairro(result.getString("bairro"));
			modelLogin.setLocalidade(result.getString("localidade"));
			modelLogin.setUf(result.getString("uf"));
			modelLogin.setNumero(result.getString("numero"));
		}

		return modelLogin;// se não entrar no loop while o retorno vai ser null
	}

	public int totalPaginas(Long userLogado) throws Exception {
		String sql = "select count(1) from model_login where useradmin is false and usuario_id = " + userLogado;
		// esse sql conta quantas linhas (registros) tem vinculado ao usuario lugado
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet result = statement.executeQuery();

		result.next();

		Double totalCadastros = result.getDouble("count");// pega o retorno do comando sql e seleciona o valor da coluna
															// total

		Double porpagina = 5.0;// estabele o número limite de registros que sera seleciona

		Double paginas = totalCadastros / porpagina;// quantas paginas será gerado para o número total de registros

		Double resto = paginas % 2;

		if (resto > 0) {
			paginas++;// se sobrar resto será gerada mais uma pagina para mostrar esse resto
		}

		return paginas.intValue();
	}

	public boolean validaLogin(String login) throws Exception {
		// aqui não precisa passar o userLogado como parametro pq quando vamos validar n
		// sabemos o usuario
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE upper(login) = upper(?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		ResultSet result = statement.executeQuery();
		result.next();// vai entrar nos valores
		return result.getBoolean("existe");

	}

	public void deletarUser(String idUser) throws Exception {
		// se só será exibidos os usuarios cadastrados pelo usuario logado não
		// precisamos passar o userLogado como parametro
		String sql = "DELETE FROM public.model_login WHERE id=? and useradmin = false ;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		connection.commit();
	}

}
