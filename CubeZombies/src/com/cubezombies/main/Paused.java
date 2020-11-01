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

public class Paused implements MouseListener, MouseMotionListener {

	private RoundRectangle2D menubutton = new RoundRectangle2D.Float(475, 600, 400, 125, 200, 200);
	private Menu menu;
	
	private int r1 = 255;
	private int g1 = 255;
	private int b1 = 255;
	private Point p;
	
	private Rectangle musicbutton = new Rectangle(10, GameCZ.HEIGHT - 140, 100, 100);
	private Rectangle soundbutton = new Rectangle(120, GameCZ.HEIGHT - 140, 100, 100);
	private Button exit;
	private Button minimize;
	
	public Paused(GameCZ game, Menu menu) {
		this.menu = menu;
		
		exit = new Button(game, 1);
		minimize = new Button(game, 2);
		
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public void tick() {
		if(exit != null && minimize != null) {
			exit.tick();
			minimize.tick();
		}
		
		if(p != null) {
			if(menubutton.contains(p)) {
				r1 = 246;
				g1 = 255;
				b1 = 0;
			} else {
				r1 = 255;
				g1 = 255;
				b1 = 255;
			}
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		Font f = new Font("arial", Font.BOLD, 100);
		Font f1 = new Font("arial", Font.BOLD, 70);
		
		g.setColor(Color.GRAY);
		g.fillRect(0,  0,  GameCZ.WIDTH, GameCZ.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString("Paused", 500, 270);
		
		g.setFont(f1);
		g2d.setColor(new Color(r1, g1, b1));
		g2d.draw(menubutton);
		g.drawString("Menu", 577, 680);
		
		Font a2 = new Font("arial", Font.BOLD, 20);
		
		g.setFont(a2);
		g.setColor(Color.WHITE);
		g2d.draw(musicbutton);
		g2d.draw(soundbutton);
		g.drawString("Music", 20, GameCZ.HEIGHT - 150);
		g.drawString("Sound", 130, GameCZ.HEIGHT - 150);
		
		if(GameCZ.wantsMusic == false) {
			g.setColor(Color.WHITE);
			g.fillRect(23, GameCZ.HEIGHT - 127, 75, 75);
		}
		if(GameCZ.wantsSound == true) {
			g.setColor(Color.WHITE);
			g.fillRect(133, GameCZ.HEIGHT - 127, 75, 75);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString("Escape to resume.", 565, 340);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 265, GameCZ.HEIGHT - 50);
		
		if(exit != null && minimize != null) {
			exit.render(g);
			minimize.render(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 245, GameCZ.HEIGHT - 10);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point2D ep = e.getPoint();
		
		if(GameCZ.state == GameCZ.STATE.Paused) {
			if(menubutton.contains(ep)) {
				GameCZ.state = GameCZ.STATE.Menu;
			} else if(musicbutton.contains(ep)) {
				menu.a1 = -menu.a1;
			} else if(soundbutton.contains(ep)) {
				menu.a2 = -menu.a2;
			}
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
