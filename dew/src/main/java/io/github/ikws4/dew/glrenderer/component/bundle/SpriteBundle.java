package io.github.ikws4.dew.glrenderer.component.bundle;

import io.github.ikws4.dew.glrenderer.component.Color;
import io.github.ikws4.dew.glrenderer.component.Sprite;
import io.github.ikws4.dew.glrenderer.component.Transform;
import io.github.ikws4.dew.ecs.Bundle;

public class SpriteBundle extends Bundle {
  public final Sprite sprite;
  public final Color color;
  public final Transform transform;

  private static final Color WHITE = new Color(0xFFFFFFFF);

  public SpriteBundle(Sprite sprite, Color color, Transform transform) {
    this.sprite = sprite;
    this.color = color == null ? WHITE : color;
    this.transform = transform;
  }

  @Override
  public Object[] unpack() {
    return new Object[] {sprite, color, transform};
  }
}
