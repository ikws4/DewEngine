package io.github.ikws4.dew.ecs;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class Query implements Iterable<Query.Result> {
  private World world;
  private final List<Entity> results;
  private final ResultIterator iterator;
  private final BitSet componentMask;

  Query(World world) {
    this.world = world;
    this.results = new ArrayList<>();
    this.iterator = new ResultIterator();
    this.componentMask = new BitSet();
  }

  void reset() {
    results.clear();
    componentMask.clear();
    iterator.reset();
  }

  public Query with(Class<?>... componentClasses) {
    for (Class<?> componentClass : componentClasses) {
      int componentId = world.getComponentId(componentClass);
      componentMask.set(componentId);
    }
    return this;
  }

  private void fillResults() {
    for (Entity entity : world.entities) {
      if (entity.removed) {
        continue;
      }

      BitSet tempMask = (BitSet) entity.componentMask.clone();
      tempMask.and(componentMask);
      if (tempMask.equals(componentMask)) {
        results.add(entity);
      }
    }
  }

  @Override
  public Iterator<Result> iterator() {
    fillResults();
    return iterator;
  }

  public class Result {
    Entity entity;

    Result(Entity entity) {
      this.entity = entity;
    }

    public <T> T get(Class<T> componentClass) {
      if (componentMask.get(world.getComponentId(componentClass)) == false) {
        throw new IllegalArgumentException("Component(" + componentClass.getSimpleName() + ") is not in the query.");
      }
      
      return (T) entity.get(componentClass);
    }
  }

  class ResultIterator implements Iterator<Result> {
    private final Result result = new Result(null);
    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < results.size();
    }

    @Override
    public Result next() {
      result.entity = results.get(index++);
      return result;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
    
    private void reset() {
      index = 0;
    }
  }
}
