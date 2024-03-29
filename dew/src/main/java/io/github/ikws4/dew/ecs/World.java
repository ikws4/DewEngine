package io.github.ikws4.dew.ecs;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class World {
  private final WeakReference<Context> context;

  private final List<System> startupSystems;
  private final List<System> systems;
  private final Command command;
  private final Query query;
  private final Res res;

  final Map<Class<?>, Object> resourceMap;

  final Map<Class<?>, Integer> componentIdMap;

  final List<Entity> entities;
  final Queue<Entity> freeEntityPool;

  final List<ComponentPool> componentPools;

  private Stage stage;

  public World(Context context) {
    this.context = new WeakReference<>(context);
    this.startupSystems = new ArrayList<>();
    this.systems = new ArrayList<>();
    this.command = new Command(this);
    this.query = new Query(this);
    this.res = new Res(this);

    this.resourceMap = new HashMap<>();

    this.componentIdMap = new HashMap<>();
    this.entities = new ArrayList<>();
    this.freeEntityPool = new LinkedList<>();
    this.componentPools = new ArrayList<>();

    this.stage = Stage.STARTUP;
  }

  public World insertResource(Object resource) {
    resourceMap.put(resource.getClass(), resource);
    return this;
  }

  public World addPlugin(Plugin plugin) {
    plugin.build(this);
    return this;
  }

  /**
   * @throws IllegalArgumentException if the system is already added
   */
  public World addStartupSystem(Class<? extends System> systemClass) {
    int i = 0;
    for (i = 0; i < startupSystems.size(); i++) {
      if (startupSystems.get(i).getClass() == systemClass) {
        break;
      }
    }

    if (i < startupSystems.size()) {
      throw new IllegalArgumentException("System already added.");
    }

    startupSystems.add(newSystem(systemClass));
    return this;
  }

  /**
   * @throws IllegalArgumentException if the system is already added
   */
  public World addSystem(Class<? extends System> systemClass) {
    int i = 0;
    for (i = 0; i < systems.size(); i++) {
      if (systems.get(i).getClass() == systemClass) {
        break;
      }
    }

    if (i < systems.size()) {
      throw new IllegalArgumentException("System already added.");
    }

    systems.add(newSystem(systemClass));
    return this;
  }

  private System newSystem(Class<? extends System> systemClass) {
    try {
      return systemClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate system.", e);
    }
  }

  int getComponentId(Class<?> componentClass) {
    Integer id = componentIdMap.get(componentClass);
    if (id == null) {
      throw new IllegalArgumentException("Component(" + componentClass.getName() + ") is not registerd in the world.");
    }
    return id;
  }

  public void run() {
    switch (stage) {
      case STARTUP:
        for (System system : startupSystems) {
          query.reset();
          system.run(context.get(), command, query, res);
        }
        stage = Stage.RUNNING;
        break;
      case RUNNING:
        for (System system : systems) {
          query.reset();
          system.run(context.get(), command, query, res);
        }
        break;
    }
  }

  static enum Stage {
    STARTUP, RUNNING
  }
}
