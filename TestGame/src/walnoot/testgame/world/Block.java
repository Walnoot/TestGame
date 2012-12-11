package walnoot.testgame.world;

public abstract class Block{
	protected final World world;
	
	public Block(World world){
		this.world = world;
	}
	
	public void update(int x, int y, int z){
	}
	
	public abstract void render(int x, int y, int z);
	
	public boolean isSolid(){
		return false;
	}
}
