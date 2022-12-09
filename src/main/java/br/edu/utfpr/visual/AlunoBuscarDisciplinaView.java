package br.edu.utfpr.visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.dao.AlunoDAO;
import br.edu.utfpr.dao.CursoDAO;
import br.edu.utfpr.dao.DisciplinaDAO;
import br.edu.utfpr.dao.MatriculaDAO;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Curso;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import br.edu.utfpr.modelo.Professor;

import java.awt.Font;

public class AlunoBuscarDisciplinaView extends JPanel {

	private JTable table;
	private JTextField disciplinaField;

	private static final long serialVersionUID = 1L;

	public AlunoBuscarDisciplinaView(Aluno a) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 430, 506, 190);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nome", "Modelo", "Semestre", "Carga", "Curso" }));
		scrollPane.setViewportView(table);

		JButton btnNewButton_2 = new JButton("Limpar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparTudo();
			}
		});
		btnNewButton_2.setBounds(325, 305, 105, 27);
		add(btnNewButton_2);

		JLabel lblNewLabel_1 = new JLabel("Codigo da Disciplina");
		lblNewLabel_1.setBounds(25, 205, 143, 17);
		add(lblNewLabel_1);

		disciplinaField = new JTextField();
		disciplinaField.setBounds(186, 203, 114, 21);
		add(disciplinaField);
		disciplinaField.setColumns(10);

		JButton btnMostrarDisciplinas = new JButton("Disciplinas Matriculadas");
		btnMostrarDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				limparTabela();
				
				AlunoDAO aldao = new AlunoDAO();
				List<Matricula> matriculas = aldao.consultarMatricula(a.getId());
			
				if(matriculas.size() <= 0) {
					JFrame frame = new JFrame("Erro");
					JOptionPane.showMessageDialog(frame, "Nenhuma disciplina matriculada!");
				}
				
				mostrarRegistrosDisciplina(matriculas);			

			}
		});
		btnMostrarDisciplinas.setBounds(73, 307, 240, 22);
		add(btnMostrarDisciplinas);
		
		JLabel lblNewLabel = new JLabel("Consulta de Disciplinas");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 150, 237, 27);
		add(lblNewLabel);
		
		JButton enviarBtn = new JButton("Enviar");
		enviarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDisciplinaId(Integer.parseInt(disciplinaField.getText()), a);
			}
		});
		enviarBtn.setBounds(315, 200, 85, 27);
		add(enviarBtn);
		
	}
	
	/* Arrumar caso a disciplina nao exista */
	public void mostrarDisciplinaId(Integer id, Aluno a) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		DisciplinaDAO dao = new DisciplinaDAO();
		limparTabela();
		
		boolean encontrou = false;
		
		AlunoDAO aldao = new AlunoDAO();
		List<Matricula> matriculas = aldao.consultarMatricula(a.getId());
	
		
		for(Matricula m : matriculas) {
			Disciplina d = m.getDisciplina();
			if(id == d.getId()) {				
				Object[] arr = new Object[6];
				arr[0] = d.getId();
				arr[1] = d.getNome();
				arr[2] = d.getModelo().getNome();
				arr[3] = d.getSemestre().getId();
				arr[4] = d.getCargaHoraria();
				arr[5] = d.getCurso().getNome();
				modelo.addRow(arr);
				encontrou = true;
			} 
		}
		
		if(encontrou == false) {
			JFrame frame = new JFrame("Erro");
			JOptionPane.showMessageDialog(frame, "Nenhuma disciplina matriculada com esse id!");
		}
		
	}

	public void mostrarRegistrosDisciplina(List<Matricula> matriculas) {
		
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				
		for(Matricula m : matriculas) {
			Object[] arr = new Object[6];
			Disciplina d = m.getDisciplina();			
			arr[0] = d.getId();
			arr[1] = d.getNome();
			arr[2] = d.getModelo().getNome();
			arr[3] = d.getSemestre().getId();
			arr[4] = d.getCargaHoraria();
			arr[5] = d.getCurso().getNome();
			modelo.addRow(arr);
		}
		
	}

	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
	}


	public void limparTudo() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		disciplinaField.setText("");
	}
}
