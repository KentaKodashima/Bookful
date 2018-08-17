package kentakodashima.com.bookrecord.viewcontroller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import javax.annotation.Nullable;

import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
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
  private File imageFile;

  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record_detail);

    bookImage = findViewById(R.id.book_image);
    bookTitle = findViewById(R.id.title_text);
    bookAuthor = findViewById(R.id.autor_text);
    bookDescription = findViewById(R.id.description_text);
    bookReview = findViewById(R.id.review_text);

    // Retrieve the RealmObject from previous activity
    Intent intent = getIntent();
    receivedRecordKey = intent.getStringExtra("recordKey");

    // Initialize Realm database
    Realm.init(this);
    realm = Realm.getDefaultInstance();
    record = realm.where(Record.class).equalTo("recordKey", receivedRecordKey).findFirst();

    // Retrieve data from the RealmObject
    if (record.getImageName() != null) {
      imageFile = new File(record.getImageName());
      if(imageFile.exists()) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        bookImage.setImageBitmap(imageBitmap);
      }
    }
    bookTitle.setText(record.getTitle());
    bookAuthor.setText(record.getAuthor());
    bookDescription.setText(record.getDescription());
    bookReview.setText(record.getReview());

    record.addChangeListener(new RealmObjectChangeListener<Record>() {
      @Override
      public void onChange(Record record, @Nullable ObjectChangeSet changeSet) {
        if (changeSet.isFieldChanged("title")) {
          bookTitle.setText(record.getTitle());
        } else if (changeSet.isFieldChanged("author")) {
          bookAuthor.setText(record.getAuthor());
        } else if (changeSet.isFieldChanged("description")) {
          bookDescription.setText(record.getDescription());
        } else if (changeSet.isFieldChanged("review")) {
          bookReview.setText(record.getReview());
        }
      }
    });

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_actions, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      case R.id.action_edit:
        Intent intent = new Intent(this, EditRecordActivity.class);
        String recordKey = record.getRecordKey();
        intent.putExtra("recordKey", recordKey);
        this.startActivity(intent);
        return true;
      case R.id.action_delete:
        deleteRecord();
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void deleteRecord() {

    File imageFile = new File(record.getImageName());
    if (imageFile.exists()) {
      imageFile.delete();
    }
    realm = Realm.getDefaultInstance();
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        record.deleteFromRealm();
      }
    });
  }
}
