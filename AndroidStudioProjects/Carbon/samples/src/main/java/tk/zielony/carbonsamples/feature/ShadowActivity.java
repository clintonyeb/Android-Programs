package tk.zielony.carbonsamples.feature;

import android.app.Activity;
import android.os.Bundle;

import com.nineoldandroids.animation.ValueAnimator;

import tk.zielony.carbonsamples.R;

/**
 * Created by Marcin on 2014-12-15.
 */
public class ShadowActivity extends Activity {
    ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
    }
}
