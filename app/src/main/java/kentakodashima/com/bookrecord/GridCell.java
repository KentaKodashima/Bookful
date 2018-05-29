package kentakodashima.com.bookrecord;

public class GridCell {
  private String content;
  private Integer image;

  public GridCell(String content, Integer image) {
    this.content = content;
    this.image = image;
  }

  public String getContent() {
    return content;
  }

  public int getImage() {
    return image;
  }
}
