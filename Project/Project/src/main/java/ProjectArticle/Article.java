/*
    RMIT University Vietnam
    Course: INTE2512 Object-Oriented Programming
    Semester: 2021B
    Assessment: Final Project
    Author:
    - Pham Duy Anh - s3802674
    - Pham Dang Khoa - s3884419
    - Nguyen Minh Hien - s3877996
    - Nathan Candre - s3938364
    Acknowledgement:
    [1]: https://jsoup.org/cookbook/extracting-data/selector-syntax
    [2]: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
    [3]: https://www.tutorialspoint.com/javafx/javafx_css.htm
    [4]: https://www.javatpoint.com/javafx-playing-video
    [5] All lecture and lab slides from RMIT univeristy
*/
package ProjectArticle;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

public class Article {
  private String date;
  private String title;
  private String timeDuration;
  private String pageCategory;
  private String linkToArticle;
  private String category;
  private String description;
  private String source;
  private String author;
  private String thumbnail;

  public Article(String date, String title, String timeDuration, String pageCategory, String linkToArticle, String category, String description, String source, String author, String thumbnail) {
    setDate(date);
    setTitle(title);
    setTimeDuration(timeDuration);
    setPageCategory(pageCategory);
    setLinkToArticle(linkToArticle);
    setCategory(category);
    setDescription(description);
    setSource(source);
    setAuthor(author);
    setThumbnail(thumbnail);
  }

  public Article(){
    this.date = "";
    this.title = "";
    this.timeDuration = "";
    this.pageCategory = "";
    this.linkToArticle = "";
    this.category = "";
    this.description = "";
    this.source = "";
    this.author = "";
    this.thumbnail = "";
  }

  //Setter
  public void setDate(String date) {
    this.date = date;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public void setTimeDuration(String timeDuration) {
    this.timeDuration = timeDuration;
  }
  public void setPageCategory(String pageCategory) {
    this.pageCategory = pageCategory;
  }
  public void setLinkToArticle(String link) {
    this.linkToArticle = link;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public void setSource(String source) {
    this.source = source;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  //Getter
  public String getDate() {
    return this.date;
  }
  public String getTimeDuration() {
    return this.timeDuration;
  }
  public String getTitle() {
    return this.title;
  }
  public String getPageCategory() {
    return this.pageCategory;
  }
  public String getLinkToArticle() {
    return this.linkToArticle;
  }
  public String getCategory() {
    return this.category;
  }
  public String getDescription() {
    return this.description;
  }
  public String getSource() {
    return this.source;
  }
  public String getAuthor() {
    return this.author;
  }
  public String getThumbnail() {
    return this.thumbnail;
  }
}