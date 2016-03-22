package Utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Utilities {
	
	public static void printMessage(String msg) { 
		System.out.println(msg);
	}
	
	public static void simpleMessage(Graphics g, Color textColor, String msg, String fontFamily, int fontSize, int x, int y) { 
		g.setColor(textColor);
		g.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
		g.drawString(msg, x, y);
	}
	
	public static void complexMessage(Graphics g, Color textColor, String msg, String fontFamily, int fontSize, int x, int textOffset, int y) {       
		int textWidth = (int) (new Font(fontFamily, Font.PLAIN, fontSize).getStringBounds(msg, new FontRenderContext(new AffineTransform(), true, true)).getWidth());
		
		g.setColor(textColor);
		g.drawString(msg, x - textWidth - textOffset, y);
	}
	
	public static int getWidthOfString(String msg) { 
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true, true);     
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		

		return (int) (font.getStringBounds(msg, frc).getWidth());
		
	}
	
	public static int getHeightOfString(String msg) { 
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true, true);     
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		
		
		return (int) (font.getStringBounds(msg, frc).getHeight());
	}
}
