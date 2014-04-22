package physicalcomputing;

import cc.arduino.Arduino;
import processing.core.PApplet;
import processing.core.PImage;

public class RainbowLED extends PApplet{

	private static final long serialVersionUID = -8532228231837622013L;

	Arduino arduino;
	PImage image;
	
	int redPin=3;
	int groundPin=4;
	int greenPin=5;
	int bluePin=6;
	
	public void setup()
	{
		image = loadImage("img/Scotland.jpg");
		
		size(image.width,image.height);
		
		String[] serialPorts = Arduino.list();
		
		String port = serialPorts[0];
		
		arduino = new Arduino(this, port, 57600);
		
		arduino.pinMode(redPin, Arduino.OUTPUT);
		arduino.pinMode(groundPin, Arduino.OUTPUT);
		arduino.pinMode(greenPin, Arduino.OUTPUT);
		arduino.pinMode(bluePin, Arduino.OUTPUT);
		
		arduino.analogWrite(redPin, 0);
		arduino.analogWrite(bluePin,0);
		arduino.analogWrite(greenPin,0);
		
		image(image, 0, 0);
	}
	
	public void draw()
	{
		if(mousePressed)
		{
			int color = image.get(mouseX, mouseY);
			
			//voodoo magic
			int r = (color >> 16) & 0xFF;  // Faster way of getting red(argb)
			int g = (color >> 8) & 0xFF;   // Faster way of getting green(argb)
			int b = color & 0xFF;          // Faster way of getting blue(argb)
			
			arduino.analogWrite(redPin, r);
			arduino.analogWrite(bluePin,b);
			arduino.analogWrite(greenPin,g);
		}
		
		delay(10);
	}	
	public static void main(String[] args)
	{
		PApplet.main(new String[] {physicalcomputing.RainbowLED.class.getName() });
	}

}
