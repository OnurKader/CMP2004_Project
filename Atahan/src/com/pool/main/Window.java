package com.pool.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private Graphics2D g;
	private BufferStrategy bs;


	public Window(Game game) {
		image = new BufferedImage(game.get_width(), game.get_height(), BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension dim = new Dimension((int)(game.get_width() * game.get_scale()), (int)(game.get_height() * game.get_scale()));
		canvas.setPreferredSize(dim);
		canvas.setMinimumSize(dim);
		canvas.setMaximumSize(dim);
		frame = new JFrame(game.get_title());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
	public void update() {
			g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
			bs.show();
	}

	public BufferedImage get_image() { return image; }
	public Canvas get_canvas() { return canvas; }
	public JFrame get_frame() { return frame; }


}





