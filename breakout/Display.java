package breakout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.Stack;

public class Display extends JPanel implements ActionListener{ //, KeyListener{
	/*////////////////////////////////////////
	/* GLOBAL VARIABLES AND GETTERS
	/*////////////////////////////////////////
	//Bricks dimensions
	private final static int brickWidth = 50;
	private final static int brickHeight = 20;	
	//Gap between bricks- size of ball scales to gap
	private final static int brickGap = 10;
	private final static int bricksX = 16;
	private final static int bricksY = 8;
	private static int bricksBroken = 0;
	private final static Color brickColor = Color.white;
	
	//Window changes size to fit dimensions and number of bricks
	private final static int panelWidth = (bricksX * (brickWidth + brickGap)) + brickGap;
	private final static int panelHeight = 2 * (bricksY * (brickHeight + brickGap) + brickGap);
	private final static Color background = Color.black;

	private static Brick[][] bricks = setupBricks();
	//Locations on the ball- used for collision detection
	private final static int detectionPoints = 32;
	private static Detection[] detectPoints = setupDetection();
	//Storage for data of one or more collision with the ball and other objects
	private Stack collisions = new Stack();
	private Paddle paddle;
	private Timer timer;
	private final int DELAY = 10;
	
	//Game State IDs
	private final static int PREPARING = 0;
	private final static int PLAYING = 1;
	private final static int VICTORY = 2;
	private final static int FAILURE = 2;
	private static int gameState;
	
	private static int time = 0;
	
	private final static Color stringColor = Color.white;
	//private final static char[] win = {'Y', 'O', 'U', ' ', 'W', 'I', 'N', '!'};
	//private final static char[] lose = {'Y', 'O', 'U', ' ', 'L', 'O', 'S', 'E', '!'};
	private final static String win = "You Win!";
	private final static String lose = "You Lose!";
	
	
	private static boolean checkVictory(){
		if(bricksBroken == (bricksX * bricksY)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static void lose(){
		gameState = FAILURE;
	}
	
	
	//Getter and Setter Functions
	public static int getBrickWidth()			{return brickWidth;}
	public static int getBrickHeight()			{return brickHeight;}
	public static int getBrickGap()				{return brickGap;}
	public static int getBricksX()				{return bricksX;}
	public static int getBricksY()				{return bricksY;}
	public static int getBricksBroken()			{return bricksBroken;}
	public static int getPanelWidth()			{return panelWidth;}
	public static int getPanelHeight()			{return panelHeight;}
	public static Brick[][] getBricks()			{return bricks;}
	public static Detection[] getDetectPoints()	{return detectPoints;}
	
	public static void setBricksBroken(int bricksBroken){
		Display.bricksBroken = bricksBroken;
	}
	
	/*////////////////////////////////////////
	/* INITIALIZATION
	/*////////////////////////////////////////
	
	//Creates the display window for the program
	public Display(){
		addKeyListener(new Adapter());
		setFocusable(true);
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setBackground(background);
		setOpaque(true);
		
		timer = new Timer(DELAY, this);
		gameState = PREPARING;
		timer.start();
		//gameState = PREPARING;
	}
	
	//Initializes the grid of Bricks
	private static Brick[][] setupBricks(){
		Brick[][] bricks = new Brick[bricksY][bricksX];
		Brick brick;
		int width;
		int height;
		for(int x = 0; x < bricksX; x++){
			for(int y = 0; y < bricksY; y++){
				brick = new Brick();
				width = brick.getBrickWidth();
				height = brick.getBrickHeight();
				brick.setBroken(false);
				brick.setPos(x*(width+brickGap) + brickGap, y*(height+brickGap) + brickGap);
				bricks[y][x] = brick;
			}
		}
		return bricks;
	}
	
	//Initializes collision detectors
	public static Detection[] setupDetection(){
		Detection[] points = new Detection[detectionPoints];
		for(int c = detectionPoints; c > 0; c--){
			points[c-1] = new Detection((Ball.getCenX() + (int) (((double) Ball.getDiameter()/2.0) *
										Math.cos((double) (2.0 * Math.PI * (c/(double) detectionPoints))))),
										(Ball.getCenY() + (int) (((double) Ball.getDiameter()/2.0) *
										Math.sin((double) (2.0 * Math.PI * (c/(double) detectionPoints))))));
		}
		return points;
	}
	
	/*////////////////////////////////////////
	/* DRAWING
	/*////////////////////////////////////////
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if((gameState == PREPARING) || (gameState == PLAYING)){
			drawBall(g);
			drawPaddle(g);
			drawBricks(g, bricks);
	        Toolkit.getDefaultToolkit().sync();
		}
        else if(gameState == FAILURE){
        	g.setColor(stringColor);
        	g.drawString(lose, panelWidth/2 - (30), panelHeight/2 - 5);
        }
		else if(gameState == VICTORY){
        	g.setColor(stringColor);
        	g.drawString(win, panelWidth/2 - (24), panelHeight/2 - 5);
        }
	}
	
	//Draws the grid of bricks to be destroyed
	private void drawBricks(Graphics g, Brick[][] bricks){
		g.setColor(brickColor);
		for(int x = 0; x < bricksX; x++){
			for(int y = 0; y < bricksY; y++){
				if(!bricks[y][x].getBroken()){
					g.fillRect(bricks[y][x].getBrickPosX(),
							   bricks[y][x].getBrickPosY(),
							   bricks[y][x].getBrickWidth(),
							   bricks[y][x].getBrickHeight());
				}
			}
		}
	}
	
	//Draws the ball
	private void drawBall(Graphics g){
		g.setColor(Ball.getColor());
		g.fillOval(Ball.getX(), Ball.getY(), Ball.getDiameter(), Ball.getDiameter());
		//System.out.println(ball.getColor() + " " + ball.getX() + " " + ball.getY() + " " + ball.getDiameter() + " " + ball.getDiameter());
	}
	
	//Draws paddle
	private void drawPaddle(Graphics g){
		g.setColor(Paddle.getColor());
		g.fillRect(Paddle.getX(), Paddle.getY(), Paddle.getWidth(), Paddle.getHeight());
		g.setColor(background);
		g.fillOval(Paddle.getX() + 2, Paddle.getY() + 2, Paddle.getWidth() - 4, Paddle.getHeight() - 4);
		g.setColor(Paddle.getColor());
		g.fillOval(Paddle.getX() + 4, Paddle.getY() + 4, Paddle.getWidth() - 8, Paddle.getHeight() - 8);
	}
	
//	private void test(Graphics g){
//		g.setColor(Color.RED);
//		for(int c = 0; c < detectionPoints; c++){
//			g.fillRect(detectPoints[c].getX(), detectPoints[c].getY(), 2, 2);
//		}
//	}
	
	/*////////////////////////////////////////
	/* MOVEMENT
	/*////////////////////////////////////////
	public void actionPerformed(ActionEvent e){
		if(time == 100){
			gameState = PLAYING;
		}
		if(checkVictory()){
			gameState = VICTORY;
		}
		
		if(gameState != VICTORY){
			Paddle.move();
			if(gameState == PREPARING){
				time++;
			}
			else if(gameState == PLAYING){
				Ball.move();
			}
		}

		
		detectPoints = setupDetection();
		repaint();
	}

    private class Adapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            Paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Paddle.keyPressed(e);
            //System.out.println(panelHeight + " " + Paddle.getY() + " " + Paddle.getHeight());
        }
    }
}