package physicalcomputing;

import netP5.NetAddress;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;
import processing.core.PImage;

public class Paint extends PApplet implements OscEventListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 406104196010534397L;

	final int listeningPort=9000;

	PImage image;

	NetAddress everyone = new NetAddress("255.255.255.255", listeningPort);

	String computerName="netjason";
	NetAddress friendsComputer = new NetAddress(computerName, listeningPort);
	
	NetAddress destination=everyone;
	public void setup()
	{
		//set the screen size
		size(800,600);

		//change the path here to load a different image
		image = loadImage("img/Mona_Lisa.jpg");

		//make the image fit the screen
		image.resize(width, height);

		//draw the image on the screen
		image(image,0,0);
	}

	int brushSize=5;
	int MAX_BRUSH_SIZE=5;
	public void draw()
	{	
		image(image,0,0);
		
		ellipse(mouseX,mouseY,brushSize,brushSize);
	}

	public void mouseMoved()
	{
		sendPixelsInAreaAround(mouseX, mouseY, brushSize);
	}

	private void sendPixelsInAreaAround(int x, int y, int squareSize) 
	{
		if(squareSize>MAX_BRUSH_SIZE)
		{
			squareSize=MAX_BRUSH_SIZE;
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

		OscP5.flush(oscMessage, destination);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] {physicalcomputing.Paint.class.getName() });
	}

	public void oscEvent(OscMessage arg0) {
		// TODO Auto-generated method stub
		
	}

	public void oscStatus(OscStatus arg0) {
		// TODO Auto-generated method stub
		
	}
}
