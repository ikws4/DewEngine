package io.github.ikws4.dew.android;

public interface Game {
  void setup();

  void loop(double dt);
  
  default void resize(int width, int height) {}

  default void pause() {}

  default void resume() {}
}
