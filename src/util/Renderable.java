package util;

import java.awt.Graphics;

public interface Renderable
{
	/**
	 * A generic method used to render to the canvas.
	 * @param g The Graphics class used to draw to the canvas.
	 */
	void render(Graphics g);
}
