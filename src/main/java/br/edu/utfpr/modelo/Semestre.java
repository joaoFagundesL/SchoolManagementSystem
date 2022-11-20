package br.edu.utfpr.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	
	/* Consulta para retornar todos os semestres */
	@NamedQuery(name = "Semestre.consultarTodos",
				query = "SELECT d FROM Semestre d"),
	
	/* Retorna todas as disciplinas de um determinado semestre */
	@NamedQuery(name = "Semestre.consultarDisciplina",
	query = "SELECT d FROM Disciplina d JOIN FETCH d.semestre s WHERE s.id = :id"),
	
	/* Retorna todos alunos de um determinado semestre */
	@NamedQuery(name = "Semestre.consultarAluno",
	query = "SELECT a FROM Aluno a JOIN FETCH a.semestre s WHERE s.id = :id"),
})
public class Semestre implements Entidade{
	
	/* Nao é auto increment porque o limite de semestres é apenas 10 */
	@Id
	private Integer id;
	
	/* Em um semestre há varias disciplinas */
	@OneToMany(mappedBy = "semestre")
	List<Disciplina> disciplinas;
	
	/* Em um semestre ha varios alunos */
	@OneToMany(mappedBy = "semestre")
	List<Aluno> alunos;
	
	/* Construtores */
	public Semestre(Integer id) {
		this.id = id;
	}

	public Semestre() {
	}
	
	/* Getters e Setters */
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}
