package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extra.Utils;

public class Walls extends Actor {

    //Para crear las paredes debemos fijar un ancho y alto
    private static final float WALL_WIDTH = 4f;
    private static final float WALL_HEIGHT = 4f;

    //Creamos Texturas, Body, fixture y mundo
    private TextureRegion pipeDownTR;

    private Body bodyDown;

    private Fixture fixtureDown;

    private World world;

    //Todo 7 Constructor con mundo textura y posicion
    public Walls(World world, TextureRegion trpDown, Vector2 position) {
        this.world = world;
        this.pipeDownTR = trpDown;

        createBodyPipeDown(position);
        createFixture();
    }


    //creamos metodo para body pasandole la posicion
    private void createBodyPipeDown(Vector2 position) {
        //Creamos el cuerpo de la pared
        BodyDef def = new BodyDef();
        //Establecemos la posicion
        def.position.set(position);
        //Le indicamos el tipo que es estatica, ya que se queda fijo
        def.type = BodyDef.BodyType.StaticBody;
        //Creamos el cuerpo
        bodyDown = world.createBody(def);
        //Le damos el identificador
        bodyDown.setUserData(Utils.USER_WALL);

    }

    //Creamos método para la fixture
    private void createFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WALL_WIDTH /2, WALL_HEIGHT /2);

        this.fixtureDown = bodyDown.createFixture(shape, 8);
        shape.dispose();
    }

    //Todo 10. Sobrecargamos métodos act y draw
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Posicion de las fisicas
        setPosition(this.bodyDown.getPosition().x - (WALL_WIDTH /2), this.bodyDown.getPosition().y - (WALL_HEIGHT /2) );
        //Posicion del "dibujo"
        batch.draw(this.pipeDownTR, getX(),getY(), WALL_WIDTH, WALL_HEIGHT);
    }

    //Todo 11. Creamos detach para liberar recursos
    public void detach(){
        bodyDown.destroyFixture(fixtureDown);
        world.destroyBody(bodyDown);
    }

}
