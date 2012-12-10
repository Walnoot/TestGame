package walnoot.testgame;

import walnoot.testgame.states.GameplayState;
import walnoot.testgame.states.State;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TestGame implements ApplicationListener{
	public static final float UPDATES_PER_SECOND = 60, SECONDS_PER_UPDATE = 1 / UPDATES_PER_SECOND;
	public static final float FONT_SCALE = 1f / 64f;
	public static BitmapFont FONT;
	public static Texture TEXTURE;
	public static Preferences PREFERENCES;
	//public static SoundManager SOUND_MANAGER = new SoundManager();
	public static final InputHandler INPUT = new InputHandler();
	
	public static State state;
	
	private float updateTimer;
	private FPSLogger fpsLogger;
	
	public void create(){
		Gdx.input.setInputProcessor(INPUT);
		
		PREFERENCES = Gdx.app.getPreferences("warPrefs");
		
		//TEXTURE = new Texture("images.png");
		//TEXTURE.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		
		fpsLogger = new FPSLogger();
		
		state = new GameplayState();
	}
	
	public void dispose(){
		//PREFERENCES.putBoolean(SoundManager.PREF_SOUND_KEY, SOUND_MANAGER.isPlaying());
		PREFERENCES.flush();
		
		//FONT.dispose();
		//TEXTURE.dispose();
		//SOUND_MANAGER.dispose();
	}
	
	public void render(){
		updateTimer += Gdx.graphics.getDeltaTime();
		while(updateTimer > SECONDS_PER_UPDATE){
			updateTimer -= SECONDS_PER_UPDATE;
			
			update();
		}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		state.render();
	}
	
	public void update(){
		if(INPUT.escape.isPressed()) Gdx.app.exit();
		
		state.update();
		//if(SOUND_MANAGER.isLoaded()) SOUND_MANAGER.update();
		INPUT.update();
		
		fpsLogger.log();
	}
	
	public static void setState(State state){
		TestGame.state = state;
	}
	
	public void resize(int width, int height){
		state.resize();
	}
	
	public void pause(){
		state.pause();
	}
	
	public void resume(){
		state.resume();
	}
}