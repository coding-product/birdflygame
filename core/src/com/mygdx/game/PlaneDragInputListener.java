package com.mygdx.game;

/**
 * Created by cash on 23.02.2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Vector2;
import java.lang.Math;

public class PlaneDragInputListener extends InputListener{
    private Vector2 MoveCounter;
    // приращение при последнем
    private Vector2 LastMove;

    public PlaneDragInputListener()
    {
        this.MoveCounter = new Vector2(0f, 0f);
        this.LastMove = new Vector2(0f, 0f);
    }

    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");

        LastMove.x = x;
        LastMove.y = y;

        return true;
    }
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
    }
    public void touchDragged(InputEvent event,
                             float x,
                             float y,
                             int pointer)
    {
        MoveCounter.x += Math.abs(x - LastMove.x);
        MoveCounter.y += Math.abs(y - LastMove.y);
        LastMove.x = x;
        LastMove.y = y;

    }

    public float getMoveCounterX()
    {
        return this.MoveCounter.x;
    }
    public float getMoveCounterY()
    {
        return this.MoveCounter.y;
    }

    public void clearMoveCounter()
    {
        this.MoveCounter.y = 0;
        this.MoveCounter.x = 0;
    }
}
