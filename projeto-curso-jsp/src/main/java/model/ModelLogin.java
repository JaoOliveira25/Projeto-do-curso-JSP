package model;

import java.io.Serializable;

public class ModelLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private boolean userAdmin;
	private String perfil;
	private String sexo;
	private String fotoUser;
	private String extesaoFotoUser;
	
	public String getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(String fotoUser) {
		this.fotoUser = fotoUser;
	}

	public String getExtesaoFotoUser() {
		return extesaoFotoUser;
	}

	public void setExtesaoFotoUser(String extesaoFotoUser) {
		this.extesaoFotoUser = extesaoFotoUser;
	}
	
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public boolean isUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}
	
	public boolean isNovo() {
		if(this.id == null) {
			return true;//inserir novo
		}else if(this.id != null && this.id > 0) {
			return false;//atualiza 
		}
		
		return id == null;
	}
	
	public String getLogin() {
		return login;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
