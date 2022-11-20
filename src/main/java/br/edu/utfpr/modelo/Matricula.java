package br.edu.utfpr.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Matricula implements Entidade {
	
	/*
	 * Matricula e uma classe de associacao que possui atributos, por isso 
	 * preciso vincular ela com aluno e disciplina
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/* A matricula é de apenas um aluno */
	@ManyToOne
	@JoinColumn(name = "aluno_id")
	private Aluno aluno;
	
	/* A matricula so pode estar vinculada com uma disciplina */
	@ManyToOne
	@JoinColumn(name = "disciplina_id")
	private Disciplina disciplina;
	
	/* Atributo que vai dizer se a matricula está ativa, inativa.. */
	private String ativo;
	
	/* Nota final do aluno na disciplina */
	private Double notaFinal;
	
	/* Construtores */
	public Matricula(Integer id, Aluno aluno, Disciplina disciplina, Double notaFinal) {
		this.id = id;
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.notaFinal = notaFinal;
	}
	
	public Matricula() {}
	
	/* Getters e Setters */
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
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
		Matricula other = (Matricula) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return id + ", ativo = " + ativo;
	}

	public Double getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(Double notaFinal) {
		this.notaFinal = notaFinal;
	}
}
