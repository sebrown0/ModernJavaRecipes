/**
 * 
 */
package chapter9.BaseballScores;

import static chapter9.BaseballScores.GamePageLinkSupplier.BASE;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Steve Brown (modified version of Ken Kousen's code)
 *
 */
public class BoxscoreRetriever implements Function<List<String>, List<Result>>{

  private OkHttpClient client = new OkHttpClient();
  private Gson gson = new Gson();
  
  public Optional<Result> gamePatternToResult(String pattern){
    Request request = new Request.Builder()
        .url(getBoxscoreURL(pattern))
        .build();
        
    try (Response response = client.newCall(request).execute()){
      if(!response.isSuccessful()) { 
        return Optional.empty(); 
      }
      
      return Optional.ofNullable(gson.fromJson(response.body().charStream(), Result.class));
      
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  public static String getBoxscoreURL(String pattern) {
    String [] yearAndMonth = pattern.split("_");
    String dateURL = String.format("year_%s/month_%s/", yearAndMonth[2], yearAndMonth[3]);
    return  BASE + dateURL + pattern +  "boxscore.json";
  }
  
  @Override
  public List<Result> apply(List<String> strings) {
    return strings.parallelStream()
        .map(this::gamePatternToResult)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }
}
