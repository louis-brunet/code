import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CapturedPiecePanel extends JPanel {
	
	JLabel countLabel;
	String type;
	String team;
	int count;
	
	public CapturedPiecePanel(String team, String type) {
		this.type = type;
		this.team = team;
		
		initLabel(team, type);
		count = 0;
		
		setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
		add(countLabel);
	}
	
	public void addOne() {
		int currCount =  Integer.parseInt(countLabel.getText()) ;
		int newCount = currCount+1;
		setCount(newCount);
	}
	
	public void setCount( int count ) {
		countLabel.setText( "" + count);
		this.count = count;
	}
	
	public void initLabel(String team, String type){
		String iconPath = "src/resources/"+team+type+".png" ;
		ImageIcon ii = new ImageIcon(iconPath);
		countLabel = new JLabel("0", ii, JLabel.CENTER);
	}

}
