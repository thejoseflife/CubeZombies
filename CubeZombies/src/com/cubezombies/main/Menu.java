package com.cubezombies.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu implements MouseListener, MouseMotionListener {

	public BufferedImage title = null;
	private GameCZ game;
	
	public int a1 = -1;
	public int a2 = -1;
	
	private int r1 = 255;
	private int g1 = 255;
	private int b1 = 255;
	private int r2 = 255;
	private int g2 = 255;
	private int b2 = 255;
	private int r3 = 255;
	private int g3 = 255;
	private int b3 = 255;

	
	private Point p;
	
	public static boolean isChanging = false;
	
	private RoundRectangle2D startbutton = new RoundRectangle2D.Float(475, 300, 400, 125, 200, 200);
	private RoundRectangle2D helpbutton = new RoundRectangle2D.Float(475, 450, 400, 125, 200, 200);
	private RoundRectangle2D quitbutton = new RoundRectangle2D.Float(475, 600, 400, 125, 200, 200);
	
	private Rectangle musicbutton = new Rectangle(10, GameCZ.HEIGHT - 140, 100, 100);
	private Rectangle soundbutton = new Rectangle(120, GameCZ.HEIGHT - 140, 100, 100);
	private Button exit;
	private Button minimize;
	
	public Menu(BufferedImageLoader loader, GameCZ game) {
		this.game = game;
		
		try {
			title = loader.loadImage("/cubezombiestitle.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		exit = new Button(game, 1);
		minimize = new Button(game, 2);
		
		game.addMouseMotionListener(this);
		game.addMouseListener(this);
	}
	
	public void tick() {
		
		if(exit != null && minimize != null) {
			exit.tick();
			minimize.tick();
		}
		
		if(p != null) {
		if(startbutton.contains(p)) {
			r1 = 246;
			g1 = 255;
			b1 = 0;
		} else {
			r1 = 255;
			g1 = 255;
			b1 = 255;
		}
		
		if(helpbutton.contains(p)) {
			r2 = 246;
			g2 = 255;
			b2 = 0;
		} else {
			r2 = 255;
			g2 = 255;
			b2 = 255;
		}
		
		if(quitbutton.contains(p)) {
			r3 = 246;
			g3 = 255;
			b3 = 0;
		} else {
			r3 = 255;
			g3 = 255;
			b3 = 255;
		}
		}
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.drawImage(title, 280, 50, null);
		
		Font f1 = new Font("arial", Font.BOLD, 70);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 265, GameCZ.HEIGHT - 50);
		
		g.setFont(f1);
		g2d.setColor(new Color(r1, g1, b1));
		g2d.draw(startbutton);
		g.drawString("Play", 598, 380);
		
		g2d.setColor(new Color(r2, g2, b2));
		g2d.draw(helpbutton);
		g.drawString("Help", 598, 530);
		
		g2d.setColor(new Color(r3, g3, b3));
		g2d.draw(quitbutton);
		g.drawString("Quit", 598, 680);
		
		Font a2 = new Font("arial", Font.BOLD, 20);
		
		g.setFont(a2);
		g.setColor(Color.WHITE);
		g.drawString("by Josef Sajonz", 880, 210);
		
		g2d.draw(musicbutton);
		g2d.draw(soundbutton);
		g.drawString("Music", 20, GameCZ.HEIGHT - 150);
		g.drawString("Sound", 130, GameCZ.HEIGHT - 150);
		
		if(exit != null && minimize != null) {
			exit.render(g);
			minimize.render(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 245, GameCZ.HEIGHT - 10);
		
		if(GameCZ.wantsMusic == false) {
			g.setColor(Color.WHITE);
			g.fillRect(23, GameCZ.HEIGHT - 127, 75, 75);
		}
		if(GameCZ.wantsSound == true) {
			g.setColor(Color.WHITE);
			g.fillRect(133, GameCZ.HEIGHT - 127, 75, 75);
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point2D ep = e.getPoint();
			if(GameCZ.state == GameCZ.STATE.Menu) {
				if(startbutton.contains(ep) && isChanging == false) {
					game.restartGame();
					isChanging = true;
				} else if(helpbutton.contains(ep)) {
					GameCZ.state = GameCZ.STATE.Help;
					isChanging = true;
				} else if(quitbutton.contains(ep)) {
					System.exit(0);
				} else if(musicbutton.contains(ep)) {
					a1 = -a1;
				} else if(soundbutton.contains(ep)) {
					a2 = -a2;
				}
			}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		p = e.getPoint();
		
	}

}
