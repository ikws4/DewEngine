package io.github.ikws4.dew.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import io.github.ikws4.dew.android.gl.Texture;

public class AssetsManager {
  private static Map<String, Texture> textureCache = new HashMap<>();
  private static Map<String, String> textCache = new HashMap<>();

  public static Texture getTexture(Context context, String filePath) {
    if (textureCache.containsKey(filePath)) return textureCache.get(filePath);

    try (InputStream in = context.getAssets().open(filePath)) {

      Bitmap bitmap = BitmapFactory.decodeStream(in);

      Texture texture = new Texture(bitmap);
      textureCache.put(filePath, texture);

      bitmap.recycle();
      return texture;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getText(Context context, String filePath) {
    if (textCache.containsKey(filePath)) return textCache.get(filePath);

    try (InputStream in = context.getAssets().open(filePath)) {
      
      int size = in.available();
      byte[] bytes = new byte[size];
      in.read(bytes);
      
      String text = new String(bytes);
      textCache.put(filePath, text);
      
      return text;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
