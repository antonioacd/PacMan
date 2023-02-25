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

    //Declaramos los atributos del Fantasma
    private TextureRegion texture;
    private Vector2 position;
    private int direction;

    //Creamos el mundo
    private World world;

    //Creamos el cuerpo del Ghost y la Fisica
    private Body body;
    private Fixture fixture;
    //Guardaremos la posicion antigua para que al chocar con algo, no vaya a la misma direccion
    private Vector2 posAntigua;

    public Ghost(World world, TextureRegion texture, Vector2 position, int direccion, String usuario) {

        this.texture = texture;
        this.position = position;
        this.world = world;
        this.direction = direccion;
        createBody();
        createFixture(usuario);
    }

    //Mediante este metod le asignamos la direccion a la que tiene que ir
    public void setDirection(int direction) {
        this.direction = direction;
    }

    //Metodo para crear el cuerpo
    private void createBody() {
        //Creamos el body asignandole una posicion y un tipo
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    //Metodo para crear la fisica
    private void createFixture(String usuario) {
        //Como es un circulo, le damos un radio que sera el tamaño de la fisica
        CircleShape circle = new CircleShape();
        circle.setRadius(GHOST_HEIGHT / 2);
        //Creamos la fisica
        this.fixture = this.body.createFixture(circle, 8);
        //Le asignamos a esta fisica un "nombre" para identificarlo
        this.fixture.setUserData(usuario);
        circle.dispose();
    }

    //Estos metodos sirven para mover nuestro fantasma de un lugar a otro,
    // y se usaran cuando el fantasma se teletransporte
    public void arriba(float xGhost) {
        this.body.setTransform(new Vector2(xGhost + 0.20f, WORLD_HEIGHT), 0);
    }

    public void abajo(float xGhost) {
        this.body.setTransform(new Vector2(xGhost + 0.20f, 0.1f), 0);
    }

    @Override
    public void act(float delta) {

        //segun la direccion asignada
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

        //Establecemos la posicion de la fisica restandole la mitad del tamaño del dibujo del fantasma
        setPosition(body.getPosition().x - 0.2f, body.getPosition().y - 0.2f);
        //Dibujamos el Ghost
        batch.draw(texture, getX(), getY(), GHOST_WIDTH, GHOST_HEIGHT);
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

