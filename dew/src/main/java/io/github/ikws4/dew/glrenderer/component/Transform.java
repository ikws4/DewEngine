package io.github.ikws4.dew.glrenderer.component;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
  private final Matrix4f transform;

  public Vector3f position;
  public Vector2f scale;
  public float rotation;

  public Transform(Vector3f position, Vector2f scale, float rotation) {
    this.transform = new Matrix4f();
    this.position = position;
    this.scale = scale;
    this.rotation = rotation;
  }
  
  public Matrix4f getTransformMatrix() {
    return transform.identity()
            .translate(position)
            .rotate(toRadian(rotation), 0, 0, 1)
            .scale(scale.x, scale.y, 0);
  }

  private float toRadian(float degree) {
    return degree * (float) Math.PI / 180;
  }
}
