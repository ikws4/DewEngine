package io.github.ikws4.dew.ecs;

public class Command {
  World world;

  Command(World world) {
    this.world = world;
  }

  Entity createEntity() {
    if (world.freeEntityPool.isEmpty()) {
      Entity entity = new Entity(world);
      world.entities.add(entity);
      return entity;
    } else {
      Entity entity = world.freeEntityPool.poll();
      entity.removed = false;
      return entity;
    }
  }

  void removeEntity(Entity entity) {
    entity.removed = true;
    entity.componentMask.clear();
    world.freeEntityPool.add(entity);
  }
}
