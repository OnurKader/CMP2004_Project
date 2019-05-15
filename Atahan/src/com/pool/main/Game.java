package com.pool.main;

import java.util.Timer;
import java.util.TimerTask;

public class Game implements Runnable
{
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	private boolean running = false;
	private final double FPS_CAP = 1.0d / 60.0d;
	private final int width = 1280, height = 720;
	private final float scale = 1.0f;
	private String title = "3-Ball Pool";
	public long frames;
	private Timer timer;
	private TimerTask tt;
	private long seconds = -1;

	public Game(AbstractGame game)
	{
		this.game = game;
	}

	public void start()
	{
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread = new Thread(this);
		thread.run();
		window.get_frame().requestFocus();
	}

	public void stop()
	{
		System.out.println("Stopping...");
	}

	@Override
	public void run()
	{
		running = true;
		double first_time = -1;
		double last_time = System.nanoTime() / 1.0e9d;
		double passed_time = -1;
		double unprocessed_time = -1;
		boolean render = false;
		double frame_time = -1;
		frames = -1;
		int fps = -1;
		timer = new Timer();
		tt = new TimerTask() {
			@Override
			public void run()
			{
				seconds++;
			}
		};
		timer.scheduleAtFixedRate(tt, 500, 1000);

		renderer.clear();

		while (running)
		{
			first_time = System.nanoTime() / 1.0e9d;
			passed_time = first_time - last_time;
			last_time = first_time;

//			render = false;

			unprocessed_time += passed_time;
			frame_time += passed_time;

			while (unprocessed_time >= FPS_CAP)
			{
				unprocessed_time -= FPS_CAP;
				render = true;

				game.update(this, (float) FPS_CAP);
				input.update();

				// Main Update Stuff
				if (frame_time >= 1.0)
				{
					frame_time = 0;
					fps = (int) frames;
					frames = 0;
				}
			}

			if (render)
			{
				game.render(this, renderer);
				frames++;
				renderer.draw_text("TIME: " + seconds, 600, 10, 0xFFF24152, 4);
				renderer.draw_text("FPS: " + fps, get_width() - 170, get_height() - 42, 0xFF07FAFB, 3);
				window.update();
			} else
			{
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	public void dispose()
	{

	}

	public String get_title()
	{
		return title;
	}

	public void set_title(String title)
	{
		this.title = title;
	}

	public int get_width()
	{
		return width;
	}

	public int get_height()
	{
		return height;
	}

	public Window get_window()
	{
		return window;
	}

	public float get_scale()
	{
		return scale;
	}

	public Input get_input()
	{
		return input;
	}

}
