package io.github.ikws4.example;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhiping on 04/26/2022.
 */
public class MyApplication extends Application {
  private static Application context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
  }

  public static Application getContext() {
    return context;
  }
}
