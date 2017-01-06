package gui.components;

import gui.AStarDemo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AStarFrame extends JFrame {
	
	@SuppressWarnings("unused")
	private AStarDemo con;

	/**
	 * Create the frame.
	 */
	public AStarFrame(final AStarDemo con) {
		this.con = con;
		setTitle("A* TestGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		AStarPanel panel = new AStarPanel(con);
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				con.clear();
			}
		});
		JButton addObstacles = new JButton("Add obstacles");
		addObstacles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				con.setEditingObstacles(true);
				con.setSettingSource(false);
				con.setSettingDestination(false);
			}
		});
		JButton setSource = new JButton("Set source");
		setSource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				con.setSettingSource(true);
				con.setSettingDestination(false);
				con.setEditingObstacles(false);
			}
		});
		JButton setDestination = new JButton("Set destination");
		setDestination.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				con.setSettingDestination(true);
				con.setSettingSource(false);
				con.setEditingObstacles(false);
			}
		});
		JButton calculate = new JButton("Calculate");
		calculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				con.calculate();
			}
		});
		buttonPanel.add(clear);
		buttonPanel.add(addObstacles);
		buttonPanel.add(setSource);
		buttonPanel.add(setDestination);
		buttonPanel.add(calculate);
	}

}
