package br.edu.utfpr.modelo;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
@NamedQueries({
	
		/* Consulta todos os alunos */
		@NamedQuery(name = "Aluno.consultarTodos",
		query = "SELECT a FROM Aluno a"),
		
		@NamedQuery(name = "Aluno.consultarUser",
		query = "SELECT a FROM Aluno a WHERE a.user = :user"),
		
		/* Consulta todas as matriculas de um aluno */
		@NamedQuery(name = "Aluno.consultarMatricula",
		query = "SELECT m FROM Matricula m JOIN m.aluno a WHERE a.id = :id"),
		
		/* Consulta aluno pelo cpf */
		@NamedQuery(name = "Aluno.consultarPeloCpf",
		query = "SELECT a FROM Aluno a WHERE cpf = :cpf"),
		
		/* Consulta aluno pelo nome, se tiver varios alunos com o nome "Joao" ele vai retornar todos */
		@NamedQuery(name = "Aluno.consultarTodosNome",
		query = "SELECT a FROM Aluno a WHERE a.nome LIKE CONCAT ('%', :nome, '%')"),
		
		/* Consulta um aluno por meio do curso */
		@NamedQuery(name = "Aluno.consultarCurso",
		query = "SELECT a FROM Aluno a JOIN FETCH a.curso c WHERE c.id = :id"),
		
		@NamedQuery(name = "Aluno.consultarMatriculaDisciplina",
		query = "SELECT a FROM Aluno a JOIN FETCH a.matriculas m JOIN FETCH m.disciplina d WHERE d.id = :id")
		
})
public class Aluno extends Usuario implements Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer anoEntrada;
	
	/* Um aluno pode ter varias matriculas, mas uma matricula so pode estar vinculada com uma disciplina */
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@OneToMany(mappedBy = "aluno")
	private List<Matricula> matriculas;
	
	/* Um aluno só pode estar em um semestre */
	@ManyToOne
	@JoinColumn(name = "semestre_id")
	private Semestre semestre;

	/* Um aluno so pode estar matriculado em um curso */
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	/* Construtores */
	public Aluno() {
	}
	
	public Aluno(Integer id, Integer anoEntrada) {
		super();
		this.id = id;
		this.anoEntrada = anoEntrada;
	}
	
	/* Getters e Setters */
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnoEntrada() {
		return anoEntrada;
	}

	public void setAnoEntrada(Integer anoEntrada) {
		this.anoEntrada = anoEntrada;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	
	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	/* Equals e HashCode */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(id, other.id);
	}

}
