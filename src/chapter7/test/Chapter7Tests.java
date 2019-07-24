package chapter7.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import global_data.GlobalTestData;

class Chapter7Tests {

  @Test
  void the10longestLinesInShakespeare() {
    try (Stream<String> lines = Files.lines(Paths.get(GlobalTestData.SHAKESPEARE_FILE))) {
      List<String> longestLines = lines
        .filter(l -> l.length() > 10)
        .sorted(Comparator.comparing(String::length).reversed())
        .limit(10)
        .collect(Collectors.toList());
      
      assertEquals(10, longestLines.size());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  @Test
  void listFiles() {
    try (Stream<Path> paths = Files.list(Paths.get(""))) {
      List<String> pathNames = paths.map(p -> p.getFileName().toString()).collect(Collectors.toList());
      assertTrue(pathNames.contains("src"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void pathWalk() {
    final int TRAVERSAL_DEPTH = 3;
    try (Stream<Path> paths = Files.walk(Paths.get(""), TRAVERSAL_DEPTH, FileVisitOption.FOLLOW_LINKS)){
      String result = paths
          .map(p -> p.toString())
          .filter(p -> p.contains("src\\chapter7"))
          .findFirst().orElse("");
      assertEquals("src\\chapter7", result);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  void pathFind() {
    final int TRAVERSAL_DEPTH = 3;
    try (Stream<Path> paths = 
        Files.find(Paths.get(""), TRAVERSAL_DEPTH, 
            (path, attr) -> attr.isRegularFile() && path.toString().contains("Golfer.java"))){
      String result = paths.map(p -> p.toString()).findFirst().orElse("");
      assertEquals("src\\helpers\\Golfer.java", result);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}
