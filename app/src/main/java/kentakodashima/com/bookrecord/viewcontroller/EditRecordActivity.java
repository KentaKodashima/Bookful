package kentakodashima.com.bookrecord.viewcontroller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import io.realm.Realm;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;

public class EditRecordActivity extends AppCompatActivity {

  private ImageView bookImage;
  private EditText bookTitle;
  private EditText bookAuthor;
  private EditText bookDescription;
  private EditText bookReview;
  private Button saveButton;

  private String titleString;
  private String authorString;
  private String descriptionString;
  private String reviewString;

  private Bitmap selectedImage;
  private String imageFilePathString;

  private String receivedRecordKey;
  private Record record;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_record);

    bookImage = findViewById(R.id.edit_book_image);
    bookTitle = findViewById(R.id.edit_book_title_field);
    bookAuthor = findViewById(R.id.edit_autor_field);
    bookDescription = findViewById(R.id.edit_description_field);
    bookReview = findViewById(R.id.edit_review_field);
    saveButton = findViewById(R.id.edit_save_button);

    Intent intent = getIntent();
    receivedRecordKey = intent.getStringExtra("recordKey");

    Realm.init(this);
    Realm realm = Realm.getDefaultInstance();
    record = realm.where(Record.class).equalTo("recordKey", receivedRecordKey).findFirst();

    File imageFile = new File(record.getImageName());

    if(imageFile.exists()) {
      Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
      bookImage.setImageBitmap(imageBitmap);
    }
    bookTitle.setText(record.getTitle());
    bookAuthor.setText(record.getAuthor());
    bookDescription.setText(record.getDescription());
    bookReview.setText(record.getReview());

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void saveChangeButton() {
    if (!isFieldEmpty()) {
      Realm realm = Realm.getDefaultInstance();

      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          record.setTitle(titleString);
          record.setAuthor(authorString);
          record.setDescription(descriptionString);
          record.setReview(reviewString);
        }
      });

    } else {

    }
  }

  private boolean isFieldEmpty() {

    boolean empty = false;
    if (bookTitle.getText() == null ||
            bookAuthor.getText() == null ||
            bookAuthor.getText() == null ||
            bookReview.getText() == null) {
      empty = true;
    }
    return empty;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 0 && resultCode == RESULT_OK) {
      try {
        Uri uri = data.getData();
        selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

        bookImage.setImageBitmap(selectedImage);

      } catch (Exception e) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        e.printStackTrace();
      }
    }
  }

  private void saveImageData(Bitmap image) {
    // Generate random image name
    String fileName = UUID.randomUUID().toString() + ".png";
    File fileDirectory = this.getFilesDir();

    File imageFile = new File(fileDirectory, fileName);
    String imageFilePath = imageFile.getAbsolutePath().toString();

    imageFilePathString = imageFilePath;

    try {
      OutputStream stream = new FileOutputStream(imageFile);
      image.compress(Bitmap.CompressFormat.PNG,100, stream);
      stream.flush();
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
