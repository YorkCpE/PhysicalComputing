package physicalcomputing;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import netP5.NetAddress;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;
import processing.core.PImage;

public class PaintAndCanvas extends PApplet implements OscEventListener,ControlListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 406104196010534397L;


	OscP5 server;
	ControlP5 gui;
	final int listeningPort=9000;

	PImage image;

	boolean listener;

	boolean painter;

	String friendComputerName="netjason";
	NetAddress friendComputer = new NetAddress(friendComputerName, listeningPort);
	NetAddress everyone = new NetAddress("255.255.255.255",listeningPort);
	NetAddress destination=everyone;

	public void setup()
	{
		//set the screen size
		size(800,600);

		//start up our network server
		server = new OscP5(this, listeningPort);

		//change the path here to load a different image
		image = loadImage("img/Mona_Lisa.jpg");

		//make the image fit the screen
		image.resize(width, height);

		//draw the image on the screen
		image(image,0,0);

		//by default we're listening and painting
		listener=true;
		painter=true;

		//don't draw an outline around our brush
		noStroke();
	}

	int brushSize=5;
	public void draw()
	{	
		if(keyPressed) //check to see if a key is pressed
		{
			if(key=='l')
			{
				listener=!listener;
				
				if(listener==true)
				{
					println("Listening");
				}
				else
				{
					println("Not Listening");
				}
			}
			else if(key=='p')
			{
				painter=!painter;
				if(painter==true)
				{
					println("Painting");
				}
				else
				{
					println("Not Painting");
				}
			}
			else if(key=='c')
			{
				background(0);
			}
		}



		text("My IP: "+server.ip(),10,(float) (height*.8));
	}

	public void mouseMoved()
	{
		if(painter==true)
		{
			sendPixelsInAreaAround(mouseX, mouseY, brushSize);
		}
	}

	private void sendPixelsInAreaAround(int x, int y, int squareSize) 
	{
		if(squareSize>5)
		{
			squareSize=5;
		}

		String messageString="";

		for(int i=0;i<squareSize;i++)
		{
			for(int j=0;j<squareSize;j++)
			{
				int color = image.get(x+i, y+j);

				//extra the alpha, red, green, and blue from the color;
				int a = (color >> 24) & 0xFF;
				int r = (color >> 16) & 0xFF;  // Faster way of getting red(argb)
				int g = (color >> 8) & 0xFF;   // Faster way of getting green(argb)
				int b = color & 0xFF;          // Faster way of getting blue(argb)

				//voodoo magic
				messageString+=(x+i)+","+(y+j)+","+a+","+r+","+g+","+b+",";
			}
		}

		OscMessage oscMessage = new OscMessage("/paintByClicks");
		oscMessage.add(messageString);

		server.send(oscMessage, destination);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {physicalcomputing.PaintAndCanvas.class.getName() });
	}

	public void oscEvent(OscMessage theMessage) 
	{

		//check to see if I'm heading myself send a message
		/*if(selfMessage(theMessage)==true)
		{
			return;
		}*/

		if(listener==false)
		{
			return;
		}
		
		if(theMessage.checkAddrPattern("/paintByClicks")==true)
		{
			String message = theMessage.get(0).stringValue();

			String[] splits=message.split(",");

			for(int i=0;i<splits.length;i+=6)
			{
				int x = Integer.valueOf(splits[i+0]);
				int y = Integer.valueOf(splits[i+1]);
				int a = Integer.valueOf(splits[i+2]);
				int r = Integer.valueOf(splits[i+3]);
				int g = Integer.valueOf(splits[i+4]);
				int b = Integer.valueOf(splits[i+4]);

				//fill(r, g, b, a);
				fill(255,255,255,255);
				rect(x,y,1,1);
			}
		}

	}

	private boolean selfMessage(OscMessage message) 
	{
		return message.netAddress().address().compareToIgnoreCase(server.ip())==0;
	}

	public void oscStatus(OscStatus theStatus) {
		// TODO Auto-generated method stub

	}

	public void controlEvent(ControlEvent arg0) {
		// TODO Auto-generated method stub

	}

}
