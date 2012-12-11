package walnoot.testgame.world;

import walnoot.testgame.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class TransportBlock extends Block{
	private int orientation;
	
	public TransportBlock(){
		super(null);
		//empty constructor for kryo
	}
	
	/**
	 * @param world
	 * @param orientation
	 *            - Orientation of this block, where 0 is north (+y), 1 is east (+x), 2 is south (-y) and 3 is west (-x)
	 */
	public TransportBlock(World world, int orientation){
		super(world);
		this.orientation = orientation;
	}
	
	public void update(int x, int y, int z){
		MovingCube cube = world.getMovingCube();
		
		if(!cube.isMoving()) if(cube.getX() == x && cube.getY() == y && cube.getZ() == z){
			if(orientation == 0) cube.translate(0, 1, 0);
			else if(orientation == 1) cube.translate(1, 0, 0);
			else if(orientation == 2) cube.translate(0, -1, 0);
			else if(orientation == 3) cube.translate(-1, 0, 0);
			
			orientation = (orientation + 1) % 4;
		}
	}
	
	public void render(int x, int y, int z){
		Gdx.gl10.glColor4f(0.5f, 1f, 0.5f, 1f);
		
		Gdx.gl10.glDisable(GL10.GL_CULL_FACE);
		Gdx.gl10.glTranslatef(x + 0.5f, y + 0.5f, z + 0.05f);
		Gdx.gl10.glRotatef(-orientation * 90f, 0, 0, 1f);
		Gdx.gl10.glTranslatef(-0.5f, -0.5f, 0f);
		Util.ARROW_MESH.render(GL10.GL_TRIANGLE_FAN);
		
		Gdx.gl10.glEnable(GL10.GL_CULL_FACE);
	}
}
