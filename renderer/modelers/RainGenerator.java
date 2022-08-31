package renderer.modelers;

import java.util.Random;

public class RainGenerator extends Thread {

	private int[][] pointer;
	Random rando = null;
	private int width;
	private int height;
	
	public RainGenerator(int[][] pointer, int width, int height)
	{
		this.pointer = pointer;
		this.width = width;
		this.height = height;
		rando = new Random(System.currentTimeMillis());
	}
	
	public void run()
	{
		int it = 1;
		while(true)
			
		try
		{
			Rain.addRain(pointer,Math.abs(rando.nextInt()) % width,Math.abs(rando.nextInt()) % height);
			
			it++;
			sleep(10);
			if (it % 5 == 0)
				sleep(rando.nextInt(1000));
			
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}
