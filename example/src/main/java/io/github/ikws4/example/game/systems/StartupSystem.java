package io.github.ikws4.example.game.systems;

import android.content.Context;

import org.joml.Vector2f;
import org.joml.Vector3f;

import io.github.ikws4.dew.core.gl.Renderer;
import io.github.ikws4.dew.core.gl.component.Color;
import io.github.ikws4.dew.core.gl.component.Sprite;
import io.github.ikws4.dew.core.gl.component.Transform;
import io.github.ikws4.dew.core.gl.component.bundle.SpriteBundle;
import io.github.ikws4.dew.core.util.AssetsManager;
import io.github.ikws4.dew.ecs.Command;
import io.github.ikws4.dew.ecs.Query;
import io.github.ikws4.dew.ecs.Res;
import io.github.ikws4.dew.ecs.System;

/**
 * Created by zhiping on 04/26/2022.
 */
public class StartupSystem implements System {
  @Override
  public void run(Context context, Command command, Query query, Res res) {
    SpriteBundle player = new SpriteBundle(
        new Sprite(
            AssetsManager.getTexture(context, "head.png")
        ),
        new Color(0xFF8ED1DA),
        new Transform(
            new Vector3f(0),
            new Vector2f(2),
            0f
        )
    );
    command.createEntity().insert(player);
  }
}
