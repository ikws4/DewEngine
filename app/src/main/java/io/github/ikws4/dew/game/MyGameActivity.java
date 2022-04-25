package io.github.ikws4.dew.game;

import io.github.ikws4.dew.android.Game;
import io.github.ikws4.dew.android.GameActivity;
import io.github.ikws4.dew.ecs.World;
import io.github.ikws4.dew.game.systems.GLRenderSystem;

/**
 * Created by zhiping on 04/25/2022.
 */
public class MyGameActivity extends GameActivity implements Game {
  @Override
  protected Game onCreateGame() {
    return this;
  }

  private World world;

  @Override
  public void setup() {
    world = new World()
        .addSystem(new GLRenderSystem());
  }

  @Override
  public void loop(double dt) {
    world.run();
  }
}
