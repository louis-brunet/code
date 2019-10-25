import java.awt.Color;

public class PowerUp {

	private static float DEFAULT_WIDTH = 30.0f;
	private static float DEFAULT_HEIGHT= 30.0f;
	private static Color DEFAULT_COLOR = Color.GREEN;
	public enum pwrUpType{SMALL, INVINCIBLE, LIFE};
	
	pwrUpType type;
	float width;
	float height;
	float xMin;
	float yMin;
	float xMax;
	float yMax;
	float xCenter;
	float yCenter;
	boolean canTeleport;
	Color color;
	
	
	public PowerUp(pwrUpType t, Player p) {
		type = t;
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		color = DEFAULT_COLOR;
		
		do {
			setXMin(DrawCanvas.getRandomWidthInBounds());
			setYMin(DrawCanvas.getRandomHeightInBounds());
		}while( p.isCollidingWith(this) );
		
		if(t == pwrUpType.INVINCIBLE) {
			canTeleport = true;
		}else {
			canTeleport = false;
		}
	}
	
	private void setXMin( float xMin) {
		this.xMin = xMin;
		this.xMax = this.xMin + width;
		this.xCenter = this.xMin + width/2f;
	}
	
	private void setYMin( float yMin) {
		this.yMin = yMin;
		this.yMax = this.yMin + height;
		this.yCenter = this.yMin + height/2f;
	}
	
	
}
