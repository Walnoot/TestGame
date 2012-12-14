package walnoot.testgame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class World{
	private Block[] tiles;
	private int width, length, height;
	private MovingCube movingCube;
	
	public World(int width, int length, int height){
		this.width = width;
		this.length = length;
		this.height = height;
		
		tiles = new Block[width * length * height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < length; y++){
				setBlock(new BasicBlock(this), x, y, 0);
				
				if(x == 0 || y == 0 || x  == width - 1 || y == length - 1) setBlock(new BasicBlock(this), x, y, 1);
			}
		}
		
		setBlock(new TransportBlock(this, 1), 1, 1, 1);
		
		movingCube = new MovingCube(this, 0, 0, 3);
	}
	
	public World(){
		this(16, 16, 4);
	}
	
	public void update(){
		movingCube.update();
		
		for(int i = 0; i < tiles.length; i++){
			if(tiles[i] != null) tiles[i].update(i % width, (i / width) % length, i / (width * length));
		}
	}
	
	public void render(){
		Gdx.gl10.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl10.glCullFace(GL10.GL_FRONT);
		
		for(int i = 0; i < tiles.length; i++){
			if(tiles[i] != null){
				Gdx.gl10.glPushMatrix();
				tiles[i].render(i % width, (i / width) % length, i / (width * length));
				Gdx.gl10.glPopMatrix();
			}
		}
		
		movingCube.render();
		
		Gdx.gl10.glDisable(GL10.GL_CULL_FACE);
	}
	
	public boolean isSolid(int x, int y, int z){
		Block block = getBlock(x, y, z);
		if(block == null) return false;
		return block.isSolid();
	}
	
	public Block getBlock(int x, int y, int z){
		if(x < 0 || y < 0 || z < 0 || x >= width || y >= length || z >= height) return null;
		
		return tiles[x + (y * width) + (z * width * length)];
	}
	
	public void setBlock(Block block, int x, int y, int z){
		if(x < 0 || y < 0 || z < 0 || x >= width || y >= length || z >= height) return;
		
		tiles[x + (y * width) + (z * width * length)] = block;
	}
	
	public MovingCube getMovingCube(){
		return movingCube;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
