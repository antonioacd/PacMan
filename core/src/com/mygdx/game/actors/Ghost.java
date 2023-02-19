package com.mygdx.game.actors;

import static com.mygdx.game.extra.Utils.USER_GHOST_01;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ghost extends Actor {

    private static float GHOST_WIDTH = 0.4f;
    private static float GHOST_HEIGHT = 0.4f;

    //Declaramos los estados posibles del personaje, que sera, vivo o muerto
    private static final int STATE_NORMAL = 0;
    private static final int STATE_DEAD = 1;
    private static final int STATE_ENABLETODEADTH = 2;
    private static final float SPEED = 1f;

    private int state;

    //Declaramos los atributos del PacMan
    //private Animation<TextureRegion> pacManAnimation;
    private TextureRegion texture;
    private Vector2 position;
    private int direction;

    private float stateTime;

    //Creamos el mundo
    private World world;

    //Creamos el cuerpo del PacMan y la Fisica
    private Body body;
    private Fixture fixture;

    private Vector2 posAntigua;

    public Ghost(World world, TextureRegion texture, Vector2 position, int direccion, String usuario) {

        this.texture = texture;
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.state = STATE_NORMAL;
        this.direction = direccion;

        createBody();
        createFixture(usuario);
    }

    public Vector2 getPosition() {
        return fixture.getBody().getPosition();
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Vector2 getPosAntigua() {
        return posAntigua;
    }

    public void setPosAntigua(Vector2 posAntigua) {
        this.posAntigua = posAntigua;
    }

    //Metodo para crear el cuerpo
    private void createBody() {
        //Creamos el body
        BodyDef bodyDef = new BodyDef();
        //Le establecemos la posicion, la cual sera la misma de la del PacMan,
        // ya que la cojemos del constructor
        bodyDef.position.set(this.position);
        //Le asignamos un tipo
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //Lo creamos
        this.body = this.world.createBody(bodyDef);
    }

    //Metodo para crear la fisica
    private void createFixture(String usuario) {
        //Como es un circulo, le damos un radio que sera el tamaño de la fisica
        CircleShape circle = new CircleShape();
        circle.setRadius(GHOST_HEIGHT / 2);

        //PolygonShape poligon = new PolygonShape();
        //poligon.setAsBox(GHOST_WIDTH, GHOST_HEIGHT);

        //Creamos la fisica
        this.fixture = this.body.createFixture(circle, 8);
        //Le asignamos a esta fisica un "nombre" para identificarlo
        this.fixture.setUserData(usuario);

        circle.dispose();
    }

    public void arriba(float xGhost) {
        this.body.setTransform(new Vector2(xGhost + 0.20f, WORLD_HEIGHT), 0);
    }

    public void abajo(float xGhost) {
        this.body.setTransform(new Vector2(xGhost + 0.20f, 0.1f), 0);
    }

    @Override
    public void act(float delta) {

        switch (direction) {
            case -1:
                moverAbajo();
                break;
            case 0:
                moverArriba();
                break;
            case 1:
                moverDerecha();
                break;
            case 2:
                moverIzquierda();
                break;

            //-1: abajo , 0: arriba , 1: derecha, 2:izquierda
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        //Establecemos la posicion de la fisica restandole la mitad del tamaño del dibujo del PacMan
        setPosition(body.getPosition().x - 0.2f, body.getPosition().y - 0.2f);
        //Dibujamos el Pacman
        batch.draw(texture, getX(), getY(), GHOST_WIDTH, GHOST_HEIGHT);
        //batch.draw(this.pacManAnimation.getKeyFrame(stateTime, true), getX(), getY(), PACMAN_WIDTH,PACMAN_WIDTH);
        //stateTime += Gdx.graphics.getDeltaTime();
    }

    public void dead() {
        state = STATE_DEAD;
        stateTime = 0f;
    }

    public void detach() {
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

    public void moverAbajo() {
        this.body.setLinearVelocity(0, -SPEED);
    }

    public void moverArriba() {
        this.body.setLinearVelocity(0, SPEED);
    }

    public void moverIzquierda() {
        this.body.setLinearVelocity(-SPEED, 0);
    }

    public void moverDerecha() {
        this.body.setLinearVelocity(SPEED, 0);
    }
}

