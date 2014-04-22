package physicalcomputing;

import cc.arduino.Arduino;
import processing.core.PApplet;

public class BlinkLED extends PApplet{

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
		arduino.digitalWrite(ledPin, Arduino.HIGH);
		delay(100);
		arduino.digitalWrite(ledPin, Arduino.LOW);
		delay(100);
	}
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] {physicalcomputing.BlinkLED.class.getName() });
	}

}
