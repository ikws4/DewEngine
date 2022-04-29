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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((color == null) ? 0 : color.hashCode());
    result = prime * result + ((sprite == null) ? 0 : sprite.hashCode());
    result = prime * result + ((transform == null) ? 0 : transform.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SpriteBundle other = (SpriteBundle) obj;
    if (color == null) {
      if (other.color != null) return false;
    } else if (!color.equals(other.color)) return false;
    if (sprite == null) {
      if (other.sprite != null) return false;
    } else if (!sprite.equals(other.sprite)) return false;
    if (transform == null) {
      if (other.transform != null) return false;
    } else if (!transform.equals(other.transform)) return false;
    return true;
  }

}
