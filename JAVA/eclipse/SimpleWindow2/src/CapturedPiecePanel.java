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
		
		setBackground(Cell.lightCell);
		setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) );
		add(countLabel);
	}
	
	public void addOne() {
		//int currCount =  Integer.parseInt(countLabel.getText()) ;
		int newCount = count+1;
		setCount(newCount);
	}
	
	public void setCount( int count ) {
		if(count>1) {
			countLabel.setText( "" + count);
		}else {
			countLabel.setText("");
		}
		this.count = count;
	}
	
	public void initLabel(String team, String type){
		String iconPath = "src/resources/"+team+type+".png" ;
		ImageIcon ii = new ImageIcon(iconPath);
		countLabel = new JLabel(ii, JLabel.CENTER);
	}

}
