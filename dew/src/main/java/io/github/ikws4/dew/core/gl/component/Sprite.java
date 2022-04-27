package io.github.ikws4.dew.core.gl.component;

import org.joml.Vector2f;
import io.github.ikws4.dew.core.gl.Texture;

public class Sprite {
  public final Texture texture;
  public final Vector2f[] texCoords;

  public Sprite() {
    this(null);
  }

  /**
   *   3    2
   *   ┌────┐
   *   │    │
   *   └────┘
   *   0    1
   *
   * @param texture
   */
  public Sprite(Texture texture) {
    this(texture, new Vector2f[] {
        new Vector2f(0, 1), // 3
        new Vector2f(1, 1), // 2
        new Vector2f(1, 0), // 1
        new Vector2f(0, 0), // 0
    });
  }

  public Sprite(Texture texture, Vector2f[] texCoords) {
    this.texture = texture;
    this.texCoords = texCoords;
  }
}
