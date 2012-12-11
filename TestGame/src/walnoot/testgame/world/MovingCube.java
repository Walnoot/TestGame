package walnoot.testgame.world;

import walnoot.testgame.TestGame;
import walnoot.testgame.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

public class MovingCube{
	private static final Interpolation INTERPOLATION = Interpolation.swingOut;
	public static final float MOVE_TIME = 0.25f;
	
	private final World world;
	private int x, y, z, oldX, oldY, oldZ;
	private float timer;
	
	public MovingCube(World world, int x, int y, int z){
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		
		oldX = x;
		oldY = y;
		oldZ = z;
	}
	
	public void update(){
		if(timer < MOVE_TIME){
			timer += TestGame.SECONDS_PER_UPDATE;
		}else{
			timer = MOVE_TIME;
		}
	}
	
	public void render(){
		Gdx.gl10.glColor4f(1f, 0.25f, 0.25f, 1f);
		
		Util.renderCube(INTERPOLATION.apply(oldX, x, timer / MOVE_TIME),
				INTERPOLATION.apply(oldY, y, timer / MOVE_TIME), INTERPOLATION.apply(oldZ, z, timer / MOVE_TIME));
	}
	
	public void translate(int x, int y, int z){
		if(world.isSolid(this.x + x, this.y + y, this.z + z)) return;
		
		oldX = this.x;
		oldY = this.y;
		oldZ = this.z;
		
		this.x += x;
		this.y += y;
		this.z += z;
		
		timer = 0f;
	}
	
	public boolean isMoving(){
		return timer < MOVE_TIME;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
}
