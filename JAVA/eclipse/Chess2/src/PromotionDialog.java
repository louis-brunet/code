import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PromotionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();


	/**
	 * Create the dialog.
	 */
	public PromotionDialog(Board b) {
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Cell.darkCell);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		contentPanel.add( new PromotionPanel(b, this));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
	}

}
