package core;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ParamTweak extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel[] panels;
	JSpinner[] sliders;
	JLabel[] names;

	public ParamTweak(Node node){
		/*setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);
		panels = new JPanel[node.params.length];
		sliders = new JSpinner[node.params.length];
		names = new JLabel[node.paramNames.length];
		for(int i=0; i<node.params.length; i++){
			sliders[i] = new JSpinner(new SpinnerNumberModel(
					node.params[i][0], node.params[i][1], node.params[i][2], node.params[i][3]));
			names[i] = new JLabel(node.paramNames[i]);
			
			panels[i] = new JPanel();
			panels[i].add(names[i]);
			panels[i].add(sliders[i]);
			add(panels[i]);
		}
		
		setTitle("Tweak parameters for '"+node.name+"' node.");
		setVisible(true);*/
	}

}
