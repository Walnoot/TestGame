package walnoot.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Util{
	public static final Mesh SQUARE_MESH = getSquareMesh();
	public static final Mesh ARROW_MESH = getArrowMesh();
	
	private static Mesh getSquareMesh(){
		Mesh mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[]{
				0, 0,
				1, 0,
				1, 1,
				0, 1
		});
		
		return mesh;
	}
	
	private static Mesh getArrowMesh(){
		Mesh mesh = new Mesh(true, 7, 0, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[]{
				0.5f, 1f,
				0f, 0.5f,
				0.25f, 0.5f,
				0.25f, 0f,
				0.75f, 0f,
				0.75f, 0.5f,
				1f, 0.5f
		});
		
		return mesh;
	}

	public static void renderCube(float x, float y, float z){
		//future self: please tidy up
		
		GL10 gl = Gdx.gl10;
		
		gl.glTranslatef(x, y, z);
		
		//down
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
		
		//up
		gl.glPushMatrix();
		gl.glRotatef(180, 1f, 0, 0);
		gl.glTranslatef(0, -1f, -1f);
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		//north
		gl.glRotatef(90, 1f, 0, 0);
		gl.glTranslatef(0, 0, -1f);
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
		
		//west
		gl.glRotatef(90, 0, 1f, 0);
		gl.glTranslatef(-1f, 0, 0);
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
		
		gl.glPopMatrix();
		
		//south
		gl.glRotatef(-90, 1f, 0, 0);
		gl.glTranslatef(0, -1f, 0);
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
		
		//east
		gl.glRotatef(-90, 0, 1f, 0);
		gl.glTranslatef(0, 0, -1f);
		SQUARE_MESH.render(GL10.GL_TRIANGLE_FAN);
	}
}
