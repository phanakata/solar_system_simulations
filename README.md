# solar_system_simulations 

This code simulates a two dimensional solar system that contains  a sun, planets, and other small objects such as asteroids or moons. All objects are governed by gravitational force  (~ 1/r^2). This simulation program allows user to observe how planets interact with each other, and how they revolve around the sun (massive objects) in a circular or elliptical path, as it has physically observed by astronomers. In addition, this program allows user to control the simulation objects. For instance, user can choose number of gravitational objects to be simulated, initial condition, and so on. Note that, in this simulation we will be using reduced units. For example, 5 unit distance in the animation does not represent 5 meter.  This solar system simulation has four codes:

* Graviation.java, a class that defines gravitational objects. Velocities and positions of these objects will be important quantities in describing their motion, and we assign these values to the fields. All gravitational objects interact via Newton's law of universal gravitation. We use velocity verlet algorithm to update their position and velocity. Lastly, these class has useful getters to obtain physical quantities like energy, velocity or position of a planet.

* ColorCircle.java, a class that defines circle object which constructed by size (radius) and color. We use this class in order to creates colorful circles with various radius representing the gravitational objects. For instance, the sun will be represented as big orange circle, and the asteroids or moons  will be represented as black small circles.

* GravitationComponent.java, a class that extends JFframe which will allow user to draw and keep redrawing our solar system in the JFrame.

* GraviationAnimation.java, contains a main method that ask user how many gravitational objects to be simulated. This class also has inner class Time allowing us to update the location of the gravitational objects with some time interval. 

Note:
This simulation program was part of my computer science class project back in 2012.  
