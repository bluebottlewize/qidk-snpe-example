package org.bluebottlewize.qidk_snpe_demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView preview_imageview;

    TextView classBox, modelLoadTimeBox, inferenceTimeBox;

    Bitmap classify_image;

    ModelController modelController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preview_imageview = findViewById(R.id.preview_imageview);
        classBox = findViewById(R.id.class_box);
        modelLoadTimeBox = findViewById(R.id.model_load_time_box);
        inferenceTimeBox = findViewById(R.id.inference_time_box);

        modelController = new ModelController(this);

        modelController.loadModel();
    }


    public void openGallery(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                int degrees = 0;

                classify_image = ModelController.resizeBitmap(bitmap, 224, 224, degrees);

                // Use the bitmap (e.g., set it to an ImageView)
                preview_imageview.setImageBitmap(classify_image);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void classify(View view)
    {
        modelController.classify(classify_image);
    }

    void setClassificationResult(int result)
    {
        String[] yogaPoses = {
                "Utkatakonasana",
                "Natarajasana",
                "Trikonasana",
                "Veerabhadrasana",
                "Padhahastasana",
                "Ashwasanchalasana",
                "Astangasana",
                "Bhujangasana",
                "ArdhaChandrasana",
                "Parvathasana",
                "BaddhaKonasana",
                "Vrukshasana",
                "Dandasana",
                "Shashangasana",
                "Ardhachakrasana",
                "Pranamasana"
        };

        classBox.setText(yogaPoses[result]);
    }

}