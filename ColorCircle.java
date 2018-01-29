/*
  phanakata: A class defining color circle with fields 
  of the position, color and radius of the circle. 
  This class has methods that vary the radius and the 
  color, and some other basic methods and accessors. 
  Additionnaly, this class has methods to draw 
  and fill ellilpses (circle)
 */

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.*;

public class ColorCircle{
    
    //fields 
    private Point2D.Double center;
    private double   radius;
    private Color color;
    
    //default constructor
    public ColorCircle()
    {
	center = new Point2D.Double(0,0);
	radius = 0;
	color = Color.BLACK;
    }
    
    //constructor
    public ColorCircle(Point2D.Double p, double r, Color c)
    {
	center = p;
	radius = r; 
	color  = c;
    }
    
    //constructor
    public ColorCircle(double x, double y, double r, Color c)
    {
	center = new Point2D.Double(x,y);
	radius = r;
	color  = c;
    }
    
    //accessors
    public double getRadius()
    {
	return radius;
    }
    
    public Point2D.Double getCenter()
    {
	return center;
    }
    
    //mutators
    public void setLoc(double x, double y)
    {
	center.setLocation(x, y);
    }
    
    //this methods change the color of the circle with some interval
    public void changeColor(int delta)
    {
	color = new Color(color.getRed()+delta, color.getGreen(), color.getBlue()-delta);
    }
    
    //draws circle
    public void drawMe(Graphics2D g)
    {
	g.setColor(color);
	Ellipse2D.Double e = new Ellipse2D.Double(center.getX() - radius, 
						  center.getY()-radius, 2*radius,
						  2*radius);
	g.draw(e);
    }
    
    //fills the circle
    public void fillMe(Graphics2D g)
    {
	g.setColor(color);
	Ellipse2D.Double e = new Ellipse2D.Double(center.getX() - radius, 
						  center.getY()-radius, 2*radius,
						  2*radius);
	g.fill(e);
    }
    
    //method
    public String toString()
    {
	return "Circle [c= ("+center.getX()+","+center.getY()+")" 
	    +"rad= "+radius+", color= java.awt.Color["+
	    "r="+color.getRed()+
	    ",g="+color.getGreen()+
	    ",b="+color.getBlue()+"]";
    }
}
