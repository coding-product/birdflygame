package com.mygdx.game;

/**
 * Created by cash on 15.02.2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen implements Screen {

    final BirdFlyGame game;
    Stage stage;
    Label StartLabel, ExitLabel;
    LabelStyle LStyle;
    Color Color1;
    BitmapFont Font1;
    Texture BackTexture;
    TextureRegion BackTextureRegion;
    Image BackImage;
    TouchCatcherInputListener startListener;
    TouchCatcherInputListener exitListener;
    public MainMenuScreen(final BirdFlyGame gam)
    {
        // устанавливаем игру
        this.game = gam;
        stage = new Stage(new ScreenViewport());
        stage.getViewport().setScreenSize(game.VIEW_WIDTH, game.VIEW_HEIGHT);

        this.BackTexture = new Texture(Gdx.files.internal("game_back.png"));
        this.BackTextureRegion = new TextureRegion(this.BackTexture, 0, 0, game.VIEW_WIDTH, game.VIEW_HEIGHT);
        BackImage = new Image(BackTextureRegion);

        startListener = new TouchCatcherInputListener(this.game, "setGameScreen");
        exitListener = new TouchCatcherInputListener(this.game, "exitGame");

        Font1 = new BitmapFont();
        Color1 = new Color(Color1.BLACK);
        LStyle = new LabelStyle(Font1, Color1);
        StartLabel = new Label("START!", LStyle);

        StartLabel.addListener(startListener);
        StartLabel.setPosition(200, 200);
        StartLabel.setFontScale(3, 3);

        ExitLabel = new Label("Exit", LStyle);
        ExitLabel.addListener(exitListener);
        ExitLabel.setFontScale(3, 3);
        ExitLabel.setPosition(100, 100);

        stage.addActor(BackImage);
        stage.addActor(StartLabel);
        stage.addActor(ExitLabel);

        Gdx.input.setInputProcessor(stage);
        stage.getViewport().apply();

    }

    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // проверка выхода!
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
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().apply();
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
