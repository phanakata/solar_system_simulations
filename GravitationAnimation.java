/*
  phanakata: This program silumates gravitational objtects. This simulation program 
  allows user  to observe how planets interact with each other, and how they revolve 
  around the sun (massive objects) in a circular or elliptical path, as it has 
  physically observed by astronomers. In addition, this program allows the user to 
  control the simuation objects. For instance, user can choose number of gravitational 
  objects to be simulated, initial condition, and so on. Moreover, this program has 
  functional JButtons which allows user to change the gravitational strength or the
  mass of the sun. JPanel also shows the average kinteic energy for some reasonable time 
  interval. Note that, in this simulation we will be using reduced units. This program 
  has public constants (int FRAME_WIDTH, int FRAME_HEIGHT) so that other classes can 
  access these value in oder to create graphical representation of gravitational objects
  that are shown in the JFrame. 
*/

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class GravitationAnimation{
    //JFrame size, set them to be be final and public
    //so can be accessed from other classes
    public static final int FRAME_WIDTH  = 800;
    public static final int FRAME_HEIGHT = 800;
    
    public static void main(String[] args)
    {
	String object = JOptionPane.showInputDialog("Enter number of planets >3:");
	String config = JOptionPane.showInputDialog("Straight linear or spiral configuration?\nPlease type linear or spiral:");
	JOptionPane.showMessageDialog(null, "Number of gravitational objects: "+object+"\nConfiguration :"+config);
	int N = Integer.parseInt(object);
	//create gravitational objects
	final Gravitation solar = new Gravitation(N, config);
	
	//send this objects to Component file 
	final GravitationComponent gc = new GravitationComponent(solar);
	
	//opens JFrame
	JFrame frame  = new JFrame();
	JFrame frame2 = new JFrame();
	
	//set the JFame slightly bigger to give some extra space
	frame.setSize(FRAME_WIDTH+10,FRAME_HEIGHT+15);
	frame.setTitle("Gravitation Animation");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//keep frame2 off of frame
	frame2.setBounds(FRAME_WIDTH+30,0,280,200);
	
	//create button to trigger additon of speed
	JButton button = new JButton("Add gravity");
	JButton button2 = new JButton("Add mass of the Sun");
	
	final JLabel label  = new JLabel("G:  "+solar.getG());
	final JLabel label1 = new JLabel("Kinetic Energy: "+solar.kineticEnergy());
	final JLabel label2 = new JLabel("Mass of the Sun (reduced): "+solar.getMass());
	
	JPanel panel = new JPanel ();
	
	//add buttons and labels to JPanel
	panel.add(button);
	panel.add(label);
	panel.add(label1);
	panel.add(button2);
	panel.add(label2);
	frame2.add(panel);
	
	//add the gravitationComponent to the frame 
	frame.add(gc);
	frame.setVisible(true);
	
	class Elistener implements ActionListener{
	    int k =0;
	    double accumEk =0;
	    public void actionPerformed(ActionEvent e) {
		solar.move();
		gc.repaint();
		
		accumEk += solar.kineticEnergy();
		//print average kinetic Energy every 20 updates 
		if(k%20==0)
		    {
			label1.setText("Kinetic Energy: "+accumEk/(double)20);
			accumEk = 0;
		    }
		k++;
	    }
	}
	
	Elistener l = new Elistener();
	javax.swing.Timer t = new javax.swing.Timer(100, l);
	t.start();
	
	class AddGlistener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		solar.setG();
		label.setText("G:  "+solar.getG());
		label1.setText("Kinetic Energy: "+solar.kineticEnergy());
		label2.setText("Mass of the Sun (reduced): "+solar.getMass());
	    }
	}
	
	class AddMasslistener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
		solar.addMass();
		label.setText("G:  "+solar.getG());
		label1.setText("Kinetic Energy: "+solar.kineticEnergy());
		label2.setText("Mass of the Sun: "+solar.getMass());
	    }
	}
	
	
	ActionListener listener = new AddGlistener();
	ActionListener listener2 = new AddMasslistener();
	
	button.addActionListener(listener);
	button2.addActionListener(listener2);
	frame2.setVisible(true);
	
    }
}

























