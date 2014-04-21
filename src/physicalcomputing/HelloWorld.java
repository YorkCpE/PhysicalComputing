package physicalcomputing;

import netP5.NetAddress;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;


public class HelloWorld extends PApplet implements OscEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274157594990025653L;

	/*
	 * BEGIN PROCESSING SOURCE
	 */
	OscP5 server;
	int listeningPort=9000;

	public void setup() 
	{
		size(800,600);
		background(0);
		textSize(10);
		server=new OscP5(this, listeningPort);
	}

	public void draw() 
	{
		if(mousePressed)
		{
				NetAddress broadcast = new NetAddress("255.255.255.255", listeningPort);
				OscMessage message = new OscMessage("/helloWorld");
			
				OscP5.flush(message, broadcast);
		}
	}

	public void oscEvent(OscMessage message) 
	{
		if(message.checkAddrPattern("/helloWorld")==true)
		{
			text("Hello World!",width/2,height/2);
		}
	}

	public void oscStatus(OscStatus status) 
	{
	
		
	}
	
	/*
	 * END PROCESSING SOURCE
	 */
	public static void main(String _args[]) {
		PApplet.main(new String[] { physicalcomputing.HelloWorld.class.getName() });
	}
}
