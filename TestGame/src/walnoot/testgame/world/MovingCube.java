package walnoot.testgame.world;

import walnoot.testgame.TestGame;
import walnoot.testgame.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

public class MovingCube{
	private static final Interpolation INTERPOLATION = Interpolation.linear;
	public static final float MOVE_TIME = 1f;
	
	private int x, y, z;
	private float renderX, renderY, renderZ;
	private float timer;
	
	public MovingCube(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
		
		renderX = x;
		renderY = y;
		renderZ = z;
	}
	
	public void update(){
		if(timer < 1f){
			timer += TestGame.SECONDS_PER_UPDATE;
			
			renderX = INTERPOLATION.apply(renderX, x, timer);
			renderY = INTERPOLATION.apply(renderY, y, timer);
			renderZ = INTERPOLATION.apply(renderZ, z, timer);
		}else{
			timer = 1f;
		}
	}
	
	public void render(){
		Gdx.gl10.glColor4f(1f, 0.25f, 0.25f, 1f);
		
		Util.renderCube(renderX, renderY, renderZ);
	}
	
	public void translate(int x, int y, int z){
		this.x += x;
		this.y += y;
		this.z += z;
		
		timer = 0f;
	}
}
