package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.USER_GHOST_01;
import static com.mygdx.game.extra.Utils.USER_GHOST_02;
import static com.mygdx.game.extra.Utils.USER_GHOST_03;
import static com.mygdx.game.extra.Utils.USER_GHOST_04;
import static com.mygdx.game.extra.Utils.USER_PACMAN;
import static com.mygdx.game.extra.Utils.USER_WALL;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Ghost;
import com.mygdx.game.actors.PacMan;
import com.mygdx.game.actors.Walls;





public class GameOverScreen  extends BaseScreen{

    //Todo 3. Creamos una valiabre contador....
    private int scoreNumber;

    private SpriteBatch batch;
    private TextureRegion texturaStartGame;
    private TextureRegion texturaFondo;
    private boolean touched;
    private float height, width;

    public GameOverScreen(MainGame mainGame) {
        super(mainGame);
        this.height = Gdx.graphics.getHeight()/3f;
        this.width = Gdx.graphics.getWidth()/2f;
    }

    @Override
    public void render(float delta) {
        //Elimina la imagen anterior anterior
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texturaFondo, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(texturaStartGame,Gdx.graphics.getWidth()/2 - (width/2), Gdx.graphics.getHeight()/2.8f, width, height);
        batch.end();
        if (Gdx.input.justTouched() && !touched) {
            touched = true;
        }
        if(touched) {
            mainGame.setScreen(new GameScreen(mainGame));
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        texturaFondo = mainGame.assetManager.getBgStartFinish();
        texturaStartGame = mainGame.assetManager.getStartText();
        touched = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}

