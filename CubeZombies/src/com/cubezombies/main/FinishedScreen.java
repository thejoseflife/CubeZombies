package com.cubezombies.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

public class FinishedScreen implements MouseMotionListener {

	public static RoundRectangle2D trybutton = new RoundRectangle2D.Float(475, 425, 400, 125, 200, 200);
	
	private RoundRectangle2D menubutton = new RoundRectangle2D.Float(475, 600, 400, 125, 200, 200);
	
	private int r1 = 255;
	private int g1 = 255;
	private int b1 = 255;
	private int r2 = 255;
	private int g2 = 255;
	private int b2 = 255;
	private Point p;
	
	public FinishedScreen(GameCZ game) {
		game.addMouseMotionListener(this);
	}
	
	public void tick() {
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
			
			if(trybutton.contains(p)) {
				r2 = 246;
				g2 = 255;
				b2 = 0;
			} else {
				r2 = 255;
				g2 = 255;
				b2 = 255;
			}
		}
	}
	
	public void render(Graphics g) {
		Font large = new Font("arial", Font.BOLD, 100);
		Font medium = new Font("arial", Font.BOLD, 50);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, GameCZ.WIDTH, GameCZ.HEIGHT);
		
		Font f1 = new Font("arial", Font.BOLD, 70);
		
		g.setFont(f1);
		
		g.setColor(Color.WHITE);
		g.setFont(large);
		g.drawString("Oh no, you died!", 280, 200);
		g.setFont(medium);
		
		g.drawString("You survived to wave " + GameCZ.waves, 390, 300);
		g.drawString("Your final score was " + GameCZ.score, 390, 380);
		g.setColor(new Color(r2, g2, b2));
		g.setFont(f1);
		g.drawString("Try again", (int)trybutton.getX() + 45, (int)trybutton.getY() + 80);
		g2d.draw(trybutton);
		g.setColor(new Color(r1, g1, b1));
		g2d.draw(menubutton);
		g.setFont(f1);
		g.drawString("Menu", 577, 680);
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
