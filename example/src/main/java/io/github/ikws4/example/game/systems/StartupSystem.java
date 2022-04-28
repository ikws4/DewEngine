package io.github.ikws4.example.game.systems;

import android.content.Context;

import org.joml.Vector2f;
import org.joml.Vector3f;

import io.github.ikws4.dew.core.gl.component.Color;
import io.github.ikws4.dew.core.gl.component.Sprite;
import io.github.ikws4.dew.core.gl.component.Transform;
import io.github.ikws4.dew.core.gl.component.bundle.SpriteBundle;
import io.github.ikws4.dew.core.resource.ViewPort;
import io.github.ikws4.dew.core.util.AssetsManager;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;
import io.github.ikws4.example.game.component.Pikachu;
import io.github.ikws4.example.game.component.Velocity;

/**
 * Created by zhiping on 04/26/2022.
 */
public class StartupSystem extends System {

  @Override
  public void run(Context context, Command command, Query query, Res res) {
    ViewPort viewPort = res.get(ViewPort.class);
    float hw = viewPort.width / 2f;
    float hh = viewPort.height / 2f;

    for (int i = 0; i < 20; i++) {
      float x = (float) (Math.random() - 0.5f) * hw;
      float y = (float) (Math.random() - 0.5f) * hh;

      float r = (float) Math.random();
      float g = (float) Math.random();
      float b = (float) Math.random();

      float angle = (float) Math.random() * 360f;

      SpriteBundle pikachuSpriteBundle = new SpriteBundle(
          new Sprite(AssetsManager.getTexture(context, "pikachu.png")),
          new Color(r, g, b, 0.5f),
          new Transform(
              new Vector3f(x, y, 0),
              new Vector2f(1),
              angle
          )
      );

      float vx = (float) (Math.random() - 0.5f) * 800;
      float vy = (float) (Math.random() - 0.5f) * 800;

      command.createEntity()
          .insertBundle(pikachuSpriteBundle)
          .insert(new Velocity(vx, vy))
          .insert(new Pikachu());
    }
  }
}
