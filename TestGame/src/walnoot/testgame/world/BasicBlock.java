package walnoot.testgame.world;

import walnoot.testgame.Util;

public class BasicBlock extends Block{
	public BasicBlock(){
		super(null);
		//empty constructor for kryo
	}
	
	public BasicBlock(World world){
		super(world);
	}
	
	public void render(int x, int y, int z){
		Util.renderCube(x, y, z);
	}
	
	public boolean isSolid(){
		return true;
	}
}
