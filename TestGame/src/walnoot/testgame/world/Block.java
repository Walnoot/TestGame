package walnoot.testgame.world;

public abstract class Block{
	protected final World world;
	
	public Block(World world){
		this.world = world;
	}
	
	public abstract void render(int x, int y, int z);
	
	public boolean isOpaque(){
		return false;
	}
}
