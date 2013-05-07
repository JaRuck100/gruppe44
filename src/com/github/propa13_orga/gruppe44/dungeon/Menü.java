package com.github.propa13_orga.gruppe44.dungeon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;


public class Men� extends JFrame implements ActionListener{
	
//ben�tigte Buttons
	
	private JButton spielen;
	private JButton einstellungen;
	private JButton programmierer;
	private JButton ende;
	
//Methoden der Buttons
	
	public void actionPerformed(ActionEvent a) {
		if (a.getSource()==spielen){
			fenster();
		}
		if (a.getSource()==programmierer){
			Object[] option = {"Schlie�en"};
			
			JOptionPane.showOptionDialog(null, "Gruppe 44","Programmierer", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option [0]);
			// null, Text, �berschrift, ..., Buttontext
		}
		
		if (a.getSource()== einstellungen){
			
			//noch ohne Inhalt

		}
		
		if (a.getSource()== ende){
			System.exit(0);
		}
		
	}
	

//Eigenschaften der Buttons 
	
	public Men� (String titel){
		super(titel);
		
		spielen = new JButton ("Spiel starten");
		spielen.setBounds(150,30,160,40); // Position, Gr��e
		spielen.addActionListener(this);
		add(spielen);
		
		einstellungen = new JButton ("Einstellungen"); // noch ohne Inhalt
		einstellungen.setBounds(150,110,160,40);
		einstellungen.addActionListener(this);
		add(einstellungen);
		
		programmierer = new JButton ("Programmierer");
		programmierer.setBounds(150,190,160,40);
		programmierer.addActionListener(this);
		add(programmierer);
		
		ende = new JButton ("Spiel beenden");
		ende.setBounds(150,270,160,40);
		ende.addActionListener(this);
		add(ende);
		
	}
	
// Startbildfenster
	
	public static void main(String[] args) {
		
		Men� Men� = new Men� ("Dungeon");
		Men�.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Men�.setSize(450,400); // Fenstergr��e
		Men�.setLayout(null); //kein vorgegebenes Layout
		Men�.setVisible(true); //sichtbar
	}
	
//Spielfenster
	
	public static void fenster (){
		JFrame fenster = new JFrame ();
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setSize(500,400);
		fenster.setVisible (true);
		fenster.add(new gui());

	}

}
