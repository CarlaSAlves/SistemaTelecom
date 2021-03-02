package guiComponentes.cliente_pessoal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
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
import standard_value_object.PacoteComercial;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class AreaCliente_VerPacotes extends JFrame {

	private JPanel panelVerTodosPacotes;
	private JTextField textFieldNome;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AreaCliente_VerPacotes() {

		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void initialize() throws Exception {

		ativarNimbusLookAndFeel();

		/**
		 * Defines the characteristics of the base panel. 
		 */
		
		panelVerTodosPacotes = new JPanel();
		panelVerTodosPacotes.setLayout(null);
		setContentPane(panelVerTodosPacotes);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1384, 586);

		setResizable(false);


		// Labels e textFieldNome da página 

		JLabel labelVerPacotes = new JLabel("Ver todos os Pacotes Comerciais");
		labelVerPacotes.setForeground(Color.WHITE);
		labelVerPacotes.setFont(new Font("Dubai Light", Font.BOLD, 22));
		labelVerPacotes.setBounds(66, 54, 600, 28);
		panelVerTodosPacotes.add(labelVerPacotes);

		JLabel labelPacoteNome = new JLabel("Nome");
		labelPacoteNome.setForeground(Color.WHITE);
		labelPacoteNome.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPacoteNome.setBounds(315, 169, 62, 31);
		panelVerTodosPacotes.add(labelPacoteNome);

		JLabel labelPacoteDescricao = new JLabel("Descrição");
		labelPacoteDescricao.setForeground(Color.WHITE);
		labelPacoteDescricao.setFont(new Font("Dubai Light", Font.PLAIN, 18));
		labelPacoteDescricao.setBounds(315, 223, 87, 23);
		panelVerTodosPacotes.add(labelPacoteDescricao);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(400, 169, 300, 31);
		textFieldNome.setColumns(10);
		panelVerTodosPacotes.add(textFieldNome);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Dubai Light", Font.PLAIN, 13));
		textArea.setBounds(400, 222, 300, 114);
		panelVerTodosPacotes.add(textArea);

		// Jlist e ScrollBar

		List<PacoteComercial> pacotes = new ArrayList<PacoteComercial>();
		for (PacoteComercial p : GestorDeDAO.getGestorDeDAO().getAllPacotesComerciais()) {
			if (p.isAtivo()) {
				pacotes.add(p);
			}
		}

		@SuppressWarnings("rawtypes")
		DefaultListModel model = new DefaultListModel();	
		model.addAll(pacotes);
		JList listVerPacote = new JList(model);

		ListCellRenderer renderer = new RendererPacote();
		listVerPacote.setCellRenderer(renderer);
		listVerPacote.setFont(new Font("Dubai Light", Font.PLAIN, 14));
		listVerPacote.setBounds(66, 120, 226, 362);
		listVerPacote.setLayoutOrientation( JList.HORIZONTAL_WRAP );
		listVerPacote.setVisibleRowCount( -1 ); // -1 sig q ele é variavel
		listVerPacote.setFixedCellHeight( 24 );
		listVerPacote.setFixedCellWidth( 226 );
		listVerPacote.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(192, 192, 192), Color.LIGHT_GRAY, null, null));
		
		listVerPacote.setSelectedIndex(0);
		PacoteComercial pacote = (PacoteComercial) listVerPacote.getSelectedValue();
		textFieldNome.setText(pacote.getNome());
		textArea.setText(pacote.getDescricao());

		listVerPacote.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				PacoteComercial pacote = (PacoteComercial) listVerPacote.getSelectedValue();
				textFieldNome.setText(pacote.getNome());
				textArea.setText(pacote.getDescricao());	

			}
		});

		panelVerTodosPacotes.add(listVerPacote);

		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(66, 120, 226, 362);
		panelVerTodosPacotes.add(scrollBar);

		/*
		 * Sets the background image via a label
		 */
		JLabel labelIconFundo = new JLabel("");
		labelIconFundo.setIcon(new ImageIcon(AreaCliente_VerPacotes.class.getResource("/guiComponentes/img/AltranClientes.png")));
		labelIconFundo.setBounds(0, -37, 1408, 586);
		getContentPane().add(labelIconFundo);
	}

	/**
	 * Activates the Nimbus Look and Feel
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

	/**
	 * Creates the JList renderer
	 */
	private class RendererPacote implements ListCellRenderer<PacoteComercial> {

		private JLabel texto;

		public RendererPacote() {
			texto = new JLabel();
			texto.setFont(new Font("Dubai Light", Font.PLAIN, 14));
			texto.setOpaque( true );
			texto.setForeground(Color.black);
		}

		public Component getListCellRendererComponent(JList<? extends PacoteComercial> list, PacoteComercial value, int index,
				boolean isSelected, boolean cellHasFocus) {
			texto.setText( value.getNome());
			texto.setBackground(isSelected ? new Color(253, 132, 67) : Color.white );
			texto.setForeground(isSelected ? Color.white : Color.black);
			return texto;
		}
	}

	/**
	 * 
	 * @return painel 
	 */
	public JPanel returnAreaClienteVerPacotes() {
		return (JPanel) getContentPane();
	}
	
}// end class
