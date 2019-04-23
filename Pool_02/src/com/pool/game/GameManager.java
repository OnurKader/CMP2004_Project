package com.pool.game;

import com.pool.main.AbstractGame;
import com.pool.main.Game;
import com.pool.main.Renderer;
import com.pool.main.gfx.Image;

public class GameManager extends AbstractGame
{
	private Image image;

	private float x = 0, y = 0, v_x = 1.32f, v_y = 1.51f;
	
	public GameManager() {
		image = new Image("/pool_bg.png");
	}
	
	public static double map(double value, double domain_min, double domain_max, double range_min, double range_max) {
		double temp = value - domain_min;
		temp *= (range_max - range_min);
		temp /= (domain_max - domain_min);
		temp += range_min;
		
		// return (value - domain_min) * ((value - domain_min)*(range_max - range_min) / (domain_max - domain_min)) + range_min;
		return temp;
	}
	
	@Override
	public void update(Game game, float elapsedTime)
	{
		x += v_x;
		y += v_y;
		if(x < 0 || x + 100 > game.get_width()) { v_x *= -1; }
		if(y < 0 || y + 125 > game.get_height()) { v_y *= -1; }
	}

	
	@Override
	public void render(Game game, Renderer r)
	{
		r.draw_image(image, 0, 0);
		r.fill_circle((int)(game.get_width()/3.65), game.get_height() / 2 - 1, 18, 0xFFF7F4FF);
	}
	
	public static void main(String[] args) {
		Game game = new Game(new GameManager());
		game.start();
		
	}

}
