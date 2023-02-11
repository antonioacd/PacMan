package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.USER_EXTERIOR_WALLS;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
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

public class GameScreen extends BaseScreen {

    private Stage stage;
    private PacMan pacMan;
    private Ghost ghost;
    private Image background;

    //Declaramos un mundo
    private World world;

    //Depuración
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    Walls wall01, wall02, wall03, wall04, wall05, wall06, wall07, wall08, wall09, wall10, wall11, wall12, wall13, wall14, wall15, wall16, wall17, wall18, wall19, wall20, wall21, wall22, wall23, wall24, wall25, wall26, wall27, wall28, wall29, wall30, wall31, wall32, wall33, wall34, wall35, wall36, wall37, wall38, wall39, wall40, wall41, wall42, wall43;


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

    private void endScreenTeleport() {
        if (pacMan.getY() < -0.1) {
            // Ajusta la posición para que aparezca por el lado derecho
            pacMan.arriba(pacMan.getX());
        }
        // Verifica si el actor ha salido de la pantalla por el lado derecho
        if (pacMan.getY() > WORLD_HEIGHT+0.1) {
            // Ajusta la posición para que aparezca por el lado izquierdo
            pacMan.abajo(pacMan.getX());
        }
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

    public void addGhost(){

        TextureRegion tRGhost = mainGame.assetManager.getGhost();
        //Asignamos la animacion a la que obtenemos del assetManager
        //Animation<TextureRegion> pacManSprite = mainGame.assetManager.getBirdAnimation();
        //Creamos el pacman asignandole el mundo y la posición
        this.ghost = new Ghost(this.world, tRGhost, new Vector2(4f, 2f), 4);
        //Añadimos el PacMan
        this.stage.addActor(this.ghost);

    }

    public void getDireccion(){

        this.stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                switch (keycode) {
                    case Input.Keys.LEFT:
                        pacMan.setDireccion(-1);
                        break;
                    case Input.Keys.RIGHT:
                        pacMan.setDireccion(1);
                        break;
                    case Input.Keys.DOWN:
                        pacMan.setDireccion(0);
                        break;
                    case Input.Keys.UP:
                        pacMan.setDireccion(2);
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
        endScreenTeleport();
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
        addGhost();
        getDireccion();
        addWalls();

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

    public void addWalls(){

        TextureRegion wall01 = mainGame.assetManager.getWall();
        this.wall01 = new Walls(this.world, wall01, new Vector2(0.55f,2.4f), 0.1f,1.95f);
        this.stage.addActor(this.wall01);

        TextureRegion wall02 = mainGame.assetManager.getWall();
        this.wall02 = new Walls(this.world, wall02, new Vector2(7.45f,2.4f), 0.1f,1.95f);
        this.stage.addActor(this.wall02);

        TextureRegion wall03 = mainGame.assetManager.getWall();
        this.wall03 = new Walls(this.world, wall03, new Vector2(4f,4.8f), 0.1f,1f);
        this.stage.addActor(this.wall03);

        TextureRegion wall04 = mainGame.assetManager.getWall();
        this.wall04 = new Walls(this.world, wall04, new Vector2(4f,0f), 0.1f,1f);
        this.stage.addActor(this.wall04);

        TextureRegion wall05 = mainGame.assetManager.getWall();
        this.wall05 = new Walls(this.world, wall05, new Vector2(1.20f,2.4f), 0.1f,0.5f);
        this.stage.addActor(this.wall05);

        TextureRegion wall06 = mainGame.assetManager.getWall();
        this.wall06 = new Walls(this.world, wall06, new Vector2(1.20f,3.9f), 0.1f,0.45f);
        this.stage.addActor(this.wall06);

        TextureRegion wall07 = mainGame.assetManager.getWall();
        this.wall07 = new Walls(this.world, wall07, new Vector2(1.20f,0.9f), 0.1f,0.45f);
        this.stage.addActor(this.wall07);

        TextureRegion wall08 = mainGame.assetManager.getWall();
        this.wall08 = new Walls(this.world, wall08, new Vector2(6.8f,2.4f), 0.1f,0.5f);
        this.stage.addActor(this.wall08);

        TextureRegion wall09 = mainGame.assetManager.getWall();
        this.wall09 = new Walls(this.world, wall09, new Vector2(6.8f,3.9f), 0.1f,0.45f);
        this.stage.addActor(this.wall09);

        TextureRegion wall10 = mainGame.assetManager.getWall();
        this.wall10 = new Walls(this.world, wall10, new Vector2(6.8f,0.9f), 0.1f,0.45f);
        this.stage.addActor(this.wall10);

        TextureRegion wall11 = mainGame.assetManager.getWall();
        this.wall11 = new Walls(this.world, wall11, new Vector2(1.75f,2.4f), 0.45f,0.1f);
        this.stage.addActor(this.wall11);

        TextureRegion wall12 = mainGame.assetManager.getWall();
        this.wall12 = new Walls(this.world, wall12, new Vector2(1.75f,3.9f), 0.45f,0.1f);
        this.stage.addActor(this.wall12);

        TextureRegion wall13 = mainGame.assetManager.getWall();
        this.wall13 = new Walls(this.world, wall13, new Vector2(1.75f,0.9f), 0.45f,0.1f);
        this.stage.addActor(this.wall13);

        TextureRegion wall14 = mainGame.assetManager.getWall();
        this.wall14 = new Walls(this.world, wall14, new Vector2(6.25f,2.4f), 0.45f,0.1f);
        this.stage.addActor(this.wall14);

        TextureRegion wall15 = mainGame.assetManager.getWall();
        this.wall15 = new Walls(this.world, wall15, new Vector2(6.25f,3.9f), 0.45f,0.1f);
        this.stage.addActor(this.wall15);

        TextureRegion wall16 = mainGame.assetManager.getWall();
        this.wall16 = new Walls(this.world, wall16, new Vector2(6.25f,0.9f), 0.45f,0.1f);
        this.stage.addActor(this.wall16);

        TextureRegion wall17 = mainGame.assetManager.getWall();
        this.wall17 = new Walls(this.world, wall17, new Vector2(3.30f,3.9f), 0.60f,0.1f);
        this.stage.addActor(this.wall17);

        TextureRegion wall18 = mainGame.assetManager.getWall();
        this.wall18 = new Walls(this.world, wall18, new Vector2(4.70f,3.9f), 0.60f,0.1f);
        this.stage.addActor(this.wall18);

        TextureRegion wall19 = mainGame.assetManager.getWall();
        this.wall19 = new Walls(this.world, wall19, new Vector2(3.30f,0.9f), 0.60f,0.1f);
        this.stage.addActor(this.wall19);

        TextureRegion wall20 = mainGame.assetManager.getWall();
        this.wall20 = new Walls(this.world, wall20, new Vector2(4.70f,0.9f), 0.60f,0.1f);
        this.stage.addActor(this.wall20);

        TextureRegion wall21 = mainGame.assetManager.getWall();
        this.wall21 = new Walls(this.world, wall21, new Vector2(2.6f,4.65f), 0.85f,0.2f);
        this.stage.addActor(this.wall21);

        TextureRegion wall22 = mainGame.assetManager.getWall();
        this.wall22 = new Walls(this.world, wall22, new Vector2(2.6f,0.12f), 0.85f,0.2f);
        this.stage.addActor(this.wall22);

        TextureRegion wall23 = mainGame.assetManager.getWall();
        this.wall23 = new Walls(this.world, wall23, new Vector2(5.4f,0.12f), 0.85f,0.2f);
        this.stage.addActor(this.wall23);

        TextureRegion wall24 = mainGame.assetManager.getWall();
        this.wall24 = new Walls(this.world, wall24, new Vector2(5.4f,4.65f), 0.85f,0.2f);
        this.stage.addActor(this.wall24);

        TextureRegion wall25 = mainGame.assetManager.getWall();
        this.wall25 = new Walls(this.world, wall25, new Vector2(2.30f,3.15f), 0.5f,0.2f);
        this.stage.addActor(this.wall25);

        TextureRegion wall26 = mainGame.assetManager.getWall();
        this.wall26 = new Walls(this.world, wall26, new Vector2(2.30f,1.65f), 0.5f,0.2f);
        this.stage.addActor(this.wall26);

        TextureRegion wall27 = mainGame.assetManager.getWall();
        this.wall27 = new Walls(this.world, wall27, new Vector2(5.70f,3.15f), 0.5f,0.2f);
        this.stage.addActor(this.wall27);

        TextureRegion wall28 = mainGame.assetManager.getWall();
        this.wall28 = new Walls(this.world, wall28, new Vector2(5.70f,1.65f), 0.5f,0.2f);
        this.stage.addActor(this.wall28);

        TextureRegion wall29 = mainGame.assetManager.getWall();
        this.wall29 = new Walls(this.world, wall29, new Vector2(4f,2.1f), 0.65f,0.05f);
        this.stage.addActor(this.wall29);

        TextureRegion wall30 = mainGame.assetManager.getWall();
        this.wall30 = new Walls(this.world, wall30, new Vector2(3.30f,2.35f), 0.05f,0.3f);
        this.stage.addActor(this.wall30);

        TextureRegion wall31 = mainGame.assetManager.getWall();
        this.wall31 = new Walls(this.world, wall31, new Vector2(4.7f,2.35f), 0.05f,0.3f);
        this.stage.addActor(this.wall31);

        TextureRegion wall32 = mainGame.assetManager.getWall();
        this.wall32 = new Walls(this.world, wall32, new Vector2(3.52f,2.7f), 0.27f,0.05f);
        this.stage.addActor(this.wall32);

        TextureRegion wall33 = mainGame.assetManager.getWall();
        this.wall33 = new Walls(this.world, wall33, new Vector2(4.48f,2.7f), 0.27f,0.05f);
        this.stage.addActor(this.wall33);

        TextureRegion wall34 = mainGame.assetManager.getWall();
        this.wall34 = new Walls(this.world, wall34, new Vector2(2.95f,2.4f), 0.30f,0.1f);
        this.stage.addActor(this.wall34);

        TextureRegion wall35 = mainGame.assetManager.getWall();
        this.wall35 = new Walls(this.world, wall35, new Vector2(5.05f,2.4f), 0.30f,0.1f);
        this.stage.addActor(this.wall35);

        TextureRegion wall36 = mainGame.assetManager.getWall();
        this.wall36 = new Walls(this.world, wall36, new Vector2(4f,3.28f), 0.75f,0.08f);
        this.stage.addActor(this.wall36);

        TextureRegion wall37 = mainGame.assetManager.getWall();
        this.wall37 = new Walls(this.world, wall37, new Vector2(4f,1.52f), 0.75f,0.08f);
        this.stage.addActor(this.wall37);

        TextureRegion wall38 = mainGame.assetManager.getWall();
        this.wall38 = new Walls(this.world, wall38, new Vector2(6.55f,0), 2f,0.01f);
        this.stage.addActor(this.wall38);

        TextureRegion wall39 = mainGame.assetManager.getWall();
        this.wall39 = new Walls(this.world, wall39, new Vector2(1.45f,0), 2f,0.01f);
        this.stage.addActor(this.wall39);

        TextureRegion wall40 = mainGame.assetManager.getWall();
        this.wall40 = new Walls(this.world, wall40, new Vector2(1.45f,4.8f), 2f,0.01f);
        this.stage.addActor(this.wall40);

        TextureRegion wall41 = mainGame.assetManager.getWall();
        this.wall41 = new Walls(this.world, wall41, new Vector2(6.55f,4.8f), 2f,0.01f);
        this.stage.addActor(this.wall41);

        TextureRegion wall42 = mainGame.assetManager.getWall();
        this.wall42 = new Walls(this.world, wall42, new Vector2(0f,2.4f), 0.01f,2.5f);
        this.stage.addActor(this.wall42);

        TextureRegion wall43 = mainGame.assetManager.getWall();
        this.wall43 = new Walls(this.world, wall43, new Vector2(8f,2.4f), 0.01f,2.5f);
        this.stage.addActor(this.wall43);

    }

}