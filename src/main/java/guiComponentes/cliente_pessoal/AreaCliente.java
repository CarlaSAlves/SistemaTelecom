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

public class AreaCliente extends JFrame {


	private static final long serialVersionUID = 1L;
	private Font font = new Font("Dubai Light", Font.PLAIN, 15);	
	private JButton btVoltar, btAtualizaDados, btVerPacotes;
	private JPanel panel;
	private JLabel lblUsernameLogged,lblTempoSessao,lblHoraSistema;
	private JPanel pane;
	private JTextField textField;
	private JTextField textField_1;

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
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		getContentPane().setFont(new Font("Verdana", Font.PLAIN, 11));
		getContentPane().setBackground(SystemColor.text);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1400, 800);
		getContentPane().setLayout(null);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setForeground(Color.WHITE);
		textArea_1.setBounds(78, 249, 260, 136);
		panel.add(textArea_1);
		
		
		// Label portal cliente

		JLabel lblPortalCliente = new JLabel("Portal Do Cliente");
		lblPortalCliente.setBounds(109, 45, 508, 33);
		lblPortalCliente.setForeground(new Color(70,74,101));
		lblPortalCliente.setFont(new Font("Dubai", Font.BOLD, 40));
		getContentPane().add(lblPortalCliente);
		
		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(new Color(70,74,101));
		lblBemVindo.setFont(new Font("Dubai Light", Font.BOLD, 17));
		lblBemVindo.setBounds(94, 89, 208, 60);
		panel.add(lblBemVindo);
		
		
	
		// Botão Atualiza dados 

		btAtualizaDados = new JButton("Atualizar Dados");
		btAtualizaDados.setBounds(469, 43, 268, 50);
		btAtualizaDados.setForeground(Color.DARK_GRAY);
		btAtualizaDados.setToolTipText("Atualize os seus dados aqui.");	
		btAtualizaDados.setFocusPainted(false);
		btAtualizaDados.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		getContentPane().add(btAtualizaDados);

		// Botão ver todos os pacotes

		btVerPacotes = new JButton("Ver Todos os Pacotes");
		btVerPacotes.setBounds(78, 412, 260, 50);
		btVerPacotes.setToolTipText("Ver todos os Pacotes disponíveis.");
		btVerPacotes.setForeground(Color.DARK_GRAY);
		btVerPacotes.setFocusPainted(false);
		btVerPacotes.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		getContentPane().add(btVerPacotes);

		//Botão voltar
		
		btVoltar = new JButton("Terminar Sessão");
		btVoltar.setForeground(Color.DARK_GRAY);
		btVoltar.setBounds(16, 687, 180, 50);
		btVoltar.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btVoltar.setFocusPainted(false);
		getContentPane().add(btVoltar);
		
		
		// Logotipo no rodapé

		JLabel lblFooter = new JLabel("");
		lblFooter.setForeground(new Color(0, 0, 0));
		lblFooter.setBounds(599, 690, 214, 65);
		lblFooter.setIcon(new ImageIcon(Admin_GUI_homepage.class.getResource("/guiComponentes/img/Altran1.1.png")));
		getContentPane().add(lblFooter);
		
		JLabel lblPacote = new JLabel("Seu pacote:");
		lblPacote.setFont(new Font("Dubai", Font.PLAIN, 20));
		lblPacote.setBounds(78, 160, 133, 44);
		panel.add(lblPacote);
		
		JLabel lblAsSuasPromoes = new JLabel("As suas promoções:");
		lblAsSuasPromoes.setFont(new Font("Dubai", Font.PLAIN, 20));
		lblAsSuasPromoes.setBounds(477, 160, 202, 44);
		panel.add(lblAsSuasPromoes);
		
		textField = new JTextField();
		textField.setBounds(78, 198, 260, 33);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnVerTodasAs = new JButton("Ver Todas as Promoções");
		btnVerTodasAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVerTodasAs.setToolTipText("Ver todos os Pacotes disponíveis.");
		btnVerTodasAs.setForeground(Color.DARK_GRAY);
		btnVerTodasAs.setFont(new Font("Dubai Light", Font.PLAIN, 17));
		btnVerTodasAs.setFocusPainted(false);
		btnVerTodasAs.setBounds(477, 412, 260, 50);
		panel.add(btnVerTodasAs);
		
		JTextArea textArea_1_1 = new JTextArea();
		textArea_1_1.setForeground(Color.WHITE);
		textArea_1_1.setBackground(SystemColor.menu);
		textArea_1_1.setBounds(477, 249, 260, 136);
		panel.add(textArea_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(477, 198, 260, 33);
		panel.add(textField_1);
		
		JLabel icon = new JLabel("");
		icon.setBounds(0, 89, 1394, 586);
		icon.setBackground(new Color(240, 240, 240));
		icon.setIcon(new ImageIcon(AreaCliente.class.getResource("/guiComponentes/img/AltranClientes.png")));
		getContentPane().add(icon);
		

		//TODO criar os action listener Atualizar dados/ Ver Pacotes / Ver Promoções
		
		btAtualizaDados.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Criar uma dialog para aparecerem os dados do cliente ver exemplo no gertor cliente GUI
				
			}
		});
		
		btVerPacotes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btVoltar.addActionListener(new ActionListener() {
			
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
