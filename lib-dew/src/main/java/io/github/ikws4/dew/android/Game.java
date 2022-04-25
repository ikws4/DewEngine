package io.github.ikws4.dew.android;

import java.lang.ref.WeakReference;
import android.content.Context;

public abstract class Game {
  private final WeakReference<Context> context;
  
  public Game(Context context) {
    this.context = new WeakReference<>(context);
  }
  
  protected Context getContext() {
    return context.get();
  }

  protected abstract void setup();

  protected abstract void loop(double dt);

  protected void resize(int width, int height) {}

  protected void pause() {}

  protected void resume() {}

  protected void dispose() {}
}
