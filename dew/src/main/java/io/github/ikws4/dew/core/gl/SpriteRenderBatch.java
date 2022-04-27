package io.github.ikws4.dew.core.gl;

import io.github.ikws4.dew.core.gl.component.Color;
import io.github.ikws4.dew.core.gl.component.Sprite;
import io.github.ikws4.dew.core.gl.component.Transform;
import io.github.ikws4.dew.core.gl.component.bundle.SpriteBundle;
import static android.opengl.GLES30.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by zhiping on 04/26/2022.
 */
class SpriteRenderBatch implements RenderBatch {
  private static final int SIZE_OF_FLOAT = 4;
  private static final int SIZE_OF_SHORT = 2;

  // @formatter:off
  // struct Vertex
  // =============
  // position | color       | texCoord | texId
  // ---------|-------------|----------|------
  // f, f, f, | f, f, f, f, | f, f     | f
  private static final int SIZE_OF_POSITION = 3 * SIZE_OF_FLOAT;
  private static final int SIZE_OF_COLOR = 4 * SIZE_OF_FLOAT;
  private static final int SIZE_OF_TEX_COORD = 2 * SIZE_OF_FLOAT;
  private static final int SIZE_OF_TEX_ID = 1 * SIZE_OF_FLOAT;
  private static final int SIZE_OF_VERTEX = SIZE_OF_POSITION + SIZE_OF_COLOR + SIZE_OF_TEX_COORD + SIZE_OF_TEX_ID;

  private static final int OFFSET_OF_POSITION = 0;
  private static final int OFFSET_OF_COLOR = OFFSET_OF_POSITION + SIZE_OF_POSITION;
  private static final int OFFSET_OF_TEX_COORD = OFFSET_OF_COLOR + SIZE_OF_COLOR;
  private static final int OFFSET_OF_TEX_ID = OFFSET_OF_TEX_COORD + SIZE_OF_TEX_COORD;

  private static final int NUMBER_OF_VERTICES_PER_QUAD = 4;
  private static final int NUMBER_OF_VERTICES_PER_TRIANGLE = 3;
  // @formatter:on

  private final int[] vaoId = new int[1];
  private final int[] vboId = new int[1];
  private final int[] eboId = new int[1];

  private final int[] textureSlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7};

  private final SpriteBundle[] spriteBundles;
  private final FloatBuffer verticesBuffer;
  private final List<Texture> textures;

  private final Shader shader;
  private final short capacity;
  private short size;

  public SpriteRenderBatch(Shader shader) {
    this(shader, (short) 1024);
  }

  public SpriteRenderBatch(Shader shader, short capacity) {
    this.spriteBundles = new SpriteBundle[capacity];
    this.textures = new ArrayList<>(textureSlots.length);
    this.verticesBuffer =
        ByteBuffer.allocateDirect(NUMBER_OF_VERTICES_PER_QUAD * SIZE_OF_FLOAT * capacity)
          .order(ByteOrder.nativeOrder())
          .asFloatBuffer();

    this.shader = shader;
    this.capacity = capacity;
    this.size = 0;

    // vao
    glGenVertexArrays(1, vaoId, 0);
    glBindVertexArray(vaoId[0]);

    // vbo
    glGenBuffers(1, vboId, 0);
    glBindBuffer(GL_ARRAY_BUFFER, vboId[0]);
    glBufferData(GL_ARRAY_BUFFER, SIZE_OF_VERTEX * NUMBER_OF_VERTICES_PER_QUAD * capacity, null,
        GL_DYNAMIC_DRAW);

    // ebo
    glGenBuffers(1, eboId, 0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId[0]);
    ShortBuffer indices = generateIndices();
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.capacity() * SIZE_OF_SHORT, indices,
        GL_STATIC_DRAW);

    // enable attributes pointer
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, SIZE_OF_POSITION / SIZE_OF_FLOAT, GL_FLOAT, false, SIZE_OF_VERTEX,
        OFFSET_OF_POSITION);

    glEnableVertexAttribArray(1);
    glVertexAttribPointer(1, SIZE_OF_COLOR / SIZE_OF_FLOAT, GL_FLOAT, false, SIZE_OF_VERTEX,
        OFFSET_OF_COLOR);

    glEnableVertexAttribArray(2);
    glVertexAttribPointer(2, SIZE_OF_TEX_COORD / SIZE_OF_FLOAT, GL_FLOAT, false, SIZE_OF_VERTEX,
        OFFSET_OF_TEX_COORD);

    glEnableVertexAttribArray(3);
    glVertexAttribPointer(3, SIZE_OF_TEX_ID / SIZE_OF_FLOAT, GL_FLOAT, false, SIZE_OF_VERTEX,
        OFFSET_OF_TEX_ID);

    glBindVertexArray(0);
  }

  private ShortBuffer generateIndices() {
    // two triangles per quad
    short[] indices = new short[2 * NUMBER_OF_VERTICES_PER_TRIANGLE * capacity];

    short step = 0;
    short offset = 0;

    // 0 1 2 2 3 0
    // 4 5 6 6 7 4
    // ...
    for (short i = 0; i < capacity; i++) {
      indices[step + 0] = (short) (0 + offset);
      indices[step + 1] = (short) (1 + offset);
      indices[step + 2] = (short) (2 + offset);
      indices[step + 3] = (short) (2 + offset);
      indices[step + 4] = (short) (3 + offset);
      indices[step + 5] = (short) (0 + offset);

      step += 6;
      offset += 4;
    }

    ShortBuffer buffer = ByteBuffer.allocateDirect(indices.length * SIZE_OF_SHORT)
      .order(ByteOrder.nativeOrder())
      .asShortBuffer();
    buffer.put(indices);
    buffer.position(0);

    return buffer;
  }

  private final float[] verticies = new float[NUMBER_OF_VERTICES_PER_QUAD * SIZE_OF_VERTEX / SIZE_OF_FLOAT];
  private final Vector3f position = new Vector3f();

  public boolean add(SpriteBundle spriteBundle) {
    if (size >= capacity) return false;

    Texture texture = spriteBundle.sprite.texture;
    if (texture != null && !textures.contains(texture) &&
        textures.size() >= textureSlots.length) {
      return false;
    }

    spriteBundles[size] = spriteBundle;
    loadVertex(size);

    size++;

    return true;
  }

  private void loadVertex(int index) {
    Sprite sprite = spriteBundles[index].sprite;
    Color color = spriteBundles[index].color;
    Matrix4f transform = spriteBundles[index].transform.getTransformMatrix();

    verticesBuffer.position(index * NUMBER_OF_VERTICES_PER_QUAD * (SIZE_OF_VERTEX / SIZE_OF_FLOAT));

    int texId = 0;
    if (sprite.texture != null) {
      texId = textures.indexOf(sprite.texture) + 1;

      if (texId == 0) {
        textures.add(sprite.texture);
        texId = textures.size();
      }
    }

    /*
     *   3    2
     *   ┌────┐
     *   │    │
     *   └────┘
     *   0    1
     */
    float w = 0.5f, h = 0.5f;

    if (sprite.texture != null) {
      w = sprite.texture.width / 2f;
      h = sprite.texture.height / 2f;
    }

    int offset = 0;
    for (int i = 0; i < NUMBER_OF_VERTICES_PER_QUAD; i++) {

      if (i == 0) {
        position.x = -w;
        position.y = -h;
      } else if (i == 1) {
        position.x = w;
        position.y = -h;
      } else if (i == 2) {
        position.x = w;
        position.y = h;
      } else {
        position.x = -w;
        position.y = h;
      }

      transform.transformPosition(position);

      // position
      verticies[offset + 0] = position.x;
      verticies[offset + 1] = position.y;
      verticies[offset + 2] = position.z;

      // color
      verticies[offset + 3] = color.r;
      verticies[offset + 4] = color.g;
      verticies[offset + 5] = color.b;
      verticies[offset + 6] = color.a;

      // texCoord
      verticies[offset + 7] = sprite.texCoords[i].x;
      verticies[offset + 8] = sprite.texCoords[i].y;

      // texId
      verticies[offset + 9] = texId;

      offset += SIZE_OF_VERTEX / SIZE_OF_FLOAT;
    }

    verticesBuffer.put(verticies);
  }

  private final float[] matrix = new float[16];

  @Override
  public void render(Matrix4f mvp) {
    shader.attach();
    shader.setUniformMatrix4fv("u_mvp", mvp.get(matrix));
    shader.setUniformArray1i("u_textures", textureSlots);
    for (int i = 0; i < textures.size(); i++) {
      glActiveTexture(GL_TEXTURE0 + i + 1);
      textures.get(i).bind();
    }

    glBindVertexArray(vaoId[0]);

    // TODO: only reload vertices that have changed
    for (int i = 0; i < size; i++) {
      loadVertex(i);
    }
    int pos = verticesBuffer.position();
    verticesBuffer.position(0);
    glBufferSubData(GL_ARRAY_BUFFER, 0, pos * SIZE_OF_FLOAT, verticesBuffer);

    glDrawElements(GL_TRIANGLES, size * 2 * NUMBER_OF_VERTICES_PER_TRIANGLE, GL_UNSIGNED_SHORT, 0);

    glBindVertexArray(0);

    for (int i = 0; i < textures.size(); i++) {
      textures.get(i).unbind();
    }
    shader.detach();
  }
}
