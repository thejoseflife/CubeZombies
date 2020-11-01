package com.cubezombies.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

	private BufferedImage bullet = null;
	private Handler handler;
	
	private float width = 30f;
	private float height = 30f;
	private float x;
	private float y;
	
	private double velX = 0;
	
	
	public Bullet(int x, int y, ID id, SpriteSheet ss, Handler handler, BasicZombie bz) {
		super(x, y, id);
		this.x = x;
		this.y = y;
		this.handler = handler;
		bullet = ss.grabImage(1, 1, 16, 16);
		
		velY = -5;
		
	}

	@Override
	public void tick() {
		y += velY;
		x += velX;
		x += 0.23f;
		
		width -= 0.4f;
		height -= 0.4f;
		if(width <= 0 && height <= 0) {
			handler.removeObject(this);
		}
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.BasicZombie || tempObject.id == ID.BossZombie) {
				if(getBounds().intersects(tempObject.getBounds())) {
					
					tempObject.setHealth(tempObject.getHealth() - 20);
					handler.removeObject(this);
				}
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(bullet, (int)x, (int)y, (int)width, (int)height, null);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int) width, (int)height);
	}

}
