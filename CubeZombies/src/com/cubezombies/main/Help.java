package com.cubezombies.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.cubezombies.main.GameCZ.STATE;

public class Help implements MouseListener, MouseMotionListener {
	
	private BufferedImage title = null;
	
	private int timer = 20;
	
	private int r1 = 255;
	private int g1 = 255;
	private int b1 = 255;
	private Point p;
	
	private String text1 = "Use \"UP\",  \"DOWN\", or Mouse Wheel to scroll through inventory. Click to shoot gun or use knife.";
	
	private RoundRectangle2D menubutton = new RoundRectangle2D.Float(475, 600, 400, 125, 200, 200);
	private Button exit;
	private Button minimize;
	
	public Help(BufferedImageLoader loader, GameCZ game) {
		exit = new Button(game, 1);
		minimize = new Button(game, 2);
		
		try {
			title = loader.loadImage("/cubezombiestitle.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public void tick() {
		if(Menu.isChanging == true) {
			timer--;
			if(timer <= 0) {
				Menu.isChanging = false;
				timer = 20;
			}
		}
		
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
		
		g.drawImage(title, 280, 50, null);
		g2d.setColor(Color.WHITE);
		
		Font f1 = new Font("arial", Font.BOLD, 70);
		Font a2 = new Font("arial", Font.BOLD, 20);
		
		//Text v
		
		Font textarial = new Font("arial", Font.PLAIN, 20);
		g.setFont(textarial);
		g.drawString(text1, 250, 400);
		g.drawString("Press \"Escape\" to pause.", 550, 450);
		g.drawString("Good luck!", 620, 500);
		
		//Text ^
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 265, GameCZ.HEIGHT - 50);
		
		g.setFont(f1);
		g2d.setColor(new Color(r1, g1, b1));
		g2d.draw(menubutton);
		g.drawString("Menu", 577, 680);
		g.setFont(a2);
		g.setColor(Color.WHITE);
		g.drawString("by Josef Sajonz", 880, 210);
		
		if(exit != null && minimize != null) {
			exit.render(g);
			minimize.render(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 245, GameCZ.HEIGHT - 10);
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
			if((GameCZ.state == GameCZ.STATE.End || GameCZ.state == GameCZ.STATE.Help) && Menu.isChanging == false) {
				if(menubutton.contains(ep) && (GameCZ.state == GameCZ.STATE.End || GameCZ.state == GameCZ.STATE.Help)) {
					GameCZ.state = STATE.Menu;
				}
			}	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		p = e.getPoint();
		
	}

}
