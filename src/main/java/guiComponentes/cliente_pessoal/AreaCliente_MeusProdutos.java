package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import guiComponentes.Admin_GUI_homepage;
import javax.swing.JTextArea;

public class AreaCliente_MeusProdutos extends JFrame {

	private JPanel panelMeusProdutos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_MeusProdutos frame = new AreaCliente_MeusProdutos();
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
	public AreaCliente_MeusProdutos() {	
		initialize();
		
	
	}
	
	private void initialize() {
		
		ativarNimbusLookAndFeel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);
		panelMeusProdutos = new JPanel();
		panelMeusProdutos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMeusProdutos);
		panelMeusProdutos.setLayout(null);
		
		JTextArea textAreaNomePromocoes = new JTextArea();
		textAreaNomePromocoes.setBounds(472, 156, 234, 41);
		panelMeusProdutos.add(textAreaNomePromocoes);

		// Criação da da janela Meus produtos
		
		JComboBox comboBoxPromocoes = new JComboBox();
		comboBoxPromocoes.setBounds(472, 349, 234, 41);
		panelMeusProdutos.add(comboBoxPromocoes);

		JLabel lblAsSuasPromoes = new JLabel("As suas Promoções");
		lblAsSuasPromoes.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		lblAsSuasPromoes.setBounds(472, 109, 219, 36); 
		panelMeusProdutos.add(lblAsSuasPromoes);

		JTextArea textAreaDescricaoPromocoes = new JTextArea();
		textAreaDescricaoPromocoes.setBounds(472, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPromocoes);

		JTextArea textAreaDescricaoPacote = new JTextArea();
		textAreaDescricaoPacote.setBounds(157, 218, 234, 92);
		panelMeusProdutos.add(textAreaDescricaoPacote);

		JTextArea textAreaNomePacote = new JTextArea();
		textAreaNomePacote.setBounds(157, 161, 234, 41);
		panelMeusProdutos.add(textAreaNomePacote);

		JLabel lblDescricaoPacote = new JLabel("Descrição:");
		lblDescricaoPacote.setBounds(55, 214, 95, 36);
		lblDescricaoPacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblDescricaoPacote);

		JLabel lblNomePacote = new JLabel("Nome:");
		lblNomePacote.setBounds(55, 157, 69, 36);
		lblNomePacote.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		panelMeusProdutos.add(lblNomePacote);

		JLabel lblSeuPacote = new JLabel("O seu Pacote Comercial:");
		lblSeuPacote.setBounds(55, 109, 219, 36);
		lblSeuPacote.setFont(new Font("Dubai Light", Font.PLAIN, 20));
		panelMeusProdutos.add(lblSeuPacote);
				
		//Imagem fundo

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(5, 5, 1358, 537);
		panelMeusProdutos.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240,240 ));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));
			
	}
	
	
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

	public JPanel returnAreaClienteMeusProdutos() {
		return  panelMeusProdutos;
	}
}// end class
