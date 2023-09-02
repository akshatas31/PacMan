import java.awt.*;

public class Sketch {
	public static int xPosition = 100;
	public static int yPosition = 100;
	public static int DISC_DIAMETER = 22;
	public static int eyex = 5;
	public static int eyey = 7;
	
	public static void main(String [] agrs) {
		DrawingPanel panel = new DrawingPanel (512, 512);
		Graphics g = panel.getGraphics();
		ghost(g,Color.pink);
		

			
	}
	public static void ghost (Graphics g, Color c) {
		g.setColor(c);	
		g.fillOval(xPosition, yPosition, DISC_DIAMETER, DISC_DIAMETER);
		g.fillRect(xPosition, yPosition +DISC_DIAMETER/2, 22, 15);
		g.setColor(Color.WHITE);	
		g.fillOval(xPosition +2, yPosition+7, eyex,eyey);
		g.fillOval(xPosition +12, yPosition+7, eyex,eyey);
		g.setColor(Color.BLACK);
		g.fillOval(xPosition +3, yPosition+7, eyex-2,eyey-2);
		g.fillOval(xPosition +12, yPosition+7, eyex-2,eyey-2);
		
	}
	
		
	


}
