package io.github.ikws4.dew.ecs;

public class Command {
  World world;

  Command(World world) {
    this.world = world;
  }

  public Command insertResource(Object resource) {
    world.insertResource(resource);
    return this;
  }

  public Entity createEntity() {
    Entity entity;
    if (world.freeEntityPool.isEmpty()) {
      entity = new Entity(world);
      world.entities.add(entity);
    } else {
      entity = world.freeEntityPool.poll();
      entity.removed = false;
    }

    for (EntityListener l : world.entityListeners) {
      l.onEntityCreated(entity);
    }
    return entity;
  }

  public void removeEntity(Entity entity) {
    entity.removed = true;
    entity.componentMask.clear();
    world.freeEntityPool.add(entity);

    for (EntityListener l : world.entityListeners) {
      l.onEntityRemoved(entity);
    }
  }
}
