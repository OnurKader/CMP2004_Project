package com.pool.main;

public class Ball
{
	public Vector pos, vel, acc;
	public final int radius = 17;
	private Input input;
	private int id;
	private static int[] colors = {0xFFF8F5FE, 0xFFF9F821, 0xFFFF1212};
	public boolean moving = false, in_pocket = false;
	public static double max_vel = 12;
	private double mass;
	public int cushion_count = 0, ball_count = 0;
	private Ball t_white, t_yellow, t_red;
	public boolean bw, by, br;

	public Ball(int x, int y, int id, double mass) {
		pos = new Vector();
		pos.x(x);
		pos.y(y);
		this.id = id;
		vel = new Vector();
		acc = new Vector();
		this.mass = mass;
	}

	public void init(Ball w, Ball y, Ball r) {
		t_red = r;
		if(id == 0) {
			// White
			t_yellow = y;
		} else if(id == 1) {
			// Yellow
			t_white = w;
		}
	}

	public void apply_force(Vector force) {
		this.acc.add(force.div(mass));
	}

	public void update(Game game) {
		this.vel.add(this.acc);
		this.vel.limit(max_vel);
		this.pos.add(this.vel);
		this.acc.mult(0);
		
		this.vel.mult(0.99623d);

        if (vel.mag() > 0.142) {
            moving = true;
        } else {
            moving = false;
            vel.set_mag(0);
            cushion_count = 0;
            ball_count = 0;
        }
	}

	public void render(Game game, Renderer r) {
		if(!in_pocket) {
			// r.draw_circle((int)pos.x(), (int)pos.y(), radius + 1, 0xFF101010);
			r.fill_circle((int)pos.x(), (int)pos.y(), radius, colors[id]);
		}
	}

    public void collide(Ball other) {
        Vector dist = Vector.sub(other.pos, this.pos);
        double n_dist = dist.mag();

        if (n_dist <= 1 + this.radius + other.radius) {
            dist.normalize();
            this.pos.sub(dist.mult(0.123));
            dist = Vector.sub(this.pos, other.pos);
            dist.normalize();

            Vector new_vel = new Vector(dist.x(), dist.y());
            new_vel.normalize();

            double temp_vel = other.vel.mag();

            this.vel.mult(0.995);
            other.vel.mult(0.99);

            this.apply_force(dist.mult(0.81*temp_vel));
            other.apply_force(new_vel.mult(-0.81*temp_vel));

            // Count Ball Collisions
            if(other == t_white) bw = true;
            else if(other == t_yellow) by = true;
            else if(other == t_red) br = true;

            this.ball_count++;
            other.ball_count++;
        }
    }

}




