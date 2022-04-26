#version 300 es

uniform mat4 u_mvp;

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_texCoord;
layout(location = 3) in float a_texId;

out vec4 v_color;
out vec2 v_texCoord;
out float v_texId;

void main() {
  gl_Position = u_mvp * vec4(a_position, 1);

  v_color = a_color;
  v_texCoord = a_texCoord;
  v_texId = a_texId;
}
