package br.edu.utfpr.exceptions;

/* Caso a duracao do curso seja maior que 5 anos */
public class DuracaoCursoInvalida extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuracaoCursoInvalida(String str) {
		super(str);
	}
}
