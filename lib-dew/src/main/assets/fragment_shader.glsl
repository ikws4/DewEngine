#version 300 es

precision mediump float;

uniform Sampler2D u_textures[];

in vec4 v_color;
in vec2 v_texCoord;
in float v_texId;

out vec4 fragColor;

void main() {
  int texId = int(v_texId);
  fragColor = texture(u_textures[texId], v_texCoord) * v_color;
}
