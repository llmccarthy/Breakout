package breakout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Brick{
	//Objects in 2-d brick array
	private static final int brickWidth = Display.getBrickWidth();
	private static final int brickHeight = Display.getBrickHeight();
	private final Color brickColor = Color.white;
	private int brickPosX = 0;
	private int brickPosY = 0;
	//True if brick has been hit, false if has not
	private boolean broken = false;
	
	public Brick(){}
	
	public int getBrickWidth(){
		return brickWidth;
	}

	public int getBrickHeight(){
		return brickHeight;
	}
	
	public Color getBrickColor(){
		return brickColor;
	}
	
	public int getBrickPosX(){
		return brickPosX;
	}
	
	public int getBrickPosY(){
		return brickPosY;
	}
	
	public boolean getBroken(){
		return broken;
	}
	
	public void setPos(int brickPosX, int brickPosY){
		this.brickPosX = brickPosX;
		this.brickPosY = brickPosY;
	}
	
	public void setBroken(boolean broken){
		this.broken = broken;
	}
}