package io.github.ikws4.dew.glrenderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.Matrix4f;
import io.github.ikws4.dew.glrenderer.component.bundle.SpriteBundle;

/**
 * Created by zhiping on 04/26/2022.
 */
public class Renderer {
  private final Set<SpriteBundle> added;
  private final List<RenderBatch> batches;
  private final Shader shader;

  public Renderer(Shader shader) {
    this.added = new HashSet<>();
    this.batches = new ArrayList<>();
    this.shader = shader;
  }

  public void add(SpriteBundle spriteBundle) {
    if (added.contains(spriteBundle)) return;
    added.add(spriteBundle);

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
    for (RenderBatch batch : batches) {
      batch.render(mvp);
    }
  }
}
