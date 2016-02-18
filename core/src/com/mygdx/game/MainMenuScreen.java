package com.mygdx.game;

/**
 * Created by cash on 15.02.2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    final BirdFlyGame game;
    Stage stage;
    Label HelloLabel;
    LabelStyle LStyle;
    Color Color1;
    BitmapFont Font1;

    public MainMenuScreen(final BirdFlyGame gam)
    {
        // устанавливаем игру
        this.game = gam;
        stage = new Stage(new ScreenViewport());
        stage.getViewport().setScreenSize(game.VIEW_WIDTH, game.VIEW_HEIGHT);
        Font1 = new BitmapFont();
        Color1 = new Color();
        LStyle = new LabelStyle(Font1, Color1);
        HelloLabel = new Label("HELLO!", LStyle);
        HelloLabel.setPosition(100, 100);
        HelloLabel.setHeight(80);
        HelloLabel.setWidth(200);
        stage.addActor(HelloLabel);
    }

    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // проверка выхода!
        if(Gdx.input.isCatchBackKey())
        {
            Gdx.app.exit();
        }
        if(Gdx.input.isTouched())
        {
            game.setScreen(game.ThisGameScreen);
        }
        stage.draw();
    }
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().apply();

    }
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height);
    }
    public void resume()
    {

    }
    public void pause()
    {

    }
    public void hide()
    {

    }
    public void dispose()
    {

    }

}
