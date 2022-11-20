package br.edu.utfpr.visual;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.utfpr.controller.DisciplinaController;
import br.edu.utfpr.controller.ProfessorController;
import br.edu.utfpr.dao.ProfessorDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VincularProfessorView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField professorField;
	private JTextField disciplinaField;

	public VincularProfessorView() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id professor");
		lblNewLabel.setBounds(53, 201, 106, 13);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Vincular Professor");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(53, 127, 186, 34);
		add(lblNewLabel_1);
		
		professorField = new JTextField();
		professorField.setBounds(169, 198, 173, 19);
		add(professorField);
		professorField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Id disciplina");
		lblNewLabel_2.setBounds(53, 241, 97, 13);
		add(lblNewLabel_2);
		
		disciplinaField = new JTextField();
		disciplinaField.setBounds(169, 238, 173, 19);
		add(disciplinaField);
		disciplinaField.setColumns(10);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ProfessorController profController = new ProfessorController();
				DisciplinaController discController = new DisciplinaController();
			
				
				if(professorField.getText().compareTo("") == 0 || disciplinaField.getText().compareTo("") == 0) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos!");
					return;
				}
				
				Integer idProf = Integer.parseInt(professorField.getText());
				Integer idDisciplina = Integer.parseInt(disciplinaField.getText());
				
				if(profController.consultarPeloId(idProf) == null || discController.consultarId(idDisciplina) == null) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Id nao encontrado!");
					return;
				}
			
				if(!profController.inserirNoBanco(idProf, idDisciplina)) {
					JFrame frame = new JFrame("Erro!");
					JOptionPane.showMessageDialog(frame, "Erro ao vincular!");
					return;
				}
				
				JFrame frame = new JFrame("Mensagem");
				JOptionPane.showMessageDialog(frame, "Enviado com sucesso!");
			}
		});
		btnNewButton.setBounds(169, 276, 85, 21);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Limpar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				professorField.setText("");
				disciplinaField.setText("");
			}
		});
		btnNewButton_1.setBounds(257, 276, 85, 21);
		add(btnNewButton_1);
		
	}
}
