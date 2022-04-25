package io.github.ikws4.dew.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class World {
  private final List<System> startupSystems;
  private final List<System> systems;
  private final Command command;
  private final Query query;

  final Map<Class<?>, Integer> componentIdMap;

  final List<Entity> entities;
  final Queue<Entity> freeEntityPool;

  final List<ComponentPool> componentPools;

  private Stage stage;

  public World() {
    startupSystems = new ArrayList<>();
    systems = new ArrayList<>();
    command = new Command(this);
    query = new Query(this);

    componentIdMap = new HashMap<>();
    entities = new ArrayList<>();
    freeEntityPool = new LinkedList<>();
    componentPools = new ArrayList<>();

    stage = Stage.STARTUP;
  }

  /**
   * @throws IllegalArgumentException if the system is already added
   */
  public World addStartupSystem(System system) {
    int i = 0;
    for (i = 0; i < startupSystems.size(); i++) {
      if (startupSystems.get(i).getClass() == system.getClass()) {
        break;
      }
    }

    if (i < startupSystems.size()) {
      throw new IllegalArgumentException("System already added.");
    }

    startupSystems.add(system);
    return this;
  }

  /**
   * @throws IllegalArgumentException if the system is already added
   */
  public World addSystem(System system) {
    int i = 0;
    for (i = 0; i < systems.size(); i++) {
      if (systems.get(i).getClass() == system.getClass()) {
        break;
      }
    }

    if (i < systems.size()) {
      throw new IllegalArgumentException("System already added.");
    }

    systems.add(system);
    return this;
  }

  public void run() {
    switch (stage) {
      case STARTUP:
        for (System system : startupSystems) {
          query.reset();
          system.run(command, query);
        }
        stage = Stage.RUNNING;
        break;
      case RUNNING:
        for (System system : systems) {
          query.reset();
          system.run(command, query);
        }
        break;
    }
  }

  static enum Stage {
    STARTUP,
    RUNNING
  }
}
