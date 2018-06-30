package com.example.holys.imagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView imageView ;
    private final static int REQUEST_CODE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageViewer);
    }

    public  void onClick(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        InputStream stream = null;
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK )
        {
            try
            {
                if (bitmap != null)
                    bitmap.recycle();
                stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException ex)
            {
                Toast.makeText(MainActivity.this, "Couldnt read Image", Toast.LENGTH_LONG).show();
            }
            finally {
                if (stream != null)
                {
                    try{
                        stream.close();
                    }catch (IOException e)
                    {
                        Toast.makeText(MainActivity.this, "Could not close file", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }
}
