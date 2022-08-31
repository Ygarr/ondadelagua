package renderer.modelers;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.PixelGrabber;

import renderer.Modeler;
import renderer.Renderer;

public class Rain implements Modeler, MouseMotionListener {

	
	
	public static final int PERTURBATION = 200;
	int[][] oldState ;
	int[][] swapPointer ;
	int[][] currentState;
	static Renderer renderer = null;
	private static int texture[] ;
	
	public Rain(Renderer renderer)
	{
		this.renderer = renderer;
		oldState = new int[Renderer.renderedWidth][Renderer.renderedHeight];
		currentState = new int[Renderer.renderedWidth][Renderer.renderedHeight];
		texture = new int[Renderer.renderedWidth*Renderer.renderedHeight];
		Image textureImage = renderer.getToolkit().getImage(this.getClass().getResource("/background2.png")); 
		
		PixelGrabber pixelgrabber = new PixelGrabber(textureImage, 0, 0,Renderer.renderedWidth, Renderer.renderedHeight, texture, 0, Renderer.renderedWidth);
        try
        {
            pixelgrabber.grabPixels();
        }
        catch(InterruptedException interruptedexception) { 
        	interruptedexception.printStackTrace(System.out);
        	
        }
        
		
		renderer.addMouseMotionListener(this);
        RainGenerator gen = new RainGenerator(currentState,640,480);
        gen.start();
	}
	
	int Xoffset = 0;
	int Yoffset = 0;
	int Shading = 0;
	
	
	public void drawOffScreen() {
		
			for (int y = 1 ; y < Renderer.renderedHeight - 1 ; y++)
				for (int x = 1 ; x < Renderer.renderedWidth - 1 ; x++)
				{
					
					// This piece of code calculate the height of every point in the
					// raster
					currentState[x][y] = (
		    	                ((oldState[x-1][y]+
		    	                  oldState[x+1][y]+
		    	                  oldState[x][y+1]+
		    	                  oldState[x][y-1])  >> 1) )	- currentState[x][y];
					currentState[x][y] -= (currentState[x][y] >> 7);
					
					
					 //where data=0 then still, where data>0 then wave
					int  data = (short)(1024-currentState[x][y]);

				    //offsets
				  	int  a=((x-Renderer.renderedWidth)*data/1024)+Renderer.renderedWidth;
				    int  b=((y-Renderer.renderedHeight)*data/1024)+Renderer.renderedHeight;

				 	  //bounds check
				          if (a>=Renderer.renderedWidth) a=Renderer.renderedWidth-1;
				          if (a<0) a=0;
				          if (b>=Renderer.renderedHeight) b=Renderer.renderedHeight-1;
				          if (b<0) b=0;
				          
				          int textOffset = x+y*Renderer.renderedWidth;
						renderer.offScreenRaster[textOffset] =  texture[a+(b*Renderer.renderedWidth)];
				          

				}
			swapPointer = currentState;
			currentState = oldState;
			oldState = swapPointer;
			
	}
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseMoved(MouseEvent arg0) {
		
		
			addRain(currentState,arg0.getX(),arg0.getY());
		
		
	}
	
	
	
	public static void addRain(int[][] pointer,int x, int y )
	{
		if (		x > renderer.getWidth() - 1
				||  x < 1
				||  y > renderer.getHeight() - 1
				||  y< 1
		)
			return;
		
//		for (int i = -17 ; i < 17 ; i++)
//			for (int j = -17 ; j < 17 ; j++)
//				pointer[x+i][y+j] += PERTURBATION;	
		try
		{
		pointer[x][y] += PERTURBATION;
		
		pointer[x][y-1] += PERTURBATION;
		pointer[x][y+1] += PERTURBATION;
		
		pointer[x+1][y] += PERTURBATION;
		pointer[x+1][y+1] += PERTURBATION;
		pointer[x+1][y-1] += PERTURBATION;
		
		pointer[x-1][y+1] += PERTURBATION;
		pointer[x-1][y] += PERTURBATION;
		pointer[x-1][y-1] += PERTURBATION;
		
		pointer[x-2][y+2] += PERTURBATION;
		pointer[x-2][y+1] += PERTURBATION;
		pointer[x-2][y] += PERTURBATION;
		pointer[x-2][y-1] += PERTURBATION;
		pointer[x-2][y-2] += PERTURBATION;
		
		pointer[x+2][y+2] += PERTURBATION;
		pointer[x+2][y+1] += PERTURBATION;
		pointer[x+2][y] += PERTURBATION;
		pointer[x+2][y-1] += PERTURBATION;
		pointer[x+2][y-2] += PERTURBATION;
		/*
		pointer[x-3][y+3] += PERTURBATION;
		pointer[x-3][y+2] += PERTURBATION;
		pointer[x-3][y+1] += PERTURBATION;
		pointer[x-3][y] += PERTURBATION;
		pointer[x-3][y-1] += PERTURBATION;
		pointer[x-3][y-2] += PERTURBATION;
		pointer[x-3][y-3] += PERTURBATION;
		pointer[x][y+3] += PERTURBATION;
		pointer[x][y+2] += PERTURBATION;
		pointer[x][y-2] += PERTURBATION;
		pointer[x][y-3] += PERTURBATION;
		*/
		
		
		pointer[x+3][y+3] += PERTURBATION;
		pointer[x+3][y+2] += PERTURBATION;
		pointer[x+3][y+1] += PERTURBATION;
		pointer[x+3][y] += PERTURBATION;
		pointer[x+3][y-1] += PERTURBATION;
		pointer[x+3][y-2] += PERTURBATION;
		pointer[x+3][y-3] += PERTURBATION;

		}
		catch(Exception e)
		{
			
		}
		
				
	}
}

