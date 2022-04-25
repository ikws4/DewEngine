package io.github.ikws4.dew.ecs.component;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Transform {
  public final Matrix4f transform;

  public Transform(Vector2f position, Vector2f scale, float rotation) {
    transform = new Matrix4f();
    translate(position.x, position.y);
    scale(scale.x, scale.y);
    rotate(rotation);
  }

  public void translate(float x, float y) {
    transform.translate(x, y, 0);
  }

  public void rotate(float angle) {
    transform.rotate(angle, 0, 0, 1);
  }

  public void scale(float x, float y) {
    transform.scale(x, y, 1);
  }
}
