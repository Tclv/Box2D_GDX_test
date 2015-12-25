package jonas.b2dtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Wrapper around the Box2D Collision World that instantiates a simple world containing some primitives.
 */
public class PhysicsWorld {
    private static final Vector2 GRAVITY_VECTOR = new Vector2(0, -9.81f);
    public World b2world = new World(GRAVITY_VECTOR, true);
    private Body groundBody;
    private Body boxBody;

    public PhysicsWorld() {
        init();
    }

    private void init() {
        initGround();
        initBox();
    }

    private void initGround() {
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody; // <- Optional, defaults to static
        groundDef.position.set(0, -10f); // <- Supports Vector2
        groundBody = b2world.createBody(groundDef); // Use factory methods in stead of constructors
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(50f, 10f);
        groundBody.createFixture(groundShape, 0); // Zero density, as static bodies have a mass of 0 by definition. A FixtureDef builder exists, but for this static body this method suffices.
    }

    private void initBox() {
        final int NUM_BOXES = 20;
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(1f, 1f);
        FixtureDef boxFixtureDef = new FixtureDef();
        boxFixtureDef.shape = boxShape;
        boxFixtureDef.density = 1f;
        boxFixtureDef.friction = 0.3f;
        for (int i = 0; i < NUM_BOXES; i++) {
            boxBodyDef.fixedRotation = false;
            boxBodyDef.position.set(MathUtils.random(-20, -15), MathUtils.random(0,50));
            boxBody = b2world.createBody(boxBodyDef);
            boxBody.createFixture(boxFixtureDef);
        }
        for (int i = 0; i < NUM_BOXES; i++) {
            boxBodyDef.fixedRotation = true;
            boxBodyDef.position.set(MathUtils.random(15, 20), MathUtils.random(0,50));
            b2world.createBody(boxBodyDef).createFixture(boxFixtureDef);
        }
    }

    public void update(float deltaTime) {
        // Properties of the simulation, values recommended by the manual. Higher numbers entail more accuracy at the cost of performance. These bear no relation to the time step.
        final int velocityIterations = 6;
        final int positionIterations = 2;
        b2world.step(deltaTime, velocityIterations, positionIterations);
    }
}
