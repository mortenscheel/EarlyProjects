package create_timed_lyrics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class CreateTimedLyrics implements Serializable{
	static File lyricsTextFile = new File("res/lyrics.txt");
	static Map<Long, String> timedLyrics;
	static JTextArea ta;
	static String[] lyrics;
	static JButton btnStart;
	static JButton btnRecord;
	static boolean recording = false;
	static boolean playing = false;
	static int currentLine = 0;
	static long startTime;
	static String songName;
	private static Thread updater;

	public static void main(String[] args) {
		loadLyrics();
		songName = lyricsTextFile.getName().substring(0, lyricsTextFile.getName().indexOf('.'));
		timedLyrics = new HashMap<Long, String>();
		JFrame frame = new JFrame("Lyrics recorder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100,100,800,600);
		frame.setLayout(new BorderLayout());
		ta = new JTextArea();
		updateText(0);
		frame.add(ta, BorderLayout.CENTER);
		JPanel btns = new JPanel(new FlowLayout());
		frame.add(btns, BorderLayout.SOUTH);
		btnStart = new JButton("Start");
		btns.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!recording){
					start();
					startTime = System.currentTimeMillis();
				}
				else{
					stop();
					saveTimedLyrics();
				}
			}
		});
		btnRecord = new JButton("Record");
		btns.add(btnRecord);
		btnRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timedLyrics.put((System.currentTimeMillis() - startTime), lyrics[currentLine]);
				goToNextLine();
			}
		});
		btnRecord.setEnabled(false);
		btns.add(btnRecord);
		final JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!playing){
					btnPlay.setText("Stop");
					play();
				}
				else {
					btnPlay.setText("Start");
					updater.stop();
					playing = false;
				}
			}
		});
		btns.add(btnPlay);
		frame.setVisible(true);
	}

	private static void goToNextLine(){
		currentLine++;
		updateText(currentLine);
	}

	private static void start(){
		recording = true;
		btnRecord.setEnabled(true);
		btnStart.setText("Stop");
	}

	private static void stop(){
		recording = false;
		btnRecord.setEnabled(false);
		btnStart.setText("Start");
	}

	@SuppressWarnings("unchecked")
	private static void play(){
		try {
			try {
				timedLyrics = (Map<Long, String>) new ObjectInputStream(new FileInputStream("res/lyrics.obj")).readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			currentLine = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		playing = true;
		btnStart.setEnabled(false);
		startTime = System.currentTimeMillis();
		playback();
	}

	private static void updateText(int line){
		String output = "";
		String temp;
		for (int i = 0 ; i < lyrics.length ; i++){
			temp = lyrics[i];
			if (line == i){
				temp = "* " + temp;
			}
			output += temp + "\n";
		}
		ta.setText(output);
	}

	private static void loadLyrics(){
		ArrayList<String> al = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(lyricsTextFile));
			String line;
			while ((line = reader.readLine()) != null){
				if (!line.equals("")) al.add(line);
			}
			reader.close();
			lyrics = al.toArray(new String[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void playback(){
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (playing){
					String s = timedLyrics.get((System.currentTimeMillis() - startTime));
					if (s != null){
						ta.setText(s);
					}
				}
			}
		};
		updater = new Thread(r);
		updater.start();
	}

	private static void saveTimedLyrics(){
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("res/" + songName + ".obj"));
			os.writeObject(timedLyrics);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
