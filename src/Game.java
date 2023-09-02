


/*Modified verision of Pacman which uses two classes
 * Game and Point to display the game using DrawingPanel
 * and its features
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;

public class Game implements KeyListener {
	
	private int xPosition;
	private int yPosition;
	private int ghost_xPosition;
	private int ghost_yPosition;
	private int ghost2_xPosition;
	private int ghost2_yPosition;
	private int ghost3_xPosition;
	private int ghost3_yPosition;
	private int ghost4_xPosition;
	private int ghost4_yPosition;
	private Point top;
	private Point bottom;
	private Point left;
	private Point right;
	private Point center;
	private int topPixelRGB;
	private int bottomPixelRGB;
	private int leftPixelRGB;
	private int rightPixelRGB;
	private int centerPixelRGB;
	private int count;
	private int time;
	private int score;
	private boolean newMaze;
	private boolean newMove;
	private Graphics g;
	
	public final int NUMBER_OF_POINTS = 132;
	public final int NUMBER_OF_BONUS_POINTS = 5;
	public final int NUMBER_OF_NEGATIVE_POINTS = 3;
	public final int POINT_COLLECTION_FACTOR = 10;
	Point[] points;
	boolean[] isPointCollected;
	Point[] bonusPoints;
	boolean[] isbonusPointCollected;
	Point[] negativePoints;
	boolean[] isnegativePointCollected;
	
	private boolean ghost_can_move;
	private boolean ghost2_can_move;
	private boolean ghost3_can_move;
	private boolean ghost4_can_move;
	
	public final int WALL_WIDTH = 10;
	public final int WALL_HEIGHT = 10;
	public final int TOLERANCE = 1;
	public final int DISC_DIAMETER = 22;
	public final int SPEED = 4;
	
	public final int TIME_FOR_GHOST1 = 10;
	public final int GHOST_MOVE = 1;
	public final int TIME_FOR_GHOST2 = 20;
	public final int TIME_FOR_GHOST3 = 40;
	public final int TIME_FOR_GHOST4 = 80;
	public final int GHOST_HEAD_DIAMETER = 22;
	public final int GHOST_BODY_HEIGHT = 15;
	public final int GHOST_EYE_WIDTH = 5;
	public final int GHOST_EYE_HEIGHT = 7;
	public final Color POINT_COLOR = Color.ORANGE;
	public final Color BONUS_POINT_COLOR = Color.MAGENTA;
	public final Color GHOST1_COLOR = Color.RED;
	public final Color GHOST2_COLOR = Color.GREEN;
	public final Color GHOST3_COLOR = Color.PINK;
	public final Color GHOST4_COLOR = Color.CYAN;
	
	public final int POINT_DIAMETER = 7;
	public final int BONUS_POINT_DIAMETER = 12;

	public static final int PANEL_WIDTH = 512;
	public static final int PANEL_HEIGHT = 512;

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel (PANEL_WIDTH, PANEL_HEIGHT);  
        Game game = new Game(panel);
        game.g = panel.getGraphics();
		panel.addKeyListener(game);
		
		while(true) {
			//System.out.println(game.count);
			
			switch( game.count ) { 
        	case 1:
        		game.newMaze = true;
        		game.drawMaze1(panel, game.g);
        		break;
        	
        	default:
        		if(!game.newMaze) {
        			panel.clear();
        		}
        		panel.clear();
        		game.gameOver(panel, game.g);
        		return;
			}
			
			game.drawDisc(panel, game.g);
			game.drawGhost(panel, game.g, game.time);
			game.drawGhost2(panel, game.g, game.time);
			game.drawGhost3(panel, game.g, game.time);
			game.drawGhost4(panel, game.g, game.time);
			game.drawPoints(panel, game.g);
			game.updateScore(panel, game.g);
			//game.drawScore(panel, game.g);
			
			if(!game.newMove) {
				panel.clear();
				game.newMove = true;
				
				if(!game.ghost_can_move) {
					
					game.ghost_can_move = true;
				}
				if(!game.ghost2_can_move) {
					
					game.ghost2_can_move = true;
				}
				if(!game.ghost3_can_move) {
					
					game.ghost3_can_move = true;
				}
				if(!game.ghost4_can_move) {
					
					game.ghost4_can_move = true;
				}

			}
			
			game.time++;		
		}
		
    }
    
    public Game(DrawingPanel panel) {
    	xPosition = 2 * WALL_WIDTH;
    	yPosition = 2 * WALL_HEIGHT;
    	top = new Point(xPosition + DISC_DIAMETER / 2, yPosition - TOLERANCE);
    	bottom = new Point(xPosition + DISC_DIAMETER / 2, yPosition + DISC_DIAMETER + TOLERANCE);
    	left = new Point(xPosition - TOLERANCE, yPosition + DISC_DIAMETER / 2);
    	right = new Point(xPosition + DISC_DIAMETER + TOLERANCE, yPosition + DISC_DIAMETER / 2);
    	center = new Point(xPosition + DISC_DIAMETER / 2, yPosition + DISC_DIAMETER / 2);
    	
    	
    	points = new Point[NUMBER_OF_POINTS];
    	isPointCollected = new boolean[NUMBER_OF_POINTS];
    	bonusPoints = new Point[NUMBER_OF_BONUS_POINTS];
    	isbonusPointCollected = new boolean[NUMBER_OF_BONUS_POINTS];
    	negativePoints = new Point[NUMBER_OF_NEGATIVE_POINTS];
    	isnegativePointCollected = new boolean[NUMBER_OF_NEGATIVE_POINTS];
    	
    	ghost_xPosition = 450; 
    	ghost_yPosition = 14;
    	ghost_can_move = false;
    	
    	ghost2_xPosition = 470; 
    	ghost2_yPosition = 280;
    	ghost2_can_move = false;
    	
    	ghost3_xPosition = 480; 
    	ghost3_yPosition = 400;
    	ghost3_can_move = false;
    	
    	ghost4_xPosition = 80; 
    	ghost4_yPosition = 410;
    	ghost4_can_move = false;
    	
		topPixelRGB = panel.getPixelRGB(top.getxCoordinate(), top.getyCoordinate());
		bottomPixelRGB = panel.getPixelRGB(bottom.getxCoordinate(), bottom.getyCoordinate());
		leftPixelRGB = panel.getPixelRGB(left.getxCoordinate(), left.getyCoordinate());
		rightPixelRGB = panel.getPixelRGB(right.getxCoordinate(), right.getyCoordinate());
		centerPixelRGB = panel.getPixelRGB(center.getxCoordinate(), center.getyCoordinate());
		
		count = 1;
		time = 1;
		score = 0;
    }
    

   
    public void drawMaze1(DrawingPanel panel, Graphics g) {
    	panel.setBackground(Color.BLACK);
    	g.setColor(Color.BLUE);
    	g.fillRoundRect(0, 0, panel.getWidth(), WALL_HEIGHT,30,30);
    	g.fillRoundRect(0, panel.getHeight() - WALL_HEIGHT, panel.getWidth(), WALL_HEIGHT,30,30);
    	g.fillRoundRect(0, 0, WALL_WIDTH, panel.getHeight(),30,30);
    	g.fillRoundRect(panel.getWidth() - WALL_WIDTH, 0, WALL_WIDTH, panel.getHeight(),30,30);
    	g.fillRoundRect(70, 40, panel.getWidth() - 14* WALL_WIDTH, WALL_HEIGHT,20,30);
    	g.fillRoundRect(0, 80,100,10,20,30);
    	g.fillRoundRect(130,40,10,70,20,30);
    	g.fillRoundRect(180,80,200,10,20,30);
    	g.fillRoundRect(230,80,10,90,20,30);
    	g.fillRoundRect(140,130,100,10,20,30);
    	g.fillRoundRect(280,120,300,10,20,30);
    	g.fillRoundRect(450,80,10,50,20,30);
    	g.fillRoundRect(230,160,230,10,20,30);
    	g.fillRoundRect(60,80,10,100,20,30);
    	g.fillRoundRect(60,170,130,10,20,30);
    	g.fillRoundRect(5,210,200,10,20,30);
    	g.fillRoundRect(270,165,10,200,20,30);
    	g.fillRoundRect(365,165,10,100,20,30);
    	g.fillRoundRect(320,220,10,240,20,30);
    	g.fillRoundRect(325,295,60,10,20,30);
    	g.fillRoundRect(410,210,100,10,20,30);
    	g.fillRoundRect(365,255,90,10,20,30);
    	g.fillRoundRect(420,255,10,90,20,30);
    	g.fillRoundRect(475,310,90,10,20,30);
    	g.fillRoundRect(360,340,90,10,20,30);
    	g.fillRoundRect(320,390,130,10,20,30);
    	g.fillRoundRect(370,440,10,100,20,30);
    	g.fillRoundRect(420,440,100,10,20,30);
    	g.fillRoundRect(5,460,270,10,20,30);
    	g.fillRoundRect(45,210,10,210,20,30);
    	g.fillRoundRect(100,250,180,10,20,30);
    	g.fillRoundRect(50,285,80,10,20,30);
    	g.fillRoundRect(222,305,10,160,20,30);
    	g.fillRoundRect(80,335,100,10,20,30);
    	g.fillRoundRect(110,340,10,90,20,30);
    	g.fillRoundRect(150,385,80,10,20,30);	
    	g.setColor(Color.WHITE);
    	g.setFont(new Font("SansSerif", Font.PLAIN, 20)); 
    	g.drawString("Score:  " +Integer.toString(score), PANEL_WIDTH - 120, PANEL_HEIGHT - 20);
    }
    // updates the score if pacman has the crossed a point, bonus point or negative point by comparing the (x,y) positions 
    public void updateScore(DrawingPanel panel, Graphics g) {
    	for (int i = 0; i < NUMBER_OF_POINTS; i++) {
    		if(Math.pow(center.getxCoordinate() - points[i].getxCoordinate(), 2) + Math.pow(center.getyCoordinate() - points[i].getyCoordinate(), 2) < POINT_COLLECTION_FACTOR * Math.pow(POINT_DIAMETER / 2.0, 2)) {
    			if(!isPointCollected[i]) {
        			isPointCollected[i] = true;
        			score+=5;
    			}
    		}
    				
    	}
    	for (int i = 0; i < NUMBER_OF_BONUS_POINTS; i++) {
    		if(Math.pow(center.getxCoordinate() - bonusPoints[i].getxCoordinate(), 2) + Math.pow(center.getyCoordinate() - bonusPoints[i].getyCoordinate(), 2) < POINT_COLLECTION_FACTOR * Math.pow(BONUS_POINT_DIAMETER / 2.0, 2)) {
    			if(!isbonusPointCollected[i]) {
        			isbonusPointCollected[i] = true;
        			score+=100;
    			}
    		}
    				
    	}
    	for (int i = 0; i < NUMBER_OF_NEGATIVE_POINTS; i++) {
    		if(Math.pow(center.getxCoordinate() - negativePoints[i].getxCoordinate(), 2) + Math.pow(center.getyCoordinate() - negativePoints[i].getyCoordinate(), 2) < POINT_COLLECTION_FACTOR * Math.pow(BONUS_POINT_DIAMETER / 2.0, 2)) {
    			if(!isnegativePointCollected[i]) {
        			isnegativePointCollected[i] = true;
        			score=0;
    			}
    		}
    				
    	}
    }
    
   
 
    //displayed once pacman has encountered ghosts/ crossed 1000 points
    public void gameOver(DrawingPanel panel, Graphics g) {
    	
    	panel.clear();
    	g.setColor(POINT_COLOR);
    	g.drawString("GAME OVER", 180, panel.getHeight() / 2);
    }
    
    // method to draw the pacman
    public void drawDisc(DrawingPanel panel, Graphics g) {
		g.setColor(Color.YELLOW);
		
		topPixelRGB = panel.getPixelRGB(top.getxCoordinate(), top.getyCoordinate());
		bottomPixelRGB = panel.getPixelRGB(bottom.getxCoordinate(), bottom.getyCoordinate());
		leftPixelRGB = panel.getPixelRGB(left.getxCoordinate(), left.getyCoordinate());
		rightPixelRGB = panel.getPixelRGB(right.getxCoordinate(), right.getyCoordinate());
		centerPixelRGB = panel.getPixelRGB(center.getxCoordinate(), center.getyCoordinate());

		g.fillArc(xPosition, yPosition, DISC_DIAMETER, DISC_DIAMETER, 30,300);
    }
    //Stores all the points in an array and redraws them each time the panel is cleared if the panel is not collected
    public void drawPoints(DrawingPanel panel, Graphics g) {

    	g.setColor(POINT_COLOR);
    	
    	for (int i = 0; i < 10; i++) {
    		points[i] = new Point(60 + 40 * i, 20);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=10; i< 22;i++) {
    		points[i] = new Point(30+ (40 *(i-10)), 60);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=22; i< 31;i++) {
    		points[i] = new Point(30+40 *(i-22), 490);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=31; i< 35;i++) {
    		points[i] = new Point(355 + 40 *(i-31), 420);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=35; i< 39;i++) {
    		points[i] = new Point(355 + 40 *(i-35), 370);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=39; i< 43;i++) {
    		points[i] = new Point(355 + 40 *(i-39), 325);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=43; i< 47;i++) {
    		points[i] = new Point(355 + 40 *(i-43), 280);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=47; i< 51;i++) {
    		points[i] = new Point(355 + 40 *(i-47), 240);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	
    	for (int i=51; i< 53;i++) {
    		points[i] = new Point(345 + 40 *(i-51), 210);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=53; i< 58;i++) {
    		points[i] = new Point(305 + 40 *(i-53), 180);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=58; i< 65;i++) {
    		points[i] = new Point(305, 215 + 40 *(i-58));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=65; i< 68;i++) {
    		points[i] = new Point(275,380 + 40 *(i-65));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=68; i< 73;i++) {
    		points[i] = new Point(250,275 + 40 *(i-68));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=73; i< 79;i++) {
    		points[i] = new Point(25,240 + 40 *(i-73));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=79; i< 83;i++) {
    		points[i] = new Point(75+40 *(i-79),440);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=83; i< 87;i++) {
    		points[i] = new Point(75+40 *(i-83),320);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=87; i< 91;i++) {
    		points[i] = new Point(75+40 *(i-87),270);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}

    	points[91] = new Point(225, 275);
		if(!isPointCollected[91]) {
			g.fillOval(points[91].getxCoordinate(), points[91].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
		}
    	for (int i=92; i< 97;i++) {
    		
    		points[i] = new Point(75+40 *(i-92),230);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=97; i< 103;i++) {

    		points[i] = new Point(30+40*(i-97), 190);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=103; i< 115;i++) {
    		
    		points[i] = new Point(90+40*(i-103), 150);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	
    	}
    	g.setColor(POINT_COLOR);
    	for (int i=115; i< 120;i++) {

    		points[i] = new Point(250+40*(i-115), 105);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=120; i< 124;i++) {
    		points[i] = new Point(90+40*(i-120), 115);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=124; i< 126;i++) {

    		points[i] = new Point(30, 115+40*(i-124));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=126; i< 128;i++) {

    		points[i] = new Point(70, 360+40*(i-126));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=128; i< 130;i++) {
    		points[i] = new Point(130, 360+40*(i-128));
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	for (int i=130; i< 132;i++) {
    		points[i] = new Point(160+40*(i-130), 360);
    		if(!isPointCollected[i]) {
    			g.fillOval(points[i].getxCoordinate(), points[i].getyCoordinate(), POINT_DIAMETER, POINT_DIAMETER);
    		}
    	}
    	g.setColor(BONUS_POINT_COLOR);
    	bonusPoints[0] = new Point(175,415);
    	if(!isbonusPointCollected[0]) {
			g.fillOval(175, 415, BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
    	bonusPoints[1] = new Point(480,100 );
    	if(!isbonusPointCollected[1]) {
			g.fillOval(bonusPoints[1].getxCoordinate(), bonusPoints[1].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
    	bonusPoints[2] = new Point(350,460);
    	if(!isbonusPointCollected[2]) {
			g.fillOval(bonusPoints[2].getxCoordinate(), bonusPoints[2].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
	    }
		bonusPoints[3] = new Point(160,90);
    	if(!isbonusPointCollected[3]) {
			g.fillOval(bonusPoints[3].getxCoordinate(), bonusPoints[3].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
		bonusPoints[4] = new Point(155,300);
    	if(!isbonusPointCollected[0]) {
			g.fillOval(bonusPoints[4].getxCoordinate(), bonusPoints[4].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
    	negativePoints[0] = new Point(410,90);
    	if(!isnegativePointCollected[0]) {
			g.fillOval(negativePoints[0].getxCoordinate(), negativePoints[0].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
    	negativePoints[1] = new Point(490,400 );
    	if(!isnegativePointCollected[1]) {
			g.fillOval(negativePoints[1].getxCoordinate(), negativePoints[1].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
		}
    	negativePoints[2] = new Point(460,300);
    	if(!isnegativePointCollected[2]) {
			g.fillOval(negativePoints[2].getxCoordinate(),negativePoints[2].getyCoordinate(), BONUS_POINT_DIAMETER, BONUS_POINT_DIAMETER);
	    }
    	
    	
    }
    // Ghosts move each time the panel clears.Like Pacman, the Ghost's top, bottom, left, and right pixels are used to prevent 
    //them from passing through walls. The movement of the Ghosts chosen as the direction which would minimize the distance between 
    //it and Pacman.
    
    public void drawGhost(DrawingPanel panel, Graphics g, int time) {
    	g.setColor(Color.RED);
    	
    	int ghost_case = 0;
    	
    	if(ghost_can_move && time > TIME_FOR_GHOST1) {
    	
			int ghost_topPixelRGB = panel.getPixelRGB(ghost_xPosition + DISC_DIAMETER / 2, ghost_yPosition - TOLERANCE);
			int ghost_bottomPixelRGB = panel.getPixelRGB(ghost_xPosition + DISC_DIAMETER / 2, ghost_yPosition + DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT + TOLERANCE);
			int ghost_leftPixelRGB = panel.getPixelRGB(ghost_xPosition - TOLERANCE, ghost_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
			int ghost_rightPixelRGB = panel.getPixelRGB(ghost_xPosition + DISC_DIAMETER + TOLERANCE, ghost_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
			
			double d = Math.pow(xPosition - ghost_xPosition, 2) + Math.pow(yPosition - ghost_yPosition, 2);
			
			double d_top = Math.pow(xPosition - ghost_xPosition, 2) + Math.pow(yPosition - (ghost_yPosition - GHOST_MOVE), 2);
			double d_bottom = Math.pow(xPosition - ghost_xPosition, 2) + Math.pow(yPosition - (ghost_yPosition + GHOST_MOVE), 2);
			double d_left = Math.pow(xPosition - (ghost_xPosition - GHOST_MOVE), 2) + Math.pow(yPosition - ghost_yPosition, 2);
			double d_right = Math.pow(xPosition - (ghost_xPosition + GHOST_MOVE), 2) + Math.pow(yPosition - ghost_yPosition, 2);
			
			if(DrawingPanel.getRed(ghost_topPixelRGB) != 0 || DrawingPanel.getGreen(ghost_topPixelRGB) != 0 || DrawingPanel.getBlue(ghost_topPixelRGB) != 255 && d_top < d) {
				ghost_case = 1;
				d = d_top;
			}
			
			if(DrawingPanel.getRed(ghost_bottomPixelRGB) != 0 || DrawingPanel.getGreen(ghost_bottomPixelRGB) != 0 || DrawingPanel.getBlue(ghost_bottomPixelRGB) != 255 && d_bottom < d) {
				ghost_case = 2;
				d = d_bottom;
			}
			
			if(DrawingPanel.getRed(ghost_leftPixelRGB) != 0 || DrawingPanel.getGreen(ghost_leftPixelRGB) != 0 || DrawingPanel.getBlue(ghost_leftPixelRGB) != 255 && d_left < d) {
				ghost_case = 3;
				d = d_left;
			}
			
			if(DrawingPanel.getRed(ghost_rightPixelRGB) != 0 || DrawingPanel.getGreen(ghost_rightPixelRGB) != 0 || DrawingPanel.getBlue(ghost_rightPixelRGB) != 255 && d_right < d){
				ghost_case = 4;
				d = d_right;
			}
			
		
		
			switch(ghost_case) { 
			case 1:
				ghost_yPosition -= GHOST_MOVE;
				ghost_can_move = false;
				break;
			case 2:
		    	ghost_yPosition += GHOST_MOVE;
		    	ghost_can_move = false;
				break;
			case 3:
		    	ghost_xPosition -= GHOST_MOVE;
		    	ghost_can_move = false;	
				break;
			case 4:
				ghost_xPosition += GHOST_MOVE;
		    	ghost_can_move = false;
				break;
		
			}
			
			ghost(g, Color.RED, ghost_xPosition, ghost_yPosition);
		}
    
    }
    
    public void drawGhost2(DrawingPanel panel, Graphics g, int time) {
    	g.setColor(Color.GREEN);
    	
    	if(ghost2_can_move && time > TIME_FOR_GHOST2) {
    	
		int ghost2_topPixelRGB = panel.getPixelRGB(ghost2_xPosition + DISC_DIAMETER / 2, ghost2_yPosition - TOLERANCE);
		int ghost2_bottomPixelRGB = panel.getPixelRGB(ghost2_xPosition + DISC_DIAMETER / 2, ghost2_yPosition + DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT + TOLERANCE);
		int ghost2_leftPixelRGB = panel.getPixelRGB(ghost2_xPosition - TOLERANCE, ghost2_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
		int ghost2_rightPixelRGB = panel.getPixelRGB(ghost2_xPosition + DISC_DIAMETER + TOLERANCE, ghost2_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
    	
    	double d = Math.pow(xPosition - ghost2_xPosition, 2) + Math.pow(yPosition - ghost2_yPosition, 2);
    	
    	double d_top = Math.pow(xPosition - ghost2_xPosition, 2) + Math.pow(yPosition - (ghost2_yPosition - GHOST_MOVE), 2);
    	double d_bottom = Math.pow(xPosition - ghost2_xPosition, 2) + Math.pow(yPosition - (ghost2_yPosition + GHOST_MOVE), 2);
    	double d_left = Math.pow(xPosition - (ghost2_xPosition - GHOST_MOVE), 2) + Math.pow(yPosition - ghost2_yPosition, 2);
    	double d_right = Math.pow(xPosition - (ghost2_xPosition + GHOST_MOVE), 2) + Math.pow(yPosition - ghost2_yPosition, 2);
    	
    	int ghost_case = 0;
    	if(DrawingPanel.getRed(ghost2_topPixelRGB) != 0 || DrawingPanel.getGreen(ghost2_topPixelRGB) != 0 || DrawingPanel.getBlue(ghost2_topPixelRGB) != 255 && d_top < d) {
			ghost_case = 1;
			d = d_top;
		}
		
		if(DrawingPanel.getRed(ghost2_bottomPixelRGB) != 0 || DrawingPanel.getGreen(ghost2_bottomPixelRGB) != 0 || DrawingPanel.getBlue(ghost2_bottomPixelRGB) != 255 && d_bottom < d) {
			ghost_case = 2;
			d = d_bottom;
		}
		
		if(DrawingPanel.getRed(ghost2_leftPixelRGB) != 0 || DrawingPanel.getGreen(ghost2_leftPixelRGB) != 0 || DrawingPanel.getBlue(ghost2_leftPixelRGB) != 255 && d_left < d) {
			ghost_case = 3;
			d = d_left;
		}
		
		if(DrawingPanel.getRed(ghost2_rightPixelRGB) != 0 || DrawingPanel.getGreen(ghost2_rightPixelRGB) != 0 || DrawingPanel.getBlue(ghost2_rightPixelRGB) != 255 && d_right < d){
			ghost_case = 4;
			d = d_right;
		}
		switch(ghost_case) { 
		case 1:
			ghost2_yPosition -= GHOST_MOVE;
			ghost_can_move = false;
			break;
		case 2:
	    	ghost2_yPosition += GHOST_MOVE;
	    	ghost_can_move = false;
			break;
		case 3:
	    	ghost2_xPosition -= GHOST_MOVE;
	    	ghost_can_move = false;	
			break;
		case 4:
			ghost2_xPosition += GHOST_MOVE;
	    	ghost2_can_move = false;
			break;
		}
    	}
    	ghost(g, Color.green, ghost2_xPosition, ghost2_yPosition);
    	
  
    }
    
    public void drawGhost3(DrawingPanel panel, Graphics g, int time) {
    	if(ghost3_can_move && time > TIME_FOR_GHOST3) {
        	
    		int ghost3_topPixelRGB = panel.getPixelRGB(ghost3_xPosition + DISC_DIAMETER / 2, ghost3_yPosition - TOLERANCE);
    		int ghost3_bottomPixelRGB = panel.getPixelRGB(ghost3_xPosition + DISC_DIAMETER / 2, ghost3_yPosition + DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT + TOLERANCE);
    		int ghost3_leftPixelRGB = panel.getPixelRGB(ghost3_xPosition - TOLERANCE, ghost3_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
    		int ghost3_rightPixelRGB = panel.getPixelRGB(ghost3_xPosition + DISC_DIAMETER + TOLERANCE, ghost3_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
        	
        	double d = Math.pow(xPosition - ghost3_xPosition, 2) + Math.pow(yPosition - ghost3_yPosition, 2);
        	
        	double d_top = Math.pow(xPosition - ghost3_xPosition, 2) + Math.pow(yPosition - (ghost3_yPosition - GHOST_MOVE), 2);
        	double d_bottom = Math.pow(xPosition - ghost3_xPosition, 2) + Math.pow(yPosition - (ghost3_yPosition + GHOST_MOVE), 2);
        	double d_left = Math.pow(xPosition - (ghost3_xPosition - GHOST_MOVE), 2) + Math.pow(yPosition - ghost3_yPosition, 2);
        	double d_right = Math.pow(xPosition - (ghost3_xPosition + GHOST_MOVE), 2) + Math.pow(yPosition - ghost3_yPosition, 2);
        	
        	int ghost_case = 0;
        	if(DrawingPanel.getRed(ghost3_topPixelRGB) != 0 || DrawingPanel.getGreen(ghost3_topPixelRGB) != 0 || DrawingPanel.getBlue(ghost3_topPixelRGB) != 255 && d_top < d) {
    			ghost_case = 1;
    			d = d_top;
    		}
    		
    		if(DrawingPanel.getRed(ghost3_bottomPixelRGB) != 0 || DrawingPanel.getGreen(ghost3_bottomPixelRGB) != 0 || DrawingPanel.getBlue(ghost3_bottomPixelRGB) != 255 && d_bottom < d) {
    			ghost_case = 2;
    			d = d_bottom;
    		}
    		
    		if(DrawingPanel.getRed(ghost3_leftPixelRGB) != 0 || DrawingPanel.getGreen(ghost3_leftPixelRGB) != 0 || DrawingPanel.getBlue(ghost3_leftPixelRGB) != 255 && d_left < d) {
    			ghost_case = 3;
    			d = d_left;
    		}
    		
    		if(DrawingPanel.getRed(ghost3_rightPixelRGB) != 0 || DrawingPanel.getGreen(ghost3_rightPixelRGB) != 0 || DrawingPanel.getBlue(ghost3_rightPixelRGB) != 255 && d_right < d){
    			ghost_case = 4;
    			d = d_right;
    		}
    		switch(ghost_case) { 
    		case 1:
    			ghost3_yPosition -= GHOST_MOVE;
    			ghost_can_move = false;
    			break;
    		case 2:
    	    	ghost3_yPosition += GHOST_MOVE;
    	    	ghost_can_move = false;
    			break;
    		case 3:
    	    	ghost3_xPosition -= GHOST_MOVE;
    	    	ghost_can_move = false;	
    			break;
    		case 4:
    			ghost3_xPosition += GHOST_MOVE;
    	    	ghost3_can_move = false;
    			break;
    		}
        	}  
    	ghost(g, Color.pink, ghost3_xPosition, ghost3_yPosition);
    	
    }
    
    public void drawGhost4(DrawingPanel panel, Graphics g, int time) {
    	if(ghost4_can_move && time > TIME_FOR_GHOST4) {
        	
    		int ghost4_topPixelRGB = panel.getPixelRGB(ghost4_xPosition + DISC_DIAMETER / 2, ghost4_yPosition - TOLERANCE);
    		int ghost4_bottomPixelRGB = panel.getPixelRGB(ghost4_xPosition + DISC_DIAMETER / 2, ghost4_yPosition + DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT + TOLERANCE);
    		int ghost4_leftPixelRGB = panel.getPixelRGB(ghost4_xPosition - TOLERANCE, ghost4_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
    		int ghost4_rightPixelRGB = panel.getPixelRGB(ghost4_xPosition + DISC_DIAMETER + TOLERANCE, ghost4_yPosition + (DISC_DIAMETER / 2 + GHOST_BODY_HEIGHT) / 2);
        	
        	double d = Math.pow(xPosition - ghost4_xPosition, 2) + Math.pow(yPosition - ghost4_yPosition, 2);
        	
        	double d_top = Math.pow(xPosition - ghost4_xPosition, 2) + Math.pow(yPosition - (ghost4_yPosition - GHOST_MOVE), 2);
        	double d_bottom = Math.pow(xPosition - ghost4_xPosition, 2) + Math.pow(yPosition - (ghost4_yPosition + GHOST_MOVE), 2);
        	double d_left = Math.pow(xPosition - (ghost4_xPosition - GHOST_MOVE), 2) + Math.pow(yPosition - ghost4_yPosition, 2);
        	double d_right = Math.pow(xPosition - (ghost4_xPosition + GHOST_MOVE), 2) + Math.pow(yPosition - ghost4_yPosition, 2);
        	
        	int ghost_case = 0;
        	if(DrawingPanel.getRed(ghost4_topPixelRGB) != 0 || DrawingPanel.getGreen(ghost4_topPixelRGB) != 0 || DrawingPanel.getBlue(ghost4_topPixelRGB) != 255 && d_top < d) {
    			ghost_case = 1;
    			d = d_top;
    		}
    		
    		if(DrawingPanel.getRed(ghost4_bottomPixelRGB) != 0 || DrawingPanel.getGreen(ghost4_bottomPixelRGB) != 0 || DrawingPanel.getBlue(ghost4_bottomPixelRGB) != 255 && d_bottom < d) {
    			ghost_case = 2;
    			d = d_bottom;
    		}
    		
    		if(DrawingPanel.getRed(ghost4_leftPixelRGB) != 0 || DrawingPanel.getGreen(ghost4_leftPixelRGB) != 0 || DrawingPanel.getBlue(ghost4_leftPixelRGB) != 255 && d_left < d) {
    			ghost_case = 3;
    			d = d_left;
    		}
    		
    		if(DrawingPanel.getRed(ghost4_rightPixelRGB) != 0 || DrawingPanel.getGreen(ghost4_rightPixelRGB) != 0 || DrawingPanel.getBlue(ghost4_rightPixelRGB) != 255 && d_right < d){
    			ghost_case = 4;
    			d = d_right;
    		}
    		switch(ghost_case) { 
    		case 1:
    			ghost4_yPosition -= GHOST_MOVE;
    			ghost_can_move = false;
    			break;
    		case 2:
    	    	ghost4_yPosition += GHOST_MOVE;
    	    	ghost_can_move = false;
    			break;
    		case 3:
    	    	ghost4_xPosition -= GHOST_MOVE;
    	    	ghost_can_move = false;	
    			break;
    		case 4:
    			ghost4_xPosition += GHOST_MOVE;
    	    	ghost4_can_move = false;
    			break;
    		}
        	}  
    	ghost(g, Color.cyan, ghost4_xPosition, ghost4_yPosition);
    }
    
    
  // draws the ghosts  used in GHOST 1,2,3,and 4 methods.
    public void ghost(Graphics g, Color c, int x, int y) {
		g.setColor(c);	
		g.fillOval(x, y, GHOST_HEAD_DIAMETER, GHOST_HEAD_DIAMETER);
		g.fillRect(x, y + GHOST_HEAD_DIAMETER / 2, GHOST_HEAD_DIAMETER, GHOST_BODY_HEIGHT);
		g.setColor(Color.WHITE);	
		g.fillOval(x + 2, y + 7, GHOST_EYE_WIDTH, GHOST_EYE_HEIGHT);
		g.fillOval(x + 12, y + 7, GHOST_EYE_WIDTH, GHOST_EYE_HEIGHT);
		g.setColor(Color.BLACK);
		g.fillOval(x + 3, y + 7, GHOST_EYE_WIDTH - 2, GHOST_EYE_HEIGHT - 2);
		g.fillOval(x + 12, y + 7, GHOST_EYE_WIDTH - 2, GHOST_EYE_HEIGHT - 2);
	}
    
    public void updatePoints(int deltaX, int deltaY) {
    	top.setxCoordinate(top.getxCoordinate() + deltaX);
    	top.setyCoordinate(top.getyCoordinate() + deltaY);
    	bottom.setxCoordinate(bottom.getxCoordinate() + deltaX);
    	bottom.setyCoordinate(bottom.getyCoordinate() + deltaY);
    	left.setxCoordinate(left.getxCoordinate() + deltaX);
    	left.setyCoordinate(left.getyCoordinate() + deltaY);
    	right.setxCoordinate(right.getxCoordinate() + deltaX);
    	right.setyCoordinate(right.getyCoordinate() + deltaY);
    	center.setxCoordinate(center.getxCoordinate() + deltaX);
    	center.setyCoordinate(center.getyCoordinate() + deltaY);
    }
    
	@Override
	public void keyTyped(KeyEvent e) {
	    
	}
	//tests rgb values of the bottom,top, left,and right pixels to prevent the pacman from running into walls
	//and to check if it has encountered any ghosts or if the score has reached 1000

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyChar());
		int keyCode = e.getKeyCode();
	    switch( keyCode ) { 
	        case KeyEvent.VK_UP:
	        	if(DrawingPanel.getRed(topPixelRGB) != 0 || DrawingPanel.getGreen(topPixelRGB) != 0 || DrawingPanel.getBlue(topPixelRGB) != 255) {
	        		if((DrawingPanel.getRed(topPixelRGB) == 255 && DrawingPanel.getGreen(topPixelRGB) == 0 && DrawingPanel.getBlue(topPixelRGB) == 0)
	        				||(DrawingPanel.getRed(topPixelRGB) == GHOST2_COLOR.getRed() && DrawingPanel.getGreen(topPixelRGB) == GHOST2_COLOR.getGreen() && DrawingPanel.getBlue(topPixelRGB) == GHOST2_COLOR.getBlue())
	        				||(DrawingPanel.getRed(topPixelRGB) == GHOST3_COLOR.getRed() && DrawingPanel.getGreen(topPixelRGB) == GHOST3_COLOR.getGreen() && DrawingPanel.getBlue(topPixelRGB) == GHOST3_COLOR.getBlue())
	        				||(DrawingPanel.getRed(topPixelRGB) == GHOST4_COLOR.getRed() && DrawingPanel.getGreen(topPixelRGB) == GHOST4_COLOR.getGreen() && DrawingPanel.getBlue(topPixelRGB) == GHOST4_COLOR.getBlue())
	        				|| score>= 1000) {
	        			if(newMaze) {
		        			count++;
		        			newMaze = false;
		        		}
		        	} else {
		        		if(newMove) {
		        			yPosition -= SPEED;
				            updatePoints(0, -SPEED);
				            newMove = false;
		        		}
		        	}
	        	}
	            break;
	        case KeyEvent.VK_DOWN:
	        	if(DrawingPanel.getRed(bottomPixelRGB) != 0 || DrawingPanel.getGreen(bottomPixelRGB) != 0 || DrawingPanel.getBlue(bottomPixelRGB) != 255) {
	        		if((DrawingPanel.getRed(bottomPixelRGB) == 255 && DrawingPanel.getGreen(bottomPixelRGB) == 0 && DrawingPanel.getBlue(bottomPixelRGB) == 0)
	        				||(DrawingPanel.getRed(bottomPixelRGB) == GHOST2_COLOR.getRed() && DrawingPanel.getGreen(bottomPixelRGB) == GHOST2_COLOR.getGreen() && DrawingPanel.getBlue(bottomPixelRGB) == GHOST2_COLOR.getBlue())
	        				||(DrawingPanel.getRed(bottomPixelRGB) == GHOST3_COLOR.getRed() && DrawingPanel.getGreen(bottomPixelRGB) == GHOST3_COLOR.getGreen() && DrawingPanel.getBlue(bottomPixelRGB) == GHOST3_COLOR.getBlue())
	        				||(DrawingPanel.getRed(bottomPixelRGB) == GHOST4_COLOR.getRed() && DrawingPanel.getGreen(bottomPixelRGB) == GHOST4_COLOR.getGreen() && DrawingPanel.getBlue(bottomPixelRGB) == GHOST4_COLOR.getBlue())
	        				|| score>= 1000) {
	        			if(newMaze) {
		        			count++;
		        			newMaze = false;
		        		}
		        	} else {
		        		if(newMove) {	
		        			yPosition += SPEED;
		        			updatePoints(0, SPEED);
		        			newMove = false;
		        		}
		        	}
	        	}
	            break;
	        case KeyEvent.VK_LEFT:
	        	if(DrawingPanel.getRed(leftPixelRGB) != 0 || DrawingPanel.getGreen(leftPixelRGB) != 0 || DrawingPanel.getBlue(leftPixelRGB) != 255) {
	        		if((DrawingPanel.getRed(leftPixelRGB) == 255 && DrawingPanel.getGreen(leftPixelRGB) == 0 && DrawingPanel.getBlue(leftPixelRGB) == 0) 
	        				||(DrawingPanel.getRed(leftPixelRGB) == GHOST2_COLOR.getRed() && DrawingPanel.getGreen(leftPixelRGB) == GHOST2_COLOR.getGreen() && DrawingPanel.getBlue(leftPixelRGB) == GHOST2_COLOR.getBlue())
	        				||(DrawingPanel.getRed(leftPixelRGB) == GHOST3_COLOR.getRed() && DrawingPanel.getGreen(leftPixelRGB) == GHOST3_COLOR.getGreen() && DrawingPanel.getBlue(leftPixelRGB) == GHOST3_COLOR.getBlue())
	        				||(DrawingPanel.getRed(leftPixelRGB) == GHOST4_COLOR.getRed() && DrawingPanel.getGreen(leftPixelRGB) == GHOST4_COLOR.getGreen() && DrawingPanel.getBlue(leftPixelRGB) == GHOST4_COLOR.getBlue())
	        				|| score>= 1000){
	        			if(newMaze) {
		        			count++;
		        			newMaze = false;
		        		}
		        	} else {
		        		if(newMove) {
			        		xPosition -= SPEED;
				            updatePoints(-SPEED, 0);
				            newMove = false;
		        		}
		        	}
	        	}
	            break;
	        case KeyEvent.VK_RIGHT :
	        	if(DrawingPanel.getRed(rightPixelRGB) != 0 || DrawingPanel.getGreen(rightPixelRGB) != 0 || DrawingPanel.getBlue(rightPixelRGB) != 255) {
	        		if((DrawingPanel.getRed(rightPixelRGB) == 255 && DrawingPanel.getGreen(rightPixelRGB) == 0 && DrawingPanel.getBlue(rightPixelRGB) == 0) 
	        				||(DrawingPanel.getRed(rightPixelRGB) == GHOST2_COLOR.getRed() && DrawingPanel.getGreen(rightPixelRGB) == GHOST2_COLOR.getGreen() && DrawingPanel.getBlue(rightPixelRGB) == GHOST2_COLOR.getBlue())
	        				||(DrawingPanel.getRed(rightPixelRGB) == GHOST3_COLOR.getRed() && DrawingPanel.getGreen(rightPixelRGB) == GHOST3_COLOR.getGreen() && DrawingPanel.getBlue(rightPixelRGB) == GHOST3_COLOR.getBlue())
	        				||(DrawingPanel.getRed(rightPixelRGB) == GHOST4_COLOR.getRed() && DrawingPanel.getGreen(rightPixelRGB) == GHOST4_COLOR.getGreen() && DrawingPanel.getBlue(rightPixelRGB) == GHOST4_COLOR.getBlue())
	        				|| score>= 1000) {
		        		if(newMaze) {
		        			count++;
		        			newMaze = false;
		        		}
		        	} else {
		        		if(newMove) {
			        		xPosition += SPEED;
				        	updatePoints(SPEED, 0);
				        	newMove = false;
		        		}
		        	}
	        	}
	            break;
	     }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
    
}


