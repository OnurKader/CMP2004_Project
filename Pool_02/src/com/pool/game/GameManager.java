package com.pool.game;

import java.awt.event.MouseEvent;

import com.pool.main.AbstractGame;
import com.pool.main.Ball;
import com.pool.main.Edge;
import com.pool.main.Game;
import com.pool.main.Input;
import com.pool.main.Renderer;
import com.pool.main.Vector;
import com.pool.main.gfx.Image;

public class GameManager extends AbstractGame
{
	private Image image, cobble, cue;
	private Ball cue_ball, black;
	private Vector middle = new Vector(350, 359);
	private Edge[] edges = new Edge[4];

	public GameManager() {
		image = new Image("/pool_bg.png");
		cobble = new Image("/cobble.png");
		cue = new Image("/cue.png");
		cue_ball = new Ball((int)middle.x(), (int)middle.y(), 0, 1.1);
		black = new Ball(720, 359, 1, 1);
		// TOP-LEFT TOP-RIGHT BOTTOM-LEFT BOTTOM-RIGHT LEFT RIGHT
		edges[0] = new Edge(60, 61, 1216, 61);
		edges[1] = new Edge(60, 652, 1216, 652);
		edges[2] = new Edge(65, 65, 65, 653);
		edges[3] = new Edge(1217, 65, 1217, 653);
	}

	public static double map(double value, double domain_min, double domain_max, double range_min, double range_max) {
		double temp = value - domain_min;
		temp *= (range_max - range_min);
		temp /= (domain_max - domain_min);
		temp += range_min;
		
		return temp;
	}

	private double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
	}


	private boolean selected = false;

	public void mouse_pressed(Game game) {
		Input input = game.get_input();
		if(input.isButtonDown(MouseEvent.BUTTON1)) {
			double dist = dist(input.mouseX(), input.mouseY(), cue_ball.pos.x(), cue_ball.pos.y());
			if(dist <= cue_ball.radius + 1 && !cue_ball.moving) {
				selected = true;
			}else {
				selected = false;
			}
		}
	}

	public void mouse_dragged(Game game) {
		Input input = game.get_input();
		if(input.isButton(MouseEvent.BUTTON1)) {

		}
	}

	public void arrow(Game game, Renderer r) {
		Input input = game.get_input();
		if(input.isButton(MouseEvent.BUTTON1)) {
			if(selected) {
				r.draw_line((int)input.mouseX(), (int)input.mouseY(), (int)cue_ball.pos.x(), (int)cue_ball.pos.y(), 2, 0xFF50FF70);
			}
		}
	}

	public void mouse_released(Game game) {
		Input input = game.get_input();
		if(input.isButtonUp(MouseEvent.BUTTON1)) {
			Vector head = new Vector(input.mouseX() - cue_ball.pos.x(), input.mouseY() - cue_ball.pos.y());
			head.div(Ball.max_vel * -0.9);
			if(selected && !cue_ball.moving) {
				cue_ball.apply_force(head);
				selected = false;
			}
		}
	}

	private Vector cue_pos = new Vector();
	private double cue_angle = 0;
	
	@Override
	public void update(Game game, float elapsedTime)
	{
		cue_ball.update(game);
		black.update(game);
		mouse_pressed(game);
		mouse_released(game);

		cue_ball.collide(black);
		black.collide(cue_ball);
		for(Edge edge: edges) {
			if(cue_ball.moving)
				edge.collide(cue_ball);
			if(black.moving)
				edge.collide(black);
		}
		
		cue_pos.set(game.get_input().mouseX(), game.get_input().mouseY());
		
	}

	@Override
	public void render(Game game, Renderer r)
	{
		r.draw_image(image, 0, 0);
		cue_ball.render(game, r);
		black.render(game, r);
		arrow(game, r);
		r.draw_line((int)cue_ball.pos.x(), (int)cue_ball.pos.y(), (int)(cue_ball.pos.x() + 28*cue_ball.vel.x()), (int)(cue_ball.pos.y() + 28*cue_ball.vel.y()), 3, 0xFFF35187);
		
		r.draw_image(cue, (int)cue_pos.x(), (int)cue_pos.y(), 0.66, cue_angle);
	}

	public static void main(String[] args) {
		Game game = new Game(new GameManager());
		game.start();		
	}

}
