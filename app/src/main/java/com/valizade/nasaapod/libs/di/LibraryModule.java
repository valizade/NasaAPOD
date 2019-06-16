package com.valizade.nasaapod.libs.di;

import android.app.Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.valizade.nasaapod.libs.GlideImageLoader;
import com.valizade.nasaapod.libs.GreenRobotEventBus;
import com.valizade.nasaapod.libs.base.ImageLoader;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

@Module
public class LibraryModule {

  private Activity mActivity;

  public LibraryModule(Activity activity) {
    mActivity = activity;
  }

  @Singleton
  @Provides
  com.valizade.nasaapod.libs.base.EventBus providesGreenRobotEventBus(EventBus eventBus) {
    return new GreenRobotEventBus(eventBus);
  }

  @Singleton
  @Provides
  EventBus providesLibraryEventBus() {
    return EventBus.getDefault();
  }

  @Singleton
  @Provides
  ImageLoader providesGlideImageLoader(RequestManager requestManager) {
    return new GlideImageLoader(requestManager);
  }

  @Singleton
  @Provides
  RequestManager providesRequestManager(Activity activity) {
    return Glide.with(activity);
  }

  @Singleton
  @Provides
  Activity providesActivity() {
    return mActivity;
  }
}
