package io.github.ikws4.example.game;

import android.content.Context;

import io.github.ikws4.dew.core.Game;
import io.github.ikws4.dew.core.GameActivity;
import io.github.ikws4.dew.ecs.World;
import io.github.ikws4.example.MyApplication;
import io.github.ikws4.example.game.systems.StartupSystem;

/**
 * Created by zhiping on 04/25/2022.
 */
public class MyGameActivity extends GameActivity {

  @Override
  protected Game onCreateGame() {
    return new MyGame(this);
  }

  static class MyGame extends Game {

    public MyGame(Context context) {
      super(context);
    }

    @Override
    public void setup() {
      super.setup();

      world.addStartupSystem(new StartupSystem());
    }
  }
}
