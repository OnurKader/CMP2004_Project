package com.pool.main.gfx;

public class Font
{
	private Image font_image;
	private int[] offsets;
	private int[] widths;
	
	public static final Font DEFAULT = new Font("/font.png");
	
	public Font(String path) {
		font_image = new Image(path);
		offsets = new int[59];
		widths  = new int[59];

		int unicode = 0;
		
		for(int i = 0; i < font_image.get_width(); i++) {
			if(font_image.get_pixels()[i] == 0xFF0000FF) {
				offsets[unicode] = i;
			}
			
			if(font_image.get_pixels()[i] == 0xFFFFFF00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}

	public Image get_font_image()
	{
		return font_image;
	}

	public int[] get_offsets()
	{
		return offsets;
	}

	public int[] get_widths()
	{
		return widths;
	}

}
