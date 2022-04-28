package io.github.ikws4.dew.glrenderer;

import org.joml.Matrix4f;

public interface RenderBatch {
  public void render(Matrix4f mvp);
}
