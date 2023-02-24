package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.USER_CHERRY_01;
import static com.mygdx.game.extra.Utils.USER_CHERRY_02;
import static com.mygdx.game.extra.Utils.USER_CHERRY_03;
import static com.mygdx.game.extra.Utils.USER_CHERRY_04;
import static com.mygdx.game.extra.Utils.USER_CHERRY_05;
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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Cherry;
import com.mygdx.game.actors.Ghost;
import com.mygdx.game.actors.PacMan;
import com.mygdx.game.actors.Walls;

import java.util.ArrayList;

public class GameScreen extends BaseScreen implements ContactListener {

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

    Vector2 posicionAntGhost;

    int dirGhost01Ultima = 1;
    int dirGhost01Anterior = 1;
    int dirGhost02Ultima = 1;
    int dirGhost02Anterior = 1;
    int dirGhost03Ultima = -1;
    int dirGhost03Anterior = -1;
    int dirGhost04Ultima = -1;
    int dirGhost04Anterior = -1;

    float tiempo = 0;

    Walls wall01, wall02, wall03, wall04, wall05, wall06, wall07, wall08, wall09, wall10, wall11, wall12, wall13, wall14, wall15, wall16, wall17, wall18, wall19, wall20, wall21, wall22, wall23, wall24, wall25, wall26, wall27, wall28, wall29, wall30, wall31, wall32, wall33, wall34, wall35, wall36, wall37, wall38, wall39, wall40, wall41, wall42, wall43;

    private Cherry cherry01, cherry02, cherry03, cherry04, cherry05;

    private int contadorCherry;

    ArrayList<Vector2> listRndPos;

    private Music bgMusic;
    private Sound deadSound;
    private Sound eatCherrySound;

    public GameScreen(MainGame mainGame) {
        super(mainGame);

        //Inicializamos el mundo dandole la gravedad, como en mi caso, no habra gravedad
        // le pondre 0,0
        this.world = new World(new Vector2(0,0), true);

        this.world.setContactListener(this);
        //Asignamos la vista al stage
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        //Colocamos la camara, la cual sera estatica
        this.worldCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();

        //Sonidos
        bgMusic = this.mainGame.assetManager.getMusicBackground();
        deadSound = this.mainGame.assetManager.getDeadSound();
        eatCherrySound = this.mainGame.assetManager.getEatCherrySound();

        contadorCherry = 0;

        addPositions();
    }

    public void addPositions(){

        listRndPos = new ArrayList<Vector2>();
        listRndPos.add(new Vector2(0.25f, 1f));
        listRndPos.add(new Vector2(0.25f, 3.6f));
        listRndPos.add(new Vector2(7.75f, 1f));
        listRndPos.add(new Vector2(0.85f, 3.6f));
        listRndPos.add(new Vector2(0.85f, 1f));
        listRndPos.add(new Vector2(7.15f, 1f));
        listRndPos.add(new Vector2(7.15f, 3.6f));
        listRndPos.add(new Vector2(7.75f, 3.6f));
        listRndPos.add(new Vector2(1.6f, 2.7f));
        listRndPos.add(new Vector2(6.4f, 2.7f));
        listRndPos.add(new Vector2(6.4f, 2f));
        listRndPos.add(new Vector2(1.6f, 2f));
        listRndPos.add(new Vector2(3.2f, 4.2f));
        listRndPos.add(new Vector2(3.2f, 0.55f));
        listRndPos.add(new Vector2(4.8f, 0.55f));
        listRndPos.add(new Vector2(4.8f, 4.2f));

    }


    ///////////////////////////////////////////////////
    /////////////////MÉTODOS PROPIOS///////////////////
    ///////////////////////////////////////////////////

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
        ghostsTeleport();
        pacManTeleport();
        seeGhosts();

        //this.debugRenderer.render(this.world, this.worldCamera.combined);
    }

    @Override
    public void show() {
        //Se muestran el fondo y el PacMan
        addBackground();
        addPacMan();
        addCherry();
        addGhosts();
        addWalls();

        bgMusic.setLooping(true);
        bgMusic.play();
    }

    @Override
    public void hide() {
        this.pacMan.detach();
        this.pacMan.remove();
        this.cherry01.detach();
        this.cherry01.remove();
        this.bgMusic.stop();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }

    ///////////////////////////////////////////////////
    //////////////////////PACMAN///////////////////////
    ///////////////////////////////////////////////////

    //Añadimos el PacMan
    public void addPacMan() {
        //Asignamos la animacion a la que obtenemos del assetManager
        Animation<TextureRegion> pacManSprite = mainGame.assetManager.getBirdAnimation();
        //Creamos el pacman asignandole el mundo y la posición
        this.pacMan = new PacMan(this.world, pacManSprite, new Vector2(3.8f, 2.4f), 4);
        //Añadimos el PacMan
        this.stage.addActor(this.pacMan);
    }

    //Metodo para mover el PacMan de arriba a abajo y viceversa
    private void pacManTeleport() {
        if (pacMan.getY() < -0.2) {pacMan.arriba(pacMan.getX());}
        if (pacMan.getY() > WORLD_HEIGHT-0.1) {pacMan.abajo(pacMan.getX());}
    }

    ///////////////////////////////////////////////////
    //////////////////////GHOSTS///////////////////////
    ///////////////////////////////////////////////////

    //Añadimos los fantasmas
    public void addGhosts(){
        TextureRegion redGhost = mainGame.assetManager.getRedGhost();
        this.ghost01 = new Ghost(this.world, redGhost, new Vector2(1.55f, 3.55f), 1,USER_GHOST_01);

        TextureRegion blueGhost = mainGame.assetManager.getBlueRedGhost();
        this.ghost02 = new Ghost(this.world, blueGhost, new Vector2(6.45f, 3.55f), 1,USER_GHOST_02);

        TextureRegion pinkGhost = mainGame.assetManager.getPinkGhost();
        this.ghost03 = new Ghost(this.world, pinkGhost, new Vector2(1.55f, 1.25f), 1,USER_GHOST_03);

        TextureRegion orangeGhost = mainGame.assetManager.getOrangeGhost();
        this.ghost04 = new Ghost(this.world, orangeGhost, new Vector2(6.45f, 1.25f), 1,USER_GHOST_04);

        //Añadimos el Ghost
        this.stage.addActor(this.ghost01);
        this.stage.addActor(this.ghost02);
        this.stage.addActor(this.ghost03);
        this.stage.addActor(this.ghost04);
    }

    //Metodo que comprueba que los fantasmas no esten parados
    //(dentro de este metodo se realiza el draw, y este metodo se llama en el render)
    public void seeGhosts(){

        Vector2 xAnteriorG1 = new Vector2(this.ghost01.getX(), this.ghost01.getY());
        Vector2 xAnteriorG2 = new Vector2(this.ghost02.getX(), this.ghost02.getY());
        Vector2 xAnteriorG3 = new Vector2(this.ghost03.getX(), this.ghost03.getY());
        Vector2 xAnteriorG4 = new Vector2(this.ghost04.getX(), this.ghost04.getY());
        int num1, num2, num3, num4;
        this.stage.draw();

        if (xAnteriorG1.equals(new Vector2(this.ghost01.getX(), this.ghost01.getY()))) {
            do {
                num1 = MathUtils.random(-1,2);
            }while (dirGhost01Anterior == num1 && dirGhost01Ultima == num1);
            this.ghost01.setDirection(num1);
        }

        if (xAnteriorG2.equals(new Vector2(this.ghost02.getX(), this.ghost02.getY()))) {
            do {
                num2 = MathUtils.random(-1,2);
            }while (dirGhost02Anterior == num2 && dirGhost02Ultima == num2);
            this.ghost02.setDirection(num2);
        }

        if (xAnteriorG3.equals(new Vector2(this.ghost03.getX(), this.ghost03.getY()))) {
            do {
                num3 = MathUtils.random(-1,2);
            }while (dirGhost03Anterior == num3 && dirGhost03Ultima == num3);
            this.ghost03.setDirection(num3);
        }

        if (xAnteriorG4.equals(new Vector2(this.ghost04.getX(), this.ghost04.getY()))) {
            do {
                num4 = MathUtils.random(-1,2);
            }while (dirGhost04Anterior == num4 && dirGhost04Ultima == num4);
            this.ghost04.setDirection(num4);
        }
    }

    //Método para mover los Fantasmas de arriba a abajo y viceversa
    public void ghostsTeleport(){

        if (pacMan.getY() < -0.2) {pacMan.arriba(pacMan.getX());}
        if (pacMan.getY() > WORLD_HEIGHT-0.1) {pacMan.abajo(pacMan.getX());}

        if (ghost01.getY() < -0.2) {ghost01.arriba(ghost01.getX());}
        if (ghost01.getY() > WORLD_HEIGHT-0.1) {ghost01.abajo(ghost01.getX());}

        if (ghost02.getY() < -0.2) {ghost02.arriba(ghost02.getX());}
        if (ghost02.getY() > WORLD_HEIGHT-0.1) {ghost02.abajo(ghost02.getX());}

        if (ghost03.getY() < -0.2) {ghost03.arriba(ghost03.getX());}
        if (ghost03.getY() > WORLD_HEIGHT-0.1) {ghost03.abajo(ghost03.getX());}

        if (ghost04.getY() < -0.2) {ghost04.arriba(ghost04.getX());}
        if (ghost04.getY() > WORLD_HEIGHT-0.1) {ghost04.abajo(ghost04.getX());}

    }

    ///////////////////////////////////////////////////
    //////////////////////ENTORNO//////////////////////
    ///////////////////////////////////////////////////

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

    //Método que añade las paredes del mapa
    public void addWalls(){

        TextureRegion wall = mainGame.assetManager.getWall();

        this.wall01 = new Walls(this.world, wall, new Vector2(0.55f,2.4f), 0.1f,1.95f);
        this.stage.addActor(wall01);

        this.wall02 = new Walls(this.world, wall, new Vector2(7.45f,2.4f), 0.1f,1.95f);
        this.stage.addActor(wall02);

        this.wall03 = new Walls(this.world, wall, new Vector2(4f,4.8f), 0.1f,1f);
        this.stage.addActor(wall03);

        this.wall04 = new Walls(this.world, wall, new Vector2(4f,0f), 0.1f,1f);
        this.stage.addActor(wall04);

        this.wall05 = new Walls(this.world, wall, new Vector2(1.20f,2.4f), 0.1f,0.5f);
        this.stage.addActor(wall05);

        this.wall06 = new Walls(this.world, wall, new Vector2(1.20f,3.9f), 0.1f,0.45f);
        this.stage.addActor(wall06);

        this.wall07 = new Walls(this.world, wall, new Vector2(1.20f,0.9f), 0.1f,0.45f);
        this.stage.addActor(wall07);

        this.wall08 = new Walls(this.world, wall, new Vector2(6.8f,2.4f), 0.1f,0.5f);
        this.stage.addActor(wall08);

        this.wall09 = new Walls(this.world, wall, new Vector2(6.8f,3.9f), 0.1f,0.45f);
        this.stage.addActor(wall09);

        this.wall10 = new Walls(this.world, wall, new Vector2(6.8f,0.9f), 0.1f,0.45f);
        this.stage.addActor(wall10);


        this.wall11 = new Walls(this.world, wall, new Vector2(1.75f,2.4f), 0.45f,0.1f);
        this.stage.addActor(wall11);

        this.wall12 = new Walls(this.world, wall, new Vector2(1.75f,3.9f), 0.45f,0.1f);
        this.stage.addActor(wall12);

        this.wall13 = new Walls(this.world, wall, new Vector2(1.75f,0.9f), 0.45f,0.1f);
        this.stage.addActor(wall13);

        this.wall14 = new Walls(this.world, wall, new Vector2(6.25f,2.4f), 0.45f,0.1f);
        this.stage.addActor(wall14);

        this.wall15 = new Walls(this.world, wall, new Vector2(6.25f,3.9f), 0.45f,0.1f);
        this.stage.addActor(wall15);

        this.wall16 = new Walls(this.world, wall, new Vector2(6.25f,0.9f), 0.45f,0.1f);
        this.stage.addActor(wall16);

        //Barras centrales abajo y arriba

        this.wall17 = new Walls(this.world, wall, new Vector2(3.30f,3.9f), 0.60f,0.1f);
        this.stage.addActor(wall17);

        this.wall18 = new Walls(this.world, wall, new Vector2(4.70f,3.9f), 0.60f,0.1f);
        this.stage.addActor(wall18);

        this.wall19 = new Walls(this.world, wall, new Vector2(3.30f,0.9f), 0.60f,0.1f);
        this.stage.addActor(wall19);

        this.wall20 = new Walls(this.world, wall, new Vector2(4.70f,0.9f), 0.60f,0.1f);
        this.stage.addActor(wall20);
        //Escalones arriba y abajo (los quito porque hacen esquina)

        this.wall21 = new Walls(this.world, wall, new Vector2(2.6f,4.65f), 0.85f,0.2f);
        this.stage.addActor(wall21);
        this.wall22 = new Walls(this.world, wall, new Vector2(2.6f,0.12f), 0.85f,0.2f);
        this.stage.addActor(wall22);
        this.wall23 = new Walls(this.world, wall, new Vector2(5.4f,0.12f), 0.85f,0.2f);
        this.stage.addActor(wall23);
        this.wall24 = new Walls(this.world, wall, new Vector2(5.4f,4.65f), 0.85f,0.2f);
        this.stage.addActor(wall24);

        //Cuadrados sectores

        this.wall25 = new Walls(this.world, wall, new Vector2(2.30f,3.15f), 0.5f,0.2f);
        this.stage.addActor(wall25);
        this.wall26 = new Walls(this.world, wall, new Vector2(2.30f,1.65f), 0.5f,0.2f);
        this.stage.addActor(wall26);
        this.wall27 = new Walls(this.world, wall, new Vector2(5.70f,3.15f), 0.5f,0.2f);
        this.stage.addActor(wall27);
        this.wall28 = new Walls(this.world, wall, new Vector2(5.70f,1.65f), 0.5f,0.2f);
        this.stage.addActor(wall28);

        //Cubo del centro

        this.wall29 = new Walls(this.world, wall, new Vector2(4f,2.1f), 0.65f,0.05f);
        this.stage.addActor(wall29);
        this.wall30 = new Walls(this.world, wall, new Vector2(3.30f,2.35f), 0.05f,0.3f);
        this.stage.addActor(wall30);
        this.wall31 = new Walls(this.world, wall, new Vector2(4.7f,2.35f), 0.05f,0.3f);
        this.stage.addActor(wall31);
        this.wall32 = new Walls(this.world, wall, new Vector2(3.52f,2.7f), 0.27f,0.05f);
        this.stage.addActor(wall32);
        this.wall33 = new Walls(this.world, wall, new Vector2(4.48f,2.7f), 0.27f,0.05f);
        this.stage.addActor(wall33);

        //

        this.wall34 = new Walls(this.world, wall, new Vector2(2.95f,2.4f), 0.30f,0.1f);
        this.stage.addActor(wall34);
        this.wall35 = new Walls(this.world, wall, new Vector2(5.05f,2.4f), 0.30f,0.1f);
        this.stage.addActor(wall35);
        this.wall36 = new Walls(this.world, wall, new Vector2(4f,3.28f), 0.75f,0.08f);
        this.stage.addActor(wall36);
        this.wall37 = new Walls(this.world, wall, new Vector2(4f,1.52f), 0.75f,0.08f);
        this.stage.addActor(wall37);

        //Paredes

        this.wall38 = new Walls(this.world, wall, new Vector2(6.55f,0f), 2f,0.02f);
        this.stage.addActor(wall38);
        this.wall39 = new Walls(this.world, wall, new Vector2(1.45f,0), 2f,0.02f);
        this.stage.addActor(wall39);
        this.wall40 = new Walls(this.world, wall, new Vector2(1.45f,4.8f), 2f,0.02f);
        this.stage.addActor(wall40);
        this.wall41 = new Walls(this.world, wall, new Vector2(6.55f,4.8f), 2f,0.02f);
        this.stage.addActor(wall41);
        this.wall42 = new Walls(this.world, wall, new Vector2(0f,2.4f), 0.02f,2.5f);
        this.stage.addActor(wall42);
        this.wall43 = new Walls(this.world, wall, new Vector2(8f,2.4f), 0.02f,2.5f);
        this.stage.addActor(wall43);
    }

    ///////////////////////////////////////////////////
    //////////////////////CHERRIES//////////////////////
    ///////////////////////////////////////////////////

    //Método que nos permite obtener una posicion aleatoria para la cereza
    public Vector2 posRnd(){

        int num = MathUtils.random(0,listRndPos.size()-1);
        Vector2 posicionElegida = listRndPos.get(num);

        listRndPos.remove(posicionElegida);

        return posicionElegida;
    }

    //Añadimos las cerezas con una posicion aleatoria llamando al metodo anterior
    public void addCherry() {
        //Asignamos la animacion a la que obtenemos del assetManager
        TextureRegion cherry = mainGame.assetManager.getCherry();

        //Creamos el pacman asignandole el mundo y la posición
        this.cherry01 = new Cherry(this.world, cherry, posRnd(), USER_CHERRY_01);
        this.cherry02 = new Cherry(this.world, cherry, posRnd(), USER_CHERRY_02);
        this.cherry03 = new Cherry(this.world, cherry, posRnd(), USER_CHERRY_03);
        this.cherry04 = new Cherry(this.world, cherry, posRnd(), USER_CHERRY_04);
        this.cherry05 = new Cherry(this.world, cherry, posRnd(), USER_CHERRY_05);
        //Añadimos la cereza*/
        this.stage.addActor(this.cherry01);
        this.stage.addActor(this.cherry02);
        this.stage.addActor(this.cherry03);
        this.stage.addActor(this.cherry04);
        this.stage.addActor(this.cherry05);
    }

    ///////////////////////////////////////////////////
    //////////////////////COLISIONES///////////////////
    ///////////////////////////////////////////////////

    public void comprobarWin(){
        if (contadorCherry == 5){
            this.stage.addAction(Actions.sequence(
                    Actions.delay(0f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            mainGame.setScreen(mainGame.gameOverScreen);
                        }
                    })
            ));
        }
    }

    private boolean areColider(Contact contact, Object objA, Object objB) {

        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if (userDataA == null || userDataB == null) {
            return false;
        }

        return (contact.getFixtureA().getUserData().equals(objA) && contact.getFixtureB().getUserData().equals(objB)) ||
                (contact.getFixtureA().getUserData().equals(objB) && contact.getFixtureB().getUserData().equals(objA));
    }

    private boolean canHide01 = true;
    private boolean canHide02 = true;
    private boolean canHide03 = true;
    private boolean canHide04 = true;
    private boolean canHide05 = true;


    //Método que se llamará cada vez que se produzca cualquier contacto
    @Override
    public void beginContact(Contact contact) {

        if (areColider(contact,USER_CHERRY_01,USER_PACMAN)){
            if (canHide01 == true && cherry01.isEliminado() == false){
                eatCherrySound.play();
                cherry01.remove();
                contadorCherry++;
                canHide01 = false;
                comprobarWin();
            }
        }

        if (areColider(contact,USER_CHERRY_02,USER_PACMAN)){
            if (canHide02 == true && cherry02.isEliminado() == false){
                eatCherrySound.play();
                cherry02.remove();
                contadorCherry++;
                canHide02 = false;
                comprobarWin();
            }        }

        if (areColider(contact,USER_CHERRY_03,USER_PACMAN)){
            if (canHide03 == true && cherry03.isEliminado() == false){
                eatCherrySound.play();
                cherry03.remove();
                contadorCherry++;
                canHide03 = false;
                comprobarWin();
            }        }

        if (areColider(contact,USER_CHERRY_04,USER_PACMAN)){
            if (canHide04 == true && cherry04.isEliminado() == false){
                eatCherrySound.play();
                cherry04.remove();
                contadorCherry++;
                canHide04 = false;
                comprobarWin();
            }        }

        if (areColider(contact,USER_CHERRY_05,USER_PACMAN)){
            if (canHide05 == true && cherry05.isEliminado() == false){
                eatCherrySound.play();
                cherry05.remove();
                contadorCherry++;
                canHide05 = false;
                comprobarWin();
            }
        }

        if (areColider(contact, USER_PACMAN, USER_GHOST_01) || areColider(contact, USER_PACMAN, USER_GHOST_02) ||
                areColider(contact, USER_PACMAN, USER_GHOST_03) || areColider(contact, USER_PACMAN, USER_GHOST_04)) {
            System.out.println("Choque");
            pacMan.dead();
            deadSound.play();
            //this.hide();
            //Todo 8.4 Se lanza la secuencia de acciones,cuya última será el pasar a la ventana de GameOverScreen
            this.stage.addAction(Actions.sequence(
                    Actions.delay(0f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            mainGame.setScreen(mainGame.gameOverScreen);
                        }
                    })
            ));
        }

        //-1: abajo , 0: arriba , 1: derecha, 2: izquierda

        if (areColider(contact, USER_GHOST_01, USER_WALL)){
            int num = 0;
            do {
                num = MathUtils.random(-1,2);
            }while(dirGhost01Anterior == num || dirGhost01Ultima == num);
            dirGhost01Ultima = dirGhost01Anterior;
            dirGhost01Anterior = num;
            this.ghost01.setDirection(num);
        }

        if (areColider(contact, USER_GHOST_02, USER_WALL)){
            int num = 0;
            do {
                num = MathUtils.random(-1,2);
            }while(dirGhost02Anterior == num || dirGhost02Ultima == num);
            dirGhost02Ultima = dirGhost02Anterior;
            dirGhost02Anterior = num;
            this.ghost02.setDirection(num);
        }

        if (areColider(contact, USER_GHOST_03, USER_WALL)){
            int num = 0;
            do {
                num = MathUtils.random(-1,2);
            }while(dirGhost03Anterior == num || dirGhost03Ultima == num);
            dirGhost03Ultima = dirGhost03Anterior;
            dirGhost03Anterior = num;
            this.ghost03.setDirection(num);
        }

        if (areColider(contact, USER_GHOST_04, USER_WALL)){
            int num = 0;
            do {
                num = MathUtils.random(-1,2);
            }while(dirGhost04Anterior == num || dirGhost04Ultima == num);
            dirGhost04Ultima = dirGhost04Anterior;
            dirGhost04Anterior = num;
            this.ghost04.setDirection(num);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}