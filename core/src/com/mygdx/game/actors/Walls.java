package com.mygdx.game.actors;

import static com.mygdx.game.extra.Utils.USER_GHOST;
import static com.mygdx.game.extra.Utils.USER_PACMAN;
import static com.mygdx.game.extra.Utils.USER_WALL;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Walls extends Actor {

    //Para crear las paredes debemos fijar un ancho y alto
    private float wallWidth = 4f;
    private float wallHeight = 4f;

    //Creamos Texturas, Body, fixture y mundo
    private TextureRegion pipeDownTR;

    private Body bodyDown;

    private Fixture fixtureDown;

    private World world;

    //Todo 7 Constructor con mundo textura y posicion
    public Walls(World world, TextureRegion trpDown, Vector2 position, float width, float height) {
        this.world = world;
        this.pipeDownTR = trpDown;
        this.wallWidth = width;
        this.wallHeight = height;

        createBodyWall(position);
        createFixture();
    }


    //creamos metodo para body pasandole la posicion
    private void createBodyWall(Vector2 position) {
        //Creamos el cuerpo de la pared
        BodyDef def = new BodyDef();
        //Establecemos la posicion
        def.position.set(position);
        //Le indicamos el tipo que es estatica, ya que se queda fijo
        def.type = BodyDef.BodyType.StaticBody;
        //Creamos el cuerpo
        bodyDown = world.createBody(def);
        //Le damos el identificador
        bodyDown.setUserData(USER_WALL);
    }

    //Creamos método para la fixture
    private void createFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wallWidth, wallHeight);

        this.fixtureDown = bodyDown.createFixture(shape, 8);
        shape.dispose();
        this.fixtureDown.setUserData(USER_WALL);
    }

    //Todo 10. Sobrecargamos métodos act y draw
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Posicion de las fisicas
        setPosition(this.bodyDown.getPosition().x, this.bodyDown.getPosition().y);
        //setPosition(this.bodyDown.getPosition().x - (wallWidth /2), this.bodyDown.getPosition().y - (wallHeight /2) );
        //Posicion del "dibujo"
        batch.draw(this.pipeDownTR, getX(),getY(), wallWidth, wallHeight);
    }

    //Todo 11. Creamos detach para liberar recursos
    public void detach(){
        bodyDown.destroyFixture(fixtureDown);
        world.destroyBody(bodyDown);
    }

}
