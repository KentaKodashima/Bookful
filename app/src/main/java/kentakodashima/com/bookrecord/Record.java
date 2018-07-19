package kentakodashima.com.bookrecord;

import java.net.URI;

import io.realm.RealmObject;
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
  private String imageURI;

  public Record() {

  }

  public Record(String title, String author, String description, String review) {
    this.title = title;
    this.author = author;
    this.description = description;
    this.review = review;
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

  public String getImageURI() {
    return imageURI;
  }

  public void setImageURI(String imageURI) {
    this.imageURI = imageURI;
  }
}
