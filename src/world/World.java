package world;

import java.awt.Color;
import java.awt.Graphics;

import util.Renderable;

/**
 * A class used to define the constraints of the in-game world and how to render it to the canvas.
 * @author nicholasadamou
 *
 */
public class World implements Renderable
{
	public static final int TILE_WIDTH = 15;
	public static final int TILE_HEIGHT = 15;
	public static final int WORLD_WIDTH = 25;
	public static final int WORLD_HEIGHT = 25;

	
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);

		g.drawRect(0, 0, WORLD_WIDTH * TILE_WIDTH, WORLD_HEIGHT * TILE_HEIGHT);

		for (int x = TILE_WIDTH; x < WORLD_WIDTH * TILE_WIDTH; x += TILE_WIDTH)
			g.drawLine(x, 0, x, WORLD_WIDTH * TILE_WIDTH);
		for (int y = TILE_HEIGHT; y < WORLD_HEIGHT * TILE_HEIGHT; y += TILE_HEIGHT)
			g.drawLine(0, y, WORLD_HEIGHT * TILE_HEIGHT, y);
	}
}
