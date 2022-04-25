package io.github.ikws4.dew.ecs.component;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
  private final Matrix4f transform;

  public Transform(Vector3f position, Vector2f scale, float rotation) {
    transform = new Matrix4f();
    transform.translate(position.x, position.y, 0)
      .scale(scale.x, scale.y, 0)
      .rotate(rotation, 0, 0, 1);
  }
  
  public void applyTo(Vector3f position) {
    transform.transformPosition(position);
  }
}
