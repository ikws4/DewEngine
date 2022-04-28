package io.github.ikws4.dew.glrenderer.system;

import android.content.Context;
import io.github.ikws4.dew.glrenderer.Renderer;
import io.github.ikws4.dew.glrenderer.Shader;
import io.github.ikws4.dew.glrenderer.resource.Camera;
import io.github.ikws4.dew.glrenderer.resource.ClearColor;
import io.github.ikws4.dew.base.resource.ViewPort;
import io.github.ikws4.dew.base.util.AssetsManager;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;

import static android.opengl.GLES30.*;

/**
 * Created by zhiping on 04/26/2022.
 */
public class GLStartupSystem extends System {
  @Override
  public void run(Context context, Command command, Query query, Res res) {
    ViewPort viewPort = res.get(ViewPort.class);
    ClearColor clearColor = res.get(ClearColor.class);

    Shader shader = new Shader(
        AssetsManager.getText(context, "vertex_shader.glsl"),
        AssetsManager.getText(context, "fragment_shader.glsl"));

    command.insertResource(new Camera(viewPort.width, viewPort.height))
      .insertResource(new Renderer(shader));
    
    glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_DITHER);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }
}
