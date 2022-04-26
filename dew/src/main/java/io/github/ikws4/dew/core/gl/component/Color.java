package io.github.ikws4.dew.core.gl.component;

public class Color {
  public final float r, g, b, a;
  
  public Color(int color) {
    this.a = ((color >> 24) & 0xFF) / 255f;
    this.r = ((color >> 16) & 0xFF) / 255f;
    this.g = ((color >>  8) & 0xFF) / 255f;
    this.b = ((color >>  0) & 0xFF) / 255f;
  }
  
  public Color(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }
}
