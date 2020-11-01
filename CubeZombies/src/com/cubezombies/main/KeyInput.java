package com.cubezombies.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.cubezombies.main.GameCZ.STATE;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private GameCZ game;
	private Inventory inventory;
	
	private boolean[] keyDown = new boolean[2];
	
	public KeyInput(Handler handler, GameCZ game, Inventory inventory, Player player) {
		this.handler = handler;
		this.game = game;
		this.inventory = inventory;
		
		keyDown[0] = false;
		keyDown[1] = false;
	}
	
	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		
		if(GameCZ.state == GameCZ.STATE.Game) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject TempObject = handler.object.get(i);
					if(TempObject.id == ID.Inventory) {
						if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_2) {
							inventory.setInventoryToKnife();
						} else if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_1) {
							inventory.setInventoryToGun();
						}
					}
			}
			if(key == KeyEvent.VK_R && Inventory.ammo < inventory.maxAmmo) {
				Inventory.ammo = 0;
			}
		}
		if(GameCZ.state == STATE.Game || GameCZ.state == STATE.Paused) {
			if(key == KeyEvent.VK_ESCAPE) {
				game.isPaused = !game.isPaused;
				if(game.isPaused) {
					GameCZ.state = STATE.Paused;
				} else {
					GameCZ.state = STATE.Game;
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent ke) {
		
	}
	
}

