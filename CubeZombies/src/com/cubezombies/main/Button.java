package com.cubezombies.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Button implements MouseListener, MouseMotionListener {
	
	private int x = 0, type = 0;
	private Rectangle button;
	private Color color = new Color(161, 161, 161);
	private int red = 255, green = 255, blue = 255;
	private Point p;
	private GameCZ game;
	
	public Button(GameCZ game, int type) {
		if(type == 1) {
			color = Color.RED;
			x = GameCZ.WIDTH - 30;
		} else if(type == 2) {
			x = GameCZ.WIDTH - 60;
		}
		this.type = type;
		this.game = game;
		
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public void tick() {
		if(p != null && button != null) {
			if(button.contains(p)) {
				red = 0;
				green = 0;
				blue = 0;
			} else {
				red = 255;
				green = 255;
				blue = 255;
			}
		}
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		button = new Rectangle(x, 0, 30, 30);
		g2d.setColor(color);
		g2d.fill(button);
		
		g.setColor(new Color(red, green, blue));
		g2d.setStroke(new BasicStroke(3));
		if(type == 1) {
			g.drawLine(x + 25, 5, x + 5, 25);
			g.drawLine(x + 5, 5, x + 25, 25);
		} else if(type == 2) {
			g.drawLine(x + 5, 25, x + 25, 25);
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
		if(e.getPoint() != null && button != null) {
			if(type == 2 && button.contains(e.getPoint())) {
				game.frame.setState(Frame.ICONIFIED);
			} else if(type == 1 && button.contains(e.getPoint())) {
				System.exit(0);
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
		
	}}
