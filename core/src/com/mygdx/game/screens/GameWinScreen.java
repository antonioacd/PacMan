package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGame;


public class GameWinScreen  extends BaseScreen{

    //Todo 3. Creamos una valiabre contador....
    private int scoreNumber;

    private SpriteBatch batch;
    private TextureRegion texturaWin;
    private TextureRegion texturaFondo;
    private TextureRegion texturaRestart;
    private boolean touched;
    private float height, width;

    public GameWinScreen(MainGame mainGame) {
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
        batch.draw(texturaWin,Gdx.graphics.getWidth()/2 - (width/2), Gdx.graphics.getHeight()/2.8f, width, height);
        batch.draw(texturaRestart,Gdx.graphics.getWidth()/2 - (width/4), Gdx.graphics.getHeight()/5f, width/2, height/2);
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
        texturaWin = mainGame.assetManager.getWin();
        texturaRestart = mainGame.assetManager.getRestart();
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

