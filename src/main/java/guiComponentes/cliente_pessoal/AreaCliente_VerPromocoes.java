package guiComponentes.cliente_pessoal;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import servico.GestorDeDAO;
import standard_value_object.Promocao;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.JScrollBar;

@SuppressWarnings("serial")
public class AreaCliente_VerPromocoes extends JFrame {

	//private JPanel panel;
	private JPanel panelVerTodasPromo;
	private JTextField textFieldNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AreaCliente_VerPromocoes frame = new AreaCliente_VerPromocoes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AreaCliente_VerPromocoes() {

		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void initialize() throws Exception {

		/**
		 * 
		 */
		ativarNimbusLookAndFeel();

		/**
		 * Define as caracteristicas dos painel base. 
		 */
		panelVerTodasPromo = new JPanel();
		panelVerTodasPromo.setLayout(null);
		setContentPane(panelVerTodasPromo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);

		// Labels e textFieldNome da página 

		JLabel labelVerPromo = new JLabel("Ver todas as Promoções");
		labelVerPromo.setForeground(Color.WHITE);
		labelVerPromo.setFont(new Font("Dubai Light", Font.BOLD, 22));
		labelVerPromo.setBounds(66, 54, 318, 28);
		panelVerTodasPromo.add(labelVerPromo);

		JLabel labelPacoteNome = new JLabel("Nome");
		labelPacoteNome.setForeground(Color.WHITE);
		labelPacoteNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPacoteNome.setBounds(315, 169, 71, 31);
		panelVerTodasPromo.add(labelPacoteNome);

		JLabel labelPromoDescricao = new JLabel("Descrição");
		labelPromoDescricao.setForeground(Color.WHITE);
		labelPromoDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPromoDescricao.setBounds(315, 223, 87, 23);
		panelVerTodasPromo.add(labelPromoDescricao);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(400, 169, 300, 31);
		panelVerTodasPromo.add(textFieldNome);
		textFieldNome.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textArea.setBounds(400, 222, 300, 114);
		panelVerTodasPromo.add(textArea);

		// Jlist e ScrollBar
		
		List<Promocao> promocoes = GestorDeDAO.getGestorDeDAO().getAllPromocoes();
		
		DefaultListModel model = new DefaultListModel();	
		model.addAll(promocoes);
		JList listVerPromo = new JList(model);
			
		ListCellRenderer renderer = new RendererPromocao();
		listVerPromo.setCellRenderer(renderer);
		listVerPromo.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		listVerPromo.setBounds(66, 120, 226, 362);
		listVerPromo.setLayoutOrientation( JList.HORIZONTAL_WRAP );
		listVerPromo.setVisibleRowCount( -1 ); // -1 sig q ele é variavel
		listVerPromo.setFixedCellHeight( 24 );
		listVerPromo.setFixedCellWidth( 226 );
	
		
		listVerPromo.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Promocao promocao = (Promocao) listVerPromo.getSelectedValue();
				textFieldNome.setText(promocao.getNome());
				textArea.setText(promocao.getDescricao());	
				
			}
		});
		

		panelVerTodasPromo.add(listVerPromo);


		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(66, 120, 226, 362);
		panelVerTodasPromo.add(scrollBar);


		/*
		 * Define a imagem de fundo através de uma label
		 */
		JLabel labelIconFundo = new JLabel("");
		labelIconFundo.setIcon(new ImageIcon(AreaCliente_VerPromocoes.class.getResource("/guiComponentes/img/AltranClientes.png")));
		labelIconFundo.setBounds(0, -37, 1408, 586);
		panelVerTodasPromo.add(labelIconFundo);


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
	
private class RendererPromocao implements ListCellRenderer<Promocao> {
		
		private JLabel texto;
		
		public RendererPromocao() {
			texto = new JLabel();
			texto.setFont(new Font("Dubai Light", Font.PLAIN, 15));
			texto.setOpaque( true );
			texto.setForeground(Color.black);
	
		}
		
		public Component getListCellRendererComponent(JList<? extends Promocao> list, Promocao value, int index,
				boolean isSelected, boolean cellHasFocus) {
			texto.setText( value.getNome());
			texto.setBackground(isSelected ? new Color(253, 132, 67) : Color.white );
			texto.setForeground(isSelected ? Color.white : Color.black);
			
			
			return texto;
		}
}
	
	public JPanel returnAreaClienteVerPromo() {
		return (JPanel) getContentPane();
	}
}// end class
