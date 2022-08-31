package renderer;

//import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import renderer.modelers.*;
import java.awt.event.WindowEvent;    
import java.awt.event.WindowListener;


//public class Renderer extends Applet implements  Runnable
public class Renderer extends Frame implements  Runnable,WindowListener
{

	private static final long serialVersionUID = 1L;

	private Modeler modeler = null;///drawOffScreen()
		
	Image vramBundle;
	public MemoryImageSource source ;
	public int[] offScreenRaster ;
	
	public final static int renderedWidth  = 400;
	public final static int renderedHeight = 300;
	
	public int drawWidth  = 400;
    public int drawHeight = 300;
	
    public void start()
    {
    	new Thread(this).start();
    }
    
    
    
    
    //public void init()
    public Renderer() //Constructor
    {
    	this.setSize(drawWidth,drawHeight);
    	
        setBackground(Color.black);
        
		
		 ColorModel colorModel = new DirectColorModel(32, 0xff0000, 0x00ff00, 0x0000ff, 0);
		
        offScreenRaster = new int[renderedWidth*renderedHeight];
        
        source = new MemoryImageSource(renderedWidth, renderedHeight, colorModel , offScreenRaster, 0, renderedWidth);
		source.setAnimated(true);
		source.setFullBufferUpdates(true);
		vramBundle  = createImage(source);
        
		 
		this.modeler = new Rain(this);
        //this.modeler = new Tunnel(this);
        //this.modeler = new FreeTunnel(this);
        //this.modeler = new FreeInterpolatedTunnel(this);
        this.addWindowListener(this);//added
    }
    
    public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}
	public void windowOpened(WindowEvent e)
	{ }
	public void windowIconified(WindowEvent e)
	{ }
	public void windowClosed(WindowEvent e)
	{ }
	public void windowDeiconified(WindowEvent e)
	{ }
	public void windowActivated(WindowEvent e)
	{ }
	public void windowDeactivated(WindowEvent e)
	{ }
	
	public static void main (String[] args) {//added
	
		Renderer renderer = new Renderer();
		renderer.setSize(400,400);
		renderer.setVisible(true);
		renderer.setLayout(new FlowLayout() );
		
	}
	
	long  now = 0;
	long  before = 0;
	//float framespd = 0;
	int fps = 0 ;
	
	 public void run()
	    {
		 before = (int)System.currentTimeMillis();
	      
	        while(true) 
	        {
	            repaint();
	            now = (int)System.currentTimeMillis();
	            if(now - before > 1000)
	            {
	                fps = frameCounter;
	                before = now;
	                frameCounter = 0;
	            }
	            
	            try
	            {
	                Thread.sleep(20L);
	            }
	            catch(InterruptedException interruptedexception) { }
	            
	        }
	    }
	 
	  public void update(Graphics g)
	    {
	        paint(g);
	    }
	  
	  int frameCounter;
	  boolean showFps = true;
	  public void paint(Graphics g)
	    {
	    	
		  	frameCounter++;
	            this.modeler.drawOffScreen();
	            source.newPixels();
	            g.drawImage(vramBundle, 0, 0, getWidth(),getHeight(), this);
	          
	            if(showFps)
	            {
	                g.setColor(Color.white);
	                g.drawString("fps : " + fps, 5, 15);
	            }
	        
	    }

	public static int  getRenderedHeight() {
		return renderedHeight;
	}



	public static int getRenderedWidth() {
		return renderedWidth;
	}

}
