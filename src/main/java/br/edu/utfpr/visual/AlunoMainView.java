package br.edu.utfpr.visual;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.edu.utfpr.modelo.Aluno;

public class AlunoMainView {
	JPanel panelCont = new JPanel();
	JPanel panelFirst = new JPanel();
	JButton btnVoltar = new JButton("Voltar");
	CardLayout cl = new CardLayout();

	public static void main(Aluno a) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AlunoMainView(a);
			}
		});
	}

	public AlunoMainView(Aluno a) {
		JFrame frame = new JFrame("Sistema");
		initialize(frame, a);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}

	private void initialize(JFrame frame, Aluno a) {
	
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		MatriculaView matriculaView = new MatriculaView(a);
		GradeView gradeView = new GradeView(a);
		AlunoBuscarDisciplinaView alunoDisciplinaView = new AlunoBuscarDisciplinaView(a);
		
		panelFirst.setLayout(null);
		panelFirst.setBackground(Color.WHITE);
		panelCont.setLayout(cl);
		panelCont.add(panelFirst, "1");
		
		tabbedPane.setBounds(219, -88, 573, 656);
		panelFirst.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		tabbedPane.addTab("New tab", null, panel_1, null);
		tabbedPane.addTab("New tab", null, matriculaView, null);
		
		tabbedPane.addTab("New tab", null, gradeView, null);
		tabbedPane.addTab("New tab", null, alunoDisciplinaView, null);
		tabbedPane.addTab("New tab", null, null, null);
		tabbedPane.addTab("New tab", null, null, null);
		tabbedPane.addTab("New tab", null, null, null);
		tabbedPane.addTab("New tab", null, null, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		panel_2.setBounds(0, 0, 218, 568);
		panelFirst.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnGerenciaAlunos = new JButton("Consultar Disciplinas");
		btnGerenciaAlunos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnGerenciaAlunos.setForeground(SystemColor.text);
		btnGerenciaAlunos.setBackground(Color.DARK_GRAY);
		btnGerenciaAlunos.setBounds(23, 70, 171, 27);
		panel_2.add(btnGerenciaAlunos);
		
		JButton btnGerenciarProfessor = new JButton("Realizar Matricula");
		btnGerenciarProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matriculaView.popularComboBoxCurso();
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnGerenciarProfessor.setForeground(Color.WHITE);
		btnGerenciarProfessor.setBackground(Color.DARK_GRAY);
		btnGerenciarProfessor.setBounds(23, 109, 171, 27);
		panel_2.add(btnGerenciarProfessor);
		
		JButton btnGerenciarCurso = new JButton("Grade");
		btnGerenciarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnGerenciarCurso.setForeground(Color.WHITE);
		btnGerenciarCurso.setBackground(Color.DARK_GRAY);
		btnGerenciarCurso.setBounds(23, 148, 171, 27);
		panel_2.add(btnGerenciarCurso);
		
		frame.getContentPane().add(panelCont);
		
	}

}
