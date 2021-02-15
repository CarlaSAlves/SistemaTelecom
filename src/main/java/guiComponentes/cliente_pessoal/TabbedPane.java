package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JList;

public class TabbedPane extends JFrame {

	private JPanel contentPane;
	private JPanel panelDados;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPanel panelProdutos;
	private JPanel panelPacotes;
	private JPanel panelPromocoes;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextArea textArea_2;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_6_2;
	private JTextArea textArea_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabbedPane frame = new TabbedPane();
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
	public TabbedPane() {
		
	
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
		
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 80, 720, 359);
		contentPane.add(tabbedPane);
		
		panelDados = new JPanel();
	
		panelPacotes = new JPanel();
		
		panelPromocoes = new JPanel();
		
	
		
		tabbedPane.addTab("Os Meus Dados", null , panelDados);
		panelDados.setLayout(null);
		
		lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(52, 24, 132, 36);
		panelDados.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("NIf");
		lblNewLabel_1.setBounds(52, 71, 132, 56);
		panelDados.add(lblNewLabel_1);
		
		lblNewLabel_3 = new JLabel("Login");
		lblNewLabel_3.setBounds(52, 138, 132, 56);
		panelDados.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Morada");
		lblNewLabel_4.setBounds(52, 192, 132, 56);
		panelDados.add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		textField_4.setBounds(124, 32, 236, 20);
		panelDados.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(124, 89, 236, 20);
		panelDados.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(124, 156, 236, 20);
		panelDados.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(124, 205, 236, 20);
		panelDados.add(textField_7);
		
		// SEgundo painel -  Meus produtos
		
		panelProdutos = new JPanel();
		tabbedPane.addTab("Os Meus produtos", null , panelProdutos);
		panelProdutos.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("O seu pacote comercial");
		lblNewLabel_5.setBounds(42, 31, 156, 43);
		panelProdutos.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Nome");
		lblNewLabel_6.setBounds(10, 77, 46, 14);
		panelProdutos.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("Descricao");
		lblNewLabel_6_1.setBounds(10, 156, 46, 14);
		panelProdutos.add(lblNewLabel_6_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(66, 72, 132, 68);
		panelProdutos.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(66, 151, 132, 68);
		panelProdutos.add(textArea_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(66, 254, 132, 22);
		panelProdutos.add(comboBox);
		
		JLabel lblNewLabel_7 = new JLabel("As suas promocoes");
		lblNewLabel_7.setBounds(287, 37, 138, 31);
		panelProdutos.add(lblNewLabel_7);
		
		textArea_2 = new JTextArea();
		textArea_2.setBounds(281, 77, 132, 68);
		panelProdutos.add(textArea_2);
		
		lblNewLabel_8 = new JLabel("Nome");
		lblNewLabel_8.setBounds(225, 82, 46, 14);
		panelProdutos.add(lblNewLabel_8);
		
		lblNewLabel_6_2 = new JLabel("Descricao");
		lblNewLabel_6_2.setBounds(225, 161, 46, 14);
		panelProdutos.add(lblNewLabel_6_2);
		
		// Terceiro separador Ver pacotes comerciais
		
		textArea_3 = new JTextArea();
		textArea_3.setBounds(281, 156, 132, 68);
		panelProdutos.add(textArea_3);
		tabbedPane.addTab("Ver todos os pacotes", null , panelPacotes);
		panelPacotes.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Pacotes comercias");
		lblNewLabel_9.setBounds(26, 24, 165, 14);
		panelPacotes.add(lblNewLabel_9);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBounds(26, 49, 92, 271);
		panelPacotes.add(textArea_4);
		
		JLabel lblNewLabel_8_1 = new JLabel("Nome");
		lblNewLabel_8_1.setBounds(255, 85, 46, 14);
		panelPacotes.add(lblNewLabel_8_1);
		
		JTextArea textArea_2_1 = new JTextArea();
		textArea_2_1.setBounds(311, 80, 132, 68);
		panelPacotes.add(textArea_2_1);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("Descricao");
		lblNewLabel_6_2_1.setBounds(255, 164, 46, 14);
		panelPacotes.add(lblNewLabel_6_2_1);
		
		
		// Terceiro separador Ver pacotes comerciais (Painel dados 4)
		
		JTextArea textArea_3_1 = new JTextArea();
		textArea_3_1.setBounds(311, 159, 132, 68);
		panelPacotes.add(textArea_3_1);
		tabbedPane.addTab("Ver todas as promocoes", null , panelPromocoes);
		panelPromocoes.setLayout(null);
		
		JLabel lblNewLabel_8_1_1 = new JLabel("Nome");
		lblNewLabel_8_1_1.setBounds(264, 72, 46, 14);
		panelPromocoes.add(lblNewLabel_8_1_1);
		
		JTextArea textArea_2_1_1 = new JTextArea();
		textArea_2_1_1.setBounds(320, 67, 132, 68);
		panelPromocoes.add(textArea_2_1_1);
		
		JLabel lblNewLabel_6_2_1_1 = new JLabel("Descricao");
		lblNewLabel_6_2_1_1.setBounds(264, 151, 46, 14);
		panelPromocoes.add(lblNewLabel_6_2_1_1);
		
		JTextArea textArea_3_1_1 = new JTextArea();
		textArea_3_1_1.setBounds(320, 146, 132, 68);
		panelPromocoes.add(textArea_3_1_1);
		
		JTextArea textArea_4_1 = new JTextArea();
		textArea_4_1.setBounds(35, 36, 92, 271);
		panelPromocoes.add(textArea_4_1);
		
		JLabel lblNewLabel_9_1 = new JLabel("Promocoes");
		lblNewLabel_9_1.setBounds(35, 11, 165, 14);
		panelPromocoes.add(lblNewLabel_9_1);
		
		lblNewLabel_2 = new JLabel("A sua Area");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(28, 11, 278, 57);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Terminar sessao");
		btnNewButton.setBounds(10, 450, 169, 23);
		contentPane.add(btnNewButton);
		
		
	}
}
