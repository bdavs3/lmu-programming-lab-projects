import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MapColorer {

    public void colorMap() {
        // You fill this in.
        // Use System.out.println() to output the colored map.
        // See the instructions for the proper format.
        // It is okay to write private helper methods in this class.
        // If you're doing things recursively, you will need one.
        //
        // Remember your job is to systematically to work your way through
        // Region.getAllRegionsAsArray(), calling canColorWith, setColor,
        // removeColor. You will be going forward and backward through
        // the array.
    }

    public void readMapFromStandardInput() {
        new BufferedReader(new InputStreamReader(System.in))
            .lines()
            .forEach(line -> {
                String[] pair = line.trim().split(",");
                Region region0 = Region.forName(pair[0]);
                Region region1 = Region.forName(pair[1]);
                region0.addNeighbor(region1);
            });
    }

    public static void main(String[] args) {
        MapColorer mapColorer = new MapColorer();
        mapColorer.readMapFromStandardInput();
        mapColorer.colorMap();
    }
 }

/**
* This class is full of a lot of stuff you don't need to know.
*
* But don't hesitate to ask about such things if you like.
*/
class Region {

    private static Map<String, Region> allRegions = new TreeMap<>();

    public static Region forName(String name) {
        allRegions.putIfAbsent(name, new Region(name));
        return allRegions.get(name);
    }

    public static Region[] getAllRegionsAsArray() {
        return allRegions.values().toArray(new Region[allRegions.size()]);
    }

    private String name;
    private Integer color;
    private Set<Region> neighbors = new HashSet<>();

    private Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getColor() {
        return color;
    }

    public boolean canColorWith(int color) {
        return !neighbors.stream().anyMatch(n -> Objects.equals(n.color,color));
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void removeColor() {
        this.color = null;
    }

    public Set<Region> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Region region) {
        Objects.requireNonNull(region);
        neighbors.add(region);
        region.neighbors.add(this);
    }
}
