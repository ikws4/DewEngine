package io.github.ikws4.dew.android.gl;

import static android.opengl.GLES30.*;
import java.util.HashMap;
import java.util.Map;

public class Shader {
  private final Map<String, Integer> uniforms;
  private final int program;

  public Shader(String vertexShader, String fragmentShader) {
    int vertexShaderId = compileShader(GL_VERTEX_SHADER, vertexShader);
    int fragmentShaderId = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

    program = glCreateProgram();
    glAttachShader(program, vertexShaderId);
    glAttachShader(program, fragmentShaderId);
    glLinkProgram(program);

    glDeleteShader(vertexShaderId);
    glDeleteShader(fragmentShaderId);

    int[] status = new int[1];
    glGetProgramiv(program, GL_LINK_STATUS, status, 0);

    if (status[0] == 0) {
      glDeleteProgram(program);
      throw new RuntimeException("Failed to link program: " + glGetProgramInfoLog(program));
    }
    
    uniforms = new HashMap<>();
    
    int[] uniformCount = new int[1];
    glGetProgramiv(program, GL_ACTIVE_UNIFORMS, uniformCount, 0);
    for (int i = 0; i < uniformCount[0]; i++) {
      int[] uniformSize = new int[1];
      int[] uniformType = new int[1];
      String uniformName = glGetActiveUniform(program, i, uniformSize, 0, uniformType, 0);
      int uniformLocation = glGetUniformLocation(program, uniformName);
      uniforms.put(uniformName, uniformLocation);
    }
  }

  public void detach() {
    glUseProgram(0);
  }

  public void attach() {
    glUseProgram(program);
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

  private Integer getUniformLocation(String name) {
    Integer location = uniforms.get(name);
    if (location == null) {
      throw new RuntimeException("Uniform not found: " + name);
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
      glDeleteShader(shaderId);
      throw new RuntimeException("Failed to compile shader: " + glGetShaderInfoLog(shaderId));
    }

    return shaderId;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    glDeleteProgram(program);
  }
}
