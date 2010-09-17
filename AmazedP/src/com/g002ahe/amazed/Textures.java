package com.g002ahe.amazed;

import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

public class Textures {

	public static TextureAtlas atlas;
	public static Texture background, bob, player, goldstar, TFont;

	public static void load() {
		atlas = new TextureAtlas();
		atlas.insert(background = new Texture("background.png"));
		atlas.insert(bob = new Texture("ball2.png")); // Here we add another texture for our character Bob.
		atlas.insert(player = new Texture("bob2.png")); 
		atlas.insert(goldstar = new Texture("goldstar2.png")); 
		atlas.insert(TFont = new Texture("font_sheet.png",10,10));
		atlas.complete();
	}
}
