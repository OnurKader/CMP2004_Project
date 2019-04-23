package com.pool.main;

import java.awt.image.DataBufferInt;
import com.pool.main.gfx.Font;
import com.pool.main.gfx.Image;
import com.pool.main.gfx.ImageTile;

public class Renderer {
	
	private final int pixel_width, pixel_height;
	private int[] pixels;
	private Font font = Font.DEFAULT;
	
	public Renderer(Game game) {
		pixel_width = game.get_width();
		pixel_height = game.get_height();		
		pixels = ((DataBufferInt)game.get_window().get_image().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFF000000;
		}
	}
	
	public void set_pixel(int x, int y, int value) {
		if((x < 0 || x >= pixel_width || y < 0 || y >= pixel_height) || ((value >> 24) & 0xFF) == 0) { return; }
		pixels[x + y * pixel_width] = value;
	}
	
	public void draw_image(Image image, int x, int y) {
		if(x < -image.get_width() || y < -image.get_height() || x > pixel_width || y > pixel_height) { return; }

		int _x = 0, _y = 0;
		int _width = image.get_width(), _height = image.get_height();

		if(x < 0) { _x -= x; }
		if(y < 0) { _y -= y; }
		if(x + _width > pixel_width) { _width -= _width + x - pixel_width; }
		if(y + _height > pixel_height) { _height -= _height + y - pixel_height; }
		 
		
		for(int j = _y; j < _height; j++) {
			for(int i = _x; i < _width; i++) {
				set_pixel(x + i, y + j, image.get_pixels()[i + j * image.get_width()]);
			}
		}
	}
	
	public void draw_image_tile(ImageTile image, int x, int y, int t_x, int t_y) {
		if(x < -image.get_tile_width() || y < -image.get_tile_height() || x > pixel_width || y > pixel_height) { return; }

		int _x = 0, _y = 0;
		int _width = image.get_tile_width(), _height = image.get_tile_height();

		if(x < 0) { _x -= x; }
		if(y < 0) { _y -= y; }
		if(x + _width > pixel_width) { _width -= _width + x - pixel_width; }
		if(y + _height > pixel_height) { _height -= _height + y - pixel_height; }
		 
		
		for(int j = _y; j < _height; j++) {
			for(int i = _x; i < _width; i++) {
				set_pixel(x + i, y + j, image.get_pixels()[(i + t_x * image.get_tile_width()) + (j + t_y * image.get_tile_height()) * image.get_width()]);
			}
		}
	}
	
	public void draw_text(String text, int x, int y, int color)
	{
		int offset = 0;
		text = text.toUpperCase();
		for(int n = 0; n < text.length(); n++) {
			int unicode = text.codePointAt(n) - 32;
			
			for(int j = 0; j < font.get_font_image().get_height(); j++) {
				for(int i = 0; i < font.get_widths()[unicode]; i++) {
					if(font.get_font_image().get_pixels()[(i + font.get_offsets()[unicode]) + j * font.get_font_image().get_width()] == 0xFFFFFFFF) {
						set_pixel(x + i + offset, y + j, color);
					}
				}
			}
			offset += font.get_widths()[unicode];
		}
	}
	
	public void draw_rect(int x, int y, int w, int h, int color) {

		
		for(int j = 0; j <= h; j++) {
			set_pixel(x, y + j, color);
			set_pixel(x + w, y + j, color);
		}
		
		for(int i = 0; i <= w; i++) {
			set_pixel(x + i, y, color);
			set_pixel(x + i, y + h, color);
		}
	}
	
	public void fill_rect(int x, int y, int w, int h, int color) {
		if(x < -w || y < -h || x > pixel_width || y > pixel_height) { return; }

		int _x = 0, _y = 0;
		int _width = w;
		int _height = h;

		if(x < 0) { _x -= x; }
		if(y < 0) { _y -= y; }
		if(x + _width > pixel_width) { _width -= _width + x - pixel_width; }
		if(y + _height > pixel_height) { _height -= _height + y - pixel_height; }
		
		
		for(int j = _y; j <= _height; j++) {
			for(int i = _x; i <= _width; i++) {
				set_pixel(x + i, y + j, color);
			}
		}
	}
	
	public void draw_circle(int x, int y, int r, int color) {
		for(double theta = 0.0d; theta <= 2.0d*Math.PI; theta += Math.PI/240.0d) {
			double rx = r * Math.cos(theta) + x;
			double ry = r * Math.sin(theta) + y;
			set_pixel((int)rx, (int)ry, color);
		}
	}

	public void fill_circle(int x, int y, int r, int color) {
		for(double theta = 0.0d; theta <= 2.0d*Math.PI; theta += Math.PI/64.0d) {
			double rx = r * Math.cos(theta) + x;
			double ry = r * Math.sin(theta) + y;
			
			double dist = x - rx;
			
			for(double i = 0; i < 2*dist; i++) {
				set_pixel((int)(rx + i), (int)ry, color);
			}
		}
	}
	

	
	
	
	
}
