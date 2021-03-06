package sg.edu.np.onetokenocbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class backNRIC extends AppCompatActivity {
    ImageView imageView;
    TextView error;
    Button button;
    private static final int PICK_IMG_REQUEST = 1;
    Uri mImageUri;
    List<String> content = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_back_n_r_i_c);

        this.imageView = findViewById(R.id.backNRICpic);
        this.error = findViewById(R.id.errorBackIC);
        this.button = findViewById(R.id.uploadBackNRIC);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

            mImageUri = data.getData();
            InputImage image;
            try {
                content.clear();
                //image = InputImage.fromFilePath(this, Uri.fromFile(new File("/storage/self/primary/Pictures/testimage.png")));
                image = InputImage.fromFilePath(this, mImageUri);
                imageView.setImageURI(mImageUri);
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
                                                //Log.d("line it works", lineText);
                                                content.add(lineText);
                                                for (Text.Element element : line.getElements()) {
                                                    String elementText = element.getText();
                                                    Point[] elementCornerPoints = element.getCornerPoints();
                                                    Rect elementFrame = element.getBoundingBox();
                                                    //whatever you want to do with the information


                                                }
                                            }
                                        }
                                        for(int i = 0; i < content.size(); i++){
                                            Log.d("words", content.get(i));
                                        }
                                        if(content.size() == 0){
                                            error.setText("Please upload the image of the back of the NRIC");
                                        }
                                        else if(!content.get(2).toLowerCase().contains("date of issue")){
                                            error.setText("Please re-upload the image of the back of the NRIC");
                                        }
                                        else{
                                            error.setText("");
                                            Bundle extras = new Bundle();
                                            Intent receive = getIntent();
                                            extras.putString("UserType", "New");
                                            extras.putString("NRIC", receive.getStringExtra("NRIC"));
                                            extras.putString("Name", receive.getStringExtra("Name"));
                                            extras.putString("Race", receive.getStringExtra("Race"));
                                            extras.putString("DOB", receive.getStringExtra("DOB"));
                                            extras.putString("Sex", receive.getStringExtra("Sex"));
                                            extras.putString("Address",content.get(5) + content.get(6));
                                            extras.putString("PostalCode",content.get(7).substring(content.get(7).length() - 6));
                                            Intent i = new Intent(backNRIC.this, createJointMainHolder.class);
                                            i.putExtras(extras);
                                            backNRIC.this.startActivity(i);
////                                            extras.putString("Username", chatUser.getUsername());
//                                            extras.putString("UID", chatUser.getUID());
//                                            extras.putString("imagePFP", chatUser.getProfilePic());
//                                            Intent i = new Intent(context, MessagePart.class);
//                                            i.putExtras(extras);
//                                            context.startActivity(i);

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
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMG_REQUEST);
    }
}