package io.github.ikws4.dew.core.resource;

import static android.opengl.GLES20.glViewport;

public class ViewPort {
  public int width;
  public int height;

  public ViewPort() {
    this(0, 0);
  }
  
  public ViewPort(int width, int height) {
    resize(width, height);
  }

  public void resize(int width, int height) {
    this.width = width;
    this.height = height;
    glViewport(0, 0, width, height);
  }
}
