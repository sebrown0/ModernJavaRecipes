package chapter9.BaseballScores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author Steve Brown (modified version of Ken Kousen's code)
 *
 */
public class Parser {

  private String outputDir;
    
  public Parser(String outputDir) {
    this.outputDir = outputDir;
    makeOutputDir();
  }

  private void makeOutputDir() {
    File dir = new File(outputDir);
    dir.mkdir();  
  }
  
  public void saveResultList(List<Result> results) {
    results.parallelStream().forEach(this::saveResultToFile);
  }
  
  public void saveResultToFile(Result result) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
  
    try {
      File file = new File(createFileName(result));
      Files.write(file.toPath(), gson.toJson(result).getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String createFileName(Result result) {
    Boxscore boxscore = result.getData().getBoxscore();    
    String fileName = String.format("%s_%s_at_%s.txt", 
        boxscore.getDate(), boxscore.getAwayFname(), boxscore.getHomeFname());

    return outputDir + "\\" + fileName.replaceAll(",", "").replaceAll("\\s+", "_");
  }
  
  public OptionalInt maxScore(List<Result> results) {
    return results.stream()
        .mapToInt(this::getTotalScore)
        .max();
  }
  
  public Optional<Result> maxGame(List<Result> results) {
    return results.stream().max(Comparator.comparingInt(this::getTotalScore));
  }
  
  private int getTotalScore(Result result) {
    Boxscore boxscore = result.getData().getBoxscore();
    String awayRuns = boxscore.getLinescore().getAwayTeamRuns();
    String homeRuns = boxscore.getLinescore().getHomeTeamRuns();
    return Integer.parseInt(homeRuns) + Integer.parseInt(awayRuns);
  }
}
