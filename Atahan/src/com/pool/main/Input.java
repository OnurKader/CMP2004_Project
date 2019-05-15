package com.pool.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private Game game;
	
	private final int NUM_OF_KEYS = 256;
	private boolean keys[] = new boolean[NUM_OF_KEYS];
	private boolean keys_last[] = new boolean[NUM_OF_KEYS];

	private final int NUM_OF_BUTTONS = 5;
	private boolean buttons[] = new boolean[NUM_OF_BUTTONS];
	private boolean buttons_last[] = new boolean[NUM_OF_BUTTONS];

	private int mouseX, mouseY, scroll;


	public Input(Game game) {
		this.game = game;
		mouseX = mouseY = scroll = 0;
		game.get_window().get_canvas().addKeyListener(this);
		game.get_window().get_canvas().addMouseListener(this);
		game.get_window().get_canvas().addMouseMotionListener(this);
		game.get_window().get_canvas().addMouseWheelListener(this);
	}
	
	public void update() {
		for(int i = 0; i < NUM_OF_KEYS; i++) {
			keys_last[i] = keys[i];
		}
		
		for(int i = 0; i < NUM_OF_BUTTONS; i++) {
			buttons_last[i] = buttons[i];
		}
		scroll = 0;
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keys_last[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keys_last[keyCode];
	}
	
	public boolean isButton(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return !buttons[button] && buttons_last[button];
	}
	
	public boolean isButtonDown(int button) {
		return buttons[button] && !buttons_last[button];
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int)(e.getX() / game.get_scale());
		mouseY = (int)(e.getY() / game.get_scale());		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX() / game.get_scale());
		mouseY = (int)(e.getY() / game.get_scale());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		scroll = e.getWheelRotation();
	}

	public int mouseX() { return mouseX; }

	public int mouseY() { return mouseY; }
	
	public int get_scroll() { return scroll; }



}
