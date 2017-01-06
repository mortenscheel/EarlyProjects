package graphs.pathfinding.gui.components;

import graphs.pathfinding.gui.DijkstraDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class DijkstraFrame extends JFrame {

	@SuppressWarnings("unused")
	private JPanel contentPane;
	private JLabel label;
	@SuppressWarnings("unused")
	private DijkstraDemo con;
	private JButton btnEdge;
	private JButton btnVertex;
	private JButton btnSource;
	private JButton btnDestination;
	private JScrollPane scrollPane;
	private JTextArea ta;

	/**
	 * Create the frame.
	 */
	public DijkstraFrame(final DijkstraDemo con) {
		setTitle("Dijkstra GUI");
		this.con = con;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setResizable(false);
		DijkstraPanel panel = new DijkstraPanel(this, con);
		getContentPane().add(panel, BorderLayout.CENTER);
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		btnVertex = new JButton("Tilføj vertices");
		btnVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.setAddingVertices(true);
				con.setAddingEdges(false);
				btnVertex.setForeground(Color.red);
				btnEdge.setForeground(Color.black);
			}
		});
		panel_1.add(btnVertex);

		btnEdge = new JButton("Tilføj edges");
		btnEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.setAddingEdges(true);
				con.setAddingVertices(false);
				btnEdge.setForeground(Color.red);
				btnVertex.setForeground(Color.black);
			}
		});
		panel_1.add(btnEdge);

		btnSource = new JButton("Vælg source");
		btnSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.setAddingEdges(false);
				con.setAddingVertices(false);
				btnVertex.setForeground(Color.black);
				btnEdge.setForeground(Color.black);
				con.setSelectingSource(true);
				btnSource.setForeground(Color.red);
			}
		});
		panel_1.add(btnSource);

		btnDestination = new JButton("Vælg destination");
		btnDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.setSelectingDestination(true);
				con.setSelectingSource(false);
				btnDestination.setForeground(Color.red);
				btnSource.setForeground(Color.black);
			}
		});
		panel_1.add(btnDestination);

		label = new JLabel();
		panel_1.add(label);
		
		ta = new JTextArea();
		ta.setText("Beregning");
		ta.setEditable(false);
		ta.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		scrollPane = new JScrollPane(ta);
		scrollPane.setPreferredSize(new Dimension(250, 100));
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}

	public String getLabel() {
		return label.getText();
	}

	public void setLabel(String s) {
		label.setText(s);
	}
	
	public void setTextArea(String s){
		ta.setText(s);
	}
}
