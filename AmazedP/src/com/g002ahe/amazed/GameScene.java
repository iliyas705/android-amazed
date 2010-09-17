//TO DO LIST
//try turning on physics
//improve collision detection math.intersets?
//Allow proper close and restart - saving state
//allow text - create a class // font sheet - kerning
//add scores
//allow in game menu options - including reset calibration for angled play
//add level system
//      target per level
//      graphics per level
//      consider xmas version
//		consider a shake option to get out of stiky situations





package com.g002ahe.amazed;

import java.util.Random;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.background.FixedBackground;
import com.stickycoding.rokon.device.Accelerometer;
import com.stickycoding.rokon.device.OnAccelerometerChange;



public class GameScene extends Scene {

	
	float player_ym, player_xm; 
	int playerscore, highscore;


	private FixedBackground background;
	private Vector2 gravity;
	private int extraballs, extraspeed;
	float[] bobxm = new float[300];
	float[] bobym = new float[300];
	PhysicalSprite[] bobspr = new PhysicalSprite[300];
	Sprite[] displaystringspr = new Sprite[300];
	private PhysicalSprite playerspr,goldstarspr, ball;

	Random rand = new Random();

	public GameScene() {
		super(3, 300);
		Debug.print("hi " + MainActivity.NUM_BALLS);
		setWorld(world = new World( gravity = new Vector2( 0, 0), false));
		setBackground(background = new FixedBackground(Textures.background));


		// Create the Bob sprites.
		for(int i = 0; i < MainActivity.NUM_BALLS+extraballs; i++) {

            bobspr[i] = new PhysicalSprite(rand.nextInt(MainActivity.GAME_WIDTH*1000)/1000,rand.nextInt(MainActivity.GAME_HEIGHT*1000)/1000, 1f  , 1f);
            bobspr[i].setName("Ball");
            //bobspr[i].setRGBA(1, 0, 0 , 1);
            bobspr[i].createDynamicCircle();
            bobspr[i].noPhysics();
            bobspr[i].setBorder(0, 0, 0, 0);
            bobspr[i].setTexture(Textures.bob);
      
            //bobspr[i].setTouchable();
            add(1,bobspr[i]);
        }
        playerspr = new PhysicalSprite(rand.nextInt(MainActivity.GAME_WIDTH*1000)/1000,rand.nextInt(MainActivity.GAME_HEIGHT*1000)/1000/100, 1.5f, 1.5f);
        playerspr.setName("Player");
        //playerspr.setRGBA(1, 0, 0 , 1);
        playerspr.createDynamicBox();
        playerspr.noPhysics();
        playerspr.setBorder(0, 0, 0, 0);
        playerspr.setTexture(Textures.player);
        //bobspr[i].setTouchable();
        add(1,playerspr);

        goldstarspr = new PhysicalSprite(rand.nextInt(MainActivity.GAME_WIDTH*1000)/1000,rand.nextInt(MainActivity.GAME_HEIGHT*1000)/1000, 1.5f, 1.5f);
        goldstarspr.setName("goldstar");
        //goldstarspr.setRGBA(1, 0, 0 , 1);
        goldstarspr.createDynamicCircle();
        goldstarspr.noPhysics();
        goldstarspr.setBorder(0, 0, 0, 0);
        goldstarspr.setTexture(Textures.goldstar);
        //bobspr[i].setTouchable();
        add(1,goldstarspr);
		

        
	}
	

    
	@Override
	public void onGameLoop() {
		// This is the game loop that is called once every frame.
		// It's where you update all your objects.
		
		// Here we make Bob constantly move to the right.

		for(int i = 0; i < MainActivity.NUM_BALLS+extraballs; i++) {
			if (bobspr[i].x >= MainActivity.GAME_WIDTH){bobxm[i]=(rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED * -1;}
			if (bobspr[i].x <= 0 ){bobxm[i]=(rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;}
			if (bobspr[i].y >= MainActivity.GAME_HEIGHT){bobym[i]=(rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED * -1;}
			if (bobspr[i].y <= 0 ){bobym[i]=(rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;}
			bobspr[i].x += bobxm[i];
			bobspr[i].y += bobym[i];
			if (rand.nextInt(3000)==1) {Debug.print("In onGameLoop" +" i="+ i + " bobxm="+bobxm[i] + " bobym="+ bobym[i]);};
         }
		playerspr.x+=player_xm;
		playerspr.y+=player_ym;
	// check collisions
		for(int i = 0; i < MainActivity.NUM_BALLS+extraballs; i++)
		{
			if ((bobspr[i].x - 1 < playerspr.x) & (bobspr[i].x + 1 > playerspr.x)
					& (bobspr[i].y - 1 < playerspr.y) & (bobspr[i].y + 1 > playerspr.y))
			{   highscore=playerscore;
				DisplayText(2,2,"Game over ! Your score was" +playerscore, 30);
				playerscore=0;
				extraballs=0;
				
				}
		}
		if ((goldstarspr.x - 1 < playerspr.x) & (goldstarspr.x + 1 > playerspr.x)
				& (goldstarspr.y - 1 < playerspr.y) & (goldstarspr.y + 1 > playerspr.y))
			{ 	playerscore +=1;
				goldstarspr.x=rand.nextInt(MainActivity.GAME_WIDTH*1000)/1000;
				goldstarspr.y=rand.nextInt(MainActivity.GAME_HEIGHT*1000)/1000;
				extraballs +=1;
				extraspeed +=1;
				DisplayText(2,17,"Score : " +playerscore, 30);
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1] = new PhysicalSprite(rand.nextInt(MainActivity.GAME_WIDTH*1000)/1000,rand.nextInt(MainActivity.GAME_HEIGHT*1000)/1000, 1f  , 1f);
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1].setName("Ball");
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1].createDynamicCircle();
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1].noPhysics();
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1].setBorder(0, 0, 0, 0);
	            bobspr[(MainActivity.NUM_BALLS + extraballs)-1].setTexture(Textures.bob);
				bobxm[(MainActivity.NUM_BALLS + extraballs)-1] = (rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;
				bobym[(MainActivity.NUM_BALLS + extraballs)-1] = (rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;
	            //bobspr[i].setTouchable();
	            add(1,bobspr[(MainActivity.NUM_BALLS + extraballs)-1]);
			}	
		}
		
	public void DisplayText(float x,float y,String text,float spritescale) 
	{
		
		for(int i = 0; i < text.length(); i++)
			{
			char aChar = text.charAt(i);
			int charVal = aChar -33;
			float spriteoffset=Textures.TFont.getTileWidth() / spritescale * i;
			if (charVal  != -1 ) 
				{
				displaystringspr[i] = new Sprite(x+spriteoffset,y, Textures.TFont.getTileWidth() /spritescale, Textures.TFont.getTileHeight()/spritescale);
				displaystringspr[i].setTexture(Textures.TFont);
				displaystringspr[i].setBorder(1, 1, 1, 1);
				displaystringspr[i].setTextureTile(charVal % 10, charVal/10 );
				displaystringspr[i].setName("Score1");
		        add(1,displaystringspr[i]);
				}
			}
	}

	@Override
	public void onPause() {
     Accelerometer.stopListening();

	}
	
	@Override
    public void onEndScene() {
        Accelerometer.stopListening();
        super.onEndScene();
    }


	@Override
	public void onResume() {
	     Accelerometer.startListening(new MyAccelerometerHandler());
	}

	@Override
	public void onReady() {
		// You can also set it to move over a period of time.
		// (if this is called again while it is already moving, the previous movement is canceled)
		// It takes x, y and time in ms.
	    Accelerometer.startListening(new MyAccelerometerHandler());
		for(int i = 0; i < MainActivity.NUM_BALLS+extraballs; i++) {
			bobxm[i] = (rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;
			bobym[i] = (rand.nextFloat()-0.5f)/MainActivity.INITIAL_BALL_SPEED;
		}
		extraballs=0;
		extraspeed=0;
	}

	class MyAccelerometerHandler implements OnAccelerometerChange {
	      @Override
	      public void onAccelerometerChange(float x, float y, float z) {
	         // React to the changes
	   //playerspr.body.applyForce(new Vector2(y*3,x*3), new Vector2(0.0f, 0.0f));

	   player_xm=y/33;
	   player_ym=x/33;

	      }

		@Override
		public void onShake(float arg0) {
			// TODO Auto-generated method stub
			
		}
	   }

}
