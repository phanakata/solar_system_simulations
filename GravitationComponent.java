/*
  phanakata: A graphics component that draws disk with different 
  colors and sizes. It has a constant RADIUS so that ColorCircle 
  class can access this radius in order to assign this value 
  to the fields. 
*/
import javax.swing.*;
import java.awt.Color;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class GravitationComponent extends JComponent {
    double thick =  1.;
    double thick2 = 8.;
    ColorCircle [] ccArr;
    Gravitation solar;
    //create a random number generator.
    Random rand = new Random();
    
    public GravitationComponent( Gravitation solar0 )
    {
	solar = solar0;
	//solar = new Gravitation(N);
	ccArr = new ColorCircle[solar.getNumberObject()];
	double R =  thick2 * solar.getRadius(0); //make it 10 pixel wide
	int red = 225;
	int green = 120;
	int blue = 0;
	Color col = new Color(red,green,blue);
	Point2D.Double point = new Point2D.Double((double)GravitationAnimation.FRAME_WIDTH/2 + 
						  thick * solar.getPos(0, 0), (double)GravitationAnimation.FRAME_HEIGHT/2
						  + thick * solar.getPos(0, 1));
	ccArr[0]=new ColorCircle(point, R, col);
	
	
	thick =  .75*GravitationAnimation.FRAME_WIDTH/2./solar.getMax();
	for (int i = 1;i<solar.getNumberObject();i++)
	    {
		R = thick2 * solar.getRadius(i); //make it 10 pixel wide
		red = rand.nextInt(255);
		green = rand.nextInt(255);
		blue = rand.nextInt(255);
		
		if(i>solar.getNumberObject()/2-1)
		    {
			col = Color.BLACK;
			R = .5*R;
		    }
		else
		    col = new Color(red,green,blue);
		
		point = new Point2D.Double((double)GravitationAnimation.FRAME_WIDTH/2 + 
					   thick * solar.getPos(i, 0), (double)GravitationAnimation.FRAME_HEIGHT/2 + 
					   thick * solar.getPos(i, 1));
		ccArr[i]=new ColorCircle(point, R, col);
	    }
    }
    
    
    
    public void paintComponent(Graphics g)
    {
	Graphics2D g2 = (Graphics2D) g;
	for (int i = 0;i<solar.getNumberObject();i++)
	    {
		
		ccArr[i].setLoc((double)GravitationAnimation.FRAME_WIDTH/2 + 
				thick * solar.getPos(i, 0), (double)GravitationAnimation.FRAME_HEIGHT/2 + 
				thick * solar.getPos(i, 1));
		
		ccArr[i].drawMe(g2);
		ccArr[i].fillMe(g2);
	    }
	
    }
}

