package walnoot.testgame;

import static com.badlogic.gdx.Input.Keys.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class InputHandler implements InputProcessor{
	public Key forward = new Key(W, UP);
	public Key backward = new Key(S, DOWN);
	public Key left = new Key(A, LEFT);
	public Key right = new Key(D, RIGHT);
	public Key up = new Key(Q);
	public Key down = new Key(E);
	public Key escape = new Key(ESCAPE);
	public Key save = new Key(F5);
	public Key load = new Key(F6);
	
	private ArrayList<Key> keys;
	private OrthographicCamera camera;
	private boolean keyDown;
	private int scrollAmount;
	
	/**
	 * Make sure to call after game logic update() is called
	 */
	public void update(){
		for(int i = 0; i < keys.size(); i++){
			keys.get(i).update();
		}
		
		keyDown = false;
		scrollAmount = 0;
	}
	
	public float getInputX(){
		float inputX = (Gdx.input.getX() - Gdx.graphics.getWidth() / 2f);
		inputX /= (Gdx.graphics.getWidth() / 2f);
		inputX *= (camera.viewportWidth / 2f) * camera.zoom;
		
		return inputX;
	}
	
	public float getInputY(){
		float inputY = -(Gdx.input.getY() - Gdx.graphics.getHeight() / 2f);
		inputY /= (Gdx.graphics.getHeight() / 2f);
		inputY *= (camera.viewportHeight / 2f) * camera.zoom;
		
		return inputY;
	}
	
	public boolean isAnyKeyDown(){
		return keyDown;
	}
	
	public int getScrollAmount(){
		return scrollAmount;
	}
	
	public void setCamera(OrthographicCamera camera){
		this.camera = camera;
	}
	
	public boolean keyDown(int keyCode){
		for(int i = 0; i < keys.size(); i++){
			if(keys.get(i).has(keyCode)) keys.get(i).press();
		}
		
		keyDown = true;
		
		return true;
	}
	
	public boolean keyUp(int keyCode){
		for(int i = 0; i < keys.size(); i++){
			if(keys.get(i).has(keyCode)) keys.get(i).release();
		}
		
		return true;
	}
	
	public boolean keyTyped(char character){
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button){
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button){
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer){
		return true;
	}
	
	public boolean touchMoved(int x, int y){
		return true;
	}
	
	public boolean scrolled(int amount){
		scrollAmount += amount;
		
		return true;
	}
	
	public class Key{
		private final int[] keyCodes;
		private boolean pressed, justPressed;
		
		public Key(int...keyCodes){
			this.keyCodes = keyCodes;
			
			if(keys == null) keys = new ArrayList<Key>();
			keys.add(this);
		}
		
		private void update(){
			justPressed = false;
		}
		
		public boolean has(int keyCode){
			for(int i = 0; i < keyCodes.length; i++){
				if(keyCodes[i] == keyCode) return true;
			}
			
			return false;
		}

		private void press(){
			pressed = true;
			justPressed = true;
		}
		
		private void release(){
			pressed = false;
		}
		
		public boolean isPressed(){
			return pressed;
		}
		
		public boolean isJustPressed(){
			return justPressed;
		}
	}
}
