package GameEngine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

import Utilities.Utilities;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	private static final String NAME = "Snake";
	public static final int WIDTH = 376;
	public static final int HEIGHT = 436; 
	public static final int SCALE = 1;

	private boolean isRunning = false;
	
	private LinkedList<Point> snake;
	private Point fruit;
	
	private static final int TILE_WIDTH = 15;
	private static final int TILE_HEIGHT = 15;
	private static final int WORLD_WIDTH = 25;
	private static final int WORLD_HEIGHT = 25;
	
	private int direction = Directions.NO_DIRECTION;
	public class Directions {
		public static final int NO_DIRECTION = 0;
		public static final int NORTH = 1;
		public static final int SOUTH = 2;
		public static final int WEST = 3;
		public static final int EAST = 4;
	}
	
	private int score = 0;
	public static final int SCORE_INCREMENT = 10;
	private boolean inGame = true;
	
	public static final String DEFAULT_FONT_FAMILY = "monospaced";
	public static final int DEFAULT_FONT_SIZE = 36;
	
	public Game() { 
		init();
	}
	
	public void init() { 
		this.addKeyListener(new KeyManager()); 
		snake = new LinkedList<>();
		fruit = new Point();
		
		placeSnake();
		placeFruit();
	}
	
	public void start() { 
		new Thread(this, Game.NAME);
		isRunning = true;
		run();
	}
	
	public void stop() { 
		isRunning = false;
	}
	
	@Override
	public void run() { 
		while (isRunning) { 
			tick();
			render();
			
			try { 
				Thread.currentThread();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() { 
		if (inGame) move();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		if (inGame) { 
			renderScore(g);
			renderGrid(g);
			renderSnake(g);
			renderFruit(g);
		} else { 
			endGame(g);
		} 
		
		g.dispose();
		bs.show();
	}
	
	public void move() { 
		Point head = snake.peekFirst();
		Point newPoint = head;
		
		switch(direction) { 
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
		
		if (newPoint.equals(fruit)) {
			snake.push((Point) newPoint.clone());
			placeFruit();
			score += Game.SCORE_INCREMENT;
		} else if (newPoint.x < 0 || newPoint.x > (WORLD_WIDTH - 1)) { 
			inGame(false);
			return;
		} else if (newPoint.y < 0 || newPoint.y > (WORLD_HEIGHT - 1)) { 
			inGame(false);
			return;
		} else if (snake.contains(newPoint)) { 
			inGame(false);
			return;
		}
		
		snake.push(newPoint);
	}
	
	public class KeyManager extends KeyAdapter { 
	 	@Override
	    public void keyPressed(KeyEvent e) {
	 		if (inGame) { 
	 			if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (direction != Directions.SOUTH) direction = Directions.NORTH;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
					if (direction != Directions.NORTH) direction = Directions.SOUTH;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) { 
					if (direction != Directions.EAST) direction = Directions.WEST;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
					if (direction != Directions.WEST) direction = Directions.EAST;
				}
			} else { 
				if (e.getKeyCode() == KeyEvent.VK_SPACE) resetGame();
			} 
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
	 	}
	}
	
	public void renderScore(Graphics g) { 
		int fontSize = 16;
		int padding = -20;
		Utilities.simpleMessage(g, Color.white, "Score: " + score, DEFAULT_FONT_FAMILY, fontSize, (getWidth() / 2) - (Utilities.getWidthOfString("Score: " + score)), getHeight() + padding);
	}
	
	public void renderGrid(Graphics g) { 
		g.setColor(Color.white); 
		
		g.drawRect(0, 0, WORLD_WIDTH * TILE_WIDTH, WORLD_HEIGHT * TILE_HEIGHT);
		
		for (int x = TILE_WIDTH; x < WORLD_WIDTH * TILE_WIDTH; x += TILE_WIDTH) g.drawLine(x, 0, x, WORLD_WIDTH * TILE_WIDTH);
		for (int y = TILE_HEIGHT; y < WORLD_HEIGHT * TILE_HEIGHT; y += TILE_HEIGHT) g.drawLine(0, y, WORLD_HEIGHT * TILE_HEIGHT, y);
	}
	
	public void renderSnake(Graphics g) { 
		g.setColor(Color.green);
		for (Point p : snake) { 
			g.fillRect(p.x * TILE_WIDTH, p.y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		}
		g.setColor(Color.black);
	}
	
	public void placeSnake() { 
		snake.clear();
		
		snake.add(new Point(0, 2));
		snake.add(new Point(0, 1));
		snake.add(new Point(0, 0));
		
		direction = Directions.SOUTH;
	}
	
	public void renderFruit(Graphics g) { 
		g.setColor(Color.red);
		g.fillOval(fruit.x * TILE_WIDTH, fruit.y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(Color.black);
	}
	
	public void placeFruit() {
		int x = new Random().nextInt(WORLD_WIDTH);
		int y = new Random().nextInt(WORLD_HEIGHT);
		
		Point randomLocation = new Point(x, y);
		
		if (snake.contains(randomLocation)) {
			x = new Random().nextInt(WORLD_WIDTH);
			y = new Random().nextInt(WORLD_HEIGHT);
			randomLocation = new Point(x, y);
		}
		
		fruit = randomLocation;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void resetGame() {
		inGame(true);
		
		setScore(0);
		placeSnake();
		placeFruit();
	}
	
	public static void endGame(Graphics g) { 
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		int fontSize = 16;
		int padding = 10;
		Utilities.simpleMessage(g, Color.white, "press [Space] to restart the Game.", Game.DEFAULT_FONT_FAMILY, fontSize, (Game.WIDTH / 2) - Utilities.getWidthOfString("press [Space] to resert the Game.") + padding, Game.HEIGHT / 2);
	}
	
	public void inGame(boolean inGame) {
		this.inGame = inGame;
	}
	
	public static void main(String[] args) { 
		Game game = new Game();
		
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}
}
