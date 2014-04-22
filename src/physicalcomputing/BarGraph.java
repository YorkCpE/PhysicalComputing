package physicalcomputing;

import cc.arduino.Arduino;
import processing.core.PApplet;

public class BarGraph extends PApplet{

	private static final long serialVersionUID = -8532228231837622013L;

	Arduino arduino;
	int ledPin=13;
	public void setup()
	{
		size(800,600);
		
		String[] serialPorts = Arduino.list();
		
		String port = serialPorts[0];
		
		arduino = new Arduino(this, port, 57600);
		
		arduino.pinMode(ledPin, Arduino.OUTPUT);
	}
	
	public void draw()
	{
		background(255);
		fill(255,0,0);
		
		int analog0 = arduino.analogRead(0);
		int analog1 = arduino.analogRead(1);
		int analog2 = arduino.analogRead(2);
		int analog3 = arduino.analogRead(3);
		int analog4 = arduino.analogRead(4);
		int analog5 = arduino.analogRead(5);
		
		
		rect(10,10,analog0,10);
		rect(10,30,analog1,10);
		rect(10,50,analog2,10);
		rect(10,70,analog3,10);
		rect(10,90,analog4,10);
		rect(10,110,analog5,10);
		
		delay(10);
	}
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] {physicalcomputing.BarGraph.class.getName() });
	}

}
