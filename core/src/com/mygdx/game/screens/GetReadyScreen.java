package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Ghost;
import com.mygdx.game.actors.PacMan;


public class GetReadyScreen extends BaseScreen {

    //Todo 3. Creamos una valiabre contador....
    private int scoreNumber;

    private SpriteBatch batch;
    private TextureRegion textura;
    private boolean touched;
    private int height, width;

    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        this.height = 200;
        this.width = 300;
    }

    @Override
    public void render(float delta) {
        //Elimina la imagen anterior anterior
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textura,Gdx.graphics.getWidth()/2 - (width/2), Gdx.graphics.getHeight()/2, width, height);
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
        textura = mainGame.assetManager.getStartText();
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
