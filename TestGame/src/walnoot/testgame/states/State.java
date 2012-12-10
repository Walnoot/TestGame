package walnoot.testgame.states;

public abstract class State{
	public abstract void render();
	
	public abstract void update();
	
	public abstract void dispose();
	
	public void resize(){
	}

	public void pause(){
	}

	public void resume(){
	}
}
