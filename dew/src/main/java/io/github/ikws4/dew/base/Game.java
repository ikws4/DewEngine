package io.github.ikws4.dew.base;

import android.content.Context;
import io.github.ikws4.dew.glrenderer.GLRendererPlugin;
import io.github.ikws4.dew.base.resource.Time;
import io.github.ikws4.dew.base.resource.ViewPort;
import io.github.ikws4.dew.ecs.World;

public abstract class Game {
  protected final World world;
  private final ViewPort viewPort;
  private final Time time;

  public Game(Context context) {
    this.world = new World(context);
    this.viewPort = new ViewPort(0, 0);
    this.time = new Time();
  }

  protected void setup() {
    world.insertResource(viewPort)
      .insertResource(time)
      .addPlugin(new GLRendererPlugin());
  }

  protected void loop(float dt) {
    time.deltaTime = dt;
    world.run();
  }

  protected void resize(int width, int height) {
    viewPort.resize(width, height);
  }

  protected void pause() {}

  protected void resume() {}

  protected void dispose() {}
}
