package com.pool.main;

public class Ball
{
	public Vector pos, vel, acc;
	public final int radius = 17;
	private Input input;
	private int id;
	private static int[] colors = {0xFFF8F5FE, 0xFF121112};
	public boolean moving = false, in_pocket = false;
	public static double max_vel = 10.75;
	private double mass;

	public Ball() {
		pos = new Vector(0, 2, 1);
		pos.x(0);
		pos.y(0);
		vel = new Vector(0, 2, 1);
		acc = new Vector(0, 2, 1);
		this.id = 0;
		mass = 1.0d;
	}

	public Ball(int x, int y, int id, double mass) {
		pos = new Vector();
		pos.x(x);
		pos.y(y);
		this.id = id;
		vel = new Vector();
		acc = new Vector();
		this.mass = mass;
	}

//0xFFF7F4FF
	
	public void apply_force(Vector force) {
		this.acc.add(force.div(mass));
	}
	
	public void update(Game game) {
		if(pos.x() - radius <= 0 || pos.x() + radius >= game.get_width()) {
			vel.x(-vel.x());
		}
		if(pos.y() - radius <= 0 || pos.y() + radius >= game.get_height()) {
			vel.y(-vel.y());
		}
		
		this.vel.add(this.acc);
		this.vel.limit(max_vel);
		this.pos.add(this.vel);
		this.acc.mult(0);
		
		this.vel.mult(0.99628);

        if (vel.mag() > 0.138) {
            moving = true;
        } else {
            moving = false;
            vel.set_mag(0);
        }
		
		
	}

	public void render(Game game, Renderer r) {
		if(!in_pocket) {
			r.fill_circle((int)pos.x(), (int)pos.y(), radius, colors[id]);
		}
	}
	
    public void collide(Ball other) {
        Vector dist = Vector.sub(other.pos, this.pos);
        double n_dist = dist.mag();

        if (n_dist <= 1 + this.radius + other.radius) {
            dist = dist.normalize();
            this.pos.sub(dist.mult(0.1));

            Vector _dist = Vector.sub(this.pos, other.pos);
            _dist = _dist.normalize();

            Vector n_v = new Vector(_dist.x(), _dist.y());
            n_v.normalize();

            double v_store = other.vel.mag();

            this.vel.mult(0.95);
            other.vel.mult(0.94);

            this.apply_force(_dist.mult(0.5*v_store));
            other.apply_force(n_v.mult(-0.5*v_store));
        }
    }
	
	
	
	
}




