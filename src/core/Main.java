package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import nodes.*;

public class Main extends JPanel implements KeyListener, MouseListener, MouseInputListener {
	
	public JFrame window = new JFrame();
	
	public int width = 1500;
	public int height = 800;
	
	public ArrayList<Node> nodes = new ArrayList<Node>();
	public ArrayList<Wire> wires = new ArrayList<Wire>();

	public final int MAXFPS = 60;
	public final long TICK = 1000000000/MAXFPS;
	public long lastTime = System.nanoTime();
	
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static boolean mouseDown = false;
	public static boolean clickLGrab = false; //True iff most recent mouse click has not been claimed by anything.
	public static boolean clickRGrab = false;
	
	public static Wire spool = null;
	public static boolean spoolDir = false;  //Should be thought of as 0 or 1, depending on spooling direction.
	
	public static Node editNode = null;
	public static int editId = 0;
	public static String editValue = "";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		window.setSize(width, height);  //TODO Panel should be sized instead of frame.
		window.setTitle("NodeSynth");
		window.setVisible(true);
		
		nodes.add(new WaveGenerator(50, 200));
		nodes.add(new Mathematics(350, 250));
		nodes.add(new Graph(550, 200));
		nodes.add(new Output(850, 200));
		nodes.add(new Transform(50, 500));
		
		startTick();
	}
	
	@Override
	public void paint(Graphics g){
		paintViewport((Graphics2D)g);
	}
	
	public void paintViewport(Graphics2D g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		
		for(Node node : nodes)node.draw(g);
		for(Wire wire : wires)wire.draw(g);
		
		if(spool != null){
			g.setColor(Color.WHITE);
			if(spool.node1 == null) g.drawLine(mouseX, mouseY, spool.node2.x, spool.node2.y+40+20*spool.jack2);
			else if(spool.node2 == null) g.drawLine(spool.node1.x+spool.node1.width, spool.node1.y+40+20*spool.jack1, mouseX, mouseY);
			else spool.draw(g);
		}
		
		if(editNode!=null){
			//TODO: black overlay when editing
			g.setColor(Color.white);
			g.fillRoundRect(editNode.x+10, editNode.y+33+20*editId, editNode.width-100, 14, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString(editValue+"|", editNode.x+editNode.width/2-50, editNode.y+45+20*editId);
		}
	}
	
	public void startTick(){
		while(true){
			if(System.nanoTime()-lastTime>TICK){
				lastTime = System.nanoTime();
				this.repaint();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {		
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO This:
		/*for(Node node : nodes) if(new Rectangle(node.x,node.y,node.width,node.height).contains(Main.mouseX, Main.mouseY)){
			new ParamTweak(node);
			break;
		}*/
		
		if(editNode!=null){
			try{
			editNode.ins[editId].value = Double.parseDouble(editValue);
			}catch(Exception a){
				System.out.println("Converting typed value to Double failed.");
			}
			editNode=null;
		}
		
		for(Node node : nodes) if(new Rectangle(node.x,node.y,node.width,node.height).contains(Main.mouseX, Main.mouseY)){
			for(int i=0; i<node.ins.length; i++) if(new Rectangle(node.x+10,node.y+33+20*i, width-100,14).contains(Main.mouseX, Main.mouseY)){
				editNode = node;
				editId = i;
				editValue = node.ins[i].value+"";
				break;
			}
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		if(e.getButton()==MouseEvent.BUTTON1) clickLGrab = true;
		if(e.getButton()==MouseEvent.BUTTON3) clickRGrab = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		clickLGrab = false;
		clickRGrab = false;
		
		if(spool!=null){
			if(spool.node1!=null&&spool.node2!=null){
				boolean kill = false;
				
				Iterator<Wire> iter = wires.iterator();
				
				while (iter.hasNext()){
					Wire wire = iter.next();
					if(wire.node1==spool.node1 && wire.node2==spool.node2 && wire.jack1==spool.jack1 && wire.jack2==spool.jack2){
						kill = true;
						wire.disconnect();
						iter.remove();
						break;
					}else if(wire.node2==spool.node2 && wire.jack2==spool.jack2){
						wire.disconnect();
						iter.remove();
						break;
					}
				}
				if(!kill)wires.add(new Wire(spool.node1, spool.jack1, spool.node2, spool.jack2));
			}
			
			spool = null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && editNode==null){
			Iterator<Node> iter = nodes.iterator();
			
			while (iter.hasNext()){
				Node node = iter.next();
				if(new Rectangle(node.x,node.y,node.width,node.height).contains(Main.mouseX, Main.mouseY)){
					
					Iterator<Wire> iter2 = wires.iterator();
					
					while (iter2.hasNext()){
						Wire wire = iter2.next();
						if(wire.node1==node || wire.node2==node){
							wire.disconnect();
							iter2.remove();
						}
					}
					
					iter.remove();
					break;
				}
			}
		}else switch(e.getKeyCode()){
			case KeyEvent.VK_W:
				nodes.add(new WaveGenerator(mouseX, mouseY));
				break;
			case KeyEvent.VK_M:
				nodes.add(new Mathematics(mouseX, mouseY));
				break;
			case KeyEvent.VK_G:
				nodes.add(new Graph(mouseX, mouseY));
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(editNode!=null && e.getKeyCode()==KeyEvent.VK_BACK_SPACE && editValue.length()>0)editValue = editValue.substring(0, editValue.length()-1);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(editNode!=null && (Character.isDigit(e.getKeyChar())
			|| e.getKeyChar()=='.' || e.getKeyChar()=='-'
			|| e.getKeyChar()=='E' || e.getKeyChar()=='e')) editValue += e.getKeyChar();
	}
	
	

}
