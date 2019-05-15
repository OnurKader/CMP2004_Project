package com.pool.game;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.pool.main.AbstractGame;
import com.pool.main.Ball;
import com.pool.main.Edge;
import com.pool.main.Game;
import com.pool.main.Input;
import com.pool.main.Player;
import com.pool.main.Renderer;
import com.pool.main.Vector;
import com.pool.main.gfx.Image;
import com.pool.network.GameClient;
import com.pool.network.GameListener;

public class GameManager extends AbstractGame
{
	private static final long serialVersionUID = 7572536865717308793L;
	private Image image, cue;
	private Ball white, yellow, red;
	private Vector middle = new Vector(350, 359);
	private Edge[] edges = new Edge[4];
	private static double power = 0.0d;
	private Vector cue_pos = new Vector();
	private double cue_angle = 0;
	private double power_amount = Math.PI / 2.0d;
	private int turn = 0;
	private Player player_1, player_2;
	private boolean motion = false, checked = false;
	private double speed = 0;
	private int mode;
	public static boolean receive = false;
	private int highscore = 0;

	private void save_highscore()
	{
		try
		{
			FileWriter fstream = new FileWriter("/highscore.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(highscore);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void load_highscore()
	{
		try (BufferedReader br = new BufferedReader(new FileReader("/highscore.txt")))
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String temp = sb.toString();
			highscore = Integer.parseInt(temp);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public GameManager(int mode)
	{
		this.mode = mode;
		image = new Image("/pool_bg.png");
		cue = new Image("/cue.png");
		white = new Ball((int) middle.x(), (int) middle.y(), 0, 1.11);
		yellow = new Ball(724, 444, 1, 1.11);
		red = new Ball(724, 256, 2, 1.01);
		white.init(white, yellow, red);
		yellow.init(white, yellow, red);
		red.init(white, yellow, red);
		// load_highscore();

		if (mode == 0)
		{
			// Practice
			player_1 = new Player(0);
		} else if (mode == 1)
		{
			player_1 = new Player(0);
			player_2 = new Player(1);
		} else if (mode == 2)
		{
			player_1 = new Player(0);
			player_2 = new Player(1);

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
						System.out.println(e);
					}

					System.out.println("qweqwe");

					while (true)
					{
						System.out.println(receive);
						if (!GameListener.player1 && receive == true)
						{
							System.out.println("BAM");
							Vector temp2 = new Vector(-GameListener.pow * Math.cos(GameListener.cue_ang),
									GameListener.pow * Math.sin(GameListener.cue_ang));
							temp2.mult(0.34d);
							white.apply_force(temp2);
							power = 0.0d;
							power_amount = Math.PI / 2.0d;
							receive = false;

						} else if (GameListener.player1 && receive == true)
						{
							System.out.println("HEHEY");
							Vector temp2 = new Vector(-GameListener.pow * Math.cos(GameListener.cue_ang),
									GameListener.pow * Math.sin(GameListener.cue_ang));
							temp2.mult(0.34d);
							yellow.apply_force(temp2);
							power = 0.0d;
							power_amount = Math.PI / 2.0d;
							receive = false;
						}
					}
				}
			});

			thread.start();
		}

		edges[0] = new Edge(60, 61, 1216, 61);
		edges[1] = new Edge(60, 652, 1216, 652);
		edges[2] = new Edge(65, 65, 65, 653);
		edges[3] = new Edge(1217, 65, 1217, 653);
	}

	private double map(double val, double min_0, double max_0, double min_1, double max_1)
	{
		double temp = val - min_0;
		temp *= (max_1 - min_1) / (max_0 - min_0);
		temp += min_1;
		return temp;
	}

	private double dist(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public void key_pressed(Game game)
	{
		Input input = game.get_input();
		if (input.isKey(KeyEvent.VK_SPACE))
		{
			// Adjust Power
			if (!white.moving && !yellow.moving && !red.moving)
			{
				power_amount += 0.027d;
				power = map(Math.sin(power_amount), -1, 1, 186, 0);
			}
		}
		if (input.isKeyDown(KeyEvent.VK_R))
		{
			if (turn == 0)
			{
				white.pos = middle.copy();
				white.vel.mult(0.0d);
			} else
			{
				yellow.pos = middle.copy();
				yellow.vel.mult(0.0d);
			}
		}

		if (input.isKeyDown(KeyEvent.VK_C))
		{
			if (turn == 0)
			{
				white.br = true;
				white.by = true;
			} else
			{
				yellow.br = true;
				yellow.bw = true;
			}
		}

		if (input.isKeyDown(KeyEvent.VK_T))
		{
			switch_turns();
		}
		if (input.isKey(KeyEvent.VK_LEFT))
		{
			if (turn == 0)
			{
				white.vel.x(white.vel.x() - 0.75);
			} else
			{
				yellow.vel.x(yellow.vel.x() - 0.75);
			}
		}
		if (input.isKey(KeyEvent.VK_RIGHT))
		{
			if (turn == 0)
			{
				white.vel.x(white.vel.x() + 0.75);
			} else
			{
				yellow.vel.x(yellow.vel.x() + 0.75);
			}
		}
		if (input.isKey(KeyEvent.VK_UP))
		{
			if (turn == 0)
			{
				white.vel.y(white.vel.y() - 0.75);
			} else
			{
				yellow.vel.y(yellow.vel.y() - 0.75);
			}
		}
		if (input.isKey(KeyEvent.VK_DOWN))
		{
			if (turn == 0)
			{
				white.vel.y(white.vel.y() + 0.75);
			} else
			{
				yellow.vel.y(yellow.vel.y() + 0.75);
			}
		}
		if (input.isKeyDown(KeyEvent.VK_S))
		{
			if (turn == 0)
			{
				player_1.score += 10;
			} else
			{
				player_2.score += 10;
			}
		}
	}

	public void key_released(Game game)
	{
		Input input = game.get_input();
		if (input.isKeyUp(KeyEvent.VK_SPACE))
		{
			if (!white.moving && !yellow.moving && !red.moving)
			{
				Vector temp = new Vector(-power * Math.cos(cue_angle), power * Math.sin(cue_angle));
				temp.mult(0.34d);
				if (mode != 2)
				{
					if (turn == 0)
					{
						white.apply_force(temp);
						power = 0.0d;
						power_amount = Math.PI / 2.0d;
					} else
					{
						yellow.apply_force(temp);
						power = 0.0d;
						power_amount = Math.PI / 2.0d;
					}
				} else
				{
					if (turn == 0)
					{
						if (GameListener.player1)
						{
							white.apply_force(temp);
							String pow = Double.toString(power);
							String pow_a = Double.toString(power_amount);
							String cue_ang = Double.toString(cue_angle);
							GameClient.send(pow + " " + pow_a + " " + cue_ang);
							power = 0.0d;
							power_amount = Math.PI / 2.0d;
						}
					} else
					{
						if (!GameListener.player1)
						{
							yellow.apply_force(temp);
							String pow = Double.toString(power);
							String pow_a = Double.toString(power_amount);
							String cue_ang = Double.toString(cue_angle);
							GameClient.send(pow + " " + pow_a + " " + cue_ang);
							power = 0.0d;
							power_amount = Math.PI / 2.0d;
						}

					}
				}
				motion = true;
			}
		}
	}

	private void switch_turns()
	{
		if (mode != 0)
			turn = (turn + 1) % 2;
	}

	public int check_score(int t)
	{
		checked = true;
		if (t == 0)
		{
			if (white.by && white.br)
			{
				white.by = false;
				white.br = false;
				return 1;
			} else
			{
				return -1;
			}
		} else if (t == 1)
		{
			if (yellow.bw && yellow.br)
			{
				yellow.bw = false;
				yellow.br = false;
				return 1;
			} else
			{
				return -1;
			}
		}
		return -1;
	}

	@Override
	public void update(Game game, float elapsedTime)
	{
		key_pressed(game);
		key_released(game);
		white.update(game);
		yellow.update(game);
		red.update(game);
		// Every Ball Needs to Collide With Each Other
		white.collide(yellow);
		white.collide(red);
		yellow.collide(white);
		yellow.collide(red);
		red.collide(white);
		red.collide(yellow);

		if (mode == 0)
		{
			if (player_1.score >= 50)
			{
				System.out.println("Player 1 Has Won!");
			}
		} else
		{
			if (player_1.score >= 50)
			{
				System.out.println("Player 1 Has Won!");
			} else if (player_2.score >= 50)
			{
				System.out.println("Player 2 Has Won!");
			}
		}

		// Check Edge Collisions
		for (Edge edge : edges)
		{
			if (white.moving)
				edge.collide(white);
			if (yellow.moving)
				edge.collide(yellow);
			if (red.moving)
				edge.collide(red);
		}

		if (!white.moving && !yellow.moving && !red.moving)
		{
			motion = false;
			checked = false;
		} else
		{
			motion = true;
		}

		speed = Math.sqrt(white.vel.mag_squared() + yellow.vel.mag_squared() + red.vel.mag_squared());
		if (turn == 0)
		{
			cue_pos.set(white.pos.x(), white.pos.y());
			cue_angle = Math.atan2((white.pos.y() - game.get_input().mouseY()),
					(game.get_input().mouseX() - white.pos.x()));
			cue_pos.add(
					new Vector(0.9 * (power + 10) * Math.cos(cue_angle), -0.9 * (power + 10) * Math.sin(cue_angle)));

			if (!checked && speed < 0.32d && speed >= 0.1d)
			{
				if (check_score(0) == 1)
				{
					player_1.score++;
				} else
				{
					player_1.score--;
					switch_turns();
				}
				if (player_1.score > highscore)
				{
					highscore = player_1.score;
				}
			}
		} else
		{
			cue_pos.set(yellow.pos.x(), yellow.pos.y());
			cue_angle = Math.atan2((yellow.pos.y() - game.get_input().mouseY()),
					(game.get_input().mouseX() - yellow.pos.x()));
			cue_pos.add(
					new Vector(0.9 * (power + 10) * Math.cos(cue_angle), -0.9 * (power + 10) * Math.sin(cue_angle)));

			if (!checked && speed < 0.32d && speed > 0.1d)
			{
				if (check_score(1) == 1)
				{
					player_2.score++;
				} else
				{
					player_2.score--;
					switch_turns();
				}
				if (player_2.score > highscore)
				{
					highscore = player_2.score;
				}
			}
		}
		cue_pos.sub(new Vector(cue.get_height() * 0.33 * Math.sin(cue_angle),
				cue.get_height() * 0.33 * Math.cos(cue_angle)));
	}

	@Override
	public void render(Game game, Renderer r)
	{
		// Background
		r.draw_image(image, 0, 0);
		// Balls
		white.render(game, r);
		yellow.render(game, r);
		red.render(game, r);

		// Velocity Indicator
		if (mode != 2)
		{
			if (turn == 0)
				r.draw_line((int) white.pos.x(), (int) white.pos.y(), (int) (white.pos.x() + 28 * white.vel.x()),
						(int) (white.pos.y() + 28 * white.vel.y()), 3, 0xFFF35187);
			else
				r.draw_line((int) yellow.pos.x(), (int) yellow.pos.y(), (int) (yellow.pos.x() + 28 * yellow.vel.x()),
						(int) (yellow.pos.y() + 28 * yellow.vel.y()), 3, 0xFFF35187);
		} else
		{
			if (turn == 0)
			{
				if (GameListener.player1)
				{
					r.draw_line((int) white.pos.x(), (int) white.pos.y(), (int) (white.pos.x() + 28 * white.vel.x()),
							(int) (white.pos.y() + 28 * white.vel.y()), 3, 0xFFF35187);
				}
			} else
			{
				if (!GameListener.player1)
				{
					r.draw_line((int) yellow.pos.x(), (int) yellow.pos.y(),
							(int) (yellow.pos.x() + 28 * yellow.vel.x()), (int) (yellow.pos.y() + 28 * yellow.vel.y()),
							3, 0xFFF35187);
				}
			}
		}

		// Cue
		if (!motion)
		{
			if (mode != 2)
			{
				if (turn == 0)
				{
					r.draw_image(cue, (int) cue_pos.x(), (int) cue_pos.y(), 0.66, cue_angle);
				} else if (turn == 1)
				{
					r.draw_image(cue, (int) cue_pos.x(), (int) cue_pos.y(), 0.66, cue_angle);
				}

				// Power Bar
				r.draw_line(1258, (int) map(power, 0, 186, 496, 228), 1258, 496, 4, 0xFF12A1F0);
			} else
			{
				if (turn == 0)
				{
					if (GameListener.player1)
					{
						r.draw_image(cue, (int) cue_pos.x(), (int) cue_pos.y(), 0.66, cue_angle);
						r.draw_line(1258, (int) map(power, 0, 186, 496, 228), 1258, 496, 4, 0xFF12A1F0);
					}
				} else if (turn == 1)
				{
					if (!GameListener.player1)
					{
						r.draw_image(cue, (int) cue_pos.x(), (int) cue_pos.y(), 0.66, cue_angle);
						r.draw_line(1258, (int) map(power, 0, 186, 496, 228), 1258, 496, 4, 0xFF12A1F0);
					}
				}

			}
		}

		// Score
		if (player_2 == null)
		{
			r.draw_text("Player 1 = " + player_1.score, 10, 9, 0xFFC020F5, 4);
		} else
		{
			r.draw_text("Player 1 = " + player_1.score, 10, 9, 0xFFBE19F6, 4);
			r.draw_text("Player 2 = " + player_2.score, 1036, 9, 0xFFBE19F6, 4);
		}
	}

}
