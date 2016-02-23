package com.mygdx.game;

/**
 * Created by cash on 15.02.2016.
 */

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
public class GameScreen implements Screen {

    final BirdFlyGame game;
    Stage stage;
    Texture GameElementsTexture;
    TextureRegion InputManagePlaneTexture; // текстура 85x600
    TextureRegion BirdTexture; // текстра птица 85x80
    TextureRegion BirdFlyingTexture; // текстура летающего птица 85x80
    TextureRegion SprigTexture; // текстура ветки 184x45
    Image LeftScrollPlaneImage;
    Image RightScrollPlaneImage;
    Image BirdImage;
    Image SprigImage;
//    ScrollPane LeftScrollPane;
//    ScrollPane RightScrollPane;

    float SpeedToDown; // это скорость, с которой объекты перемещаются вниз!
    float LeftFlatMoveCounter; // счетчик движения слева!
    float RightFlatMoveCounter; // счетчик движения справа!

    float TimeToControlFlying; // время проверки параметров полета
                               // проверяет - сколько пользователь наводил пальцем по поверхностям
                              // если недостаточно - опускаемся...
    float AverageMovingCount; // с этим параметром будет сравниваться количество движения справа
                              // и слева!


    int FlightZoneHeight; // высота, до которой птичка птичка будет летать!
    int FlightZoneWidth; //  ширина видимой зоны, до которой птичка будет летать!
    public GameScreen(final BirdFlyGame gam)
    {
        // устанавливаем игру
        this.game = gam;
        // устанавливаем скроллпаны!
//        LeftScrollPane = new ScrollPane(LeftScrollPlaneImage);
//        RightScrollPane = new ScrollPane(RightScrollPlaneImage);

        // подниматься будем до 2/3 экрана, дальше - прокрутка других актеров вниз!
        FlightZoneHeight = 2/3 * game.VIEW_HEIGHT;
        // в ширину будем летать по всей вилимой зоне!
        FlightZoneWidth = game.VIEW_WIDTH;

        stage = new Stage(new ScreenViewport());
        stage.getViewport().setScreenSize(game.VIEW_WIDTH, game.VIEW_HEIGHT);
        // грузим текстуры
        GameElementsTexture = new Texture(Gdx.files.internal("game_textures.png"));
        InputManagePlaneTexture = new TextureRegion(GameElementsTexture, 0, 0, 85, 600);
        BirdTexture = new TextureRegion(GameElementsTexture, 85, 0, 85, 80);
        BirdFlyingTexture = new TextureRegion(GameElementsTexture, 85 + 85, 0, 85, 80);
        SprigTexture = new TextureRegion(GameElementsTexture, 85 + 85 + 85, 0, 184, 45);

        LeftScrollPlaneImage = new Image(InputManagePlaneTexture);
        LeftScrollPlaneImage.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
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
                Gdx.app.log("From LeftScrollPlaneImage", "x: " + x + ", y: " + y);
            }
        });
        RightScrollPlaneImage = new Image(InputManagePlaneTexture);
        RightScrollPlaneImage.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
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
                Gdx.app.log("From RightScrollPlaneImage", "x: " + x + ", y: " + y);
            }
        });
        BirdImage = new Image(BirdTexture);
        BirdImage = new Image(BirdTexture);
        SprigImage = new Image(SprigTexture);

//        LeftScrollPane = new ScrollPane(LeftScrollPlaneImage);
// RightScrollPane = new ScrollPane(RightScrollPlaneImage);

        LeftScrollPlaneImage.setPosition(0, 0);
        RightScrollPlaneImage.setPosition(game.VIEW_WIDTH - RightScrollPlaneImage.getWidth(), 0);
        BirdImage.setPosition(200, 100);

        SprigImage.setPosition(game.VIEW_WIDTH - SprigImage.getWidth(), game.VIEW_HEIGHT - SprigImage.getHeight());

        stage.addActor(BirdImage);
        stage.addActor(SprigImage);
        stage.addActor(LeftScrollPlaneImage);
        stage.addActor(RightScrollPlaneImage);
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().apply();
    }

    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isCatchBackKey())
        {
            game.setScreen(game.ThisMainMenuScreen);
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
        GameElementsTexture.dispose();
    }

}
