package io.github.ikws4.dew.glrenderer.system;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import android.content.Context;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;
import io.github.ikws4.dew.glrenderer.Renderer;
import io.github.ikws4.dew.glrenderer.component.Color;
import io.github.ikws4.dew.glrenderer.component.Sprite;
import io.github.ikws4.dew.glrenderer.component.Transform;
import io.github.ikws4.dew.glrenderer.component.bundle.SpriteBundle;
import io.github.ikws4.dew.glrenderer.resource.Camera;

/**
 * Created by zhiping on 04/26/2022.
 */
public class GLRenderSystem extends System {
  @Override
  public void run(Context context, Command command, Query query, Res res) {
    Renderer renderer = res.get(Renderer.class);
    Camera camera = res.get(Camera.class);

    for (Query.Result r : query.with(Sprite.class, Color.class, Transform.class)) {
      Sprite sprite = r.get(Sprite.class);
      Color color = r.get(Color.class);
      Transform transform = r.get(Transform.class);

      renderer.add(new SpriteBundle(sprite, color, transform));
    }

    glClear(GL_COLOR_BUFFER_BIT);
    renderer.render(camera.mvp);
  }
}
