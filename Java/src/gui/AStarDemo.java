package gui;

import graph_models.AStarNode;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import gui.components.AStarFrame;
import gui.components.AStarPanel;
import pathfinding_algorithms.AStar;

public class AStarDemo implements MouseListener, MouseMotionListener{
	
	private AStarFrame frame;
	private AStarPanel panel;
	private ArrayList<AStarNode> allNodes;
	private AStar aStar;
	private AStarNode source;
	private AStarNode destination;
	private boolean editingObstacles;
	private boolean settingSource;
	private boolean settingDestination;
	private Point mouseGrid;
	
	public AStarDemo(){
		frame = new AStarFrame(this);
		frame.setVisible(true);
		clear();
		Timer t = new Timer(500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.repaint();
			}
		});
		t.start();
	}

	public void clear() {
		allNodes = new ArrayList<AStarNode>();
		for (int y = 0 ; y < 30 ; y++){
			for (int x = 0 ; x < 40 ; x++){
				allNodes.add(new AStarNode(x, y));
			}
		}
		source = null;
		destination = null;
		aStar = null;
		panel.repaint();
	}
	
	public void calculate() {
		if (source != null && destination != null){
			aStar = new AStar(allNodes, source, destination);
			Thread t = new Thread(aStar);
			t.start();
		}
	}
	
	public void repaint(){
		panel.repaint();
	}

	public void setPanel(AStarPanel aStarPanel) {
		panel = aStarPanel;
	}
	
	public static void main(String[] args) {
		new AStarDemo();
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		int xGrid = e.getX() / panel.getXSpacing();
		int yGrid = e.getY() / panel.getYSpacing();
		mouseGrid = new Point(xGrid, yGrid);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AStarNode selected = findNode(mouseGrid.x, mouseGrid.y);
		if (isEditingObstacles()){
			if (selected.isLegalMove()) selected.setLegalMove(false);
			else selected.setLegalMove(true);
		}
		if (isSettingSource()) {
			selected.setSource(true);
			source = selected;
			setSettingSource(false);
		}
		if (isSettingDestination()) {
			selected.setDestination(true);
			destination = selected;
			setSettingDestination(false);
		}
		panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	public boolean isEditingObstacles() {
		return editingObstacles;
	}

	public void setEditingObstacles(boolean editingObstacles) {
		this.editingObstacles = editingObstacles;
	}

	public boolean isSettingSource() {
		return settingSource;
	}

	public void setSettingSource(boolean settingSource) {
		this.settingSource = settingSource;
	}

	public boolean isSettingDestination() {
		return settingDestination;
	}

	public void setSettingDestination(boolean settingDestination) {
		this.settingDestination = settingDestination;
	}

	public AStarNode getSource() {
		return source;
	}

	public void setSource(AStarNode source) {
		this.source = source;
	}

	public AStarNode getDestination() {
		return destination;
	}

	public void setDestination(AStarNode destination) {
		this.destination = destination;
	}
	
	private AStarNode findNode(int x, int y){
		AStarNode result = null;
		for (AStarNode n : allNodes){
			if (n.x == x && n.y == y){
				result = n;
				break;
			}
		}
		return result;
	}
	
	public ArrayList<AStarNode> getNodes(){
		return allNodes;
	}
}
