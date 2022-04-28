package io.github.ikws4.dew.glrenderer.resource;

import org.joml.Matrix4f;

public class Camera {
  private final Matrix4f projection;
  private final Matrix4f view;
  public final Matrix4f mvp;

  public Camera(int width, int height) {
    float hw = width / 2f;
    float hh = height / 2f;

    projection = new Matrix4f();
    projection.ortho2D(-hw, hw, -hh, hh);

    view = new Matrix4f();
    view.lookAt(0, 0, 1, 0, 0, 0, 0, 1, 0);
    
    mvp = new Matrix4f();
    projection.mul(view, mvp);
  }

  public void follow(float x, float y) {
    view.lookAt(x, y, 1, x, y, 0, 0, 1, 0);
  }

  public void zoom(float factor) {
    projection.scale(factor, factor, 1);
  }

  public void update() {
    projection.mul(view, mvp);
  }
}
