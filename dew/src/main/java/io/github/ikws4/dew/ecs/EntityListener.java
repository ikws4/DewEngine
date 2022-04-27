package io.github.ikws4.dew.ecs;

public interface EntityListener {
  void onEntityCreated(Entity entity);

  void onEntityRemoved(Entity entity);
}
