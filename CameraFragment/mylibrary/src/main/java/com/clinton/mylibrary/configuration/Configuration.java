package com.github.florent37.camerafragment.configuration;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Created by memfis on 7/6/16.
 * Updated by Florent37
 */
public final class Configuration implements Serializable {

    public static final int MEDIA_QUALITY_AUTO = 10;
    public static final int MEDIA_QUALITY_LOWEST = 15;
    public static final int MEDIA_QUALITY_LOW = 11;
    public static final int MEDIA_QUALITY_MEDIUM = 12;
    public static final int MEDIA_QUALITY_HIGH = 13;
    public static final int MEDIA_QUALITY_HIGHEST = 14;

    public static final int CAMERA_FACE_FRONT = 0x6;
    public static final int CAMERA_FACE_REAR = 0x7;

    public static final int SENSOR_POSITION_UP = 90;
    public static final int SENSOR_POSITION_UP_SIDE_DOWN = 270;
    public static final int SENSOR_POSITION_LEFT = 0;
    public static final int SENSOR_POSITION_RIGHT = 180;
    public static final int SENSOR_POSITION_UNSPECIFIED = -1;

    public static final int DISPLAY_ROTATION_0 = 0;
    public static final int DISPLAY_ROTATION_90 = 90;
    public static final int DISPLAY_ROTATION_180 = 180;
    public static final int DISPLAY_ROTATION_270 = 270;

    public static final int ORIENTATION_PORTRAIT = 0x111;
    public static final int ORIENTATION_LANDSCAPE = 0x222;

    public static final int FLASH_MODE_ON = 1;
    public static final int FLASH_MODE_OFF = 2;
    public static final int FLASH_MODE_AUTO = 3;

    @IntDef({MEDIA_QUALITY_AUTO, MEDIA_QUALITY_LOWEST, MEDIA_QUALITY_LOW, MEDIA_QUALITY_MEDIUM, MEDIA_QUALITY_HIGH, MEDIA_QUALITY_HIGHEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaQuality {
    }

    @IntDef({FLASH_MODE_ON, FLASH_MODE_OFF, FLASH_MODE_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlashMode {
    }

    @IntDef({CAMERA_FACE_FRONT, CAMERA_FACE_REAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraFace {
    }

    @IntDef({SENSOR_POSITION_UP, SENSOR_POSITION_UP_SIDE_DOWN, SENSOR_POSITION_LEFT, SENSOR_POSITION_RIGHT, SENSOR_POSITION_UNSPECIFIED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SensorPosition {
    }

    @IntDef({DISPLAY_ROTATION_0, DISPLAY_ROTATION_90, DISPLAY_ROTATION_180, DISPLAY_ROTATION_270})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayRotation {
    }

    @IntDef({ORIENTATION_PORTRAIT, ORIENTATION_LANDSCAPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceDefaultOrientation {
    }


    @MediaQuality
    private int mediaQuality = -1;

    @CameraFace
    private int cameraFace = -1;

    @FlashMode
    private int flashMode = FLASH_MODE_AUTO;

    public static class Builder {

        private Configuration configuration;

        public Builder() {
            configuration = new Configuration();
        }

        public Builder setMediaQuality(@MediaQuality int mediaQuality) {
            configuration.mediaQuality = mediaQuality;
            return this;
        }

        public Builder setFlashMode(@FlashMode int flashMode) {
            configuration.flashMode = flashMode;
            return this;
        }

        public Builder setCamera(@CameraFace int camera){
            configuration.cameraFace = camera;
            return this;
        }
    }

    public int getMediaQuality() {
        return mediaQuality;
    }

    @CameraFace
    public int getCameraFace() {
        return cameraFace;
    }

    @FlashMode
    public int getFlashMode() {
        return flashMode;
    }
}
