package io.github.ikws4.dew.ecs;

import java.util.BitSet;

public class Entity {
  private final int id;

  World world;
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

    int componentId = world.componentIdMap.get(componentClass);
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
    insert(bundle);
    return this;
  }

  public Entity remove(Class<?> componentClass) {
    componentMask.clear(world.componentIdMap.get(componentClass));
    return this;
  }

  public <T> T get(Class<T> componentClass) {
    int componentId = world.componentIdMap.get(componentClass);
    Object component = world.componentPools.get(componentId).get(id);
    if (component == null) {
      throw new IllegalStateException("Component " + componentClass.getName() + " is not attached to entity " + id);
    }
    return componentClass.cast(component);
  }
}
