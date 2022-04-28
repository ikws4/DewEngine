package io.github.ikws4.example.game.systems;

import android.content.Context;

import io.github.ikws4.dew.glrenderer.component.Transform;
import io.github.ikws4.dew.base.resource.Time;
import io.github.ikws4.dew.base.resource.ViewPort;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Entity;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;
import io.github.ikws4.example.game.component.Pikachu;
import io.github.ikws4.example.game.component.Velocity;

/**
 * Created by zhiping on 04/27/2022.
 */
public class PikachuMovementSystem extends System {

  @Override
  public void run(Context context, Command command, Query query, Res res) {
    Time time = res.get(Time.class);
    ViewPort viewPort = res.get(ViewPort.class);

    for (Entity entity : query.with(Pikachu.class)) {
      Transform transform = entity.get(Transform.class);
      Velocity velocity = entity.get(Velocity.class);

      float hw = viewPort.width / 2f;
      float hh = viewPort.height / 2f;

      if (transform.position.x > hw || transform.position.x < -hw) {
        velocity.x *= -1;
      }

      if (transform.position.y > hh || transform.position.y < -hh) {
        velocity.y *= -1;
      }

      float dx = velocity.x * time.deltaTime;
      float dy = velocity.y * time.deltaTime;

      transform.position.x += dx;
      transform.position.y += dy;
      transform.rotation += 1;
    }
  }
}
