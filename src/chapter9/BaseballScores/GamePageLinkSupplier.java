/**
 * 
 */
package chapter9.BaseballScores;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Steve Brown
 *
 */
public class GamePageLinkSupplier implements Supplier<List<String>>{

  public static final String BASE = "http://gd2.mlb.com/components/game/mlb/";    
  private LocalDate startDate;    
  private int days;
    
  public GamePageLinkSupplier(LocalDate startDate, int days) {
    this.startDate = startDate;
    this.days = days;
  }

  public List<String> getGamePageLinks(LocalDate localDate){
    try {
      Document doc = Jsoup.connect(formattedURI(localDate)).get();
      Elements links = doc.select("a");
      
      return links.stream()
          .filter(link -> link.attr("href").contains("gid"))
          .map(link -> link.attr("href"))
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String formattedURI(LocalDate localDate) {
    return String.format("%syear_%04d/month_%02d/day_%02d%n", 
        BASE, localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()); 
  }
  
  @Override
  public List<String> get() {
    return Stream.iterate(startDate, date -> date.plusDays(1))
        .limit(days)
        .map(this::getGamePageLinks)
        .flatMap(listURL -> listURL.isEmpty() ? Stream.empty() : listURL.stream())
        .collect(Collectors.toList());
  }
}
