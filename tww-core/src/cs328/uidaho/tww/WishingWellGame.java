package cs328.uidaho.tww;

import cs328.uidaho.tww.screens.MenuScreen;

public class WishingWellGame extends BaseGame {
	
	@Override
	public void create() {
		super.create();
		BaseGame.setActiveScreen(new MenuScreen());
	}
	
}
