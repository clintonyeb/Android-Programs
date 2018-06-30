package com.github.florent37.camerafragment.configuration;

/*
 * Created by memfis on 7/6/16.
 */
public interface ConfigurationProvider {

    @Configuration.MediaQuality
    int getMediaQuality();

    @Configuration.SensorPosition
    int getSensorPosition();

    int getDegrees();

    @Configuration.FlashMode
    int getFlashMode();

    @Configuration.CameraFace
    int getCameraFace();

    void setMediaQuality(int mediaQuality);

    void setPassedMediaQuality(int mediaQuality);

    void setFlashMode(int flashMode);

    void setSensorPosition(int sensorPosition);

    int getDeviceDefaultOrientation();

    void setDegrees(int degrees);

    void setDeviceDefaultOrientation(int deviceDefaultOrientation);

    int getPassedMediaQuality();

    void setupWithAnnaConfiguration(Configuration configuration);
}
