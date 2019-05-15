package com.pool.main;

public abstract class AbstractGame
{
	public abstract void update(Game game, float elapsedTime);
	
	public abstract void render(Game game, Renderer r);
	
}
