package com.mygdx.game.actors;

import static com.mygdx.game.extra.Utils.USER_PACMAN;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.event.KeyEvent;


public class PacMan extends Actor {

    private static float PACMAN_WIDTH = 0.4f;
    private static float PACMAN_HEIGHT = 0.4f;

    //Declaramos los estados posibles del personaje, que sera, vivo o muerto
    private static final int STATE_NORMAL = 0;
    private static final int STATE_DEAD = 1;

    //Velocidad del PacMan

    private static final float SPEED = 1f;

    private int state;

    //Declaramos los atributos del PacMan
    private Animation<TextureRegion> pacManAnimation;
    private Vector2 position;
    private int direccion;

    private float stateTime;

    //Creamos el mundo
    private World world;

    //Creamos el cuerpo del PacMan y la Fisica
    private Body body;
    private Fixture fixture;

    private int rotacion;

    public PacMan(World world,Animation<TextureRegion> animation, Vector2 position, int direccion){

        //Asignamos la animacion y la posicion
        this.pacManAnimation = animation;
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.state = STATE_NORMAL;
        this.direccion = direccion;
        this.rotacion = 0;

        createBody();
        createFixture();
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public void setRotacion(int rotacion) {
        this.rotacion = rotacion;
    }

    //Metodo para crear el cuerpo
    private void createBody(){
        //Creamos el body asignandole una posicion y un tipo
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    //Metodo para crear la fisica
    private void createFixture(){
        //Como es un circulo, le damos un radio que sera el tamaño de la fisica
        CircleShape circle = new CircleShape();
        circle.setRadius(PACMAN_HEIGHT/2);

        //Creamos la fisica
        this.fixture = this.body.createFixture(circle, 8);
        //Le asignamos a esta fisica un "nombre" para identificarlo
        this.fixture.setUserData(USER_PACMAN);
        circle.dispose();
    }

    public void dead() {
        state = STATE_DEAD;
        stateTime = 0f;
    }

    //Estos metodos sirven para mover nuestro PacMan de un lugar a otro,
    // y se usaran cuando el PacMan se teletransporte
    public void arriba(float xPacMan){
        this.body.setTransform(new Vector2(xPacMan + 0.20f,WORLD_HEIGHT), 0);
    }

    public void abajo(float xPacMan){
        this.body.setTransform(new Vector2(xPacMan + 0.20f, 0.1f), 0);
    }

    @Override
    public void act(float delta) {

        //Si se detecta una pulsacion en la pantalla
        if (Gdx.input.isTouched()) {
            //Detectamos la posicion de la pulsacion
            float deltaX = Gdx.input.getDeltaX();
            float deltaY = Gdx.input.getDeltaY();

            //Dependiendo de los valores de la pulsacion se le asignara una direccion
            if (deltaX > 0 && deltaY > -5 && deltaY < 15){
                this.body.setLinearVelocity(SPEED, 0f);
                this.setRotacion(0);
            }

            if (deltaX < 0 && deltaY > -5 && deltaY < 15){
                this.body.setLinearVelocity(-SPEED, 0f);
                this.setRotacion(180);
            }

            if (deltaY > 0 && deltaX > -15 && deltaX < 5){
                this.body.setLinearVelocity(0f, -SPEED);
                this.setRotacion(-90);
            }

            if (deltaY < 0 && deltaX > -15 && deltaX < 5){
                this.body.setLinearVelocity(0f, SPEED);
                this.setRotacion(90);
            }

            /*//Dependiendo de la direccion recibida se moveran hacia una direccion determinada
            switch (direccion) {
                case -1:
                    this.body.setLinearVelocity(-1f, 0f);
                    this.setRotacion(180);
                    break;
                case 1:
                    this.body.setLinearVelocity(1f, 0f);
                    this.setRotacion(0);
                    break;
                case 0:
                    this.body.setLinearVelocity(0f, -1f);
                    this.setRotacion(-90);
                    break;
                case 2:
                    this.body.setLinearVelocity(0f, 1f);
                    this.setRotacion(90);
                    break;
            }*/


        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        //Establecemos la posicion de la fisica restandole la mitad del tamaño del dibujo del PacMan
        setPosition(body.getPosition().x-0.2f, body.getPosition().y-0.2f);

        //Dibujamos el Pacman
        //batch.draw(this.pacManAnimation.getKeyFrame(stateTime, true), getX(), getY(), PACMAN_WIDTH,PACMAN_WIDTH);

        //Rotamos la animacion segun el angulo indicado en el parametro rotacion
        batch.draw(pacManAnimation.getKeyFrame(stateTime, true), getX(),
                getY(), PACMAN_WIDTH/2, PACMAN_HEIGHT/2,
                PACMAN_WIDTH, PACMAN_HEIGHT, 1, 1, rotacion);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

}
