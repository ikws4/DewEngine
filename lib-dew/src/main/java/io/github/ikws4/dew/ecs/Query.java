package io.github.ikws4.dew.ecs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class Query implements Iterable<Entity> {
  World world;
  private final List<Entity> result;
  private final EntityIterator iterator;
  private final BitSet componentMask;

  Query(World world) {
    this.world = world;
    this.result = new ArrayList<>();
    this.iterator = new EntityIterator();
    this.componentMask = new BitSet();
  }

  void reset() {
    result.clear();
    componentMask.clear();
    iterator.index = 0;
  }

  public Query with(Class<?>... componentClasses) {
    for (Class<?> componentClass : componentClasses) {
      if (!world.componentIdMap.containsKey(componentClass)) {
        throw new IllegalArgumentException("Component not found");
      }

      int componentId = world.componentIdMap.get(componentClass);
      componentMask.set(componentId);
    }
    return this;
  }

  private void fillResult() {
    for (Entity entity : world.entities) {
      if (entity.removed) {
        continue;
      }

      BitSet tempMask = (BitSet) entity.componentMask.clone();
      tempMask.and(componentMask);
      if (tempMask.equals(componentMask)) {
        result.add(entity);
      }
    }
  }

  @Override
  public Iterator<Entity> iterator() {
    fillResult();
    return iterator;
  }

  class EntityIterator implements Iterator<Entity> {
    int index = 0;

    @Override
    public boolean hasNext() {
      return index < result.size();
    }

    @Override
    public Entity next() {
      return result.get(index++);
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
