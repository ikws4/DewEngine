package io.github.ikws4.dew.ecs;

import android.content.Context;

public abstract class System {
  public abstract void run(Context context, Command command, Query query, Res res);
}
