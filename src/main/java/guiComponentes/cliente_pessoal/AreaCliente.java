package guiComponentes.cliente_pessoal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import guiComponentes.Admin_GUI_homepage;
import guiComponentes.GUI_total;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

public class AreaCliente extends JFrame {


	private static final long serialVersionUID = 1L;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);	
	private JButton btAtualizarDados, btAtualizaDados;
	private JPanel panel;
	private JLabel lblUsernameLogged,lblTempoSessao,lblHoraSistema;
	private JPanel pane;
	private JPanel panelMeusDados;
	private JPanel panelDados2;
	private JPanel panelDados3;
	private JPanel panelDados4;
	private JTextField textFieldNome;
	private JTextField textFieldNIF;
	private JTextField textFieldLogin;
	private JTextField textFieldMorada;
	private JPanel panelProdutos; 

	private JTextArea textArea_2;
	private JLabel lblNewLabel_8;

	private JLabel lblNewLabel_6_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//GUI_total guit = new GUI_total();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente frame = new AreaCliente(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 * @param guit 
	 */
	public AreaCliente(GUI_total guit) {
		initialize(guit);
		ativarNimbusLookAndFeel();

	}

	private void initialize(GUI_total guit) {

		setupTempoSessao();
		ativarNimbusLookAndFeel(); 


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Dubai", Font.PLAIN, 12));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);

		// Label portal cliente

		JLabel lblPortalCliente = new JLabel("Portal Do Cliente");
		lblPortalCliente.setBounds(51, 16, 508, 33);
		lblPortalCliente.setForeground(new Color(70,74,101));
		lblPortalCliente.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);

		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(new Color(70,74,101));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(51, 49, 104, 33);
		panel.add(lblBemVindo);


		// Construção JTabbedPane

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Dubai Light", Font.PLAIN, 16));
		tabbedPane.setBounds(0, 89, 1384, 586);
		panel.add(tabbedPane);

		// Separador os meus dados

		panelMeusDados = new JPanel();
		tabbedPane.addTab("Meus Dados",null,  panelMeusDados);
		panelMeusDados.setLayout(null);
		panelMeusDados.setForeground(Color.BLUE);
		panelMeusDados.setFont(new Font("Dubai", Font.PLAIN, 12 ));


		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNome.setBounds(51, 77, 69, 36);
		panelMeusDados.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(145, 77, 246, 29);
		panelMeusDados.add(textFieldNome);
		textFieldNome.setColumns(10);

		JLabel lblNIF = new JLabel("NIF:");
		lblNIF.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblNIF.setBounds(51, 138, 69, 36);
		panelMeusDados.add(lblNIF);

		textFieldNIF = new JTextField();
		textFieldNIF.setColumns(10);
		textFieldNIF.setBounds(145, 138, 246, 29);
		panelMeusDados.add(textFieldNIF);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblLogin.setBounds(51, 205, 69, 36);
		panelMeusDados.add(lblLogin);

		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);
		textFieldLogin.setBounds(145, 205, 246, 29);
		panelMeusDados.add(textFieldLogin);

		JLabel lblMorada = new JLabel("Morada");
		lblMorada.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		lblMorada.setBounds(51, 268, 81, 36);
		panelMeusDados.add(lblMorada);

		textFieldMorada = new JTextField();
		textFieldMorada.setColumns(10);
		textFieldMorada.setBounds(145, 272, 246, 29);
		panelMeusDados.add(textFieldMorada);

		btAtualizarDados = new JButton("Atualizar dados");
		btAtualizarDados.setBounds(175, 362, 180, 50);
		panelMeusDados.add(btAtualizarDados);
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);

		//Imagem de fundo - Provisoria

		JLabel imagemDados = new JLabel("");
		imagemDados.setBounds(6, -41, 1394, 598);
		panelMeusDados.add(imagemDados);
		imagemDados.setBackground(new Color(240, 240, 240));
		imagemDados.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));


		/****************************/
		// OS meus produtos
		/****************************/

		JPanel panelMeusProdutos = new JPanel();
		tabbedPane.addTab("Os Meus produtos",null,  panelMeusProdutos);
		panelMeusProdutos.setLayout(null);

		JComboBox comboBoxPromocoes = new JComboBox();
		comboBoxPromocoes.setBounds(472, 157, 234, 41);
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

		JTextArea textArea = new JTextArea();
		textArea.setBounds(157, 161, 234, 41);
		panelMeusProdutos.add(textArea);

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

		//Imagem de fundo - Provisoria
		JLabel imagemProdutos = new JLabel("");
		imagemProdutos.setBounds(6, -41, 1394, 598);
		panelMeusProdutos.add(imagemProdutos);
		imagemProdutos.setBackground(new Color(240, 240, 240));
		imagemProdutos.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));



		/****************************/
		// Separador promoções
		/****************************/


		JPanel panelPromocoes = new JPanel();
		tabbedPane.addTab("As suas Promoções",null,  panelPromocoes);
		panelPromocoes.setLayout(null);

		//Imagem de fundo - Provisoria
		JLabel imagemPromocoes = new JLabel("");
		imagemPromocoes.setBounds(6, -41, 1394, 598);
		panelPromocoes.add(imagemPromocoes);
		imagemPromocoes.setBackground(new Color(240, 240, 240));
		imagemPromocoes.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));



		/****************************/
		// Separador Pacotes
		/****************************/


		JPanel panelVerPacotes = new JPanel();
		tabbedPane.addTab("Ver todos os pacotes comercial",null,  panelVerPacotes);
		panelVerPacotes.setLayout(null);

		//Imagem de fundo - Provisoria
		JLabel imagemVerPacotes = new JLabel("");
		imagemVerPacotes.setBounds(6, -41, 1394, 598);
		panelVerPacotes.add(imagemVerPacotes);
		imagemVerPacotes.setBackground(new Color(240, 240, 240));
		imagemVerPacotes.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));



		/****************************/
		// Separador Promoções
		/****************************/


		JPanel panelVerPromocoes = new JPanel();
		tabbedPane.addTab("Ver todas as promoções",null,  panelVerPromocoes);
		panelVerPromocoes.setLayout(null);

		//Imagem de fundo - Provisoria
		JLabel imagemVerPromocoes = new JLabel("");
		imagemVerPromocoes.setBounds(6, -41, 1394, 598);
		panelVerPromocoes.add(imagemVerPromocoes);
		imagemVerPromocoes.setBackground(new Color(240, 240, 240));
		imagemVerPromocoes.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/fundoAltran.png")));




		// Botão Atualiza dados 

		//		btAtualizaDados = new JButton("Atualizar Dados");
		//		btAtualizaDados.setBounds(468, 27, 268, 50);
		//		btAtualizaDados.setForeground(Color.DARK_GRAY);
		//		btAtualizaDados.setToolTipText("Atualize os seus dados aqui.");	
		//		btAtualizaDados.setFocusPainted(false);
		//		btAtualizaDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		//		getContentPane().add(btAtualizaDados);




		/* RUDAPÉ */
		//Botão voltar

		btAtualizarDados = new JButton("Terminar Sessão");
		btAtualizarDados.setForeground(Color.DARK_GRAY);
		btAtualizarDados.setBounds(16, 687, 180, 50);
		btAtualizarDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btAtualizarDados.setFocusPainted(false);
		getContentPane().add(btAtualizarDados);


		// Logotipo no rodapé

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);



		//		JLabel icon = new JLabel("");
		//		icon.setBounds(0, 89, 1394, 586);
		//		icon.setBackground(new Color(240, 240, 240));
		//		icon.setIcon(new ImageIcon(AreaCliente.class.getResource("/guiComponentes/img/AltranClientes.png")));
		//		getContentPane().add(icon);


		//TODO criar os action listener Atualizar dados/ Ver Pacotes / Ver Promoções


		btAtualizarDados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});


	}//end initialize method



	// TODO  Coloca a lable de boas vindas

	protected void setupTempoSessao() {
		lblUsernameLogged = new JLabel();
		lblUsernameLogged.setForeground(Color.BLACK);
		lblUsernameLogged.setText("Username:");
		lblUsernameLogged.setBounds(1215, 699, 166, 16);
		panel.add(lblUsernameLogged);
		lblUsernameLogged.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblTempoSessao = new JLabel();
		lblTempoSessao.setForeground(Color.BLACK);
		lblTempoSessao.setText("Sessao:");
		lblTempoSessao.setBounds(1215, 717, 159, 18);
		panel.add(lblTempoSessao);
		lblTempoSessao.setFont(new Font("Dubai Light", Font.PLAIN, 10));

		lblHoraSistema = new JLabel();
		lblHoraSistema.setForeground(Color.BLACK);
		lblHoraSistema.setBounds(1215, 737, 159, 18);
		panel.add(lblHoraSistema);
		lblHoraSistema.setText("Data:");
		lblHoraSistema.setFont(new Font("Dubai Light", Font.PLAIN, 10));


	}


	public void setLableBoasVindas(String name) {
		//Passos a fazer: guitOTAL - SETAR A LBL DE BOAS VINDAS no método labelUsernameNavegaPaginas
		//Funcionario DAO copiar o método pesquisar funcionarioAdmin - Copiar e modificar para pesquisa funcionario por login ()
		//Acrescentar o novo método gestor dao
		//GUItotal altear pesquisafuncionario
		// funcionario f = gestorDeDaoget...pesquisaFuncionario  Admin

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
}
