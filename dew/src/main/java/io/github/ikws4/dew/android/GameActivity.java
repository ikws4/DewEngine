package io.github.ikws4.dew.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import io.github.ikws4.dew.R;

public abstract class GameActivity extends Activity {
  private GLRenderer glRenderer;
  private GLSurfaceView glSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupWindow();
    setupRenderer();
  }

  protected abstract Game onCreateGame();

  private void setupWindow() {
    setContentView(R.layout.activity_game);

    // landscape and sensor mode
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

    // hide nav bar
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  private void setupRenderer() {
    glRenderer = new GLRenderer(onCreateGame());
    glSurfaceView = findViewById(R.id.glSurfaceView);
    glSurfaceView.setEGLContextClientVersion(3);
    glSurfaceView.setPreserveEGLContextOnPause(true);
    glSurfaceView.setRenderer(glRenderer);
    glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
  }

  @Override
  public void onPause() {
    super.onPause();
    glSurfaceView.onPause();
    glRenderer.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
    glSurfaceView.onResume();
    glRenderer.onResume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    glRenderer.onDestory();
  }

  static class GLRenderer implements GLSurfaceView.Renderer {
    private final Game game;

    public GLRenderer(Game game) {
      this.game = game;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      game.setup();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
      game.resize(width, height);
    }

    private long lastTime = -1;

    @Override
    public void onDrawFrame(GL10 gl) {
      long currentTime = System.nanoTime();
      double dt = (currentTime - lastTime) / 1e9;
      lastTime = currentTime;

      game.loop(dt);
    }

    public void onPause() {
      game.pause();
    }

    public void onResume() {
      game.resume();
      lastTime = System.nanoTime();
    }

    public void onDestory() {
      game.dispose();
    }
  }
}
