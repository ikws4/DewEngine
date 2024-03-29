package io.github.ikws4.dew.glrenderer.component;

import io.github.ikws4.dew.glrenderer.Texture;

public class SpriteSheet {
  public final Texture texture;
  public final Sprite[] sprites;

  public SpriteSheet(Texture texture, int numSprites, int spriteWidth, int spriteHeight, int hGap, int vGap) {
    this.texture = texture;
    this.sprites = new Sprite[numSprites];
  }
}
