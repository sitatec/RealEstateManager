package com.berete.realestatemanager.ui.map;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.berete.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class LocationPermissionHandler implements EasyPermissions.PermissionCallbacks {

  private static final int PERMISSION_REQUEST_CODE = 11;
  private boolean permissionAlreadyDenied = false;
  private final Activity activity;

  private static final List<Runnable> onPermissionGrantedListeners =
      new ArrayList<>();

  @Inject
  public LocationPermissionHandler(Activity activity) {
    this.activity = activity;
  }

  public boolean hasPermission() {
    return EasyPermissions.hasPermissions(activity, Manifest.permission.ACCESS_FINE_LOCATION);
  }

  public void requestPermission() {
    EasyPermissions.requestPermissions(
        new PermissionRequest.Builder(
                activity, PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION)
            .setRationale(R.string.rational_location_request_msg)
            .setNegativeButtonText(R.string.not_now_txt)
            .setPositiveButtonText(R.string.allow_location_permission)
            .build());
  }

  public void requestPermission(Runnable onPermissionGranted) {
    Log.d("LocationPermHandler", "requestPermission");
    onPermissionGrantedListeners.add(onPermissionGranted);
    requestPermission();
  }

  @Override
  public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    for (Runnable listener : onPermissionGrantedListeners) {
      Log.d("LocationPermHandler", "onPermissionsGranted : " + listener);
      if (listener != null) listener.run();
    }
  }

  @Override
  public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    if (requestCode == PERMISSION_REQUEST_CODE && !permissionAlreadyDenied) {
      permissionAlreadyDenied = true;
      requestPermissionAfterDenied();
    }
  }

  private void requestPermissionAfterDenied() {
    EasyPermissions.requestPermissions(
        new PermissionRequest.Builder(
                activity, PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION)
            .setRationale(R.string.when_location_permission_denied)
            .setNegativeButtonText(R.string.continue_without_permission)
            .setPositiveButtonText(R.string.allow_location_permission_1)
            .build());
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

}
