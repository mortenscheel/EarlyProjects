package language_detection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class TextAnalysis {
	private static File textfile;
	private int resizeFactor = 6;
	private int lowerSpacing = 30;
	private ArrayList<String> letters;
	private ArrayList<Integer> counts;
	private double[] percents;
	private double[] english = {0.08167, 0.01492, 0.02782, 0.04253,
			0.12702, 0.02228, 0.02015, 0.06094,	0.06966, 
			0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 
			0.07507, 0.01929, 0.00095, 0.05987,	0.06327, 
			0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 
			0.01974, 0.00074};
	private double[] danish = {0.05381, 0.00115, 0.00195, 0.06091, 
			0.16666, 0.03220, 0.05000, 0.00256, 0.06492, 
			0.00390, 0.02582, 0.06605, 0.02551, 0.08035, 
			0.04537, 0.00946, 0.00000, 0.09002, 0.05628, 
			0.06438, 0.01296, 0.02171, 0.00000, 0.00000, 
			0.00545, 0.00000};
	private double[] spanish = {0.1272, 0.0103, 0.0443,	0.0499, 
			0.1620, 0.0061, 0.0108, 0.0028, 0.0670, 0.0028, 
			0.0000, 0.0504, 0.0332, 0.0688, 0.0857, 0.0022, 
			0.0063, 0.0642, 0.0655,0.0452, 0.0424, 0.0073, 
			0.0000, 0.0016, 0.0000, 0.0022};
	private String languageGuess;
	private boolean showEnglish = true;
	private boolean showDanish = true;
	private boolean showSpanish = true;
	private JPanel panel;
	private Color[] colors;
	private Font font;

	public static void main(String[] args) {
		new TextAnalysis("res/ordliste.txt");
	}
	
	private void initialize(){
		letters = new ArrayList<>();
		counts = new ArrayList<>();
		percents = new double[26];
		for (char c = 'a' ; c <= 'z' ; c++) {
			letters.add("" + c);
			counts.add(0);
		}
		colors = new Color[26];
		Random r = new Random();
		font = new Font("Helvetica", Font.PLAIN, 18);
		for (int i = 0; i < colors.length; i++) {
			colors[i] = new Color(r.nextInt(156) + 100, r.nextInt(156) + 100, r.nextInt(156) + 100);
		}
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public TextAnalysis(String filename){
		textfile = new File(filename);
		initialize();
		// load text file
		Scanner parser = null;
		try {
			parser = new Scanner(textfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (parser != null){
			// read each line
			while (parser.hasNextLine()){
				Scanner s = new Scanner(parser.nextLine());
				// read each character
				while (s.hasNext()){
					String letter = s.next().toLowerCase();
					// update letter counts
					for (int i = 0; i < letter.length(); i++) {
						String chara = "" + letter.charAt(i);
						if (letters.contains(chara)){
							int index = letters.indexOf(chara);
							int count = counts.get(index);
							count++;
							counts.remove(index);
							counts.add(index, count);
						}
					}
					
				}
				s.close();
				
			}
		}
		parser.close();
		calculatePercentages();
		showGraph();
	}

	private void calculatePercentages(){
		int total = 0;
		for (int i : counts) total += i;
		for (int i = 0 ; i < letters.size() ; i++){
			percents[i] = (double)counts.get(i) / total;
		}
		double[] diffEnglish = new double[26];
		double[] diffDanish = new double[26];
		double[] diffSpanish = new double[26];
		for (int i = 0 ; i < percents.length ; i++){
			double percent = percents[i];
			diffEnglish[i] = Math.abs(english[i] - percent);
			diffDanish[i] = Math.abs(danish[i] - percent);
			diffSpanish[i] = Math.abs(spanish[i] - percent);
		}
		double danishCount = 0;
		for (double d : diffDanish) danishCount += d;
		double englishCount = 0;
		for (double d : diffEnglish) englishCount += d;
		double spanishCount = 0;
		for (double d : diffSpanish) spanishCount += d;
		if (danishCount < englishCount && danishCount < spanishCount) languageGuess = "Danish";
		else if (englishCount < spanishCount) languageGuess = "English";
		else languageGuess = "Spanish";
	}

	@SuppressWarnings("serial")
	private void showGraph() {
		final JFrame frame = new JFrame("Text analysis of " + textfile.getName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setBounds(100, 100, 800, 600);
		JPanel south = new JPanel(new FlowLayout());
		south.setBackground(Color.black);
		frame.add(south, BorderLayout.SOUTH);
		final JCheckBox cbEnglish = new JCheckBox("English");
		final JCheckBox cbDanish = new JCheckBox("Danish");
		final JCheckBox cbSpanish = new JCheckBox("Spanish");
		JLabel label = new JLabel("Toggle language display ");
		label.setForeground(Color.white);
		south.add(label);
		south.add(cbEnglish);
		south.add(cbDanish);
		south.add(cbSpanish);
		label.setFont(font);
		cbEnglish.setFont(font);
		cbDanish.setFont(font);
		cbSpanish.setFont(font);
		cbEnglish.setSelected(true);
		cbEnglish.setForeground(Color.white);
		cbDanish.setSelected(true);
		cbDanish.setForeground(Color.white);
		cbSpanish.setSelected(true);
		cbSpanish.setForeground(Color.white);
		ActionListener checkboxes = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbEnglish.isSelected()) showEnglish = true;
				else showEnglish = false;
				if (cbDanish.isSelected()) showDanish = true;
				else showDanish = false;
				if (cbSpanish.isSelected()) showSpanish = true;
				else showSpanish = false;
				panel.repaint();
			}
		};
		cbEnglish.addActionListener(checkboxes);
		cbDanish.addActionListener(checkboxes);
		cbSpanish.addActionListener(checkboxes);
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				// turn on anti aliasing
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int max = -1;
				// find the highest letter count
				for (int i = 0 ; i < counts.size() ; i++){
					int count = counts.get(i);
					if (count > max) max = count;
				}
				// calculate constants
				int xSpacing = getWidth() / letters.size();
				// draw legend
				g.setFont(font);
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.white);
				g.drawString("The letter frequencies suggest the language is " + languageGuess, getWidth() / 2 - 210, 20);
				g.setColor(Color.green);
				g.fillRect(10, 10, 10, 10);
				g.drawString("English", 25, 20);
				g.setColor(Color.red);
				g.fillRect(10, 30, 10, 10);
				g.drawString("Danish", 25, 40);
				g.setColor(Color.yellow);
				g.fillRect(10, 50, 10, 10);
				g.drawString("Spanish", 25, 60);
				// draw bar graph
				for (int i = 0 ; i < counts.size() ; i++){
					int height = (int)((getHeight() / 100) * percents[i] * 100 * resizeFactor);
					g.setColor(colors[i]);
					g.fillRect(i * xSpacing, getHeight() - height, xSpacing, height - lowerSpacing);
					g.setColor(Color.darkGray);
					g.drawLine(i * xSpacing + xSpacing, 100, i * xSpacing + xSpacing, getHeight() - lowerSpacing);
				}
				g.setColor(Color.white);
				for (int i = 0 ; i < letters.size() ; i++){
					String l = letters.get(i).toUpperCase();
					g.drawString(l, i * xSpacing + xSpacing / 2 - 5, getHeight() - 8);
				}
				g.fillRect(0, getHeight() - 2, getWidth(), 2);
				// english
				if (showEnglish) {
					g.setColor(Color.green);
					for (int i = 0 ; i < letters.size() ; i++){
						int height = (int) ((getHeight() / 100) * english[i] * 100 * resizeFactor) + lowerSpacing;
						g.fillRect(xSpacing * i, getHeight() - height, xSpacing, 2);
					}
				}
				// danish
				if (showDanish){
					g.setColor(Color.red);
					for (int i = 0 ; i < letters.size() ; i++){
						int height = (int) ((getHeight() / 100) * danish[i] * 100 * resizeFactor) + lowerSpacing;
						g.fillRect(xSpacing * i, getHeight() - height, xSpacing, 2);
					}
				}
				// spanish
				if (showSpanish){
					g.setColor(Color.yellow);
					for (int i = 0 ; i < letters.size() ; i++){
						int h = (int) ((getHeight() / 100) * spanish[i] * 100 * resizeFactor) + lowerSpacing;
						g.fillRect(xSpacing * i, getHeight() - h, xSpacing, 2);
					}
				}
			}
		};
		frame.add(panel, BorderLayout.CENTER);
		panel.repaint();
		frame.setVisible(true);
	}
}
