package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;

public class AreaCliente_MeusDados extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_MeusDados frame = new AreaCliente_MeusDados();
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
	public AreaCliente_MeusDados() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMeusDados = new JPanel();
		panelMeusDados.setLayout(null);
		panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai", Font.PLAIN, 12));
		panelMeusDados.setBounds(0, 47, 1384, 544);
		contentPane.add(panelMeusDados);
		
		JLabel imagemDados = new JLabel("");
		imagemDados.setBackground(SystemColor.menu);
		imagemDados.setBounds(6, -41, 1394, 598);
		panelMeusDados.add(imagemDados);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNome.setBounds(76, 77, 69, 36);
		panelMeusDados.add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(170, 77, 246, 36);
		panelMeusDados.add(textField);
		
		JLabel lblNIF = new JLabel("NIF:");
		lblNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNIF.setBounds(76, 138, 69, 36);
		panelMeusDados.add(lblNIF);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(170, 138, 246, 36);
		panelMeusDados.add(textField_1);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblLogin.setBounds(76, 205, 69, 36);
		panelMeusDados.add(lblLogin);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(170, 205, 246, 36);
		panelMeusDados.add(textField_2);
		
		JLabel lblMorada = new JLabel("Morada");
		lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblMorada.setBounds(76, 268, 81, 36);
		panelMeusDados.add(lblMorada);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(170, 272, 246, 36);
		panelMeusDados.add(textField_3);
		
		JButton btAtualizarDados = new JButton("Atualizar dados");
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);
		btAtualizarDados.setBounds(200, 362, 180, 50);
		panelMeusDados.add(btAtualizarDados);
	}
}
