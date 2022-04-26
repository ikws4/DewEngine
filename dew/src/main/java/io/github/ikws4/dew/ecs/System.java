package io.github.ikws4.dew.ecs;

import android.content.Context;

public interface System {
  void run(Context context, Command command, Query query, Res res);
}
