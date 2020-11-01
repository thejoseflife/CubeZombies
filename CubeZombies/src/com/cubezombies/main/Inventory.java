package com.cubezombies.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inventory extends GameObject{

	private BufferedImage gun1 = null;
	private BufferedImage knife1 = null;
	private Player player;
	private Handler handler;

	public static int ammo = 20;
	public static int ammotimer = 600;
	public int ammotimerdisplay = 0;
	public int maxAmmo = 20;
	private int ammoIncremental = 5;
	private boolean ammoIncremented = false;
	
	public boolean isStabbed = false;
	
	public static int selection = 1;
		
	public Inventory(int x, int y, ID id, SpriteSheet ss, Player player, Handler handler) {
		super(x, y, id);
		this.player = player;
		this.handler = handler;
		
		gun1 = ss.grabImage(2, 1, 16, 16);
		knife1 = ss.grabImage(3, 1, 16, 16);
		
		
	}
	
	public Rectangle pointer = new Rectangle(x, y, 100, 100);

	@Override
	public void tick() {
		
		if(GameCZ.state == GameCZ.STATE.Game) {
			if(ammo == 0) {
				ammotimer--;
				ammotimerdisplay = (ammotimer / 100) + 1;
			}
		
			if(ammotimer == 0) {
				ammo = maxAmmo;
				ammotimer = 600;
			}
		}
		
		if((GameCZ.waves + 4) % 10 == 0 && maxAmmo < ((GameCZ.waves + 4) / 10) * 10 + 20 && !ammoIncremented) {
			
			maxAmmo = ((GameCZ.waves + 4) / 10) * ammoIncremental + 20;
			ammo = maxAmmo;
			ammoIncremented = true;
		} else ammoIncremented = false;
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id == ID.BasicZombie || tempObject.id == ID.BossZombie) {
				if(getKnifeBounds().intersects(tempObject.getBounds()) && isStabbed == false) {
					tempObject.setHealth(tempObject.getHealth() - 30);
					isStabbed = true;
				}
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		Font arial = new Font("arial", Font.BOLD, 15);
		Font aarial = new Font("arial", Font.BOLD, 40);
		
		int borderWidth1 = 1;
		g2d.setStroke(new BasicStroke(borderWidth1));
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, 100, 100);
		g.drawRect(x, y + 100, 100, 100);
		
		g2d.drawImage(gun1, x - 5, y, 100, 100, null);

		g2d.drawImage(knife1, x + 33, y + 115, 70, 70, null);
		g2d.setFont(arial);
		g2d.drawString("Ammo: " + ammo, x + 10, y + 90);
		
		if(ammo == 0) {
			g.setFont(aarial);
			g.drawString("Reloading...", GameCZ.WIDTH/2 - 100, GameCZ.HEIGHT/2 - 100);
			g.setFont(new Font("arial", Font.BOLD, 60));
			g.drawString("" + ammotimerdisplay, GameCZ.WIDTH/2 - 15, GameCZ.HEIGHT/2 - 40);
			g.setFont(aarial);
		}
		
		int borderWidth = 10;
		g2d.setStroke(new BasicStroke(borderWidth));
		g2d.draw(pointer);
	
	}
	
	public void setInventoryToGun() {
		if(selection == 2) {
			pointer.y -= 100;
			selection--;
			player.ky = player.y - 40;
			isStabbed = false;
		}
	}
	
	public void setInventoryToKnife() {
		if(selection == 1) {
			pointer.y += 100;
			selection++;
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
	
	public Rectangle getKnifeBounds() {
		return new Rectangle(player.x, player.ky - 60, 40, 156);
	}

}
