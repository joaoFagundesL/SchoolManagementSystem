package br.edu.utfpr.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	
	@NamedQuery(name = "ModeloDisciplina.consultarTodos",
			query = "SELECT md FROM ModeloDisciplina md"),
	
	/* Retorna todas as disciplinas de um determinado modelo */
	@NamedQuery(name = "ModeloDisciplina.consultarDisciplina",
	query = "SELECT d FROM Disciplina d JOIN FETCH d.modelo m WHERE m.id = :id"),
	
})
public class ModeloDisciplina implements Entidade {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	/* Em um modelo pode ter varias disciplinas */
	@OneToMany(mappedBy = "modelo")
	List<Disciplina> disciplinas;
	
	/* Construtores */
	public ModeloDisciplina(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public ModeloDisciplina() {
	}
	
	/* Getters e Setters */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
