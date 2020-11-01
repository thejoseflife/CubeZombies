package com.cubezombies.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FW {
	
	public static int text1;

	public static void main(String[] args) {
		File highscorefile = new File("highscorefile.txt");
		try {
	        BufferedReader reader = new BufferedReader(new FileReader(highscorefile));
	        String line = reader.readLine();
	        while (line != null)                 // read the score file line by line
	        {
	            try {
	                int score = Integer.parseInt(line.trim());   // parse each line as an int
	                if (score > GameCZ.highscore)                       // and keep track of the largest
	                { 
	                    GameCZ.highscore = score; 
	                }
	            } catch (NumberFormatException e1) {
	                // ignore invalid scores
	                //System.err.println("ignoring invalid score: " + line);
	            }
	            line = reader.readLine();
	        }
	        reader.close();

	    } catch (IOException ex) {
	    	try {
				highscorefile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
		try {
	        BufferedWriter output = new BufferedWriter(new FileWriter(highscorefile, true));
	        output.newLine();
	        output.append("" + GameCZ.highscore);
	        output.close();

	    } catch (IOException ex1) {
	        System.out.printf("ERROR writing score to file: %s\n", ex1);
	    }
	}

}
