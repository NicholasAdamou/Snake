package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import engine.Game;
import entities.components.Placeable;
import util.Renderable;
import world.World;

/**
 * Defines the fruit object in the game of Snake.
 * 
 * @author nicholasadamou
 */
public class Fruit implements Placeable, Renderable
{
	private Point fruit;
	
	/**
	 * Defines the fruit object and initializes all the relevant variables.
	 */
	public Fruit()
	{
		init();
	}
	
	/**
	 * Initializes all the relevant variables
	 * and places the fruit on a random grid
	 * spot on the grid.
	 */
	private void init()
	{
		fruit = new Point();
		place();
	}
	
	@Override
	public void place()
	{
		int x = new Random().nextInt(World.WORLD_WIDTH);
		int y = new Random().nextInt(World.WORLD_HEIGHT);

		Point randomLocation = new Point(x, y);

		if (Game.snake.get().contains(randomLocation))
		{
			x = new Random().nextInt(World.WORLD_WIDTH);
			y = new Random().nextInt(World.WORLD_HEIGHT);
			randomLocation = new Point(x, y);
		}

		fruit = randomLocation;
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.fillOval(fruit.x * World.TILE_WIDTH, fruit.y * World.TILE_HEIGHT, World.TILE_WIDTH, World.TILE_HEIGHT);
		g.setColor(Color.black);
	}
	
	/**
	 * @return The fruit (Point) object.
	 */
	public Point get()
	{
		return fruit;
	}

}
