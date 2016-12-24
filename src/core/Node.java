package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Node{
	public int x;
	public int y;
	public int width;
	public int height;

	protected String name;

	public Jack[] ins = new Jack[0];
	public Jack[] outs = new Jack[0];

	/*public double[][] params = new double[0][4];  //value, min, max, step
	public String[] paramNames = new String[0];*/

	int mouseX = 0;
	int mouseY = 0;
	boolean hover = false;

	private int deltaX = 0;
	private int deltaY = 0;
	private boolean moving = false;
	private boolean resizing = false;

	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void hover(int x, int y){
		mouseX = x;
		mouseY = y;
		hover = true;
		System.out.println(function(0,0)+"");
	}

	public void draw(Graphics2D g){

		if(!Main.mouseDown){
			moving = false;
			resizing = false;
		}

		g.setColor(Color.GRAY);
		//TODO Must be able to disconnect spool by moving away from jack.
		if(new Rectangle(x-5,y+35,10,height-35).contains(Main.mouseX, Main.mouseY)){  //hover ins
			for(int i=0; i<ins.length; i++) if(new Rectangle(x-5, y+35+20*i, 10, 10).contains(Main.mouseX, Main.mouseY)){
				if(Main.clickLGrab){
					Main.clickLGrab = false;
					Main.spool = new Wire(null, 0, this, i);
					Main.spoolDir = false;
					break;
				}else if(Main.spool!=null && Main.spool.node1!=this && Main.spool.node2!=this){
					Main.spool.node2 = this;
					Main.spool.jack2 = i;
				}
			}
		}if(new Rectangle(x+width-5,y+35,10,height-35).contains(Main.mouseX, Main.mouseY)){  //hover outs
			for(int i=0; i<outs.length; i++) if(new Rectangle(x+width-5, y+35+20*i, 10, 10).contains(Main.mouseX, Main.mouseY)){
				if(Main.clickLGrab){
					Main.clickLGrab = false;
					Main.spool = new Wire(this, i, null, 0);
					Main.spoolDir = true;
					break;
				}else if(Main.spool!=null && Main.spool.node1!=this && Main.spool.node2!=this){
					Main.spool.node1 = this;
					Main.spool.jack1 = i;
				}
			}
		}else if(new Rectangle(x,y,width,height).contains(Main.mouseX, Main.mouseY) || moving){  //hover node
			if(Main.mouseDown){
				if(Main.clickLGrab){
					deltaX = Main.mouseX - x;
					deltaY = Main.mouseY - y;
					Main.clickLGrab = false;
					moving = true;
				}else if(Main.clickRGrab){
					deltaX = Main.mouseX - width;
					deltaY = Main.mouseY - height;
					Main.clickRGrab = false;
					resizing = true;
				}

				if(moving){
					x = Main.mouseX - deltaX;
					y = Main.mouseY - deltaY;
				}else if(resizing){
					width = Main.mouseX - deltaX;
					height = Main.mouseY - deltaY;
				}
			}
			g.setColor(new Color(100,100,100));
		}else{
			moving = false; //TODO Maybe avoid setting this variable every single draw cycle?
			resizing = false;
		}
		g.fillRoundRect(x, y, width, height, 20, 20);
		g.setColor(Color.WHITE);
		g.drawString(name, x+10, y+20);

		/*for(int i=0; i<params.length; i++){
			g.setColor(Color.WHITE);
			g.drawString(paramNames[i], x+20, y+height-35-50*i);
			int marginL = 100;
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(x+10, y+height-30-50*i, (int)Math.round((params[i][0]-params[i][1])/(params[i][2]-params[i][1])*(width-marginL)), 20, 10, 10);
			g.setColor(Color.WHITE);
			g.drawRoundRect(x+10, y+height-30-50*i, width-marginL, 20, 10, 10);
			g.drawString(params[i][0]+"", x+15, y+height-15-50*i);
		}*/

		for(int i=0; i<ins.length; i++){
			g.setColor(Color.BLUE);
			g.fillOval(x-5, y+35+20*i, 10, 10);
			g.setColor(Color.WHITE);
			if(ins[i].source==null && ins[i].max!=ins[i].min){
				int marginL = 100; //NOTE: when this changes, change in MAIN.clicked and MAIN.paintViewport as well!
				g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(x+10, y+33+20*i, width-marginL, 14, 10, 10);
				g.setColor(Color.BLUE);
				if(ins[i].value<=ins[i].max)g.fillRoundRect(x+10, y+33+20*i, (int)Math.round((ins[i].value-ins[i].min)/(ins[i].max-ins[i].min)*(width-marginL)), 14, 10, 10);
				else g.fillRoundRect(x+10, y+33+20*i, (width-marginL), 14, 10, 10);
				g.setColor(Color.WHITE);
				g.drawRoundRect(x+10, y+33+20*i, width-marginL, 14, 10, 10);

				g.drawString(ins[i].value+"", x+width/2-50, y+45+20*i);
				g.drawString(ins[i].name+":", x+14, y+45+20*i);
			}else g.drawString(ins[i].name, x+10, y+45+20*i);
		}

		for(int i=0; i<outs.length; i++){
			g.setColor(Color.BLUE);
			g.fillOval(x+width-5, y+35+20*i, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString(outs[i].name, x+width-75, y+45+20*i);
		}

		subDraw(g);
	}

	public void subDraw(Graphics2D g){

	}

	public abstract double function(int output, double t);
}
