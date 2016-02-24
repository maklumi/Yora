package com.example.maklumi.yora.views;

import android.hardware.Camera;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.maklumi.yora.activities.BaseActivity;

import java.util.List;

/**
 * Created by Maklumi on 24-02-16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Camera.CameraInfo cameraInfo;
    private boolean isSurfaceCreated;

    public CameraPreview(BaseActivity activity){
        super(activity);
        isSurfaceCreated = false;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void setCamera(Camera camera, Camera.CameraInfo cameraInfo){
        if (this.camera != null){
Log.e(TAG, "Camera mana!");
            try {
                this.camera.stopPreview();
            } catch (Exception e) {
                Log.e(TAG, "Could not stop camera preview", e);
            }
        }

        this.camera = camera;
        this.cameraInfo = cameraInfo;

        if (camera == null) return;

        if (!isSurfaceCreated){
            return;
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            configureCamera();
            camera.startPreview();
        } catch (Exception e){
            Log.e(TAG, "Could not start camera preview", e);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (surfaceHolder != holder) {
            surfaceHolder = holder;
            surfaceHolder.addCallback(this);
        }

        isSurfaceCreated = true;

        if (camera != null){
            setCamera(camera, cameraInfo);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceCreated = false;
        surfaceHolder.removeCallback(this);
        surfaceHolder= null;

        if (camera == null){
            return;
        }

        try {
            camera.stopPreview();
            camera = null;
            cameraInfo = null;
        } catch (Exception e){
            Log.e(TAG, "Could not stop preview", e);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        heightMeasureSpec = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    private void configureCamera() {
        Camera.Parameters parameters = camera.getParameters();

        Camera.Size targetPreviewSize = getClosestSize(getWidth(), getHeight(), parameters.getSupportedPreviewSizes());
        parameters.setPictureSize(targetPreviewSize.width, targetPreviewSize.height);

        camera.setDisplayOrientation(90);
        camera.setParameters(parameters);
    }

    private Camera.Size getClosestSize(int width, int height, List<Camera.Size> supportedPreviewSizes) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) height/width;

        Camera.Size targetSize = null;
        double minDifference = Double.MAX_VALUE;

        for (Camera.Size size : supportedPreviewSizes){
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }

            int heightDifference = Math.abs(size.height - height);
            if (heightDifference < minDifference) {
                targetSize = size;
                minDifference = heightDifference;
            }


        }

        if (targetSize == null) {
            minDifference = Double.MAX_VALUE;
            for (Camera.Size size : supportedPreviewSizes) {
                int heightDifference = Math.abs(size.height - height);
                if (heightDifference < minDifference) {
                    targetSize = size;
                    minDifference = heightDifference;
                }
            }
        }

        return targetSize;
    }


}
