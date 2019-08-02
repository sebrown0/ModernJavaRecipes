package helpers;

public class BlogPost {

  private String title;
  private String author;
  private BlogPostType type;
  private int likes;
  
  public BlogPost(String title, String author, BlogPostType type, int likes) {
    super();
    this.title = title;
    this.author = author;
    this.type = type;
    this.likes = likes;
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
  public BlogPostType getType() {
    return type;
  }
  public void setType(BlogPostType type) {
    this.type = type;
  }
  public int getLikes() {
    return likes;
  }
  public void setLikes(int likes) {
    this.likes = likes;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BlogPost [title=");
    builder.append(title);
    builder.append(", author=");
    builder.append(author);
    builder.append(", type=");
    builder.append(type);
    builder.append(", likes=");
    builder.append(likes);
    builder.append("]");
    return builder.toString();
  }

  
}
