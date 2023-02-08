package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.PacMan;

public class GameScreen  extends BaseScreen {

    private Stage stage;
    private PacMan pacMan;
    private Image background;

    //Declaramos un mundo
    private World world;

    //Depuración
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    public GameScreen(MainGame mainGame) {
        super(mainGame);

        //Inicializamos el mundo dandole la gravedad, como en mi caso, no habra gravedad
        // le pondre 0,0
        this.world = new World(new Vector2(0,0), true);

        //Asignamos la vista al stage
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        //Colocamos la camara, la cual sera estatica
        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    //Añadimos el fondo
    public void addBackground() {
        //Obtenemos la imagen
        this.background = new Image(mainGame.assetManager.getBackground());
        //La colocamos en la posicion
        this.background.setPosition(0, 0);
        //Le asignamos el tamaño
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        //La añadimos
        this.stage.addActor(this.background);
    }

    //Añadimos el PacMan
    public void addPacMan() {
        //Asignamos la animacion a la que obtenemos del assetManager
        Animation<TextureRegion> pacManSprite = mainGame.assetManager.getBirdAnimation();
        //Creamos el pacman asignandole el mundo y la posición
        this.pacMan = new PacMan(this.world, pacManSprite, new Vector2(4f, 4f), 4);
        //Añadimos el PacMan
        this.stage.addActor(this.pacMan);
    }

    public void getDireccion(){

        this.stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                switch (keycode) {
                    case Input.Keys.LEFT:
                        pacMan.setDireccion(-1);
                        pacMan.setRotation(180);
                        System.err.println("Izquierda pulsada");
                        break;
                    case Input.Keys.RIGHT:
                        pacMan.setDireccion(1);
                        System.err.println("Derecha pulsada");
                        break;
                    case Input.Keys.DOWN:
                        pacMan.setDireccion(0);
                        System.err.println("Abajo pulsado");
                        break;
                    case Input.Keys.UP:
                        pacMan.setDireccion(2);
                        System.err.println("Arriba pulsada");
                        break;
                }

                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {

                switch (keycode) {
                    case Input.Keys.LEFT:
                        pacMan.setDireccion(4);
                        System.err.println("Izquierda pulsada");
                        break;
                    case Input.Keys.RIGHT:
                        pacMan.setDireccion(4);
                        System.err.println("Derecha pulsada");
                        break;
                    case Input.Keys.DOWN:
                        pacMan.setDireccion(4);
                        System.err.println("Abajo pulsado");
                        break;
                    case Input.Keys.UP:
                        pacMan.setDireccion(4);
                        System.err.println("Arriba pulsada");
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void render(float delta) {
        //Elimina la imagen anterior anterior
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(this.stage);
        //Hace que no pase de menos de 30 FPS
        this.stage.act();
        //Se encarga de la deteccion de colisiones
        this.world.step(delta,6,2);
        //Ejecuta el metodo draw
        this.stage.draw();
        //
        this.debugRenderer.render(this.world, this.ortCamera.combined);
    }

    @Override
    public void show() {
        //Se muestran el fondo y el PacMan
        addBackground();
        addPacMan();
        getDireccion();

        /*TextureRegion wall01 = mainGame.assetManager.getPipeDownTR();
        this.pipes = new Pipes(this.world, pipeTRDown, new Vector2(3.75f,2f));
        this.stage.addActor(this.pipes);*/

    }

    @Override
    public void hide() {
        this.pacMan.detach();
        this.pacMan.remove();
    }


    @Override
    public void dispose() {
        //Elimina el escenario y el mundo
        this.stage.dispose();
        this.world.dispose();
    }



}