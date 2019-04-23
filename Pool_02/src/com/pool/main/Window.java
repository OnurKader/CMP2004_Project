package com.pool.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Window {
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private Graphics g;
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
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
	}
	
	public void update() {
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}

	public BufferedImage get_image() { return image; }

	public Canvas get_canvas() { return canvas; }
	
	public JFrame get_frame() { return frame; }
	

}










