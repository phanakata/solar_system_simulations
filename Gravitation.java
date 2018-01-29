/*
  phanakata: a class that defines gravitational objects. Velocities and 
  positions of these objects will be important quantities in describing 
  their motion, and we assign these values to the fields. All gravitational
  objects interact via Newton's law of universal gravitation. We use velocity
  verlet algorithm to update their position and velocity. Lastly, these class 
  has useful getters to obtain physical quantities like energy, velocity or
  position of a planet.
*/

import java.util.*;
public class Gravitation{
    
    public static final int NDIM = 2;
    public static final double RMIN = 1.;
    //fields
    private double pos[][];
    private double vel[][];
    private double force[][];
    private double mass[];
    private double radius[];
    private int N;
    private double max;
    private double G; 
    Random generator = new Random();
    
    //default constructor
    public Gravitation()
    {
	N    = 4;  
	G = 19.94*10;
	double[]   mass   = new double[N];
	double[]   radius = new double[N];
	double[][] pos    = new double[N][NDIM];
	double[][] vel    = new double[N][NDIM];
	double[][] force  = new double[N][NDIM];
	
	//set up initail condition
	initMassRadius();
	initPos();
	initVel();
	centerMass();
    }
    
    //constructor
    public Gravitation(int numberMass, String config)
    {
	N = numberMass;
	G = 19.94*100;
	pos    = new double[N][NDIM];
	vel    = new double[N][NDIM];
	force  = new double[N][NDIM];
	mass   = new double[N];
	radius = new double[N];
	
	//intialize mass and radius
	initMassRadius();
	
	//initialize position and velocity depending 
	//on the user's choice
	if(config.charAt(0)=='l')
	    {
		initPos();
		initVel();
	    }
	else 
	    {
		initPosSpiral();
		initVelSpiral(); 
	    }
	
	//shift position and velocity to center of mass 
	centerMass();
    }
    
    //intial condition
    public void initMassRadius()
    {
	mass[0] = N*N*N*N; //roughly the Sun
	mass[1] = 1.;  //earth
	radius[0] = 4.;
	radius[1] =1.;
	   
	if(N>2)
	    {
		//planets
		for(int i=2; i<N/2; i++)
		    {
			mass[i]   = (1. + (double)(generator.nextInt(N-2)))*4./(double)N;
			radius[i] = (1. + (double)(generator.nextInt(N-2)))*2./(double)N;
		    }
		
		//for moons or asteroids
		for(int i=N/2; i<N; i++)
		    {
			mass[i] =.01*mass[1];
			radius[i] = Math.pow((3./(4.*Math.PI)), 1./3.);
		    }
	    }
	
    }

    //intial condition for a linear configuration
    public void initPos()
    {
	double theta = 0;
	//set the sun at the center
	pos[0][0] = 0.;
	pos[0][1] = 0.;
	
	for(int i = 1; i<N/2; i++)
	    {
		pos[i][0] = 20.*radius[0] + (2.*radius[i] + (double)(i*i)*radius[0]) * Math.cos(theta*(double)i);
		pos[i][1] = 0.;
	    }
	max = pos[N/2-1][0];
	
	for(int i = N/2; i<N; i++)
	    {
		pos[i][0] = pos[i-N/2 +1][0];
		pos[i][1] = 2.*radius[i-N/2+1];
	    }
    }

    public void initPosSpiral()
    {
	//create masses circling an origin 
	double theta = 2*Math.PI/(double)((N-1)/2);
	//set the sun at the center
	pos[0][0] = 0.;
	pos[0][1] = 0.;
	for(int i = 1; i<N/2; i++)
	    {
		pos[i][0] = (radius[i]*5.+ 10.*(double)i*radius[0]) * Math.cos(theta*(double)(i-1));
		pos[i][1] = (radius[i]*5.+ 10.*(double)i*radius[0]) * Math.sin(theta*(double)(i-1));
	    }

	max = pos[N/2-1][0];
	for(int i = N/2; i<N; i++)
	    {
		pos[i][0] = pos[i-N/2 +1][0];
		pos[i][1] = pos[i-N/2 +1][1] + 2.*radius[i-N/2+1];
	    }
    }

    
    //intial condition
    public void initVel()
    {
	//index of the mass and dimension
	int i, dim;
	
	vel[0][0] = 0.;
	vel[0][1] = 0.;
	for(i = 1 ; i<N/2; i++)
	    {
		double distance = Math.sqrt(pos[i][0]*pos[i][0] + pos[i][1]*pos[i][1]);
		
		//randomnize the angular momentum (direction of the revolution)
		int sign = generator.nextInt(10);
		if(sign<1)
		    sign =-1;
		else 
		    sign =1;

		double theta = Math.PI/2.;
		vel[i][0] = Math.sqrt(G*mass[0]/distance)*Math.cos(theta);
		vel[i][1] = sign*Math.sqrt(G*mass[0]/distance)*Math.sin(theta);
	    }
	
	for(i = N/2 ; i<N; i++)
	    {
		double distance = 2.*radius[i-N/2+1];
		double theta = 0.;
		
		vel[i][0] = Math.sqrt(G*mass[i-N/2+1]/distance)*Math.cos(theta);
		vel[i][1] = vel[i-N/2+1][1];
	    }
    }
    
    //intial condition
    public void initVelSpiral()
    {
	//index of the mass and dimension
	int i, dim;
	
	vel[0][0] = 0.;
	vel[0][1] = 0.;
	
	for(i = 1 ; i<N/2; i++)
	    {
	
		double distance = Math.sqrt(pos[i][0]*pos[i][0] + pos[i][1]*pos[i][1]);
		int sign = generator.nextInt(10);
		
		if(sign<1)
		    sign =-1;
		else 
		    sign =1;
		
		double theta = 2.*Math.PI/(double)((N-1)/2);
		
		vel[i][0] = Math.sqrt(G*mass[0]/distance)*Math.sin(theta*(double)(i-1));
		vel[i][1] = sign*Math.sqrt(G*mass[0]/distance)*Math.cos(theta*(double)(i-1));
	    }
	
	for(i = N/2 ; i<N; i++)
	    {
	
		double distance = 2.*radius[i-N/2+1];
		
		double theta = 2.*Math.PI/(double)((N-1)/2);
		
		vel[i][0] = vel[i-N/2+1][0] + Math.sqrt(G*mass[i]/distance)*Math.sin(theta*(double)(i-N/2));
		vel[i][1] = vel[i-N/2+1][1] +  Math.sqrt(G*mass[i]/distance)*Math.cos(theta*(double)(i-N/2));
	    }
	
	
    }
    
    //this method change the position and velocity relative to center of mass
    public void centerMass()
    {
	double totalMass;
	double [] posCM = new double [NDIM];
	double [] velCM = new double [NDIM];
	int i, dim;
	
	totalMass = 0;
	for(dim =0; dim<NDIM; dim++)
	    {
		posCM[dim] = 0;
		velCM[dim] = 0;
	    }
	    
	for(i =0; i<N; i++)
	    {
		totalMass += mass[i];
	    }
	
	for(i=0; i<N; i++)
	    {
		for(dim = 0; dim<NDIM; dim++)
		    {
			posCM[dim] += mass[i]*pos[i][dim]/totalMass;
			velCM[dim] += mass[i]*pos[i][dim]/totalMass;
		    }
	    }

	for(i=0; i<N; i++)
	    {
		for(dim = 0; dim<NDIM; dim++)
		    {
			pos[i][dim] -= posCM[dim];
			vel[i][dim] -= velCM[dim];			
		    }
	    }

    }

    
    public double getMax()
    {
	return max;
    }
    
    public double getG()
    {
	return G/100;
    }
    
    public int getNumberObject()
    {
	return N;
    }

    public double getPos(int i, int dim)
    {
	return pos[i][dim];
    }
    
    public double getRadius(int i)
    {
	return radius[i];
    }
    
    //calculate total kinetic Energy 1/2 m (v dot v) 
    public double kineticEnergy()
    {
	int i, dim;
	double kinetic = 0.;
	for(i=0; i<N; i++)
	    {
		for(dim=0; dim<NDIM; dim++)
		    {
			kinetic += mass[i]*vel[i][dim]*vel[i][dim];
		    }
	    }
	//float kin = (float)Math.round(kinetic);
	return kinetic/2.; 
    }
    
    //get mass of the Sun
    public double getMass()
    {
	return mass[0]/((double)(N*N*N*N));
    }
    

    //mutators 
    

    //to change the strength of the gravity 
    public void setG()
    {
	G += 100; 
    }
    

    //to add mass of the sun
    public void addMass()
    {
	mass[0] += .05*(double)(N*N*N*N);
    }
    
    /* To update position and velocity. Veloicty Verlet alhorith is choosen 
       because it preserve the total energy */
    
    public void move()
    {
	int i,j,k,t;
	double rij;
	double dt = 0.01;
	
	for(i=0; i<N; i++)
	    {
		force[i][0]=0.0;
		force[i][1]=0.0;
	    }
	
	for(i=0; i<N-1; i++)
	    {
		for(j=i+1; j<N; j++)
		    {
			/*Calculating Force*/
			rij=Math.sqrt((pos[i][0]-pos[j][0])*(pos[i][0]-pos[j][0])+((pos[i][1]-pos[j][1])*(pos[i][1]-pos[j][1])));
			
			if(rij >RMIN)
			    {
				/* x direction*/
				force[i][0] += - G * mass[i]*mass[j] * (pos[i][0]-pos[j][0]) / (rij*rij*rij); 
				force[j][0] -= - G * mass[i]*mass[j] * (pos[i][0]-pos[j][0]) / (rij*rij*rij);
				
				/* y direction*/
				force[i][1] += - G * mass[i]*mass[j] * (pos[i][1]-pos[j][1]) / (rij*rij*rij); 
				force[j][1] -= - G * mass[i]*mass[j] * (pos[i][1]-pos[j][1]) / (rij*rij*rij);
			    }
			
			
			/*Case when rij<=RMIN*/
			
			else
			    {
				/* x direction */
				force[i][0] += 0.0; 
				force[j][0] -= 0.0;
				
				/* y direction */
				force[i][1] += 0.0; 
				force[j][1] -= 0.0;
			    }
		    }
	    }
	
	//updating psoition and half step of velocity
	for(i=0; i<N; i++)
	    {
		pos[i][0] = pos[i][0] + dt * vel[i][0] + .5 * dt * dt * (force[i][0]/mass[i]);
		vel[i][0] = vel[i][0] + .5 * dt * (force[i][0]/mass[i]);
		
		pos[i][1] = pos[i][1] + dt * vel[i][1] + .5 * dt * dt * (force[i][1]/mass[i]);
		vel[i][1] = vel[i][1] + .5 * dt * (force[i][1]/mass[i]);
	    }  
	
	for(i=0; i<N; i++)
	    {
		force[i][0]=0.0;
		force[i][1]=0.0;
	    }
	for(i=0; i<N-1; i++)
		{
		    for(j=i+1; j<N; j++)
			{
			    /*Calculating Force FROM r[i][k] t + dt*/
			    rij=Math.sqrt((pos[i][0]-pos[j][0])*(pos[i][0]-pos[j][0])+((pos[i][1]-pos[j][1])*(pos[i][1]-pos[j][1])));
			    if(rij> RMIN)
				{
				    /* x direction*/
				    force[i][0] += - G * mass[i]*mass[j] * (pos[i][0]-pos[j][0]) / (rij*rij*rij); 
				    force[j][0] -= - G * mass[i]*mass[j] * (pos[i][0]-pos[j][0]) / (rij*rij*rij);
				    
				    /* y direction*/
				    force[i][1] += - G * mass[i]*mass[j] * (pos[i][1]-pos[j][1]) / (rij*rij*rij); 
				    force[j][1] -= - G * mass[i]*mass[j] * (pos[i][1]-pos[j][1]) / (rij*rij*rij);
				}
			    
			    
			    /*Case when rij<=RMIN*/
			    else
				{
				    /* x direction */
				    force[i][0] += 0.0; 
				    force[j][0] -= 0.0;
				    
				    /* y direction */
				    force[i][1] += 0.0; 
				    force[j][1] -= 0.0;
				}
			}
		}
	
	/*We just need update velocity*/ 
	for(i=0; i<N; i++)
	    {
		vel[i][0] = vel[i][0] + .5 * dt * (force[i][0]/mass[i]);
		vel[i][1] = vel[i][1] + .5 * dt * (force[i][1]/mass[i]);
	    }
	
    }
}
