package com.cubezombies.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import com.cubezombies.main.GameCZ.STATE;

public class Handler {

	private float b1 = 0;
	private float vb1 = 0;
	private Color cb1;
	public static boolean night = false;
	private int timer = 0;
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void reset() {
		b1 = 0;
		vb1 = 0;
		night = false;
		timer = 0;
	}
	
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
		
		b1 = GameCZ.clamp((int)b1, 0, 230);
		
		b1 += vb1;
		
		if(GameCZ.state == STATE.Game || GameCZ.state == STATE.Paused) {
			if(GameCZ.state == STATE.Game) {
			timer++;
			if(timer >= 3600 && !night) {
				vb1 = 1;
				GameCZ.suny++;
			} else if(timer >= 3600 && night) {
				vb1 = -1;
				GameCZ.suny--;
			}
			
			if(GameCZ.suny >= GameCZ.HEIGHT/2) {
				GameCZ.suny = GameCZ.HEIGHT/2;
			} else if(GameCZ.suny <= GameCZ.HEIGHT/2 - 300) {
				GameCZ.suny = GameCZ.HEIGHT/2 - 300;
			}
			
			if(b1 >= 230 && !night) {
				b1 = 230;
				vb1 = 0;
				timer = 0;
				night = true;
			}
			
			//System.out.println(timer/150/2);
			
			if(b1 <= 0 && night) {
				b1 = 0;
				vb1 = 0;
				timer = 0;
				night = false;
			}
			} else if(GameCZ.state == STATE.Paused) {
				timer += 0;
			}
		} else {
			timer = 0;
		}
		
	}
	
	public void render(Graphics g) {
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getID() != ID.Inventory && tempObject.getID() != ID.Player)
			tempObject.render(g);
			
			
		}
		
		cb1 = new Color(0, 0, 0, (int)b1);
		g.setColor(cb1);
		g.fillRect(0, 0, GameCZ.WIDTH, GameCZ.HEIGHT);
		
		Font arial = new Font("arial", Font.BOLD, 50);
		g.setFont(arial);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + GameCZ.score, 10, 110);
		g.drawString("Wave: " + GameCZ.waves, 10, 160);
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getID() == ID.Inventory || tempObject.getID() == ID.Player)
			tempObject.render(g);
			
		}
				
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public void clearEnemies() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.id != ID.Player && tempObject.id != ID.Inventory) {
				this.removeObject(tempObject);
			}
			
		}
	}
}
