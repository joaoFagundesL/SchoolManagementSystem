package br.edu.utfpr.modelo;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NamedQueries({
	
	/* Retorna todos os professores cadastrados */
	@NamedQuery(name = "Professor.consultarTodos",
	query = "SELECT p FROM Professor p"),
	
	@NamedQuery(name = "Professor.consultarUser",
	query = "SELECT p FROM Professor p WHERE p.user = :user"),
	
	/* Retorna os professores pelo nome, se houver alguma ocorrencia em qualquer posicao ele retorna todos os nomes */
	@NamedQuery(name = "Professor.consultarTodosNome",
	query = "SELECT p FROM Professor p WHERE p.nome LIKE CONCAT ('%', :nome, '%')"),
	
	/* Consulta professor por meio do CPF */
	@NamedQuery(name = "Professor.consultarPeloCpf",
	query = "SELECT p FROM Professor p WHERE cpf = :cpf"),
	
	
	/* Consulta todos os professores de uma determinada disciplina */
	@NamedQuery(name = "Professor.consultarDisciplinas",
	query = "SELECT p FROM Professor p JOIN FETCH p.disciplinas d WHERE p.id = :id")
	
})

public class Professor extends Usuario implements Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer  id;
	private String titulo;
	private Double salario;
	
	/* Um professor pode ter varias disciplinas. N-M, Criando uma nova tabela no banco */
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "Disciplina_Professor", joinColumns = @JoinColumn(name = "professorid"),
				inverseJoinColumns = @JoinColumn(name = "disciplinaid"))
	private List<Disciplina> disciplinas;
	
	
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;

	/* Construtores */
	public Professor() {
	}

	public Professor(Integer id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}
	
	/* Getters e Setters */
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
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
		Professor other = (Professor) obj;
		return Objects.equals(id, other.id);
	}
}	
