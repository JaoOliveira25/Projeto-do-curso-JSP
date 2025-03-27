package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;

import model.ModelTelefone;

public class DAOTelefoneRepository {
	private Connection connection;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public DAOTelefoneRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception {
		String sql = "INSERT INTO telefone(numero, usuario_pai_id, usuario_cad_id) VALUES(?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedStatement.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		preparedStatement.execute();
		connection.commit();
		
	}
	
	public void deleteFone(Long id) throws Exception  {
		String sql = "DELETE FROM telefone WHERE id = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
		connection.commit();
		
	}
	
	
	public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		String sql = "SELECT * FROM telefone WHERE usuario_pai_id = ? AND useradmin is false";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUserPai);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setId(resultSet.getLong("id"));
			modelTelefone.setNumero(resultSet.getString("numero"));
			
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);
		}

		return retorno;
	}
	
	
	
}
