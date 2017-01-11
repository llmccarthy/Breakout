package breakout;

import java.awt.*;
import java.util.Stack;

public class Ball{
	public final static Integer NO_COLLISION = 0;
	public final static Integer TOP = 1;
	public final static Integer TR_CORNER = 2;
	public final static Integer RIGHT = 3;
	public final static Integer BR_CORNER = 4;
	public final static Integer BOTTOM = 5;
	public final static Integer BL_CORNER = 6;
	public final static Integer LEFT = 7;
	public final static Integer TL_CORNER = 8;
	
	private static int diameter = Display.getBrickGap() * 2;
	//Coordinates for the center of the ball
	private static int cenX  = (Display.getPanelWidth()/2);
	private static int cenY = (int) ((double) Display.getPanelHeight() / 1.5);
	//X,Y position of ball to be used for drawing the ball
	private static int x = cenX - (diameter/2);
	private static int y = cenY - (diameter/2);
	private static Color color = Color.white;
	//private static double dir = Math.PI;
	private static int dx = 0;
	private static int dy = 3;
	private static int maxDX = 10;
	private static Stack<Integer> collisions = new Stack<Integer>();
	
	public static int getDiameter(){
		return diameter;
	}
	
	public static int getCenX(){
		return cenX;
	}
	
	public static int getCenY(){
		return cenY;
	}
	
	public static int getX(){
		return x;
	}
	
	public static int getY(){
		return y;
	}
	
	public static Color getColor(){
		return color;
	}
	
	public static int getDX(){
		return dx;
	}
	
	public static int getDY(){
		return dy;
	}
	
	public static int getMaxDX(){
		return maxDX;
	}
	
	public static void setX(int x){
		Ball.x = x;
		cenX = x + (diameter/2);
	}
	
	public static void setY(int y){
		Ball.y = y;
		cenY = y + (diameter/2);
	}
	
	public static void setDX(int dx){
		Ball.dx = dx;
	}
	
	public static void setDY(int dy){
		Ball.dy = dy;
	}
	
	public static void move(){
		if((0 < x) || (Display.getPanelWidth() - diameter) > x){
			x += dx;
			if(x < 0){
				x = 0;
				dx = (-1)*dx;
				//collisions.add((Integer) RIGHT);
			}
			else if(x > (Display.getPanelWidth() - diameter)){
				x = Display.getPanelWidth() - diameter;
				dx = (-1)*dx;
				//collisions.add((Integer) LEFT);
			}
		}
		if((0 < y) || (Display.getPanelHeight() - diameter) > y){
			y += dy;
			if(y > (Display.getPanelHeight() - (2*diameter))){
//				y = Display.getPanelHeight() - (2*diameter);
//				collisions.add((Integer) TOP);
				Display.lose();
			}
			if(y < 0){
				y = 0;
				collisions.add((Integer) BOTTOM);
			}
		}
		int side;
		Brick[][] bricks = Display.getBricks();
		Detection[] detectPoints = Display.getDetectPoints();
		for(int i = 0; i < detectPoints.length; i++){
			for(int n = 0; n < Display.getBricksY(); n++){
				for(int m = 0; m < Display.getBricksX(); m++){
					side = brickCollision(detectPoints[i].getX(), detectPoints[i].getY(), bricks[n][m]);
					if(side != NO_COLLISION){
						collisions.add((Integer) side);	
					}
				}
			}
		}
		
		cenX = x + (diameter/2);
		cenY = y + (diameter/2);
	
		if(!collisions.isEmpty()){
			deflect(collisions);
		}
	}
	
	public static void deflect(Stack<Integer> collisions){
		int sumDir = 0;
		int colNum = collisions.size();
		while(!collisions.empty()){
			sumDir += collisions.pop();
		}
		int side = sumDir/colNum;
		if((side == TOP) || (side == BOTTOM)){
			dy = (-1)*dy;
		}
		else if(side == RIGHT){
			dx = (-1)*dx;
			//System.out.println("π * " + (dir/Math.PI)+ " " + side);
		}
		else if(side == LEFT){
			dx = (-1)*dx;
			//System.out.println("π * " + (dir/Math.PI)+ " " + side);
		}
		else if((side == TR_CORNER) || (side == BL_CORNER)){
			dx = (-1)*dx;
			dy = (-1)*dy;
		}
		else if(side == BR_CORNER){
			dx = (-1)*dx;
			dy = (-1)*dy;
		}
		else if(side == TL_CORNER){
			dx = (-1)*dx;
			dy = (-1)*dy;
		}
	}
	
	//Helper method for the ball colliding with bricks
	private static int brickCollision(int detX, int detY, Brick brick){
		int brickX = brick.getBrickPosX();
		int brickY = brick.getBrickPosY();
		//x and y are the position of detector relative to the inside of the brick
		int x = detX - brickX;
		int y = detY - brickY;
		int w = brick.getBrickWidth();
		int h = brick.getBrickHeight();
		boolean b = brick.getBroken();
		
		int ret = NO_COLLISION;
		
		if((x < 0) || (x > w) || (y < 0) || (y > h) || (b == true)){
			ret = NO_COLLISION;
		}
		else if((y >= 0) && (y <= (h/2)) && (y < x) && (y < ((-1)*x) + w)){
			ret = TOP;
		}
		else if((y > (h/2)) && (y <= h) && (y > ((-1)*x) + h) && (y > x - (w-h))){
			ret = BOTTOM;
		}
		else if((x >= 0) && (y > x) && (y < ((-1)*x) + h)){
			ret = LEFT;
		}
		else if((x <= w) && (y > ((-1)*x) + w) && (y < x - (w-h))){
			ret = RIGHT;
		}
		else if((y <= (h/2)) && (y == x)){
			ret = TL_CORNER;
		}
		else if((y <= (h/2)) && (y == ((-1)*x) + w)){
			ret = TR_CORNER;
		}
		else if((y > (h/2)) && (y == ((-1)*x) + h)){
			ret = BL_CORNER;
		}
		else if((y > (h/2)) && (y == x - (w-h))){
			ret = BR_CORNER;
		}
		
		if(ret != NO_COLLISION){
			Display.setBricksBroken(Display.getBricksBroken() + 1);
			brick.setBroken(true);
		}
		return (int) ret;
	}
}