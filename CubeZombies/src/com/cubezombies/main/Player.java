package com.cubezombies.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.cubezombies.main.GameCZ.STATE;	

public class Player extends GameObject {

	public float phealth = 200;
	
	private Handler handler;
	private GameCZ game;

	public BufferedImage k = null;
	
	int height = 64;
	int width = 64;
	
	public int fay = -768;
	
	public int hlength = 200;
	
	public Player(int x, int y, ID id, Handler handler, SpriteSheet ss, BasicZombie bz, GameCZ game) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;
		
		k = ss.grabImage(3, 1, 16, 16);
	}

	public int ky = y - 40;
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.BasicZombie) {
				if(getBounds().intersects(tempObject.getBounds()) && !game.isPaused) {
					phealth -= 0.1f;
				}
			} else if(tempObject.id == ID.BossZombie) {
				if(getBounds().intersects(tempObject.getBounds()) && !game.isPaused) {
					phealth -= 0.4f;
				}
			}
		}
		
		if((GameCZ.waves + 4) % 10 == 0 && phealth < ((GameCZ.waves + 4) / 10) * 150 + 200) {
			hlength = (((GameCZ.waves + 4) / 10) * 150) + 200;
			phealth = (((GameCZ.waves + 4) / 10) * 150) + 200;
		}
	}


	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		if(Inventory.selection == 1) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, 40, 150);
		} else if(Inventory.selection == 2) {
			g.drawImage(k, x, ky, 90, 156, null);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("CubeZombies Version " + GameCZ.VERSION, GameCZ.WIDTH - 265, GameCZ.HEIGHT - 50);
		
		int borderWidth = 1;
		g2d.setStroke(new BasicStroke(borderWidth));
		
		g.setColor(Color.WHITE);
		g.drawRect(9, 9, hlength + 1, 51);
		g.setColor(Color.RED);
		g.fillRect(10, 10, (int)phealth, 50);
		
		Font aarial = new Font("arial", Font.BOLD, 40);
		
		g.setFont(aarial);
		g.setColor(Color.GREEN);
		
		if(phealth <= 0 && GameCZ.state == STATE.Game) {
			g.setColor(Color.RED);
			g.fillRect(0, fay, GameCZ.WIDTH, 768);
			if(fay >= 0) {
				GameCZ.state = STATE.End;
				
			}
		}
		
		g.drawImage(GameCZ.cursor, GameCZ.ex, GameCZ.ey, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0, GameCZ.HEIGHT - 300, GameCZ.WIDTH, 300);
		
	}

}
