package com.cubezombies.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

public class GameCZ extends Canvas implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = -2421751466973514721L;
	public JFrame frame;
	
	private BufferedImage spritesheet = null;
	
	public static final int WIDTH = 1366, HEIGHT = 768;
	public static final double VERSION = 1.0;
	
	public static int score = 0;
	public static int waves = 0;
	public static int zombieskilled = 0;
	public static int highscore = 0;
	public static int suny = HEIGHT/2 - 300;
	
	public static boolean isFinished = false;
	
	private int timer = 20;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean wantsMusic = false;
	public static boolean wantsSound = false;
	public boolean isPaused = false;
	
	private Handler handler;
	private Player player;
	private Inventory inventory;
	private BasicZombie bz;
	private Bullet bullet;
	private SpriteSheet ss;
	private Menu menu;
	private Help help;
	private Paused paused;
	
	public static int ex, ey;
	
	private FinishedScreen fsc;
	
	public static BufferedImage playeri;
	public static BufferedImage cursor = null;
	
	private BufferedImageLoader loader;
	
	public static Random r = new Random();	

	public static enum STATE {
		Game,
		Menu,
		Help,
		End,
		Paused
	}
	
	public static STATE state = STATE.Menu;
	
	public void init() {
		requestFocus();
		
		FW.main(null);
		
		handler = new Handler();
		
		loader = new BufferedImageLoader();
		
			try {

				spritesheet = loader.loadImage("/sprite_sheet.png");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		ss = new SpriteSheet(spritesheet);
		playeri = ss.grabImage(6, 1, 16, 16);
		cursor = ss.grabImage(4, 1, 16, 16);
		
		player = new Player(WIDTH/2 - 100, HEIGHT - 150, ID.Player, handler, ss, bz, this);
		
		bz = new BasicZombie(r.nextInt(GameCZ.WIDTH - 200) + 10, GameCZ.HEIGHT/2 - 128, ID.BasicZombie, loader, handler, player);
		
		handler.addObject(player);
		
		inventory = new Inventory(WIDTH - 110, HEIGHT /2 - 200, ID.Inventory, ss, player, handler);
		handler.addObject(inventory);
		
		menu = new Menu(loader, this);
		help = new Help(loader, this);
		fsc = new FinishedScreen(this);
		paused = new Paused(this, menu);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(new KeyInput(handler, this, inventory, player));
		this.addMouseWheelListener(this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		
		if(score > highscore) {
			highscore = score;
			FW.main(null);
		}
		
		if(menu.a1 > 0) {
			wantsMusic = false;
		} else if(menu.a1 < 0) {
			wantsMusic = true;
		}
		
		if(menu.a2 > 0) {
			wantsSound = true;
		} else if(menu.a2 < 0) {
			wantsSound = false;
		}
		
		handler.tick();

		if(state == STATE.Game && zombieskilled == waves) {
			zombieskilled = 0;
			startWaves();
		}
		
		if(Menu.isChanging == true) {
			this.timer--;
			if(this.timer <= 0) {
				Menu.isChanging = false;
				this.timer = 20;
			}
		}
		
		if(state == STATE.Paused) {
			paused.tick();
		}
		
		if(player.phealth <= 0) {
			
			player.fay += 10;
			if(player.fay >= 0) {
				player.fay = 0;
			}
		}
		
		if(state == STATE.Help) {
			help.tick();
		} else if(state == STATE.Menu) {
			menu.tick();
		} else if(state == STATE.End) {
			fsc.tick();
		}
		
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		Color darkgreen = new Color(28, 153, 46);
		Font arial = new Font("arial", Font.BOLD, 50);
		Font mrial = new Font("arial", Font.BOLD, 25);
		//background
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT/2 - 100);
		g.setColor(Color.YELLOW);
		g.fillOval(WIDTH/2 - 200, suny, 400, 400);
		
		if(Handler.night) {
			g.setColor(Color.WHITE);
			g.fillOval(WIDTH/2 + 250, HEIGHT/2 - 350, 200, 200);
		}
		g.setColor(darkgreen);
		g.fillRect(0, HEIGHT/2 - 100, WIDTH, 584);
	
		g.setColor(Color.BLACK);
		
		//Graphics here v
		
		if(state == STATE.Game) {
			handler.render(g);
			g.setFont(arial);
			g.setColor(Color.WHITE);
			g.drawString("Score: " + score, 10, 110);
			g.drawString("Wave: " + waves, 10, 160);
			
		} else if(state == STATE.End) {
			fsc.render(g);
		} else if(state == STATE.Menu) {
			menu.render(g);
			g.setColor(Color.WHITE);
			g.setFont(mrial);
			g.drawString("Can you beat your highscore of " + highscore + "?", 30, 370);
		} else if(state == STATE.Paused) {
			paused.render(g);
		} else if(state == STATE.Help) {
			help.render(g);
		}
		
		g.drawImage(cursor, ex, ey, null);
		
		//Graphics here ^
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		GameCZ gameCZ = new GameCZ();
		gameCZ.frame = new JFrame("CubeZombies");
		gameCZ.frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		gameCZ.frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		gameCZ.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		gameCZ.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameCZ.frame.setResizable(true);
		gameCZ.frame.setLocationRelativeTo(null);
		
		gameCZ.frame.add(gameCZ);
		gameCZ.frame.pack();
		gameCZ.frame.setVisible(true);

		gameCZ.frame.setCursor(gameCZ.frame.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		
		gameCZ.start();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//System.out.println("Out");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(Inventory.ammo > 0 && Inventory.selection == 1 && state == STATE.Game && player.phealth > 0 && Menu.isChanging == false && !isPaused) {
			bullet = new Bullet((int)player.getX() + 4, (int)player.getY() - 30, ID.Bullet, ss, handler, bz);
			handler.addObject(bullet);
			Inventory.ammo--;
		} else if(state == STATE.End) {
			if(FinishedScreen.trybutton.contains(e.getPoint())) {
				state = STATE.Game;
				restartGame();
			}
			
		} else if(Inventory.selection == 2 && state == STATE.Game && player.phealth > 0) {
			player.ky -= 70;
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(Inventory.selection == 2 && state == STATE.Game) {
			player.ky = player.y - 40;
			inventory.isStabbed = false;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		ex = e.getX() - 8;
		ey = e.getY() - 8;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ex = e.getX() - 8;
		ey = e.getY() - 8;
		
		player.setX(e.getX() - 20);
		
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	
	public void restartGame() {
		player.phealth = 200;
		player.hlength = 200;
		Inventory.ammo = 20;
		Inventory.ammotimer = 600;
		score = 0;
		waves = 0;
		zombieskilled = 0;
		isFinished = false;
		isPaused = false;

		handler.removeObject(inventory);
		handler.removeObject(player);
		handler.clearEnemies();
		handler.reset();
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.BasicZombie || tempObject.id == ID.BossZombie) {
				handler.removeObject(tempObject);
			}
		}
		startWaves();
		handler.addObject(inventory);
		handler.addObject(player);
		inventory.setInventoryToGun();
		state = STATE.Game;
	}
	
	public void startWaves() {
		waves++;
		handler.removeObject(player);
		handler.removeObject(inventory);
		if(waves == 1) {
			
			
			handler.addObject(new BasicZombie(r.nextInt(GameCZ.WIDTH - 200) + 10, GameCZ.HEIGHT/2 - 128, ID.BasicZombie, loader, handler, player));
		} else if(waves % 5 > 0) {
			for(int a = 0; a < waves; a++) {
				handler.addObject(new BasicZombie(r.nextInt(GameCZ.WIDTH - 200) + 10, GameCZ.HEIGHT/2 - 128, ID.BasicZombie, loader, handler, player));

			}
		} else if(waves % 5 == 0) {
			for(int a = 0; a < waves / 5; a++) {
				handler.addObject(new BossZombie(r.nextInt(GameCZ.WIDTH - 200) + 10, GameCZ.HEIGHT/2 - 128, ID.BossZombie, loader, handler, player));
			}
			for(int a = 0; a < r.nextInt(4); a++) {
				zombieskilled--;
				handler.addObject(new BasicZombie(r.nextInt(GameCZ.WIDTH - 200) + 10, GameCZ.HEIGHT/2 - 128, ID.BasicZombie, loader, handler, player));
			}
			
		}
		
		handler.addObject(inventory);
		handler.addObject(player);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if(notches < 0 && Inventory.selection == 2) {
			inventory.pointer.y -= 100;
			Inventory.selection--;
			player.ky = player.y - 40;
			inventory.isStabbed = false;
			notches = 0;
		} else if(notches > 0 && Inventory.selection == 1){
			if(Inventory.selection == 1) {
				inventory.pointer.y += 100;
				Inventory.selection++;
				notches = 0;
			}
		}
		
	}
}
