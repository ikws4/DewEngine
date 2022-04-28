package io.github.ikws4.dew.glrenderer;

import io.github.ikws4.dew.glrenderer.resource.ClearColor;
import io.github.ikws4.dew.glrenderer.system.GLRenderSystem;
import io.github.ikws4.dew.glrenderer.system.GLStartupSystem;
import io.github.ikws4.dew.ecs.Plugin;
import io.github.ikws4.dew.ecs.World;

public class GLRendererPlugin implements Plugin {

  @Override
  public void build(World world) {
    world.insertResource(new ClearColor(0xFFFFFFFF))
        .addStartupSystem(GLStartupSystem.class)
        .addSystem(GLRenderSystem.class);
  }
}
