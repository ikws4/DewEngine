package io.github.ikws4.dew.ecs;

public interface System {
  void run(Command command, Query query);
}
