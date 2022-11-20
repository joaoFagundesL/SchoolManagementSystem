package br.edu.utfpr.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity

@NamedQueries({
	/* Consulta todos os admins */
	@NamedQuery(name = "Administrador.consultarTodos",
	query = "SELECT a FROM Administrador a"),
	
	@NamedQuery(name = "Administrador.consultarPeloNome",
	query = "SELECT a FROM Administrador a WHERE a.user = :user"),

})
public class Administrador extends Usuario implements Entidade {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer id;

	public Administrador(Integer id) {
		this.id = id;
	}
	
	public Administrador() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
