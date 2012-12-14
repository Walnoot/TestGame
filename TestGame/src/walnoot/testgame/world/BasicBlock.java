package walnoot.testgame.world;

import walnoot.testgame.Util;

import com.badlogic.gdx.Gdx;

public class BasicBlock extends Block{
	public BasicBlock(){
		super(null);
		//empty constructor for kryo
	}
	
	public BasicBlock(World world){
		super(world);
	}
	
	public void render(int x, int y, int z){
		Gdx.gl10.glColor4f(0.5f, 0.5f, 0.5f, 1f);
		Util.renderCube(x, y, z);
	}
	
	public boolean isSolid(){
		return true;
	}
}
