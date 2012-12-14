package walnoot.testgame.states;

import walnoot.testgame.InputHandler;
import walnoot.testgame.TestGame;
import walnoot.testgame.world.MovingCube;
import walnoot.testgame.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;



public class GameplayState extends State{
	public static final String SAVE_FOLDER = "testgame-saves";
	public static final String SAVE_NAME = "world.dat";
	
	public static final float SENSITIVITY = 5f;
	public static final float MOUSE_SENSITIVITY = 150f;
	
	private static final float[] LIGHT_DIRECTION = {1f, 0f, -2f, 0};
	private static final float[] LIGHT_COLOR_DIFFUSE = {0.9f, 0.9f, 0.9f, 1f};
	private static final float[] LIGHT_COLOR_SPECULAR = {0.9f, 0.1f, 0.1f, 1f};
	private static final float[] LIGHT_COLOR_AMBIENT = {0.4f, 0.4f, 0.4f, 1f};
	
	private PerspectiveCamera sceneCamera;
	private float angle;
	private World world;
	
	private OrthographicCamera uiCamera;
	private SpriteBatch batch;
	
	private String debugString = "";
	
	public GameplayState(){
		super();
		
		sceneCamera = new PerspectiveCamera(90f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sceneCamera.near = 0.01f;
		sceneCamera.position.z = 6f;
		sceneCamera.rotate(Vector3.X, 45f);
		
		uiCamera = new OrthographicCamera(2f * Gdx.graphics.getWidth() / Gdx.graphics.getHeight(), 2f);
		batch = new SpriteBatch();
		
		world = new World();
	}
	
	public void render(){
		sceneCamera.update();
		sceneCamera.apply(Gdx.gl10);
		
		Gdx.gl10.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl10.glEnable(GL10.GL_CLAMP_TO_EDGE);
		setLighting();
		
		world.render();
		
		uiCamera.apply(Gdx.gl10);
		batch.setProjectionMatrix(uiCamera.combined);
		
		batch.begin();
		TestGame.FONT.draw(batch, debugString, -1f, 1f);
		batch.end();
	}
	
	public void fixedUpdate(){
		angle += 0.3f;
		
		LIGHT_DIRECTION[0] = MathUtils.cosDeg(angle);
		LIGHT_DIRECTION[1] = MathUtils.sinDeg(angle);
		
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) moveCube();
		else moveCamera();
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) destroyBlock();

		Gdx.input.setCursorCatched(Gdx.input.isButtonPressed(Buttons.RIGHT));
		
		world.update();
		
		if(TestGame.INPUT.save.isJustPressed()){
			FileHandle folder = Gdx.files.external(SAVE_FOLDER);
			if(!folder.exists()) folder.mkdirs();
			
			FileHandle file = folder.child(SAVE_NAME);
			
			Output fileOutput = new Output(file.write(false));
			TestGame.KRYO.writeObject(fileOutput, world);
			fileOutput.close();
		}else if(TestGame.INPUT.load.isJustPressed()){
			FileHandle folder = Gdx.files.external(SAVE_FOLDER);
			if(!folder.exists()) folder.mkdirs();
			
			FileHandle file = folder.child(SAVE_NAME);
			
			Input fileInput = new Input(file.read());
			world = TestGame.KRYO.readObject(fileInput, World.class);
			fileInput.close();
		}
	}
	
	private void destroyBlock(){
		Plane plane = new Plane(Vector3.Z, Vector3.Zero);
		Ray ray = new Ray(sceneCamera.position, sceneCamera.direction);
		
		//Vector3.tmp.x += sceneCamera.position.x;
		//Vector3.tmp.y += sceneCamera.position.y;
		
		System.out.println(Intersector.intersectRayPlane(ray, plane, Vector3.tmp));
		debugString = String.format("%4f:%4f:%4f", Vector3.tmp.x, Vector3.tmp.y, Vector3.tmp.z);
		
		world.setBlock(null, (int) Vector3.tmp.x, (int) Vector3.tmp.y, 0);
	}

	private void moveCamera(){
		InputHandler input = TestGame.INPUT;
		Vector2 translation = Vector2.tmp.set(0, 0);
		
		if(input.forward.isPressed()){
			translation.x += sceneCamera.direction.x;
			translation.y += sceneCamera.direction.y;
		}
		if(input.backward.isPressed()){
			translation.x -= sceneCamera.direction.x;
			translation.y -= sceneCamera.direction.y;
		}
		if(input.right.isPressed()){
			translation.x += sceneCamera.direction.y;
			translation.y -= sceneCamera.direction.x;
		}
		if(input.left.isPressed()){
			translation.x -= sceneCamera.direction.y;
			translation.y += sceneCamera.direction.x;
		}
		if(input.up.isPressed()) sceneCamera.translate(0, 0, TestGame.SECONDS_PER_UPDATE);
		if(input.down.isPressed()) sceneCamera.translate(0, 0, -TestGame.SECONDS_PER_UPDATE);
		
		translation.nor().mul(TestGame.SECONDS_PER_UPDATE * SENSITIVITY);
		sceneCamera.position.add(translation.x, translation.y, 0);
	}

	private void moveCube(){
		InputHandler input = TestGame.INPUT;
		MovingCube cube = world.getMovingCube();
		
		if(!cube.isMoving()){
			if(input.forward.isPressed()) cube.translate(0, 1, 0);
			if(input.backward.isPressed()) cube.translate(0, -1, 0);
			if(input.left.isPressed()) cube.translate(-1, 0, 0);
			if(input.right.isPressed()) cube.translate(1, 0, 0);
			if(input.up.isPressed()) cube.translate(0, 0, 1);
			if(input.down.isPressed()) cube.translate(0, 0, -1);
		}
	}

	public void update(){
		if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
			sceneCamera.rotate(Vector3.Z, Gdx.input.getDeltaX() * MOUSE_SENSITIVITY / Gdx.graphics.getWidth());
			sceneCamera.rotate(Vector3.tmp.set(sceneCamera.direction).crs(sceneCamera.up),
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
		sceneCamera.viewportWidth = Gdx.graphics.getWidth();
		sceneCamera.viewportHeight = Gdx.graphics.getHeight();
		
		uiCamera.viewportWidth = 2f * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		uiCamera.viewportHeight = 2f;
		
		uiCamera.update();
	}

	public void dispose(){
	}
}
