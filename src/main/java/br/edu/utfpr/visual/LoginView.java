package br.edu.utfpr.visual;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.edu.utfpr.controller.AdminController;
import br.edu.utfpr.controller.AlunoController;
import br.edu.utfpr.controller.ProfessorController;
import br.edu.utfpr.modelo.Administrador;
import br.edu.utfpr.modelo.Aluno;
import br.edu.utfpr.modelo.Professor;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;

public class LoginView {

	
	JPanel panelCont = new JPanel();
	JPanel panelFirst = new JPanel();
	JButton btnVoltar = new JButton("Voltar");
	CardLayout cl = new CardLayout();
	private JTextField textField;
	private JPasswordField passwordField;
	
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();

	JFrame frame = new JFrame("Sistema");
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public LoginView() {
		initComponents(frame);
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initComponents(JFrame frame) {
		panelFirst.setLayout(null);
		panelFirst.setBackground(Color.LIGHT_GRAY);
		panelCont.setLayout(cl);
		panelCont.add(panelFirst, "1");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecione", "Aluno", "Professor", "Administrador"}));
		comboBox.setBounds(118, 156, 186, 21);
		panelFirst.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(118, 64, 186, 21);
		panelFirst.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsuario = new JLabel("User");
		lblUsuario.setBounds(118, 47, 60, 17);
		panelFirst.add(lblUsuario);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(118, 94, 60, 17);
		panelFirst.add(lblSenha);
		
		JLabel lblEntrarComo = new JLabel("Entrar como");
		lblEntrarComo.setBounds(117, 138, 83, 17);
		panelFirst.add(lblEntrarComo);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(118, 110, 186, 21);
		panelFirst.add(passwordField);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String op = (String) comboBox.getSelectedItem();
				if(op == "Administrador") {
					logarAdmin();
				}
				
				else if(op == "Aluno") {
					logarAluno();
				}
				
				else if(op == "Professor") {
					logarProfessor();
				}
				
				else {
					erroSelecao();
				}	
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setBounds(118, 192, 186, 27);
		panelFirst.add(btnNewButton_1);
		frame.getContentPane().add(panelCont);
		
	}
	
	public void logarAluno() {
		AlunoController adController = new AlunoController();
		Aluno a = adController.validarLogin(textField.getText(), String.valueOf(passwordField.getPassword()));
		if(a == null) {
			JFrame frame = new JFrame("Erro");
			JOptionPane.showMessageDialog(frame, "Login ou Senha inválidos!");
		} else {				
			frame.dispose();
			frame.setVisible(false);
			//trocar view
			new AlunoMainView(a);
		}
	}
	
	public void logarProfessor() {
		ProfessorController adController = new ProfessorController();
		Professor p = adController.validarLogin(textField.getText(), String.valueOf(passwordField.getPassword()));
		
		if(p == null) {
			JFrame frame = new JFrame("Erro");
			JOptionPane.showMessageDialog(frame, "Login ou Senha inválidos!");
		} else {				
			frame.dispose();
			frame.setVisible(false);
			new ProfessorMainView(p);
		}
	}
	
	public void erroSelecao() {
		JFrame frame = new JFrame("Erro");
		JOptionPane.showMessageDialog(frame, "É preciso selecionar uma opção!");
	}
	
	public void logarAdmin() {
		AdminController adController = new AdminController();
		Administrador admin = adController.validarLogin(textField.getText(), String.valueOf(passwordField.getPassword()));
		
		if(admin == null) {
			JFrame frame = new JFrame("Erro");
			JOptionPane.showMessageDialog(frame, "Login ou Senha inválidos!");
		} else {				
			frame.dispose();
			frame.setVisible(false);
			new MainView();
		}
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginView();
			}
		});
	}
}