package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGame;


public class GetReadyScreen extends BaseScreen {

    private int scoreNumber;

    private SpriteBatch batch;
    private TextureRegion texturaStartGame;
    private TextureRegion texturaFondo;
    private boolean touched;
    private Music musicBG;
    private float height, width;

    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();
        this.musicBG = mainGame.assetManager.getBgStart();
    }

    @Override
    public void render(float delta) {
        //Elimina la imagen anterior anterior
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texturaFondo, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(texturaStartGame,Gdx.graphics.getWidth()/2 - (width/2f/2f), Gdx.graphics.getHeight()/2.5f, width/2f, height/4f);
        batch.end();

        if (Gdx.input.justTouched() && !touched) {
            touched = true;
        }
        if(touched) {
            musicBG.stop();
            mainGame.setScreen(new GameScreen(mainGame));
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        texturaFondo = mainGame.assetManager.getBgStartFinish();
        texturaStartGame = mainGame.assetManager.getStartText();
        touched = false;
        musicBG.setLooping(true);
        musicBG.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        musicBG.dispose();
    }
}
