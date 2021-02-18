package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import javax.swing.JTextField;
import javax.swing.JScrollBar;

public class AreaCliente_VerPacotes extends JFrame {

	//private JPanel panel;
	private JPanel panelVerTodosPacotes;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_VerPacotes frame = new AreaCliente_VerPacotes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AreaCliente_VerPacotes() {

		initialize();

	}

	private void initialize() {
		
		/**
		 * 
		 */
		ativarNimbusLookAndFeel();
		
		/**
		 * Define as caracteristicas dos painel base. 
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panelVerTodosPacotes = new JPanel();
		setContentPane(panelVerTodosPacotes);
		panelVerTodosPacotes.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		getContentPane().setLayout(null);
		
		/*
		 * Define a imagem de fundo através de uma label
		 */
		
		JLabel labelVerPacotes = new JLabel("Ver todos os Pacotes Comerciais:");
		labelVerPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelVerPacotes.setBounds(57, 101, 248, 23);
		panelVerTodosPacotes.add(labelVerPacotes);
		
		JLabel labelPacoteNome = new JLabel("Nome:");
		labelPacoteNome.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelPacoteNome.setBounds(360, 189, 71, 23);
		panelVerTodosPacotes.add(labelPacoteNome);
		
		JLabel labelPacoteDescricao = new JLabel("Descrição:");
		labelPacoteDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		labelPacoteDescricao.setBounds(360, 252, 99, 23);
		panelVerTodosPacotes.add(labelPacoteDescricao);
		
		textField = new JTextField();
		textField.setBounds(462, 191, 154, 20);
		panelVerTodosPacotes.add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(462, 252, 154, 103);
		panelVerTodosPacotes.add(textArea);
		
		JList list = new JList();
		list.setBounds(57, 151, 225, 328);
		panelVerTodosPacotes.add(list);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(265, 151, 17, 48);
		panelVerTodosPacotes.add(scrollBar);
		JLabel labelIconFundo = new JLabel("");
		labelIconFundo.setIcon(new ImageIcon(AreaCliente_VerPacotes.class.getResource("/guiComponentes/img/AltranClientes.png")));
		labelIconFundo.setBounds(0, 0, 1368, 547);
		getContentPane().add(labelIconFundo);

		



	}//end initialize



	/**
	 * Activa o Nimbus Look and Feel
	 * 
	 */

	private void ativarNimbusLookAndFeel() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}


	public JPanel returnAreaClienteVerPacotes() {
		return  panelVerTodosPacotes;
	}
}// end class
