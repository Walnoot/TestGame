package walnoot.testgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class TestMain{
	public static void main(String[] args){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "TestGame";
		cfg.useGL20 = false;
		cfg.vSyncEnabled = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new TestGame(), cfg);
	}
}
