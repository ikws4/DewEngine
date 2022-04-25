package io.github.ikws4.example.game;

import android.content.Context;

import io.github.ikws4.dew.android.Game;
import io.github.ikws4.dew.android.GameActivity;
import io.github.ikws4.dew.ecs.World;
import io.github.ikws4.example.game.systems.GLRenderSystem;

/**
 * Created by zhiping on 04/25/2022.
 */
public class MyGameActivity extends GameActivity {

  @Override
  protected Game onCreateGame() {
    return new MyGame(this);
  }

  static class MyGame extends Game {
    private World world;

    public MyGame(Context context) {
      super(context);
    }

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
}
