package graphs.pathfinding.gui.components;


import graphs.Edge;
import graphs.Vertex;
import graphs.pathfinding.gui.DijkstraDemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DijkstraPanel extends JPanel implements MouseMotionListener, MouseListener{

	@SuppressWarnings("unused")
	private DijkstraFrame frame;
	private DijkstraDemo con;
	private int mouseY;
	private int mouseX;
	private Edge edge;

	public DijkstraPanel(DijkstraFrame frame, DijkstraDemo con) {
		this.con = con;
		this.frame = frame;
		con.setPanel(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// draw vertices
		g.setColor(Color.WHITE);
		for (Vertex v : con.getVertices()){
			Point pos = v.getPosition();
			g.fillOval(pos.x - 5, pos.y - 5, 10, 10);
			String name = v.getVertexName();
			if (v.getWeight() != 0) name += " (" + v.getWeight() + ")";
			g.drawString(name, v.getPosition().x - 5, v.getPosition().y - 10);
		}

		// draw edges
		for (Edge e : con.getEdges()){
			g.setColor(Color.red);
			Point p1 = e.getConnections().get(0).getPosition();
			Point p2 = e.getConnections().get(1).getPosition();
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			int midX = (p1.x + p2.x) / 2;
			int midY = (p1.y + p2.y) / 2;
			g.drawString("" + e.getWeight(), midX + 10, midY);
		}

		// draw source
		if (con.getSource() != null){
			g.setColor(Color.green);
			g.fillOval(con.getSource().getPosition().x - 5, con.getSource().getPosition().y - 5, 10, 10);
		}
		
		// draw destination
		if (con.getDestination() != null){
			g.setColor(Color.red);
			g.fillOval(con.getDestination().getPosition().x - 5, con.getDestination().getPosition().y - 5, 10, 10);
		}
		
		// draw path
		g.setColor(Color.GREEN);
		if (con.getPath() != null){
			for (int i = 0 ; i < con.getPath().size() - 1; i++){
				Point p1 = con.getPath().get(i).getPosition();
				Point p2 = con.getPath().get(i + 1).getPosition();
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// add vertex
		if (con.isAddingVertices()){
			Vertex v = new Vertex(con.getLetter());
			v.setPosition(new Point(mouseX, mouseY));
			con.addVertex(v);
		}
		// add edge
		if (con.isAddingEdges()){
			Vertex nearest = con.findNearestVertex(mouseX, mouseY);
			// selecting from
			if(!con.isFromSelected()){
				con.setFromSelected(true);
				edge = new Edge();
				edge.addVertex(nearest);
			}
			else{
				con.setFromSelected(false);
				edge.addVertex(nearest);
				int weight = (int) edge.getConnections().get(0).getPosition().distance(edge.getConnections().get(1).getPosition()) / 10;
				edge.setWeight(weight);
				con.addEdge(edge);
			}
		}
		// select source
		if (con.isSelectingSource()){
			con.setSource(con.findNearestVertex(mouseX, mouseY));
			con.calculate();
		}
		// select destination
		if (con.isSelectingDestination()){
			con.setDestination(con.findNearestVertex(mouseX, mouseY));
			con.showPath();
			repaint();
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
