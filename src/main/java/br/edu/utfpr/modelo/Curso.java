package br.edu.utfpr.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;

@Entity
@NamedQueries({
	
	/* Retorna todos os cursos */
	@NamedQuery(name = "Curso.consultarTodos",
	query = "SELECT d FROM Curso d"),
	
	/* Retorna um curso pelo nome */
	@NamedQuery(name = "Curso.consultarPeloNome",
	query = "SELECT c FROM Curso c WHERE c.nome = :nome"),
	
})
public class Curso implements Serializable, Entidade{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	private String nome;
	private Integer duracao;
	
	/* Um curso tem várias disciplinas */
	@OneToMany(mappedBy = "curso")
	private List<Disciplina> disciplinas;
	
	@OneToMany(mappedBy = "curso")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	List<Professor> professores;
	
	/* Um curso tem varios alunos */
	@OneToMany(mappedBy = "curso")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	List<Aluno> alunos;
	
	/* Construtores */
	public Curso() {
	}
	
	public Curso(Integer id, String nome, Integer duracao) {
		this.id = id;
		this.setNome(nome);
		this.setDuracao(duracao);
	}
	
	/* Getters e Setters */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
	
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
	
	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}

}
