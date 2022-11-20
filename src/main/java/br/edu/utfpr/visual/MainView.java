package br.edu.utfpr.visual;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import br.edu.utfpr.modelo.Administrador;

import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;

public class MainView {
	JPanel panelCont = new JPanel();
	JPanel panelFirst = new JPanel();
	JButton btnVoltar = new JButton("Voltar");
	CardLayout cl = new CardLayout();

	public MainView() {
		JFrame frame = new JFrame("Sistema");
		initComponents(frame);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public void initComponents(JFrame frame) {
		AlunoView alunoView = new AlunoView();
		ProfessorView professorView = new ProfessorView();
		CursoView cursoView = new CursoView();
		DisciplinaView disciplinaView = new DisciplinaView();
		BuscarAlunoView buscarAlunoView = new BuscarAlunoView();
		BuscarProfessorView buscarProfessorView = new BuscarProfessorView();
		BuscarDisciplinaView buscarDisciplinaView = new BuscarDisciplinaView();
		VincularProfessorView vincularProfessor = new VincularProfessorView();
	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		panelFirst.setLayout(null);
		panelFirst.setBackground(Color.WHITE);
		panelCont.setLayout(cl);
		panelCont.add(panelFirst, "1");
		
		tabbedPane.setBounds(219, -88, 573, 656);
		panelFirst.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		tabbedPane.addTab("New tab", null, alunoView, null);
		tabbedPane.addTab("New tab", null, professorView, null);
		tabbedPane.addTab("New tab", null, cursoView, null);
		tabbedPane.addTab("New tab", null, disciplinaView, null);
		tabbedPane.addTab("New tab", null, buscarAlunoView, null);
		tabbedPane.addTab("New tab", null, buscarProfessorView, null);
		tabbedPane.addTab("New tab", null, buscarDisciplinaView, null);
		tabbedPane.addTab("New tab", null, vincularProfessor, null);

		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		panel_2.setBounds(0, 0, 218, 568);
		panelFirst.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnGerenciaAlunos = new JButton("Gerenciar Aluno");
		btnGerenciaAlunos.setForeground(SystemColor.text);
		btnGerenciaAlunos.setBackground(Color.DARK_GRAY);
		btnGerenciaAlunos.setBounds(23, 70, 171, 27);
		panel_2.add(btnGerenciaAlunos);
		
		JButton btnGerenciarProfessor = new JButton("Gerenciar Professor");
		btnGerenciarProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorView.popularComboBoxCurso();
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnGerenciarProfessor.setForeground(Color.WHITE);
		btnGerenciarProfessor.setBackground(Color.DARK_GRAY);
		btnGerenciarProfessor.setBounds(23, 109, 171, 27);
		panel_2.add(btnGerenciarProfessor);
		
		JButton btnGerenciarCurso = new JButton("Gerenciar Curso");
		btnGerenciarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnGerenciarCurso.setForeground(Color.WHITE);
		btnGerenciarCurso.setBackground(Color.DARK_GRAY);
		btnGerenciarCurso.setBounds(23, 148, 171, 27);
		panel_2.add(btnGerenciarCurso);
		
		JButton btnGerenciarDisciplina = new JButton("Gerenciar Disciplina");
		btnGerenciarDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disciplinaView.popularComboBoxCurso();
				disciplinaView.popularComboBoxModelo();
				tabbedPane.setSelectedIndex(4);
			}
		});
		btnGerenciarDisciplina.setForeground(Color.WHITE);
		btnGerenciarDisciplina.setBackground(Color.DARK_GRAY);
		btnGerenciarDisciplina.setBounds(23, 187, 171, 27);
		panel_2.add(btnGerenciarDisciplina);
		
		JLabel lblSistemaFaculdade = new JLabel("Sistema Faculdade");
		lblSistemaFaculdade.setFont(new Font("Dialog", Font.BOLD, 16));
		lblSistemaFaculdade.setForeground(Color.WHITE);
		lblSistemaFaculdade.setBounds(33, 12, 161, 46);
		panel_2.add(lblSistemaFaculdade);
		
		
		
		JButton btnConsultarAluno = new JButton("Consultar Aluno");
		btnConsultarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(5);
			}
		});
		btnConsultarAluno.setForeground(Color.WHITE);
		btnConsultarAluno.setBackground(Color.DARK_GRAY);
		btnConsultarAluno.setBounds(23, 224, 171, 27);
		panel_2.add(btnConsultarAluno);
		
		JButton btnConsultarProfessor = new JButton("Consultar Professor");
		btnConsultarProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(6);
			}
		});
		btnConsultarProfessor.setForeground(Color.WHITE);
		btnConsultarProfessor.setBackground(Color.DARK_GRAY);
		btnConsultarProfessor.setBounds(23, 261, 171, 27);
		panel_2.add(btnConsultarProfessor);
		
		JButton btnConsultarDisciplina = new JButton("Consultar Disciplina");
		btnConsultarDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarDisciplinaView.popularComboBoxCurso();
				tabbedPane.setSelectedIndex(7);
			}
		});
		btnConsultarDisciplina.setForeground(Color.WHITE);
		btnConsultarDisciplina.setBackground(Color.DARK_GRAY);
		btnConsultarDisciplina.setBounds(23, 298, 171, 27);
		panel_2.add(btnConsultarDisciplina);
		
		JButton btnVincularProfessor = new JButton("Vincular Professor");
		btnVincularProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(8);
			}
		});
		btnVincularProfessor.setForeground(Color.WHITE);
		btnVincularProfessor.setBackground(Color.DARK_GRAY);
		btnVincularProfessor.setBounds(23, 335, 171, 27);
		panel_2.add(btnVincularProfessor);
	
		btnGerenciaAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alunoView.popularComboBoxCurso();
				tabbedPane.setSelectedIndex(1);
			}
		});
		frame.getContentPane().add(panelCont);
		
	}

	public void trocarPanel(JButton btn) {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainView();
			}
		});
	}
}