package io.github.ikws4.dew.core.gl;

import io.github.ikws4.dew.core.gl.system.GLRenderSystem;
import io.github.ikws4.dew.core.gl.system.GLStartupSystem;
import io.github.ikws4.dew.ecs.Plugin;
import io.github.ikws4.dew.ecs.World;

public class GLRendererPlugin implements Plugin {

  @Override
  public void build(World world) {
    world.addStartupSystem(new GLStartupSystem())
      .addSystem(new GLRenderSystem());
  }
}
