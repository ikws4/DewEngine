package io.github.ikws4.dew.ecs;

class ComponentPool {
  private Object[] pool;

  ComponentPool(int size) {
    this.pool = new Object[Math.max(size, 16)];
  }

  public Object get(int entityId) {
    return pool[entityId];
  }

  public void set(int entityId, Object component) {
    if (entityId >= pool.length) {
      Object[] newPool = new Object[Math.max(entityId + 1, (int) (pool.length * 1.75f))];
      java.lang.System.arraycopy(pool, 0, newPool, 0, pool.length);
      pool = newPool;
    }
    pool[entityId] = component;
  }
}
