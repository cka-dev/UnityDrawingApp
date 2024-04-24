package com.sample.drawingappsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.unity3d.player.UnityPlayerActivity;
import com.unity3d.player.UnityPlayer;
import android.view.MotionEvent;
import android.view.View;

public class UnityPlayerCustomActivity extends UnityPlayerActivity {

  private static final String TAG = "CustomActivityLogs";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate called");
    View view = mUnityPlayer.getView();
    view.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        // Log.d(TAG, "Touch received by Unity view: Action=" + action);
        return UnityPlayerCustomActivity.this.onTouchEvent(event);
      }
    });
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getActionMasked();
    int index = event.getActionIndex();
    int pointerId = event.getPointerId(index);

    // Log.d(TAG, "onTouchEvent: Action=" + action + ", Index=" + index + ", PointerId=" + pointerId);

    switch (action) {
      case MotionEvent.ACTION_CANCEL:
        Log.d(TAG, "Gesture canceled by ACTION_CANCEL");
        UnityPlayer.UnitySendMessage("GameObjectName", "CancelGesture", "ACTION_CANCEL");
        return true;
      case MotionEvent.ACTION_POINTER_UP:
        Log.d(TAG, "ACTION_POINTER_UP detected");
        if ((event.getFlags() & MotionEvent.FLAG_CANCELED) != 0) {
          Log.d(TAG, "Gesture canceled by ACTION_POINTER_UP with FLAG_CANCELED");
          UnityPlayer.UnitySendMessage("GameObjectName", "CancelGesture",
              "ACTION_POINTER_UP_FLAG_CANCELED");
          return true;
        }
    }
    return super.onTouchEvent(event);
  }


  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart called");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop called");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy called");
  }
}
