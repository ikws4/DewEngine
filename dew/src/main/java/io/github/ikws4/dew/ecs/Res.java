package io.github.ikws4.dew.ecs;

/**
 * Created by zhiping on 04/26/2022.
 */
public class Res {
  private final World world;

  public Res(World world) {
    this.world = world;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> res) {
    return (T) world.resourceMap.get(res);
  }
}
