import java.awt.Color;

public abstract class LevelItem {
	protected static final float DEFAULT_SIZE_MODIF = 8f;
	enum ItemType {PLAYER,ENEMY,PROJECTILE}
	
	protected ItemType itemType;
	protected Color color;
	protected float sizeModifier;

	protected int xCenter, xLeft, xRight;
	protected int yCenter, yTop, yBottom;
	protected int width, height;
	
	protected int[] xpoints, ypoints;
	protected int npoints;
	
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getSizeModifier() {
		return sizeModifier;
	}

	public void setSizeModifier(float sizeModifier) {
		this.sizeModifier = sizeModifier;
	}

	protected void setXLeft(int xLeft) {
		if(xLeft<0) {
			setXLeft(0);
			return;
		} else if(xLeft > DrawCanvas.CANVAS_WIDTH - width){
			setXLeft(DrawCanvas.CANVAS_WIDTH- width);
			return;
		}
		
		int offset = this.xLeft - xLeft;
		
		this.xLeft = xLeft;
		this.xRight = this.xLeft + width;
		this.xCenter = this.xLeft + width/2;
		
		for(int i=0; i<npoints && offset!=0; i++) {
			xpoints[i] -= offset;
		}
	}
	
	protected void setYTop( int yTop) {
		if(yTop<0) {
			setYTop(0);
		} else if(yTop > DrawCanvas.CANVAS_HEIGHT - height){
			setYTop(DrawCanvas.CANVAS_HEIGHT - height);
		}
		
		int offset = this.yTop - yTop;
		
		this.yTop = yTop;
		this.yBottom= this.yTop + height;
		this.yCenter = this.yTop + height/2;
		

		for(int i=0; i<npoints && offset!=0; i++) {
			ypoints[i] += offset;
		}
	}
	
	// Init xpoints and ypoints based on template relative points (1 unit = sizeModifier pixels)
	protected void initPositionArrays(int[][] relativePoints) {
		npoints = relativePoints.length;
		xpoints= new int[npoints];
		ypoints= new int[npoints];
		for(int i=npoints - 1, index = 0; i>=0; i--, index++) {
			xpoints[index] = (int) ( xLeft+sizeModifier * (float) relativePoints[i][0] );
			ypoints[index] = (int) ( yTop+sizeModifier * (float) relativePoints[i][1]);
		}
	}
	
}