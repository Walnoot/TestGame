package walnoot.testgame.world;

import walnoot.testgame.Util;

import com.badlogic.gdx.Gdx;

public class MovingCube{
	private int x, y, z;
	
	public MovingCube(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void render(){
		Gdx.gl10.glColor4f(1f, 0.25f, 0.25f, 1f);
		
		Util.renderCube(x, y, z);
	}
	
	public void translate(int x, int y, int z){
		this.x += x;
		this.y += y;
		this.z += z;
	}
}
