package io.github.ikws4.dew.core.gl;

import static android.opengl.GLES30.*;
import java.util.HashMap;
import java.util.Map;

public class Shader {
  private final Map<String, Integer> uniforms;
  private final int programId;

  public Shader(String vertexShader, String fragmentShader) {
    int vertexShaderId = compileShader(GL_VERTEX_SHADER, vertexShader);
    int fragmentShaderId = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

    this.programId = glCreateProgram();
    glAttachShader(programId, vertexShaderId);
    glAttachShader(programId, fragmentShaderId);
    glLinkProgram(programId);

    glDeleteShader(vertexShaderId);
    glDeleteShader(fragmentShaderId);

    int[] status = new int[1];
    glGetProgramiv(programId, GL_LINK_STATUS, status, 0);

    if (status[0] == 0) {
      String error = glGetProgramInfoLog(programId);
      glDeleteProgram(programId);
      throw new RuntimeException("Failed to link program: " + error);
    }

    this.uniforms = new HashMap<>();
  }

  public void detach() {
    glUseProgram(0);
  }

  public void attach() {
    glUseProgram(programId);
  }

  public void setUniform1i(String name, int value) {
    glUniform1i(getUniformLocation(name), value);
  }

  public void setUniform1f(String name, float value) {
    glUniform1f(getUniformLocation(name), value);
  }

  public void setUniformMatrix4fv(String name, float[] matrix) {
    glUniformMatrix4fv(getUniformLocation(name), 1, false, matrix, 0);
  }

  public void setUniformArray1i(String name, int[] values) {
    glUniform1iv(getUniformLocation(name), values.length, values, 0);
  }

  public void setUniformArray1f(String name, float[] values) {
    glUniform1fv(getUniformLocation(name), values.length, values, 0);
  }

  private int getUniformLocation(String name) {
    Integer location = uniforms.get(name);
    if (location == null) {
      location = glGetUniformLocation(programId, name);
      uniforms.put(name, location);
      if (location == -1) {
        throw new RuntimeException("Could not find uniform: " + name);
      }
    }
    return location;
  }

  private int compileShader(int type, String shaderCode) {
    int shaderId = glCreateShader(type);
    glShaderSource(shaderId, shaderCode);
    glCompileShader(shaderId);

    int[] status = new int[1];
    glGetShaderiv(shaderId, GL_COMPILE_STATUS, status, 0);

    if (status[0] == GL_FALSE) {
      String error = glGetShaderInfoLog(shaderId);
      glDeleteShader(shaderId);
      throw new RuntimeException("Failed to compile shader: " + error + "\n" + shaderCode);
    }

    return shaderId;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    glDeleteProgram(programId);
  }
}
