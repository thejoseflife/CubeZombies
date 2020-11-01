package com.cubezombies.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BasicZombie extends GameObject {
	
	private Handler handler;
	
	public float width = 16, height = 32;
	public int health = 100;
	
	private BufferedImage[] zombiea = new BufferedImage[2];
	private Animation a;
	private Player player;

	public BasicZombie(int x, int y, ID id, BufferedImageLoader loader, Handler handler, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		
		try {
			zombiea[0] = loader.loadImage("/zombieanimation1.png");
			zombiea[1] = loader.loadImage("/zombieanimation2.png");

		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		a = new Animation(5, zombiea[0], zombiea[1]);
		
	}

	@Override
	public void tick() {
		
		if(GameCZ.state == GameCZ.STATE.Game) {
			if(height < 50) {
				height += 0.1f;
				width += 0.05f;
				
			} else if(height < 100 && height >= 50) {
				height += 0.23f;
				width += 0.115f;
			} else if(height >= 100 && height < 150) {
				height += 0.5f;
				width += 0.25f;
			} else if(height >= 150 && height < 250) {
				height += 0.75f;
				width += 0.325f;
			} else if(height >= 250 && height < 400) {
				height += 1;
				width += 0.5f;
			} else if(height >= 400 && height < 700) {
				height += 1.8f;
				width += 0.9f;
			} else if(height >= 700) {
				width += 0;
				height += 0;
			
			}
		}
		
		
		a.runAnimation();
		
		if(health <= 0) {
			GameCZ.zombieskilled++;
			GameCZ.score++;
			handler.removeObject(this);
			
		}
		
		if(player.phealth <= 0) {
			handler.removeObject(this);
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		a.drawAnimation(g, x, y, 0, this);

		int borderWidth = 1;
		g2d.setStroke(new BasicStroke(borderWidth));
		
		g.setColor(Color.RED);
		g.fillRect((int)x + (int)(width/2) - 50, (int)(y - 50), health, 30);
		
		
		g.setColor(Color.WHITE);
		g.drawRect((int)x + (int)(width/2) - 50, (int)(y - 50), 100, 30);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, (int) width - (int)(width/16), (int)height/3);
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}
