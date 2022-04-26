#version 300 es

precision mediump float;

uniform sampler2D u_textures[8];

in vec4 v_color;
in vec2 v_texCoord;
in float v_texId;

out vec4 fragColor;

void main() {
  if (v_texId == 0.0) {
    fragColor = v_color;
  } else {
    int index = int(v_texId);
    switch (index) {
      case 1:
        fragColor = texture(u_textures[1], v_texCoord) * v_color;
        break;
      case 2:
        fragColor = texture(u_textures[2], v_texCoord) * v_color;
        break;
      case 3:
        fragColor = texture(u_textures[3], v_texCoord) * v_color;
        break;
      case 4:
        fragColor = texture(u_textures[4], v_texCoord) * v_color;
        break;
      case 5:
        fragColor = texture(u_textures[5], v_texCoord) * v_color;
        break;
      case 6:
        fragColor = texture(u_textures[6], v_texCoord) * v_color;
        break;
      case 7:
        fragColor = texture(u_textures[7], v_texCoord) * v_color;
        break;
    }
  }
}
