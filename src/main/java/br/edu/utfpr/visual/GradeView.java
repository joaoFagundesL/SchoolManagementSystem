package br.edu.utfpr.visual;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
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
import br.edu.utfpr.modelo.DiaSemana;
import br.edu.utfpr.modelo.Disciplina;
import br.edu.utfpr.modelo.Matricula;
import javax.swing.JComboBox;
import java.awt.Color;

public class GradeView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;

	public GradeView(Aluno al) {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 197, 495, 190);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Segunda", "Terça", "Quarta", "Quinta", "Sexta" }));
		scrollPane.setViewportView(table);
		
		mostrarGrade(al);
		
	}
	
	public void mostrarGrade(Aluno al) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		AlunoDAO alDao = new AlunoDAO();
		MatriculaDAO mdao = new MatriculaDAO();
		
		List<Matricula> matriculas = alDao.consultarMatricula(al.getId());

		for (Matricula m : matriculas) {
			Object[] arr = new Object[5];
			Disciplina d = mdao.consultarDisciplina(m.getId());
			List<DiaSemana> dias = d.getDias();
		
			
			for(int i = 0; i < dias.size(); i++) {
				if(dias.get(i).getNome().equals("Segunda")) {
					arr[0] = d.getNome();
				}
				else if(dias.get(i).getNome().equals("Terca")) {
					arr[1] = d.getNome();
				}
				else if(dias.get(i).getNome().equals("Quarta")) {
					arr[2] = d.getNome();
				}
				else if(dias.get(i).getNome().equals("Quinta")) {
					arr[3] = d.getNome();
				}
				else if(dias.get(i).getNome().equals("Sexta")) {
					arr[4] = d.getNome();
				}
			}
			modelo.addRow(arr);
		}		
	}
	
	
	public void limparTabela() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
	}
	
	
}
