package io.github.ikws4.dew.ecs;

import java.util.BitSet;

public class Entity {
  private final int id;

  private World world;
  boolean removed;
  BitSet componentMask;

  Entity(World world) {
    this.id = world.entities.size();
    this.world = world;
    this.componentMask = new BitSet();
  }

  public Entity insert(Object component) {
    Class<?> componentClass = component.getClass();
    if (!world.componentIdMap.containsKey(componentClass)) {
      world.componentIdMap.put(componentClass, world.componentIdMap.size());
    }

    int componentId = world.getComponentId(componentClass);
    componentMask.set(componentId);

    if (componentId >= world.componentPools.size()) {
      world.componentPools.add(new ComponentPool(world.entities.size()));
    }
    world.componentPools.get(componentId).set(id, component);

    return this;
  }

  public Entity insertBundle(Bundle bundle) {
    for (Object component : bundle.unpack()) {
      insert(component);
    }
    return this;
  }

  public Entity remove(Class<?> componentClass) {
    ensureHasComponent(componentClass);

    componentMask.clear(world.getComponentId(componentClass));
    return this;
  }

  private void ensureHasComponent(Class<?> componentClass) {
    boolean has = componentMask.get(world.getComponentId(componentClass));
    if (!has) {
      throw new IllegalArgumentException(
          toString() + " does not have Component(" + componentClass.getName() + ").");
    }
  }

  public <T> T get(Class<T> componentClass) {
    ensureHasComponent(componentClass);

    int componentId = world.getComponentId(componentClass);
    Object component = world.componentPools.get(componentId).get(id);
    return componentClass.cast(component);
  }

  @Override
  public String toString() {
    return "Entity(" + id + ")";
  }
}
