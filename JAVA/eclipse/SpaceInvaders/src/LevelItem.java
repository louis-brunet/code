import java.awt.Color;

public abstract class LevelItem {
	protected static final float DEFAULT_SIZE_MODIF = 6.0f;
	enum ItemType {PLAYER,ENEMY,PROJECTILE,BUNKER}
	
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

	/**
	 * Init xLeft, xRight, xCenter, and xpoints based on item width, canvas width, and xLeft arg
	 */
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
			ypoints[i] -= offset;
		}
	}
	
	// Init xpoints and ypoints based on template relative points, xLeft and yTop (1 unit = sizeModifier pixels)
	protected void initPositionArrays(int[][] relativePoints) {
		npoints = relativePoints.length;
		xpoints= new int[npoints];
		ypoints= new int[npoints];
		for(int i=npoints - 1, index = 0; i>=0; i--, index++) {
			xpoints[index] = (int) ( xLeft+sizeModifier * (float) relativePoints[i][0] );
			ypoints[index] = (int) ( yTop+sizeModifier * (float) relativePoints[i][1]);
		}
	}
	
	protected boolean isCollidingWith(LevelItem item) {
		return (yTop < item.yBottom
	 			   && yBottom > item.yTop
	 			   && xLeft< item.xRight
	 			   && xRight > item.xLeft);
	}
	
	/**
	 * Collision detection with the smallest rectangle containing given polygon
	 * @param xpoints
	 * @param ypoints
	 * @return 
	 */
	protected boolean isCollidingWith(int[] xpoints, int[] ypoints) {
		int xLeftPolygon = xpoints[0];
		int xRightPolygon = xpoints[1];
		int yTopPolygon = ypoints[0];
		int yBottomPolygon = ypoints[1];
		
		for(int i: xpoints) {
			if(xLeftPolygon > i) {
				xLeftPolygon = i;
			}
			if(xRightPolygon < i) {
				xRightPolygon = i;
			}
		}
		
		for(int i: ypoints) {
			if(yTopPolygon > i) {
				yTopPolygon = i;
			}
			if(yBottomPolygon < i) {
				yBottomPolygon = i;
			}
		}
		
		return (yTop < yBottomPolygon
	 			   && yBottom > yTopPolygon
	 			   && xLeft< xRightPolygon
	 			   && xRight > xLeftPolygon);
	}
	
	protected boolean isCompletelyOutOfBounds() {
		if (yTop > DrawCanvas.CANVAS_HEIGHT
				|| yBottom < 0
				|| xRight < 0
				|| xLeft > DrawCanvas.CANVAS_WIDTH) {
			System.out.println(this.toString()+" is out of bounds");
			return true;
		}
		return false;
	}
	
}
