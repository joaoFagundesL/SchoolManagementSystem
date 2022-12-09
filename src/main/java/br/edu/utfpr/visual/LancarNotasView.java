package br.edu.utfpr.visual;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.MatriculaDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import br.edu.utfpr.modelo.Professor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;

public class LancarNotasView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField primeiraNotaField;
	private JTextField segundaNotaField;
	private JTextField terceiraNotaField;
	private JTextField quartaNotaField;
	private JTextField raField;
	private JTextField matriculaField;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LancarNotasView(Professor p) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Nota 1");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel.setBounds(253, 224, 157, 17);
		add(lblNewLabel);

		primeiraNotaField = new JTextField();
		primeiraNotaField.setBounds(312, 222, 143, 21);
		add(primeiraNotaField);
		primeiraNotaField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nota 2");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1.setBounds(253, 253, 60, 17);
		add(lblNewLabel_1);

		JButton btnNewButton_1 = new JButton("Limpar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(211, 372, 105, 27);
		add(btnNewButton_1);

		JLabel lblConsultaDeProfessores = new JLabel("Lançamento de Notas");
		lblConsultaDeProfessores.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConsultaDeProfessores.setBounds(38, 163, 227, 17);
		add(lblConsultaDeProfessores);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nota 3");
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(253, 282, 60, 17);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Nota 4");
		lblNewLabel_1_2.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(253, 311, 60, 17);
		add(lblNewLabel_1_2);
		
		segundaNotaField = new JTextField();
		segundaNotaField.setColumns(10);
		segundaNotaField.setBounds(312, 253, 143, 21);
		add(segundaNotaField);
		
		terceiraNotaField = new JTextField();
		terceiraNotaField.setColumns(10);
		terceiraNotaField.setBounds(312, 282, 143, 21);
		add(terceiraNotaField);
		
		quartaNotaField = new JTextField();
		quartaNotaField.setColumns(10);
		quartaNotaField.setBounds(312, 311, 143, 21);
		add(quartaNotaField);
		
		JLabel lblRaDoAluno = new JLabel("Ra");
		lblRaDoAluno.setFont(new Font("Dialog", Font.BOLD, 13));
		lblRaDoAluno.setBounds(54, 224, 53, 17);
		add(lblRaDoAluno);
		
		raField = new JTextField();
		raField.setColumns(10);
		raField.setBounds(92, 222, 143, 21);
		add(raField);
		
		JButton btnNewButton_1_1 = new JButton("Lançar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AlunoDAO alunoDao = new AlunoDAO();
		
				MatriculaDAO matriculaDao = new MatriculaDAO();
				List<Aluno> alunos = alunoDao.consultarTodos();
				
				Integer matriculaCampo = Integer.parseInt(matriculaField.getText());
				
				Matricula matriculaAluno = matriculaDao.consultarPorId(Matricula.class, matriculaCampo);
		
				
				if(verificarSeRaExiste(alunos) == true && verificarSeMatriculaExiste() == true) {
					double notaFinal = calcularMediaNotas();	
					
					matriculaDao.update(matriculaAluno, notaFinal);
				}
			}
		});
		btnNewButton_1_1.setBounds(96, 372, 105, 27);
		add(btnNewButton_1_1);
		
		matriculaField = new JTextField();
		matriculaField.setColumns(10);
		matriculaField.setBounds(92, 251, 143, 21);
		add(matriculaField);
		
		JLabel lblMatricula = new JLabel("Matricula");
		lblMatricula.setFont(new Font("Dialog", Font.BOLD, 13));
		lblMatricula.setBounds(12, 253, 157, 17);
		add(lblMatricula);

		}
	
		public boolean verificarSeRaExiste(List<Aluno> alunos) {
			Integer raAluno = Integer.parseInt(raField.getText());
			
			boolean encontrou = false;
			
			for(Aluno a : alunos) {
				if(a.getId().equals(raAluno)) {
					encontrou = true;
				}
			}
			
			if(encontrou == false) {
				JFrame frame = new JFrame("Erro");
				JOptionPane.showMessageDialog(frame, "Ra inválido!");	
				return false;
			}
			
			return true;
		}
		
		public boolean verificarSeMatriculaExiste() {
			if(matriculaField.getText().equals("")) {
				JFrame frame = new JFrame("Erro");
				JOptionPane.showMessageDialog(frame, "Informe a matrícula do aluno!");
			}
			
			boolean encontrou = false;
			
			AlunoDAO alunoDao = new AlunoDAO();
			Aluno aluno = alunoDao.consultarPorId(Aluno.class, Integer.parseInt(raField.getText()));
			
			List<Matricula> matriculas = aluno.getMatriculas();
			
			for(Matricula m : matriculas) {
				System.out.println(m.getId());
				if(m.getId().equals(Integer.parseInt(matriculaField.getText()))) {
					encontrou = true;
				}
			}
			
			if(encontrou == false) {
				JFrame frame = new JFrame("Erro");
				JOptionPane.showMessageDialog(frame, "Matricula não encontrada!");
				return false;
			}
			
			return true;
		}
		
		public double calcularMediaNotas() {
			JTextField raFields[] = {primeiraNotaField, segundaNotaField, terceiraNotaField, quartaNotaField};
			
			double somaNotas = 0;
			
			int contadorDeCamposPreenchidos = 0;
			
			for(int i = 0; i < raFields.length; i++) {
				if(!raFields[i].getText().equals("")) {
					contadorDeCamposPreenchidos++;
					somaNotas += Double.parseDouble(raFields[i].getText());
				} 			
			}
			
			return somaNotas / contadorDeCamposPreenchidos;
		}

}
