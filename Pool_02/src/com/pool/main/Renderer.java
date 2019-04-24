package com.pool.main;

import java.awt.image.DataBufferInt;
import com.pool.main.gfx.Font;
import com.pool.main.gfx.Image;
import com.pool.main.gfx.ImageTile;

public class Renderer
{
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
	
	public void draw_image(Image image, int x, int y, double scale) {
		if(x < -image.get_width() || y < -image.get_height() || x > pixel_width || y > pixel_height) { return; }

		int _x = 0, _y = 0;
		int _width = (int)(image.get_width() * scale), _height = (int)(image.get_height() * scale);

		if(x < 0) { _x -= x; }
		if(y < 0) { _y -= y; }
		if(x + _width > pixel_width) { _width -= _width + x - pixel_width; }
		if(y + _height > pixel_height) { _height -= _height + y - pixel_height; }


		for(int j = _y; j < _height; j++) {
			for(int i = _x; i < _width; i++) {
				set_pixel(x + i, y + j, image.get_pixels()[(int)(i/scale) + (int)(j/scale) * image.get_width()]);
			}
		}
	}
	
	public void draw_image(Image image, int x, int y, double scale, double angle) {
		if(x < -image.get_width()*scale || y < -image.get_height()*scale || x > pixel_width*scale + image.get_width()*scale || y > pixel_height*scale + image.get_height()*scale) { return; }

		int _x = 0, _y = 0;
		int _width = (int)(image.get_width() * scale), _height = (int)(image.get_height() * scale);

		double n_x, n_y;
		for(int j = _y; j < _height; j++) {
			for(int i = _x; i < _width; i++) {
				n_x = Math.cos(angle)*(i) + Math.sin(angle)*(j) + x;
				n_y = -Math.sin(angle)*(i) + Math.cos(angle)*(j) + y;
				for(int n = 0; n < scale; n++)
					set_pixel((int)n_x + n, (int)n_y, image.get_pixels()[(int)(i/scale) + (int)(j/scale) * image.get_width()]);
			}
		}
	}
	
	public void draw_image_tile(ImageTile image, int x, int y, int t_x, int t_y, int scale) {
		if(x < -image.get_tile_width() || y < -image.get_tile_height() || x > pixel_width || y > pixel_height) { return; }

		int _x = 0, _y = 0;
		int _width = image.get_tile_width() * scale, _height = image.get_tile_height() * scale;

		if(x < 0) { _x -= x; }
		if(y < 0) { _y -= y; }
		if(x + _width > pixel_width) { _width -= _width + x - pixel_width; }
		if(y + _height > pixel_height) { _height -= _height + y - pixel_height; }
		 
		
		for(int j = _y; j < _height; j++) {
			for(int i = _x; i < _width; i++) {
				for(int n = 0; n < scale; n++) {
					set_pixel(x + i + n, y + j, image.get_pixels()[(i/scale + t_x * image.get_tile_width()) + (j/scale + t_y * image.get_tile_height()) * image.get_width()]);
				}
			}
		}
	}
	
	public void draw_text(String text, int x, int y, int color, int scale)
	{
		int offset = 0;
		text = text.toUpperCase();
		for(int n = 0; n < text.length(); n++) {
			int unicode = text.codePointAt(n) - 32;
			
			for(int j = 0; j < font.get_font_image().get_height()*scale; j++) {
				for(int i = 0; i < font.get_widths()[unicode]*scale; i++) {
					for(int a = 0; a < scale; a++) {
						if(font.get_font_image().get_pixels()[(i/scale + font.get_offsets()[unicode]) + j/scale * font.get_font_image().get_width()] == 0xFFFFFFFF) {
							set_pixel(x + i + offset + a, y + j, color);
						}
					}
				}
			}
			offset += font.get_widths()[unicode]*scale;
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
		for(double theta = 0.0d; theta <= 2.0d*Math.PI; theta += Math.PI/56.0d) {
			double rx = r * Math.cos(theta) + x;
			double ry = r * Math.sin(theta) + y;
			
			double dist = x - rx;
			
			for(double i = 0; i < 2.0d*dist; i++) {
				set_pixel((int)(rx + i), (int)ry, color);
			}
		}
	}
	
	// Bresenham's Line Algorithm
	
	public void plot_line_low(int x1, int y1, int x2, int y2, int thickness, int color) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		int yi = 1;
		if(dy <= 0){
			yi = -1;
			dy = -dy;
		}
		int D = 2*dy - dx;
		int y = y1;
		
		for(int x = x1; x <= x2; x++) {
			for(int i = -3; i <= thickness; i++) {
				set_pixel(x + i, y, color);
				set_pixel(x, y + i, color);
			}
			if(D > 0) {
				y += yi;
				D -= 2*dx;
			}
			D += 2*dy;
		}	
	}

	public void plot_line_high(int x1, int y1, int x2, int y2, int thickness, int color) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		int xi = 1;
		if(dx <= 0) {
			xi = -1;
			dx = -dx;
		}
		int D = 2*dx - dy;
		int x = x1;
		
		for(int y = y1; y <= y2; y++) {
			for(int i = -3; i <= thickness; i++) {
				set_pixel(x + i, y, color);
				set_pixel(x, y + i, color);
			}
			if(D > 0) {
				x += xi;
				D -= 2*dy;
			}
			D += 2*dx;
		}
	}

	public void draw_line(int x1, int y1, int x2, int y2, int thickness, int color) {
		if(Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
			if(x1 > x2){
				plot_line_low(x2, y2, x1, y1, thickness, color);
			}
			else {
				plot_line_low(x1, y1, x2, y2, thickness, color);
			}
		}
		else {
			if(y1 > y2) {
				plot_line_high(x2, y2, x1, y1, thickness, color);
			}
			else {
				plot_line_high(x1, y1, x2, y2, thickness, color);
			}
		}
	}

	
	
	
	
}
