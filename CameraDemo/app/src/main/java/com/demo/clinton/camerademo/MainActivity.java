package com.demo.clinton.camerademo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    Camera camera;
    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkIfCameraPresent(MainActivity.this)){
            System.out.println("has a camera");
            camera = getCameraInstance();

            cameraPreview = new CameraPreview(this, camera);
            FrameLayout cameraLayout = (FrameLayout) findViewById(R.id.cameraPrev);
            cameraLayout.addView(cameraPreview);
        }
        else System.out.println("no camera detected");

        Button button = (Button) findViewById(R.id.button_capture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkIfCameraPresent(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) return true;
        else return false;
    }

    private Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            System.out.println("camera in use");
        }
        return c;
    }
}
