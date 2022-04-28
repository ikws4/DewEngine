package io.github.ikws4.dew.glrenderer.system;

import android.content.Context;

import io.github.ikws4.dew.glrenderer.Renderer;
import io.github.ikws4.dew.glrenderer.component.bundle.SpriteBundle;
import io.github.ikws4.dew.glrenderer.resource.Camera;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Entity;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;

import static android.opengl.GLES30.*;

/**
 * Created by zhiping on 04/26/2022.
 */
public class GLRenderSystem extends System {
  @Override
  public void run(Context context, Command command, Query query, Res res) {
    Renderer renderer = res.get(Renderer.class);
    Camera camera = res.get(Camera.class);

    for (Entity entity : query.with(SpriteBundle.class)) {
      renderer.add(entity.get(SpriteBundle.class));
    }

    glClear(GL_COLOR_BUFFER_BIT);
    renderer.render(camera.mvp);
  }
}
