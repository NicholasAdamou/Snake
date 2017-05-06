package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import engine.Game;
import entities.components.Placeable;
import gui.Score;
import util.Renderable;
import world.Directions;
import world.World;

/**
 * Defines a snake as a list of Points that are linked.
 * 
 * @author nicholasadamou
 */
public class Snake implements Placeable, Renderable
{
	private LinkedList<Point> snake;
	public int direction = Directions.NO_DIRECTION;
	
	/**
	 * Defines the Snake object and and initializes all the relevant variables.
	 */
	public Snake()
	{
		init();
	}
	
	/**
	 * Initializes all the relevant variables
	 * and places the snake on the grid.
	 */
	private void init()
	{
		snake = new LinkedList<>();
		place();
	}
	
	@Override
	public void place()
	{
		snake.clear();

		snake.add(new Point(0, 2));
		snake.add(new Point(0, 1));
		snake.add(new Point(0, 0));
		
		direction = Directions.SOUTH;
	}
	
	/**
	 * Moves the snake around the grid
	 * based off of the direction it is moving.
	 * 
	 * Handles the conditions were the snake overlaps with its self,
	 * hits the bounds of the grid or it eats a fruit.
	 */
	public void move()
	{
		Point head = snake.peekFirst();
		Point newPoint = head;

		switch (direction)
		{
		case Directions.NORTH:
			newPoint = new Point(head.x, head.y - 1);
			break;
		case Directions.SOUTH:
			newPoint = new Point(head.x, head.y + 1);
			break;
		case Directions.WEST:
			newPoint = new Point(head.x - 1, head.y);
			break;
		case Directions.EAST:
			newPoint = new Point(head.x + 1, head.y);
			break;
		}

		snake.remove(snake.peekLast());

		if (newPoint.equals(Game.fruit.get()))
		{
			snake.push((Point) newPoint.clone());
			Game.fruit.place();
			Score.score += Score.SCORE_INCREMENT;
		} else if (newPoint.x < 0 || newPoint.x > (World.WORLD_WIDTH - 1))
		{
			Game.inGame(false);
			return;
		} else if (newPoint.y < 0 || newPoint.y > (World.WORLD_HEIGHT - 1))
		{
			Game.inGame(false);
			return;
		} else if (snake.contains(newPoint))
		{
			Game.inGame(false);
			return;
		}

		snake.push(newPoint);
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.green);
		for (Point p : snake)
		{
			g.fillRect(p.x * World.TILE_WIDTH, p.y * World.TILE_HEIGHT, World.TILE_WIDTH, World.TILE_HEIGHT);
		}
		g.setColor(Color.black);
	}
	
	/**
	 * @return The snake LinkedList of (Point) objects.
	 */
	public LinkedList<Point> get()
	{
		return snake;
	}
}
