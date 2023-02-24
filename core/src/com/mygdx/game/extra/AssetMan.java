package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.CHERRY;
import static com.mygdx.game.extra.Utils.DEADSOUND;
import static com.mygdx.game.extra.Utils.EATCHERRY;
import static com.mygdx.game.extra.Utils.EATGHOST;
import static com.mygdx.game.extra.Utils.GHOST01;
import static com.mygdx.game.extra.Utils.GHOST02;
import static com.mygdx.game.extra.Utils.GHOST03;
import static com.mygdx.game.extra.Utils.GHOST04;
import static com.mygdx.game.extra.Utils.MUSICBG;
import static com.mygdx.game.extra.Utils.PACMAN1;
import static com.mygdx.game.extra.Utils.PACMAN2;
import static com.mygdx.game.extra.Utils.PACMAN3;
import static com.mygdx.game.extra.Utils.START_GAME;
import static com.mygdx.game.extra.Utils.WALL;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetMan {

    //Creamos el asset manager que cargara los datos multimedia
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    //Constructor
    public AssetMan(){
        //Inicializamos el assetManager
        this.assetManager = new AssetManager();

        //Musica
        assetManager.load(MUSICBG, Music.class);
        assetManager.load(EATCHERRY, Sound.class);
        assetManager.load(EATGHOST, Sound.class);
        assetManager.load(DEADSOUND, Sound.class);

        //Cargamos el Atlas
        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.finishLoading();

        //Obtenemos el atlas
        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    //IMAGEN DE FONDO
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    //ANIMACIÓN PÁJARO
    public Animation<TextureRegion> getBirdAnimation(){
        return new Animation<TextureRegion>(0.11f,
                textureAtlas.findRegion(PACMAN3),
                textureAtlas.findRegion(PACMAN2),
                textureAtlas.findRegion(PACMAN1));
    }

    //Textura de las paredes

    public TextureRegion getWall(){
        return this.textureAtlas.findRegion(WALL);
    }

    //Texturas de los fantasmas

    public TextureRegion getRedGhost(){
        return this.textureAtlas.findRegion(GHOST01);
    }
    public TextureRegion getBlueRedGhost(){return this.textureAtlas.findRegion(GHOST02);}
    public TextureRegion getPinkGhost(){return this.textureAtlas.findRegion(GHOST03);}
    public TextureRegion getOrangeGhost(){return this.textureAtlas.findRegion(GHOST04);}

    //Textura de la cereza

    public TextureRegion getCherry(){return this.textureAtlas.findRegion(CHERRY);}

    //Sonidos

    public Music getMusicBackground(){return this.assetManager.get(MUSICBG);}
    public Sound getEatCherrySound(){return this.assetManager.get(EATCHERRY);}
    public Music getEatGhostSound(){return this.assetManager.get(EATGHOST);}
    public Sound getDeadSound(){return this.assetManager.get(DEADSOUND);}

    //Textos del juego
    public TextureRegion getStartText(){return this.textureAtlas.findRegion(START_GAME);}


}














