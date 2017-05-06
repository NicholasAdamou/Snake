package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import entities.Fruit;
import entities.Snake;
import gui.Score;
import keyboard.KeyManager;
import util.Util;
import world.World;

/**
 * The main game class that contains the run(), tick() and render()
 * functionality. This class can be thought of as the central processing point at which the
 * game runs. Without it, the game could not function.
 * 
 * @author nicholasadamou
 */
@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable
{
	public static final String NAME = "Snake";
	public static final int WIDTH = 376;
	public static final int HEIGHT = 436;
	public static final int SCALE = 1;
	public static final String DEFAULT_FONT_FAMILY = "monospaced";
	public static final int DEFAULT_FONT_SIZE = 36;

	public static Snake snake;
	public static Fruit fruit;
	public static Score score;
	public static KeyManager kManager;
	public static boolean inGame = true;

	public World world;

	private boolean isRunning = false;

	/**
	 * Defines the game object and initializes all relevant variables.
	 */
	public Game()
	{
		init();
	}

	/**
	 * Initializes all relevant variables.
	 */
	private void init()
	{
		snake = new Snake();
		fruit = new Fruit();
		score = new Score(this);
		world = new World();

		kManager = new KeyManager(this, snake);

		score.update();
	}

	/**
	 * Starts the game thread.
	 */
	public void start()
	{
		new Thread(this, Game.NAME);
		isRunning = true;
		run();
	}

	/**
	 * Stops the game thread.
	 */
	public void stop()
	{
		isRunning = false;
	}

	@Override
	public void run()
	{
		while (isRunning)
		{
			tick();
			render();

			try
			{
				Thread.currentThread();
				Thread.sleep(100);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		stop();
	}

	/**
	 * Updates the game every frame.
	 */
	private void tick()
	{
		if (inGame)
			snake.move();

		score.tick();
	}

	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		if (inGame)
		{
			score.render(g);
			world.render(g);
			snake.render(g);
			fruit.render(g);
		} else
		{
			end(g);
		}

		g.dispose();
		bs.show();
	}

	/**
	 * Renders the end game screen.
	 * 
	 * @param g Graphics class used to render to the canvas.
	 */
	private static void end(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		int fontSize = 16;
		int padding = 10;
		String text = "press [Space] to restart the Game.";

		if (!score.get().isEmpty())
		{
			score.renderHighScores(g);

			Util.simpleMessage(g, Color.white, text, Game.DEFAULT_FONT_FAMILY, fontSize,
					(Game.WIDTH / 2) - Util.getWidthOfString(text) + padding,
					Game.HEIGHT - Util.getHeightOfString(text));
		} else
		{
			Util.simpleMessage(g, Color.white, text, Game.DEFAULT_FONT_FAMILY, fontSize,
					(Game.WIDTH / 2) - Util.getWidthOfString(text) + padding, Game.HEIGHT / 2);
		}
	}

	/**
	 * Sets the state of the game. 
	 * If true, we are playing the game. 
	 * If false, we are in the end game screen.
	 * 
	 * @param inGame Weather or not we are in the game.
	 */
	public static void inGame(boolean inGame)
	{
		Game.inGame = inGame;
	}
	
	/**
	 * Resets the game.
	 */
	public static void resetGame()
	{
		inGame(true);

		score.reset();
		snake.place();
		fruit.place();
	}
}
