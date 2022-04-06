import java.util.Scanner;

/**
 * Map class can initialize a default map or generate a customized map based on given dat.
 * The two main functions are initialization and traversable judgement.
 */
public class Map {
    private int width;
    private int height;
    private String[][] map;  // First height, then width.
    private static final int DEFAULT_MAP_WIDTH = 6;
    private static final int DEFAULT_MAP_HEIGHT = 4;

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String[][] getMap(){
        return map;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    // A default constructor of map when no data provided.
    public Map(){
        this.width = DEFAULT_MAP_WIDTH;
        this.height = DEFAULT_MAP_HEIGHT;
        this.map = new String[this.height][this.width];
        this.setMap();
    }

    // A customized map constructor.
    public Map(int width, int height){
        this.width = width;
        this.height = height;
        this.map = new String[height][width];
    }

    // A default empty map generator.
    private void setMap(){
        for (int h = 0 ; h < this.height ; h++){
            for (int w = 0 ; w < this.width ; w++){
                map[h][w] = ".";
            }
        }
    }

    // A customized map loaded with given template.
    public void setMap(Scanner scanner){
        String mapLine = null;
        for (int line = 0 ; line < this.height ; line++){
            mapLine = scanner.nextLine();
            System.out.println(mapLine);
            for (int bit = 0 ; bit < this.width ; bit++){
                map[line][bit] = Character.toString(mapLine.charAt(bit));
            }
        }

    }

    // Print out Map as text.
    public void printMap(){
        for (String[] s:map){
            for (String ss:s){
                System.out.print(traversable(ss));
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean traversable(int xCoordinate, int yCoordinate){
        if (this.map[yCoordinate][xCoordinate].compareTo(".") == 0) {
            return true;
        }
        return false;
    }

    public boolean traversable(String s){
        if (s.compareTo(".") == 0){
            return true;
        }
        return false;
    }
}
