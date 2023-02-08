package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.PACMAN1;
import static com.mygdx.game.extra.Utils.PACMAN2;
import static com.mygdx.game.extra.Utils.PACMAN3;
import static com.mygdx.game.extra.Utils.WALL;

import com.badlogic.gdx.assets.AssetManager;
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
        return new Animation<TextureRegion>(0.33f,
                textureAtlas.findRegion(PACMAN3),
                textureAtlas.findRegion(PACMAN2),
                textureAtlas.findRegion(PACMAN1));

    }

    //Textura de las tuberías
    public TextureRegion getWall(){
        return this.textureAtlas.findRegion(WALL);
    }


}














