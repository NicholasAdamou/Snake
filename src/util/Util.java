package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Util
{
	/**
	 * Prints a message to the console.
	 * 
	 * @param msg The message to print to the console.
	 */
	public static void printMessage(String msg)
	{
		System.out.println(msg);
	}

	/**
	 * Draws a standard message to the canvas at a specific x-coordinate and y-coordinate that is not Off set by a certain value.
	 * 
	 * @param g The Graphics class use to draw to the canvas.
	 * @param textColor The color of the text on the canvas.
	 * @param msg The text to be displayed on the canvas.
	 * @param fontFamily The font family of the text.
	 * @param fontSize The size of the font to be drawn to the canvas.
	 * @param x The x-coordinate on the canvas.
	 * @param y The y-coordinate on the canvas.
	 */
	public static void simpleMessage(Graphics g, Color textColor, String msg, String fontFamily, int fontSize, int x,
			int y)
	{
		g.setColor(textColor);
		g.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
		g.drawString(msg, x, y);
	}

	/**
	 * Draws a message to the canvas at a specific x-coordinate and y-coordinate that is not Off set by a certain value.
	 * 
	 * @param g The Graphics class use to draw to the canvas.
	 * @param textColor The color of the text on the canvas.
	 * @param msg The text to be displayed on the canvas.
	 * @param fontFamily The font family of the text.
	 * @param fontSize The size of the font to be drawn to the canvas.
	 * @param x The x-coordinate on the canvas.
	 * @param textOffset The offset in the x-plane
	 * @param y The y-coordinate on the canvas.
	 */
	public static void complexMessage(Graphics g, Color textColor, String msg, String fontFamily, int fontSize, int x,
			int textOffset, int y)
	{
		int textWidth = (int) (new Font(fontFamily, Font.PLAIN, fontSize)
				.getStringBounds(msg, new FontRenderContext(new AffineTransform(), true, true)).getWidth());

		g.setColor(textColor);
		g.drawString(msg, x - textWidth - textOffset, y);
	}

	/**
	 * Calculates the width of a string of text in respect to its font metrics.
	 * @param msg The message to calculate the width of.
	 * @return The width of the screen.
	 */
	public static int getWidthOfString(String msg)
	{
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		Font font = new Font("Tahoma", Font.PLAIN, 12);

		return (int) (font.getStringBounds(msg, frc).getWidth());

	}

	/**
	 * Calculates the height of a string of text in respect to its font metrics.
	 * @param msg The message to calculate the height of.
	 * @return The height of the screen.
	 */
	public static int getHeightOfString(String msg)
	{
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		Font font = new Font("Tahoma", Font.PLAIN, 12);

		return (int) (font.getStringBounds(msg, frc).getHeight());
	}
}
