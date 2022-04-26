package io.github.ikws4.dew.core.gl;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import io.github.ikws4.dew.core.gl.component.bundle.SpriteBundle;

/**
 * Created by zhiping on 04/26/2022.
 */
public class Renderer {
  private final List<RenderBatch> batches;
  private final Shader shader;

  public Renderer(Shader shader) {
    this.batches = new ArrayList<>();
    this.shader = shader;
  }

  public void add(SpriteBundle spriteBundle) {
    for (RenderBatch batch : batches) {
      if (batch instanceof SpriteRenderBatch) {
        SpriteRenderBatch spriteRenderBatch = (SpriteRenderBatch) batch;
        if (spriteRenderBatch.add(spriteBundle)) {
          return;
        }
      }
    }

    SpriteRenderBatch batch = new SpriteRenderBatch(shader);
    batches.add(batch);
    batch.add(spriteBundle);
  }
  
  public void render(Matrix4f mvp) {
    for (RenderBatch renderable : batches) {
      renderable.render(mvp);
    }
  }
}
