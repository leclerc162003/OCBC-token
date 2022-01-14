package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;

public class secretactivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        imageView = (ImageView)findViewById(R.id.imageView);
//        button = (Button)findViewById(R.id.buttonLoadPicture);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
//            }
//        });
        setContentView(R.layout.activity_secretactivity);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);


        InputImage image;
        try {
            //image = InputImage.fromFilePath(this, Uri.fromFile(new File("/storage/self/primary/Pictures/testimage.png")));
            image = InputImage.fromFilePath(this, Uri.fromFile(new File("/storage/self/primary/Pictures/testimage.png")));
            //image = InputImage.fromFilePath(this, Uri.parse("https://www.google.com.sg/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F178103360251844589%2F&psig=AOvVaw1TMNj2KZuSkMSURNQBPUBI&ust=1642085641742000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCJCDiu-7rPUCFQAAAAAdAAAAABAD"));
            //Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), R.drawable.testimage);
            //image = InputImage.fromBitmap(b, 0);
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    Log.d("fuck", "it work");
                                    String resultText = visionText.getText();
                                    for (Text.TextBlock block : visionText.getTextBlocks()) {
                                        String blockText = block.getText();
                                        Point[] blockCornerPoints = block.getCornerPoints();
                                        Rect blockFrame = block.getBoundingBox();
                                        for (Text.Line line : block.getLines()) {
                                            String lineText = line.getText();
                                            Point[] lineCornerPoints = line.getCornerPoints();
                                            Rect lineFrame = line.getBoundingBox();
                                            for (Text.Element element : line.getElements()) {
                                                String elementText = element.getText();
                                                Point[] elementCornerPoints = element.getCornerPoints();
                                                Rect elementFrame = element.getBoundingBox();
                                                //whatever you want to do with the information

                                                Log.d("line it works", elementText);
                                            }
                                        }
                                    }


                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Task failed with an exception
                                            // ...
                                            Log.d("fuck", "it did not work1");
                                        }
                                    });
        } catch (IOException e) {
            Log.d("fuck", "it did not work2");
            e.printStackTrace();
        }

    }


}