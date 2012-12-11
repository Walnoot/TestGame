package walnoot.testgame.states;

public abstract class State{
	public abstract void render();
	
	/**
	 * update method, called at fixed rate (TestGame.UPDATES_PER_SECOND)
	 */
	public abstract void fixedUpdate();
	
	public abstract void dispose();
	
	/**
	 * update method, called every frame;
	 */
	public void update(){
	}
	
	public void resize(){
	}

	public void pause(){
	}

	public void resume(){
	}
}
