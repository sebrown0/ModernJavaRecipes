package chapter8;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @author Steve Brown
 *
 */
public class ZoneUtility {

  private static Instant instant = Instant.now();
  
  private ZoneUtility() {}
 
  /**
   * Get all the Zones and their offsets that have non-integral offsets.
   * 
   * @return map of zone id and the offset. 
   */
  public static Map<String, ZoneOffset> MapZonesWithIrregularOffsets() {
        
    return ZoneId.getAvailableZoneIds().stream()
        .map(ZoneId::of)
        .filter(zoneId -> {
          ZoneOffset offset = instant.atZone(zoneId).getOffset();
          return offset.getTotalSeconds() % 3600 != 0;
        })
        .sorted(Comparator.comparingInt(zoneId -> instant.atZone(zoneId).getOffset().getTotalSeconds()))
        .collect(Collectors.toMap(ZoneId::getId, z -> instant.atZone(z).getOffset()));
  }
  
  /**
   * Get a list of region names for a specified offset in hours and minutes.
   * 
   * @param hours   : offset parameter.
   * @param minutes : offset parameter.
   * @return a list of regions.
   */
  public static List<String> getRegionNamesForOffset(int hours, int minutes) {
    ZoneOffset offset = ZoneOffset.ofHoursMinutes(hours, minutes);
    return getRegionNamesForOffset(offset);
  }
  
  /**
   * Get a list of region names for a specified offset, i.e +05.45.
   * 
   * @param offset : The offset to look for.
   * @return a list of regions.
   */
  public static List<String> getRegionNamesForOffset(ZoneOffset offset) throws DateTimeException {    
    return ZoneId.getAvailableZoneIds().stream()
        .map(ZoneId::of)
        .filter(zoneId -> instant.atZone(zoneId).getOffset().equals(offset))
        .map(ZoneId::getId)
        .collect(Collectors.toList());    
  }
  
  /**
   * Get a zone offset for a given zone.
   * 
   * @param zoneId : the id of the zone.
   * @return the offset for the zone.
   */
  public static String getOffsetForZone(String zoneId) {
    return instant.atZone(ZoneId.of(zoneId)).getOffset().toString(); 
  }
  
  /**
   * Get a zone offset for a given zone.
   * 
   * @param zoneId : the id of the zone.
   * @return the offset for the zone.
   */
  public static String getOffsetForZone(ZoneId zoneId) {
    return instant.atZone(zoneId).getOffset().toString(); 
  }
}
