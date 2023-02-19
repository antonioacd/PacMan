package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Stage stage;
    private PacMan pacMan;
    private Ghost ghost01, ghost02, ghost03, ghost04;
    private Image background;

    //Declaramos un mundo
    private World world;

    //Depuración
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera worldCamera;
    //Score Cámara
    private OrthographicCamera fontCamera;
    private BitmapFont score;

    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);

        //Inicializamos el mundo dandole la gravedad, como en mi caso, no habra gravedad
        // le pondre 0,0
        this.world = new World(new Vector2(0,0), true);
        //Asignamos la vista al stage
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        //Colocamos la camara, la cual sera estatica
        this.worldCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        //Elimina la imagen anterior anterior
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(this.stage);

        this.stage.getBatch().setProjectionMatrix(worldCamera.combined);
        //Hace que no pase de menos de 30 FPS
        this.stage.act();
        //Llama a todos los acts de las clases
        this.world.step(delta, 6, 2);

        this.debugRenderer.render(this.world, this.worldCamera.combined);
    }

    @Override
    public void show() {
        //Añadir objetos
    }

    @Override
    public void hide() {
        this.pacMan.detach();
        this.pacMan.remove();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }
}
