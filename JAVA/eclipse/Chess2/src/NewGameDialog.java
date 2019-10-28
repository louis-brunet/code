import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NewGameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			NewGameDialog dialog = new NewGameDialog("White");
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public NewGameDialog(String winner, Application a) {
		a.sidePanel.scorePanel.incrementScore(a.board.currentPlayer);
		
		setSize(350, 150);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			buttonPane.setBorder( new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton yesBtn = new JButton("Yes");
			yesBtn.setActionCommand("Yes");
			addBtnListener(yesBtn, this, a);
			buttonPane.add(yesBtn);
			getRootPane().setDefaultButton(yesBtn);
			
			
			JButton noBtn = new JButton("No");
			noBtn.setActionCommand("No");
			addBtnListener(noBtn, this, a);
			buttonPane.add(noBtn);

			
			JLabel textLbl = new JLabel(winner+" wins ! Play again ?", JLabel.CENTER);
			getContentPane().add(textLbl, BorderLayout.CENTER);
		}
	}
	
	private void addBtnListener(JButton btn, NewGameDialog dialog,  Application a) {
		btn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand()=="Yes") {
					String nextStartingPlayer = a.board.startingPlayer == Piece.white ? Piece.black : Piece.white;
					a.resetBoard();
					a.board.currentPlayer = nextStartingPlayer;
					a.board.startingPlayer = nextStartingPlayer;
					dialog.setVisible(false);
				}else if(e.getActionCommand()=="No") {
					dialog.setVisible(false);
				}
			}
		});
	}

}
