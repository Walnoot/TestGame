package walnoot.testgame.states;

import walnoot.testgame.InputHandler;
import walnoot.testgame.TestGame;
import walnoot.testgame.world.MovingCube;
import walnoot.testgame.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;



public class GameplayState extends State{
	public static final float SENSITIVITY = 5f;
	public static final float MOUSE_SENSITIVITY = 150f;
	
	private static final float[] LIGHT_DIRECTION = {1f, 0f, -2f, 0};
	private static final float[] LIGHT_COLOR_DIFFUSE = {0.9f, 0.9f, 0.9f, 1f};
	private static final float[] LIGHT_COLOR_SPECULAR = {0.9f, 0.1f, 0.1f, 1f};
	private static final float[] LIGHT_COLOR_AMBIENT = {0.4f, 0.4f, 0.4f, 1f};
	
	private PerspectiveCamera camera;
	private float angle;
	private World world;
	
	public GameplayState(){
		super();
		
		camera = new PerspectiveCamera(90f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.near = 0.01f;
		camera.position.z = 4f;
		camera.rotate(Vector3.X, 45f);
		
		world = new World();
	}
	
	public void render(){
		camera.update();
		camera.apply(Gdx.gl10);
		
		Gdx.gl10.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl10.glEnable(GL10.GL_CLAMP_TO_EDGE);
		setLighting();
		
		world.render();
	}
	
	public void update(){
		angle += 0.3f;
		
		LIGHT_DIRECTION[0] = MathUtils.cosDeg(angle);
		LIGHT_DIRECTION[1] = MathUtils.sinDeg(angle);
		
		InputHandler input = TestGame.INPUT;
		
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)){
			MovingCube cube = world.getMovingCube();
			
			if(input.forward.isJustPressed()) cube.translate(0, 1, 0);
			if(input.backward.isJustPressed()) cube.translate(0, -1, 0);
			if(input.left.isJustPressed()) cube.translate(-1, 0, 0);
			if(input.right.isJustPressed()) cube.translate(1, 0, 0);
			if(input.up.isJustPressed()) cube.translate(0, 0, 1);
			if(input.down.isJustPressed()) cube.translate(0, 0, -1);
		}else{
			Vector2 translation = Vector2.tmp.set(0, 0);
			
			if(input.forward.isPressed()){
				translation.x += camera.direction.x;
				translation.y += camera.direction.y;
			}
			if(input.backward.isPressed()){
				translation.x -= camera.direction.x;
				translation.y -= camera.direction.y;
			}
			if(input.right.isPressed()){
				translation.x += camera.direction.y;
				translation.y -= camera.direction.x;
			}
			if(input.left.isPressed()){
				translation.x -= camera.direction.y;
				translation.y += camera.direction.x;
			}
			if(input.up.isPressed()) camera.translate(0, 0, TestGame.SECONDS_PER_UPDATE);
			if(input.down.isPressed()) camera.translate(0, 0, -TestGame.SECONDS_PER_UPDATE);
			
			translation.nor();
			translation.mul(TestGame.SECONDS_PER_UPDATE * SENSITIVITY);
			camera.position.add(translation.x, translation.y, 0);
		}
		
		Gdx.input.setCursorCatched(Gdx.input.isButtonPressed(Buttons.RIGHT));
		
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
			camera.rotate(Vector3.Z, Gdx.input.getDeltaX() * MOUSE_SENSITIVITY / Gdx.graphics.getWidth());
			camera.rotate(Vector3.tmp.set(camera.direction).crs(camera.up),
					-Gdx.input.getDeltaY() * MOUSE_SENSITIVITY / Gdx.graphics.getWidth());
		}
	}
	
	private void setLighting(){
		Gdx.gl10.glEnable(GL10.GL_LIGHTING);
		Gdx.gl10.glEnable(GL10.GL_LIGHT0);
		
		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, LIGHT_COLOR_AMBIENT, 0);
		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, LIGHT_COLOR_DIFFUSE, 0);
		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, LIGHT_COLOR_SPECULAR, 0);
		
		Gdx.gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, LIGHT_DIRECTION, 0);
		
		Gdx.gl10.glEnable(GL10.GL_COLOR_MATERIAL);
	}
	
	public void resize(){
		camera.viewportWidth = Gdx.graphics.getWidth();
		camera.viewportHeight = Gdx.graphics.getHeight();
	}

	public void dispose(){
	}
}
