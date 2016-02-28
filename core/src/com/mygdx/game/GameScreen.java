package com.mygdx.game;

/**
 * Created by cash on 15.02.2016.
 */

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen {

    final BirdFlyGame game;
    Stage stage;
    Texture GameElementsTexture;
    Texture GameBackTexture;
    TextureRegion InputManagePlaneTexture; // текстура 85x600
    TextureRegion BirdTexture; // текстра птица 85x80
    TextureRegion BirdFlyingTexture; // текстура летающего птица 85x80
    TextureRegion SprigTexture; // текстура ветки 184x45
    TextureRegion CloudTexture; // текстура облака! 177x80
    TextureRegion BackTexture;

    Image LeftScrollPlaneImage;
    Image RightScrollPlaneImage;
    Image BirdImage;
    Image SprigImage;
    Image CloudImage;
    Image BackImage;

    float SpeedToDown; // это скорость, с которой объекты перемещаются вниз!
    float SpeedToUp;
    PlaneDragInputListener LeftDragInputListener;
    PlaneDragInputListener RightDragInputListener;

    float AverageMovingCount; // с этим параметром будет сравниваться количество движения справа
                              // и слева!
    // этот вектор служит направлением движения птицы!
    Vector2 CurrentMovingVector;

    int FlightZoneHeight; // высота, до которой птичка птичка будет летать!
    int FlightZoneWidth; //  ширина видимой зоны, до которой птичка будет летать!
    // показывает сколько времени уже прошло
    // с последнего сеанса проверки полета!

    float CurrentFlyingTimer;
    float TimeToControlFlying; // время проверки параметров полета
    // проверяет - сколько пользователь наводил пальцем по поверхностям
    // если недостаточно - опускаемся...

    float CurrentDownMoveTimer;
    float TimeToDownMove;

    public GameScreen(final BirdFlyGame gam)
    {
        // устанавливаем игру
        this.game = gam;

        // каждые 0.2 секунды проверяем, сколько человек сделал движений!
        this.TimeToControlFlying = 0.1f;
        this.CurrentFlyingTimer = 0.0f;
        this.AverageMovingCount = 80f;
        this.CurrentMovingVector = new Vector2(0f, 0f);

        this.CurrentDownMoveTimer = 0.0f;
        this.TimeToDownMove = 0.08f;

        this.SpeedToDown = 5f;
        this.SpeedToUp = 4f;

        // подниматься будем до 2/3 экрана, дальше - прокрутка других актеров вниз!
        this.FlightZoneHeight = 2/3 * game.VIEW_HEIGHT;
        // в ширину будем летать по всей вилимой зоне!
        this.FlightZoneWidth = game.VIEW_WIDTH;

        this.stage = new Stage(new ScreenViewport());
        this.stage.getViewport().setScreenSize(game.VIEW_WIDTH, game.VIEW_HEIGHT);
        // грузим текстуры
        GameElementsTexture = new Texture(Gdx.files.internal("game_textures.png"));
        GameBackTexture = new Texture(Gdx.files.internal("game_back.png"));
        InputManagePlaneTexture = new TextureRegion(GameElementsTexture, 0, 0, 85, 600);
        BirdTexture = new TextureRegion(GameElementsTexture, 82, 0, 82, 80);
        BirdFlyingTexture = new TextureRegion(GameElementsTexture, 85 + 85, 0, 85, 80);
        SprigTexture = new TextureRegion(GameElementsTexture, 85 + 85 + 85, 0, 184, 45);
        CloudTexture = new TextureRegion(GameElementsTexture, 85 + 85 + 85 + 184, 0, 177, 88);
        BackTexture = new TextureRegion(GameBackTexture, 0, 0, game.VIEW_WIDTH, game.VIEW_HEIGHT);

        this.LeftDragInputListener = new PlaneDragInputListener();
        this.RightDragInputListener = new PlaneDragInputListener();

        this.LeftScrollPlaneImage = new Image(InputManagePlaneTexture);
        this.LeftScrollPlaneImage.addListener(LeftDragInputListener);
        this.RightScrollPlaneImage = new Image(InputManagePlaneTexture);
        this.RightScrollPlaneImage.addListener(RightDragInputListener);
        this.BirdImage = new Image(BirdTexture);
        this.SprigImage = new Image(SprigTexture);
        this.CloudImage = new Image(CloudTexture);
        this.BackImage = new Image(BackTexture);

        this.LeftScrollPlaneImage.setPosition(0, 0);
        this.RightScrollPlaneImage.setPosition(game.VIEW_WIDTH - RightScrollPlaneImage.getWidth(), 0);
        // установка птицы по центру экрана!
        this.BirdImage.setPosition(game.VIEW_WIDTH/2 - this.BirdImage.getWidth()/2,
                                    game.VIEW_HEIGHT / 2 - this.BirdImage.getHeight() / 2);

        this.SprigImage.setPosition(RightScrollPlaneImage.getX() - SprigImage.getWidth(), game.VIEW_HEIGHT - SprigImage.getHeight());
        this.CloudImage.setPosition(600, 100);
        this.BackImage.setPosition(0, 0);

        this.stage.addActor(BackImage);
        this.stage.addActor(CloudImage);
        this.stage.addActor(SprigImage);
        this.stage.addActor(BirdImage);
        this.stage.addActor(LeftScrollPlaneImage);
        this.stage.addActor(RightScrollPlaneImage);

    }

    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            this.game.setScreen(this.game.ThisMainMenuScreen);
        }

        this.CurrentFlyingTimer += delta;
        this.CurrentDownMoveTimer += delta;

        this.flyingControl();

        stage.draw();
    }

    public void flyingControl()
    {
        float lY = this.LeftDragInputListener.getMoveCounterY(),
                rY = this.RightDragInputListener.getMoveCounterY(),
                dY = -(rY - lY);

        if(this.CurrentFlyingTimer >= this.TimeToControlFlying)
        {
            Gdx.app.log("Main Process worker",
                    "Left: " + this.LeftDragInputListener.getMoveCounterY() +
                            "Right: " + this.RightDragInputListener.getMoveCounterY()
            );
            this.CurrentMovingVector.set(dY/ this.AverageMovingCount * this.SpeedToUp,
                    (rY + lY - Math.abs(dY)) * 3 / 4 /this.AverageMovingCount * this.SpeedToUp);

            this.LeftDragInputListener.clearMoveCounter();
            this.RightDragInputListener.clearMoveCounter();
            this.CurrentFlyingTimer -= this.TimeToControlFlying;
            this.BirdImage.moveBy(this.CurrentMovingVector.x, (-this.SpeedToDown) + this.CurrentMovingVector.y);

        }

    }

    public void intersectionControl()
    {

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
    public void pause(){}
    public void hide(){}
    public void dispose()
    {
        GameElementsTexture.dispose();
    }


}
