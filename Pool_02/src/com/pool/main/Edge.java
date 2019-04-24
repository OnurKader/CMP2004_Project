package com.pool.main;

public class Edge
{
	// X = 0, Y = 1
	private int orientation;
	private Vector pos_1, pos_2;
	
	public Edge(int x1, int y1, int x2, int y2) {
		pos_1 = new Vector(x1, y1);
		pos_2 = new Vector(x2, y2);
		orientation = y2 - y1 == 0 ? 0 : 1;
	}
	
	public void collide(Ball ball) {
		if(orientation == 0)
		{
			// Horizontal
			if(pos_1.y() < 100)
			{
				// Top
				if(ball.pos.x() + ball.radius >= pos_1.x() && ball.pos.x() - ball.radius <= pos_2.x() && ball.pos.y() - ball.radius <= pos_1.y()) {
					ball.vel.y(-ball.vel.y()*0.99);
					ball.pos.y(ball.pos.y() + 3.14152654);
				}
			}
			else
			{
				// Bottom
				if(ball.pos.x() + ball.radius >= pos_1.x() && ball.pos.x() - ball.radius <= pos_2.x() && ball.pos.y() + ball.radius >= pos_1.y()) {
					ball.vel.y(-ball.vel.y()*0.99);
					ball.pos.y(ball.pos.y() - 3.14152654);
				}
			}
		}
		else
		{
			// Vertical
			if(pos_1.x() < 100)
			{
				// Left
				if(ball.pos.y() + ball.radius >= pos_1.y() && ball.pos.y() - ball.radius <= pos_2.y() && ball.pos.x() - ball.radius <= pos_1.x()) {
					ball.vel.x(-ball.vel.x()*0.99);
					ball.pos.x(ball.pos.x() + 3.14152654);
				}
			}
			else
			{
				// Right
				if(ball.pos.y() + ball.radius >= pos_1.y() && ball.pos.y() - ball.radius <= pos_2.y() && ball.pos.x() + ball.radius >= pos_1.x()) {
					ball.vel.x(-ball.vel.x()*0.99);
					ball.pos.x(ball.pos.x() - 3.14152654);
				}
			}
			
		}
	}


}
