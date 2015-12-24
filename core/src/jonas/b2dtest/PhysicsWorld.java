package jonas.b2dtest;

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

    /**
     * For the construction of a dynamic body we need the following elements:
     * BodyDef: defines non-physical properties of the body in the simulation, used as builder for World.createBody
     * Body: Reference to the body in the world
     * PolygonShape: Gives shape to the body
     * FixtureDef: Defines physical properties of the body, used as builder for createFixture.
     */
    private void initBox() {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        boxBodyDef.position.set(new Vector2(0f, 4f));
        boxBody = b2world.createBody(boxBodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(1f, 1f);
        FixtureDef boxFixtureDef = new FixtureDef();
        boxFixtureDef.shape = boxShape;
        boxFixtureDef.density = 1f;
        boxFixtureDef.friction = 0.3f;
        boxBody.createFixture(boxFixtureDef);
    }

    public void update(float deltaTime) {
        // Properties of the simulation, values recommended by the manual. Higher numbers entail more accuracy at the cost of performance. These bear no relation to the time step.
        final int velocityIterations = 8;
        final int positionIterations = 2;
        b2world.step(deltaTime, velocityIterations, positionIterations);
        System.out.println(String.format("x: %1.2f, y: %1.2f, dx: %1.2f, dy: %1.2f", boxBody.getPosition().x, boxBody.getPosition().y, boxBody.getLinearVelocity().x, boxBody.getLinearVelocity().y));
    }
}
