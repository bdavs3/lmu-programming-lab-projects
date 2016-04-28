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

    private Region[] regions;
    private int index, color;

    //INSTEAD OF USING COLORS 0-3 I AM USING 1-4 TO MAKE IT CLEAR THAT THERE ARE 4 COLORS
    public void colorMap() {
        regions = Region.getAllRegionsAsArray();
        index = 0;
        color = 1;
        while (index < regions.length) {
            for (int i = color; i <= 4; i++) {
                if (regions[index].canColorWith(i)) {
                    regions[index].setColor(i);
                    i = 5;
                }
            }

            if (regions[index].getColor() == null) {
                index--;
                if (index < 0) {
                    System.out.println("IMPOSSIBLE MAP");
                    System.exit(0);
                }
                color = regions[index].getColor() + 1;
                regions[index].removeColor();
            } else {
                index++;
                color = 1;
            }
        }
    }

    public void outputResults() {
        System.out.println("");
        for (Region r : regions) {
            System.out.println(r.getName() + ":" + r.getColor());
        }
    }

    public void readMapFromStandardInput() {
        new BufferedReader(new InputStreamReader(System.in))
            .lines()
            .forEach(line -> {
                System.out.println(line);
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
        mapColorer.outputResults();
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
