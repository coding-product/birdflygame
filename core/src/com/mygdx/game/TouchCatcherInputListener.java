package com.mygdx.game;

/**
 * Created by cash on 28.02.2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.Input;

public class TouchCatcherInputListener extends InputListener {

    BirdFlyGame game;
    String CatcherType;
    public TouchCatcherInputListener(BirdFlyGame gam, String CatcherTyp)
    {
        this.game = gam;
        this.CatcherType = CatcherTyp;

    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
    {
            if(this.CatcherType == "setGameScreen")
            {
                game.setScreen(game.ThisGameScreen);
            }
            if(this.CatcherType == "setMainMenuScreen")
            {
                game.setScreen(game.ThisMainMenuScreen);
            }
            if(this.CatcherType == "exitGame")
            {
                Gdx.app.exit();
            }
        return false;
    }
}
