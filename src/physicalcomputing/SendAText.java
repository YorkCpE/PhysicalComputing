package physicalcomputing;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import netP5.NetAddress;
import oscP5.OscArgument;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;


public class SendAText extends PApplet implements OscEventListener,ControlListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274157594990025653L;

	/*
	 * BEGIN PROCESSING SOURCE
	 */
	ControlP5 gui;
	OscP5 server;
	int listeningPort=9000;

	NetAddress broadcast = new NetAddress("255.255.255.255", listeningPort);
	
	String textMessage="";
	public void setup() 
	{
		size(800,600);
		background(0);
		textSize(10);
		
		server=new OscP5(this, listeningPort);
		
		gui = new ControlP5(this);
		gui.addTextfield("Send a Text")
		.setPosition(10, 10)
		.setSize(200, 20)
		.addListener(new ControlListener() 
		{
			
			public void controlEvent(ControlEvent event) 
			{
				textMessage=event.getStringValue();
				
				OscMessage message = new OscMessage("/textMessage");
				message.add(textMessage);
				OscP5.flush(message,broadcast);
			}
		});
	}

	long messageTime=-1;
	public void draw() 
	{	
		long timeBetweenMessages = System.currentTimeMillis()-messageTime;
		
		if(timeBetweenMessages>20000)
		{
			background(0);
		}
	}

	public void oscEvent(OscMessage message) 
	{
		//check to see if I'm heading myself send a message
		/*if(selfMessage(message)==true)
		{
			return;
		}*/

		//is this the type of message I want?
		if(message.checkAddrPattern("/textMessage")==true)
		{
			String textMessage = message.get(0).stringValue();
			
			text(textMessage, random(width), random(height));
			messageTime=System.currentTimeMillis();
		}
	}

	private boolean selfMessage(OscMessage message) 
	{
		return message.netAddress().address().compareToIgnoreCase(server.ip())==0;
	}

	public void oscStatus(OscStatus status) 
	{


	}

	/*
	 * END PROCESSING SOURCE
	 */
	public static void main(String _args[]) {
		PApplet.main(new String[] { physicalcomputing.SendAText.class.getName() });
	}

	public void controlEvent(ControlEvent arg0) {
		
		
	}
}
