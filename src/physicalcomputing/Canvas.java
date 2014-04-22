package physicalcomputing;

import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;
import processing.core.PImage;

public class Canvas extends PApplet implements OscEventListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 406104196010534397L;

	OscP5 server;
	final int listeningPort=9000;

	PImage image;

	private boolean listener;

	public void setup()
	{
		//set the screen size
		size(800,600);

		//make a blank white background
		background(255);
		
		//start up our network server
		server = new OscP5(this, listeningPort);

		//don't draw an outline around our 	
		noStroke();
		
		//enable listening
		listener=true;
	}

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
			if(key=='c')
			{
				background(255);
			}
		}

		fill(255);
		text("My IP: "+server.ip(),10,(float) (height*.8));
	}

	public void oscEvent(OscMessage theMessage) 
	{

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
				int b = Integer.valueOf(splits[i+5]);

				fill(r, g, b, a);
				rect(x,y,1,1);
			}
		}

	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {physicalcomputing.Canvas.class.getName() });
	}

	public void oscStatus(OscStatus arg0) {}
}
