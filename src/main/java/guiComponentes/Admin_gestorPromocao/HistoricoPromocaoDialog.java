package guiComponentes.Admin_gestorPromocao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import historicos.HistoricoOperador;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import standard_value_object.Funcionario;

public class HistoricoPromocaoDialog extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JLabel operadorRegistoLabel;

	
	public static void main(String[] args) {
		try {
			HistoricoPromocaoDialog dialog = new HistoricoPromocaoDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preencherTable(Funcionario funcionario, List<HistoricoOperador> historicoFuncionario) {
		operadorRegistoLabel.setText(funcionario.getNome());
		
		HistoricoPromocaoTableModel model = new HistoricoPromocaoTableModel(historicoFuncionario); 
		
		table.setModel(model);
		
		TableCellRenderer tableCellRenderer = new DateTimeCellRenderer();
		table.getColumnModel().getColumn(HistoricoPromocaoTableModel.DATA_COL).setCellRenderer(tableCellRenderer);		
		
	}
	
	public HistoricoPromocaoDialog() {
		setBounds(100, 100, 450, 300);
		setTitle("Historico de Registos");
		setModal(true);
		setBounds(100, 100, 651, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			{
				JLabel lblHistoricoPara = new JLabel("Historico do Operador :");
				panel.add(lblHistoricoPara);
			}
			{
				operadorRegistoLabel = new JLabel("");
				panel.add(operadorRegistoLabel);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Sair");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}		
	}
	
	private final class DateTimeCellRenderer extends DefaultTableCellRenderer {
		
		private static final long serialVersionUID = 5466469925138317415L;
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		public Component getTableCellRendererComponent(JTable table,
		        Object value, boolean isSelected, boolean hasFocus,
		        int row, int column) {
		    if( value instanceof Date) {
		        value = f.format(value);
		    }
		    return super.getTableCellRendererComponent(table, value, isSelected,
		            hasFocus, row, column);
		}
	}
	
}
