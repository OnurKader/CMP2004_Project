package com.pool.main.gfx;

public class ImageTile extends Image
{
	private int tile_width, tile_height;
	
	public ImageTile(String path, int tile_width, int tile_height) {
		super(path);
		this.tile_width = tile_width;
		this.tile_height = tile_height;
	}

	public int get_tile_width()
	{
		return tile_width;
	}

	public void set_tile_width(int tile_width)
	{
		this.tile_width = tile_width;
	}

	public int get_tile_height()
	{
		return tile_height;
	}

	public void set_tile_height(int tile_height)
	{
		this.tile_height = tile_height;
	}

}
