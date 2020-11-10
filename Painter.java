import java.applet.Applet;
import java.util.* ;
import java.awt.*; 
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Vector;
public class Painter extends Applet {
	//Declaring buttons objects
	Button rectanglebtn;
	Button circlebtn;
	Button linebtn;
	Button redbtn;
	Button greenbtn;
	Button freebtn;
	Button bluebtn;
	Button clearbtn;
	Button erasebtn;
	Button undobtn;
	//Creating refrence from the parent class (Shape), so that if can refer to objects from any of the childern's types 
	//Childern being (Rectangle - Circle - Line - Free - Eraser)
	Shape shape;
	Checkbox fillcheckbox;
	//Creating a vector of elements of my user defined type Shape
	Vector<Shape> shapes = new Vector<Shape>();
	
	boolean Dragged = false;
	
	public final int RECTANGLE = 1;
	public final int CIRCLE = 2;
	public final int LINE = 3;
	public final int ERASE = 4;
	public final int FREE = 5;
	public final int RED = 1;
	public final int GREEN = 2;
	public final int BLUE = 3;
	public final int WHITE = 4;
	// flag for currently chosen shape
	int chosenshape = 3;
	// flag for currently chosen color
	int chosencolor=1;
	int colortemp;
	// flag for undo button clicked
	boolean undoflag = false;
	
	//all listeners are created inside of init function to be intialized instantly with the applet display
	public void init(){	
			//creating buttons and adding labels & colors to them
			rectanglebtn = new Button("Rectangle");
			circlebtn = new Button("Circle");
			linebtn = new Button("Line");
			freebtn = new Button("Free-hand");
			redbtn = new Button("Red");
			redbtn.setBackground(Color.red);
			greenbtn = new Button("Green");
			greenbtn.setBackground(Color.green);
			bluebtn = new Button("Blue");
			bluebtn.setBackground(Color.blue);
			clearbtn = new Button("Clear All");
			erasebtn = new Button("Eraser");
			undobtn =  new Button("Undo");	
			//Adding buttons to the applet window
			add(rectanglebtn);
			add(circlebtn);
			add(linebtn);
			add(freebtn);
			add(redbtn);
			add(greenbtn);
			add(bluebtn);
			add(clearbtn);
			add(erasebtn);
			add(undobtn);
			fillcheckbox = new Checkbox("Fill");
			add(fillcheckbox);
			
		//Adding Mouselistener as a local anonymous inner class	
		this.addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e){
				if(undoflag)
				undoflag = false;
				if(chosenshape != ERASE && chosencolor == WHITE)
				chosencolor= colortemp;
			//An object is created from the class of the chosen shape and the current location of the mouse is recorded as the start point of the shape
			//if the chosen shape is rectangle an object is created from class (Rectangle), same for circle, line, free-hand, or eraser
				switch (chosenshape){
					case RECTANGLE:
					shape = new Rectangle(e.getPoint());
					if(fillcheckbox.getState())
					shape.setFill(true);
					break;
					case CIRCLE:
					shape = new Circle(e.getPoint());
					if(fillcheckbox.getState())
					shape.setFill(true);
					break;
					case LINE:
					shape = new Line(e.getPoint());
					break;
					case ERASE:
					shape = new Eraser(e.getPoint());
					shape.setWidth(15);
					shape.setHeight(10);
					shape.setColor(ERASE);
					break;
					case FREE:
					shape = new Free(e.getPoint());
					break;
				}
				
			}
				
			
			public void mouseReleased(MouseEvent e){
				if(Dragged){
					shape.setEndPoint(e.getPoint());
					shapes.add(shape);
					Dragged =false;
					repaint();
					
				}
			}
			public void mouseClicked(MouseEvent e){
				if(chosenshape == ERASE){
				shape.setWidth(15);
				shape.setHeight(10);
				shapes.add(shape);
				shape = new Eraser(e.getPoint());
				repaint();
				}
			}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
		});
		//Adding MouseMotionlistener as a local anonymous inner class	
		this.addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e){}
			public void mouseDragged(MouseEvent e){
			
			switch (chosencolor){
					case RED:
					shape.setColor(RED);
					break;
					case GREEN:
					shape.setColor(GREEN);
					break;
					case BLUE:
					shape.setColor(BLUE);
					break;
					case WHITE:
					shape.setColor(WHITE);
					break;
					}
				
			
			switch(chosenshape){ 
			//In case of chosing the eraser option with each drag motion a new little with oval of fixed width and height is created, added to the (shapes) vector
			case ERASE:
				Dragged = true;
				shape.setWidth(20);
				shape.setHeight(10);
				shapes.add(shape);
				shape = new Eraser(e.getPoint());
				repaint();
				break;
			
			case RECTANGLE:
			//In case of chosing the rectangle, the current coordinates of the mouse are recorded as the end point of the current rectangle
				Dragged = true;
				shape.setEndPoint(e.getPoint());
				repaint();
				break;
			//In case of chosing the circle, the current coordinates of the mouse are recorded as the end point of the current circle
			case CIRCLE:
				Dragged = true;
				shape.setEndPoint(e.getPoint());
				repaint();
				break;
			//In case of chosing the line, the current coordinates of the mouse are recorded as the end point of the current line
			case LINE:
				Dragged = true;
				shape.setEndPoint(e.getPoint());
				repaint();
				break;
			case FREE:
			//In case of chosing the free-hand option, the current coordinates of the mouse are recorded 
			//as the end point of the current free-hand object and the object is added to the vector then 
			//a new free-hand object is created with a start point at the end point of the last free-hand object
				Dragged = true;
				shape.setEndPoint(e.getPoint());
				shapes.add(shape);
				shape = new Free(e.getPoint());
				repaint();
				break;
				
			}
			
			}
			
		});
		
		//All Buttons' listeners
		rectanglebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosenshape = RECTANGLE;
			}});
		circlebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosenshape = CIRCLE;
			}});
		linebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosenshape = LINE;
			}});
			
		redbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosencolor = RED;
			}});
		greenbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosencolor = GREEN;
			}});
		bluebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosencolor = BLUE;
			}});
		clearbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			shapes.clear();
			shape = null;
			repaint();
			}});
		erasebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosenshape = ERASE;
			colortemp = chosencolor;
			chosencolor = WHITE;
			}});
		freebtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			chosenshape = FREE;
			}});
		undobtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			if(!shapes.isEmpty()){
			undoflag = true;
			shapes.removeElementAt(shapes.size()-1);
			repaint();
			}
			}});
			
			
	}
	// The paint method, where all the magic happens
	public void paint(Graphics g){

		
				//A for loop to go over the shapes saved in the vector and draw them one by one
				for(int i=0; i<shapes.size();i++){
					//First we check for the color of the object by invoking the getColor method of this object
					switch(shapes.get(i).getColor()){
							case RED:
							g.setColor(Color.RED);
							break;
							case GREEN:
							g.setColor(Color.GREEN);
							break;
							case BLUE:
							g.setColor(Color.BLUE);
							break;
							case WHITE:
							g.setColor(Color.WHITE);
							break;
							
						}
					//Secondly we check for the type of the object by invoking the getType method of this object
					switch (shapes.get(i).getType()){
					case RECTANGLE:
					//Thirdly we check for the fill-or-not-state of the object by invoking the getFill method of this object that returns a boolean value
					if(!shapes.get(i).getFill())
					g.drawRect(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getWidth(),shapes.get(i).getHeight());
					else
					g.fillRect(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getWidth(),shapes.get(i).getHeight());
					break;
					case CIRCLE:
					if(!shapes.get(i).getFill())
					g.drawOval(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getWidth(),shapes.get(i).getHeight());
					else
					g.fillOval(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getWidth(),shapes.get(i).getHeight());
					break;
					case LINE:
					g.drawLine(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getEndPoint().x,shapes.get(i).getEndPoint().y);
					break;
					case ERASE:
					g.fillOval(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getWidth(),shapes.get(i).getHeight());
					break;
					case FREE:
					if(shapes.get(i).getEndPoint() != null)
					g.drawLine(shapes.get(i).getStartPoint().x,shapes.get(i).getStartPoint().y,shapes.get(i).getEndPoint().x,shapes.get(i).getEndPoint().y);
					break;
					}
				}
				//we repeat commands from the for loop but for the current shape being drawn because it's not saved to the vector yet
				//if we removed this piece of code the only change in the behaviour is that 
				//(the shape will only appears after the user releases the mouse, it will not be painted while the user is dragging the mouse)
				if(shape != null){
				switch(shape.getColor()){
							case RED:
							g.setColor(Color.RED);
							break;
							case GREEN:
							g.setColor(Color.GREEN);
							break;
							case BLUE:
							g.setColor(Color.BLUE);
							break;
							case WHITE:
							g.setColor(Color.WHITE);
							break;
							
						}
			if(shape != null){			
			if(!undoflag){
			switch (chosenshape){
					case RECTANGLE:
					if(!shape.getFill())
					g.drawRect(shape.getStartPoint().x,shape.getStartPoint().y,shape.getWidth(),shape.getHeight());
					else
					g.fillRect(shape.getStartPoint().x,shape.getStartPoint().y,shape.getWidth(),shape.getHeight());	
					break;
					case CIRCLE:
					if(!shape.getFill())
					g.drawOval(shape.getStartPoint().x,shape.getStartPoint().y,shape.getWidth(),shape.getHeight());
					else
					g.fillOval(shape.getStartPoint().x,shape.getStartPoint().y,shape.getWidth(),shape.getHeight());
					break;
					case LINE:
					g.drawLine(shape.getStartPoint().x,shape.getStartPoint().y,shape.getEndPoint().x,shape.getEndPoint().y);
					break;
					case ERASE:
					g.fillOval(shape.getStartPoint().x,shape.getStartPoint().y,shape.getWidth(),shape.getHeight());
					break;
					case FREE:
					if(shape.getEndPoint() != null)
					g.drawLine(shape.getStartPoint().x, shape.getStartPoint().y,shape.getEndPoint().x,shape.getEndPoint().y);
					break;			
				}
				}
				}
			
			
		}
		
		
	}
}

//The parent class
class Shape{
	public final int RECTANGLE = 1;
	public final int CIRCLE = 2;
	public final int LINE = 3;
	public final int ERASE = 4;
	public final int FREE = 5;
	public final int RED = 1;
	public final int GREEN = 2;
	public final int BLUE = 3;
	public final int WHITE = 4;
	boolean fill;
	protected int color;
	protected int type;
	protected Point startPoint, endPoint;
	protected int width;
	protected int height;
	public Shape(){
		startPoint = null;
		endPoint = null;
		fill = false;
	}
	public Shape(Point s){
		startPoint=s;
		fill = false;
	}
	
	public Shape(Point s, Point e){
		startPoint=s;
		endPoint=e;
		fill = false;
	}
	
	public void setFill(boolean f){
		fill=f;
	}
	public boolean getFill(){
		return fill;
	}
	
	public void setStartPoint(Point p){
		startPoint = p;
	}
	public void setEndPoint(Point p){
		endPoint = p;
	}
	public Point getStartPoint(){
		return startPoint;
	}
	public Point getEndPoint(){
		return endPoint;
	}
	public void setType(int t){
		type=t;
	}
	public int getType(){
		return type;
	}
	public void setColor(int c){
		color=c;
	}
	public int getColor(){
		return color;
	}
	public int getWidth(){
		width = endPoint.x - startPoint.x ;
		return width;
	}
	public int getHeight(){
		height = endPoint.y - startPoint.y ;
		return height;
	}
	public void setWidth(int w){
		width = w;
	}
	public void setHeight(int h){
		height = h;
	}
	
}

//The children classes who extends class (Shape)
class Rectangle extends Shape{
	
	
	public Rectangle(){
		super.startPoint = null;
		super.endPoint = null;
		super.type = RECTANGLE;
	}
	public Rectangle(Point s){
		super.startPoint=s;
		super.type = RECTANGLE;
	}
	public Rectangle(Point s, Point e){
		super.startPoint=s;
		super.endPoint=e;
		super.type = RECTANGLE;
	}
	
}

class Circle extends Shape{
	
	
	public Circle(){
		super.startPoint = null;
		super.endPoint = null;
		super.type = CIRCLE;
	}
	public Circle(Point s){
		super.startPoint=s;
		super.type = CIRCLE;
	}
	public Circle(Point s, Point e){
		super.startPoint=s;
		super.endPoint=e;
		super.type = CIRCLE;
	}	
}
class Line extends Shape{
	
	
	public Line(){
		super.startPoint = null;
		super.endPoint = null;
		super.type = LINE;
	}
	public Line(Point s){
		super.startPoint=s;
		super.type = LINE;
	}
	public Line(Point s, Point e){
		super.startPoint=s;
		super.endPoint=e;
		super.type = LINE;
	}	
}
class Eraser extends Shape{
	
	
	public Eraser(){
		super.startPoint = null;
		super.endPoint = null;
		super.type = ERASE;
	}
	public Eraser(Point s){
		super.startPoint=s;
		super.type = ERASE;
	}
	public Eraser(Point s, Point e){
		super.startPoint=s;
		super.endPoint=e;
		super.type = ERASE;
	}	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
class Free extends Shape{
	
	
	public Free(){
		super.startPoint = null;
		super.endPoint = null;
		super.type = FREE;
	}
	public Free(Point s){
		super.startPoint=s;
		super.type = FREE;
	}
	public Free(Point s, Point e){
		super.startPoint=s;
		super.endPoint=e;
		super.type = FREE;
	}	
}


