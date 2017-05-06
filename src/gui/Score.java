package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import engine.Game;
import util.Renderable;
import util.Util;

/**
 * Handles the creation and displaying of the score in the game.
 * 
 * @author nicholasadamou
 */
public class Score implements Renderable
{
	public static final String HIGH_SCORES_FILE_NAME = "scores";
	public static final int MAX_HIGH_SCORE_DISPLAY = 6;
	public static final int SCORE_INCREMENT = 10;
	
	public static int score;
	
	private ArrayList<Integer> list = new ArrayList<>();
	
	private boolean hasWrittenScores = false;
	private Game game;

	/**
	 * Defines the Score object that takes a reference to the 
	 * main game class.
	 * @param game The main game class that contains the game loop.
	 */
	public Score(Game game)
	{
		this.game = game;
		
		init();
	}
	
	/**
	 * Initializes the score to 0.
	 */
	private void init()
	{
		score = 0;
	}
	
	/**
	 * Handles weather or not to write to the high scores file every frame.
	 */
	public void tick()
	{
		if (!Game.inGame && !hasWrittenScores)
		{
			update();
			
			hasWrittenScores = !hasWrittenScores;
		}
	}
	
	/**
	 * Updates the high scores file and sorts the scores from highest to lowest.
	 */
	public void update()
	{
		File file = new File(HIGH_SCORES_FILE_NAME);
		
		if (!file.exists())
		{
			try 
			{
				PrintWriter out = new PrintWriter(HIGH_SCORES_FILE_NAME);
				out.println(score);
				out.close();
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		write(HIGH_SCORES_FILE_NAME, score);
		list = get(HIGH_SCORES_FILE_NAME);
		Collections.sort(list);
		Collections.reverse(list);
	}
	
	/**
	 * Writes a score to a file in the root of this project. 
	 * 
	 * @param fileName The name of the file to write to.
	 * @param score The score to be written to the file given.
	 */
	private void write(String fileName, int score)
	{
		if (score == 0) 
			return;
		else 
		{
			try (FileWriter fw = new FileWriter(fileName, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw))
			{
				out.println(score);
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Reads in a list of scores from a text file in the root of the project.
	 * 
	 * @param fileName The name of the file to read.
	 * @return The list of high scores.
	 */
	private ArrayList<Integer> get(String fileName)
	{
		Scanner in = null;
		ArrayList<Integer> scores = new ArrayList<>();

		try
		{
			in = new Scanner(new File(fileName));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		int data = 0;
		while (in.hasNextInt())
		{
			data = in.nextInt();
			scores.add(data);
		}

		in.close();

		return scores;
	}
	
	/**
	 * Resets the score board.
	 */
	public void reset()
	{
		score = 0;
		hasWrittenScores = false;
	}
	
	/**
	 * Renders the high score section of the end game screen.
	 * 
	 * @param g The Graphics class used to add content to the canvas.
	 */
	public void renderHighScores(Graphics g)
	{
		int fontSize = 16;
		int padding = 10;
		String text = "High Scores";

		Util.simpleMessage(g, Color.WHITE, text, Game.DEFAULT_FONT_FAMILY, fontSize,
				(Game.WIDTH / 2) - Util.getWidthOfString(text) + padding, 75);
		
		int count = 1;
		for (Integer i : list)
		{
			if (count == MAX_HIGH_SCORE_DISPLAY)
				return;
			
			Util.simpleMessage(g, Color.WHITE, i + "", Game.DEFAULT_FONT_FAMILY, 16,
					(Game.WIDTH / 2) - Util.getWidthOfString(text) + 50, (count * 50) + (count + 75));
			
			count++;
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		int fontSize = 16;
		int padding = -20;
		Util.simpleMessage(g, Color.white, "Score: " + score, Game.DEFAULT_FONT_FAMILY, fontSize,
				(game.getWidth() / 2) - (Util.getWidthOfString("Score: " + score)), game.getHeight() + padding);
		
	}
	
	/**
	 * @return The list of high scores.
	 */
	public ArrayList<Integer> get()
	{
		return list;
	}
}
