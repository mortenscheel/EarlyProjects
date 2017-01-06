package graphs.pathfinding.gui.components;

import graphs.pathfinding.AStarNode;
import graphs.pathfinding.gui.AStarDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AStarPanel extends JPanel {

	private AStarDemo con;
	private int xSpacing;
	private int ySpacing;

	/**
	 * Create the panel.
	 */
	public AStarPanel(AStarDemo con) {
		this.con = con;
		con.setPanel(this);
		setPreferredSize(new Dimension(800, 600));
		addMouseListener(con);
		addMouseMotionListener(con);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		xSpacing = getWidth() / 40;
		ySpacing = getHeight() / 30;
		// clear
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		// draw grid
		g.setColor(Color.DARK_GRAY);
		for (int y = 0 ; y <= 30 * ySpacing ; y += ySpacing){
			g.drawLine(0, y, 40 * xSpacing, y);
		}
		for (int x = 0 ; x <= 40 * xSpacing ; x += xSpacing){
			g.drawLine(x, 0, x, 30 * ySpacing);
		}
		// draw nodes
		for (AStarNode n : con.getNodes()){
			// draw obstacles
			if (!n.isLegalMove()){
				g.setColor(Color.black);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
			// draw source
			else if (n.isSource()){
				g.setColor(Color.green);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
			// draw destination
			else if (n.isDestination()){
				g.setColor(Color.red);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
			// draw tested
			else if (n.isTested() && !n.isOnPath()){
				g.setColor(Color.lightGray);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
			// draw path
			else if (n.isOnPath()){
				g.setColor(Color.cyan);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
			// draw others
			else{
				g.setColor(Color.white);
				g.fillRect(n.x * xSpacing + 1, n.y * ySpacing + 1, xSpacing - 1, ySpacing - 1);
			}
		}
	}
	public int getXSpacing(){
		return xSpacing;
	}
	
	public int getYSpacing(){
		return ySpacing;
	}
}
