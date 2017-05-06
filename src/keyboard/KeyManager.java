package keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import engine.Game;
import entities.Snake;
import world.Directions;

/**
 * Manages what happens when a player hits a certain key on their keyboard.
 * 
 * @author nicholasadamou
 */
public class KeyManager extends KeyAdapter
{
	private Snake snake;

	/**
	 * Defines the KeyManager class.
	 * @param g The main Game class that contains the game loop.
	 * @param snake The snake object that the player has control of.
	 */
	public KeyManager(Game g, Snake snake)
	{
		g.addKeyListener(this);
		this.snake = snake;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (Game.inGame)
		{
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				if (snake.direction != Directions.SOUTH)
					snake.direction = Directions.NORTH;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if (snake.direction != Directions.NORTH)
					snake.direction = Directions.SOUTH;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if (snake.direction != Directions.EAST)
					snake.direction = Directions.WEST;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if (snake.direction != Directions.WEST)
					snake.direction = Directions.EAST;
			}
		} else
		{
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
				Game.resetGame();
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}
}