package io.github.ikws4.dew.core.gl.resource;

public class ClearColor {
  public float r;
  public float g;
  public float b;
  public float a;

  public ClearColor(int color) {
    this.a = ((color >> 24) & 0xFF) / 255f;
    this.r = ((color >> 16) & 0xFF) / 255f;
    this.g = ((color >>  8) & 0xFF) / 255f;
    this.b = ((color >>  0) & 0xFF) / 255f;
  }

  public ClearColor(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }
}
