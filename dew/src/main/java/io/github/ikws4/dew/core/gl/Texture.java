package io.github.ikws4.dew.core.gl;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import static android.opengl.GLES30.*;

public class Texture {
  private final int id;
  public final int width;
  public final int height;

  public Texture(Bitmap bitmap) {
    int[] textureIds = new int[1];
    glGenTextures(1, textureIds, 0);
    id = textureIds[0];

    width = bitmap.getWidth();
    height = bitmap.getHeight();

    glBindTexture(GL_TEXTURE_2D, id);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, GL_FALSE);

    glGenerateMipmap(GL_TEXTURE_2D);
  }

  public void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  public void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
