package jonas.b2dtest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Wrapper around the Box2D Collision World that instantiates a simple world containing some primitives.
 */
public class PhysicsWorld {
    private static final Vector2 gravityVector = new Vector2(0, -9.81f);
    public World b2world = new World(gravityVector, true);

    public PhysicsWorld() {
        init();
    }

    private void init() {
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0); // <- Supports Vector2
    }
}
