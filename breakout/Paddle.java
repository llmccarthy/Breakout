package breakout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.JTextField;

public class Paddle{
	//Initialization
	private final static Integer NO_COLLISION = 0;
	private final static Integer L_SIDE = 1;
	private final static Integer LEFT = 2;
	private final static Integer CEN_LEFT = 3;
	private final static Integer CENTER = 4;
	private final static Integer CEN_RIGHT = 5;
	private final static Integer RIGHT = 6;
	private final static Integer R_SIDE = 7;
	private final static Integer BOTTOM = 8;
	
	private static int width = Display.getBrickWidth() * 2;
	private static int height = Display.getBrickHeight();
	private static int x = (Display.getPanelWidth()/2) - (width/2);
	private static int y = Display.getPanelHeight() - ((Display.getBrickGap() * 4) + Display.getBrickHeight());
	private static int dx = 0;
	private static Color color = Color.white;
	
	private static int leftDivide;
	private static int cenLeftDivide;
	private static int cenRightDivide;
	private static int rightDivide;

	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
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
	
	public static void setupDivides(){
		leftDivide = ((int) (.2 * ((double) width)));
		cenLeftDivide = ((int) (.4 * ((double) width)));
		cenRightDivide = ((int) (.6 * ((double) width)));
		rightDivide = ((int) (.8 * ((double) width)));
	}

	public static void move(){
		setupDivides();
		int sum = 0;
		int num = 0;
		int side;
		Detection[] detectPoints = Display.getDetectPoints();
		for(int i = 0; i < detectPoints.length; i++){
			side = deflect(detectPoints[i].getX(), detectPoints[i].getY());
			if(side != NO_COLLISION){
				sum += side;
				num++;
			}
		}
		if(num != 0){
			side = sum/num;
		}
		else{
			side = NO_COLLISION;
		}
		if(side == L_SIDE){
			if(x == Ball.getDiameter()){
				Ball.setY(y - Ball.getDiameter());
				Ball.setDX(0);
				Ball.setDY(-3);
			}
			else{
				Ball.setDX((-1)*Ball.getDX());
				Ball.setDY((-1)*Ball.getDY());
				Ball.setX(x - (Ball.getDiameter()));
				Ball.setY(Ball.getY() - 3);
			}
		}
		else if(side == R_SIDE){
			if(x == (Display.getPanelWidth() - Ball.getDiameter()) - width){
				Ball.setY(y + Ball.getDiameter() + width);
				Ball.setDX(0);
				Ball.setDY(-3);
			}
			else{
				Ball.setDX((-1)*Ball.getDX());
				Ball.setDY((-1)*Ball.getDY());
				Ball.setX(x + width);
				Ball.setY(Ball.getY() - 3);
			}
		}
		else if(side == LEFT){
			Ball.setDX(Ball.getDX() - 2);
			Ball.setDY((-1)*Ball.getDY());
			Ball.setY(y - Ball.getDiameter());
		}
		else if(side == CEN_LEFT){
			Ball.setDX(Ball.getDX() - 1);
			Ball.setDY((-1)*Ball.getDY());
			Ball.setY(y - Ball.getDiameter());
		}
		else if(side == CENTER){
			Ball.setDY((-1)*Ball.getDY());
			Ball.setY(y - Ball.getDiameter());
		}
		else if(side == CEN_RIGHT){
			Ball.setDX(Ball.getDX() + 1);
			Ball.setDY((-1)*Ball.getDY());
			Ball.setY(y - Ball.getDiameter());
		}
		else if(side == RIGHT){
			Ball.setDX(Ball.getDX() + 2);
			Ball.setDY((-1)*Ball.getDY());
			Ball.setY(y - Ball.getDiameter());
		}
		else if(side == BOTTOM){
			Ball.setDX(0);
			Ball.setDY(0);
		}
		if((0 < x) || (Display.getPanelWidth() - width) > x){
			x += dx;
			if(x < 0){
				x = 0;
			}
			else if(x > (Display.getPanelWidth() - width)){
				x = Display.getPanelWidth() - width;
			}
		}
	}
	
	public static int deflect(int detX, int detY){
		int side = NO_COLLISION;
		//x and y are the position of detector relative to the inside of the paddle
		int x = detX - Paddle.x;
		int y = detY - Paddle.y;
		int w = width;
		int h = height;
		int l = ((int) (.2 * ((double) width)));
		int cl = ((int) (.4 * ((double) width)));
		int cr = ((int) (.6 * ((double) width)));
		int r = ((int) (.8 * ((double) width)));
		
		if((y < h) && (y >= x) && (x >= 0) && (x < l)){
			side = L_SIDE;
		}
		else if((y < h) && (y >= 0) && (y < x) && (x < l)){
			side = LEFT;
		}
		else if((y < h) && (y >= 0) && (x >= l) && (x < cl)){
			side = CEN_LEFT;
		}
		else if((y < h) && (y >= 0) && (x >= cl) && (x <= cr)){
			side = CENTER;
		}
		else if((y < h) && (y >= 0) && (x > cr) && (x <= r)){
			side = CEN_RIGHT;
		}
		else if((y >= 0) && (y < ((-1) * x) + w) && (x > r)){
			side = RIGHT;
		}
		else if((detY >= y) && (detY <= (y + height)) && (detX == (x + width))){
			side = R_SIDE;
		}
		else if((detY == (y + height)) && (detX > x) && (detX < (x + width))){
			side = BOTTOM;
		}

		return side;

	}
	
	public static void keyPressed(KeyEvent e){
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if((key == KeyEvent.VK_LEFT)){
			dx = -Display.getBrickWidth()/7;
		}
		else if((key == KeyEvent.VK_RIGHT)){
			dx = Display.getBrickWidth()/7;
		}
	}

	public static void keyReleased(KeyEvent e){
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT){
			dx = 0;
		}
		else if(key == KeyEvent.VK_RIGHT){
			dx = 0;
		}
	}
}
