package com.pool.main.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private int width, height;
	private int[] pixels;
	
	public Image(String path) {
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		image.flush();
		
	}

	public int get_width() { return width; }

	public void set_width(int width) { this.width = width; }

	public int get_height() { return height; }

	public void set_height(int height) { this.height = height; }

	public int[] get_pixels() { return pixels; }

	public void set_pixels(int[] pixels) { this.pixels = pixels; }

}
