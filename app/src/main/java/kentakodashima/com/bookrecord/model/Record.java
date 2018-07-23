package kentakodashima.com.bookrecord.model;

import android.content.Context;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Record extends RealmObject {
  @PrimaryKey
  @Required
  private String recordKey;

  private String title;
  private String author;
  private String description;
  private String review;
  private String imageName;

  public Record() {

  }

  public Record(String title, String author, String description, String review) {
    this.title = title;
    this.author = author;
    this.description = description;
    this.review = review;
    // Assign a random key
    this.recordKey = UUID.randomUUID().toString();
  }

  public String getRecordKey() {
    return recordKey;
  }

  public void setRecordKey(String recordKey) {
    this.recordKey = recordKey;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public RealmResults<Record> getRecords(Context context) {
    Realm.init(context);
    Realm realm = Realm.getDefaultInstance();

    return realm.where(Record.class).findAll();
  }
}
