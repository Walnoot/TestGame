package walnoot.testgame.world;

import walnoot.testgame.Util;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BasicBlock extends Block{
	private Mesh mesh;
	
	public BasicBlock(World world){
		super(world);
		
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[]{
				0, 0,
				1, 0,
				1, 1,
				0, 1
		});
	}
	
	public void render(int x, int y, int z){
		Util.renderCube(x, y, z);
	}
	
	public boolean isSolid(){
		return true;
	}
}
