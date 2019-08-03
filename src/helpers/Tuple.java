package helpers;

import helpers.BlogPost.BlogPostType;

/**
 * A record of Blog post and author.
 * 
 * @author Steve Brown
 *
 */
public class Tuple {
  private BlogPostType type;
  private String author;
  
  public Tuple(BlogPostType type, String author) {
    this.type = type;
    this.author = author;
  }

  public BlogPostType getType() {
    return type;
  }

  public void setType(BlogPostType type) {
    this.type = type;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
