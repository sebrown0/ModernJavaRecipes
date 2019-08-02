/**
 * 
 */
package chapter10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;


/**
 * Basic class to supply a stream of names from a text file.
 * 
 * @author Steve Brown
 *
 */
public class NameSupplier implements Supplier<Stream<String>> {

  public static final Path FILE_LOCATION = Paths.get(System.getProperty("user.dir") + "\\resource\\names");
  
  @Override
  public Stream<String> get() {
    try {
      return Files.lines(FILE_LOCATION);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
