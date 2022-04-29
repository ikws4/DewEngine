package io.github.ikws4.dew.ecs;

public class Command {
  private World world;

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
    } else {
      entity = world.freeEntityPool.poll();
      entity.removed = false;
    }
    world.entities.add(entity);

    return entity;
  }

  /**
    * Remove the entity from the world.
    *
    * @param entity to be removed
    * @throws IllegalArgumentException if the entity is already removed.
    */
  public void removeEntity(Entity entity) {
    if (entity.removed) {
      throw new IllegalArgumentException(entity + " is already removed.");
    }
    
    entity.removed = true;
    entity.componentMask.clear();
    world.freeEntityPool.add(entity);
  }
}
