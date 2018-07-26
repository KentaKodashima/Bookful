package kentakodashima.com.bookrecord.viewcontroller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import io.realm.Realm;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;

public class RecordDetailActivity extends AppCompatActivity {

  private ImageView bookImage;
  private TextView bookTitle;
  private TextView bookAuthor;
  private TextView bookDescription;
  private TextView bookReview;

  private String receivedRecordKey;
  private Record record;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record_detail);

    bookImage = findViewById(R.id.book_image);
    bookTitle = findViewById(R.id.title_text);
    bookAuthor = findViewById(R.id.autor_text);
    bookDescription = findViewById(R.id.description_text);
    bookReview = findViewById(R.id.review_text);

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

//    getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    startActivity(new Intent(RecordDetailActivity.this, MainActivity.class));
    finish();
  }
}
