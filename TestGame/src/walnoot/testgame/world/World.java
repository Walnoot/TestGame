package walnoot.testgame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class World{
	public static final int WIDTH = 16;//x-axis
	public static final int LENGTH = 8;//y-axis
	public static final int HEIGHT = 4;//z-axis
	
	private Block[] tiles = new Block[WIDTH * LENGTH * HEIGHT];
	private MovingCube movingCube;
	
	public World(){
		for(int x = 0; x < WIDTH; x++){
			for(int y = 0; y < LENGTH; y++){
				setBlock(new BasicBlock(this), x, y, 0);
			}
		}
		
		setBlock(new TransportBlock(this, 1), 1, 1, 1);
		
		movingCube = new MovingCube(this, 0, 0, 3);
	}
	
	public void update(){
		movingCube.update();
		
		for(int i = 0; i < tiles.length; i++){
			if(tiles[i] != null) tiles[i].update(i % WIDTH, (i / WIDTH) % LENGTH, i / (WIDTH * LENGTH));
		}
	}
	
	public void render(){
		Gdx.gl10.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl10.glCullFace(GL10.GL_FRONT);
		
		Gdx.gl10.glColor4f(0.5f, 0.5f, 0.5f, 1f);
		
		for(int i = 0; i < tiles.length; i++){
			if(tiles[i] != null){
				Gdx.gl10.glPushMatrix();
				tiles[i].render(i % WIDTH, (i / WIDTH) % LENGTH, i / (WIDTH * LENGTH));
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
		if(x < 0 || y < 0 || z < 0 || x >= WIDTH || y >= LENGTH || z >= HEIGHT) return null;
		
		return tiles[x + (y * WIDTH) + (z * WIDTH * LENGTH)];
	}
	
	public void setBlock(Block block, int x, int y, int z){
		tiles[x + (y * WIDTH) + (z * WIDTH * LENGTH)] = block;
	}
	
	public MovingCube getMovingCube(){
		return movingCube;
	}
}
