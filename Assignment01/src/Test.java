import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void test(String... args) {
        for(String arg : args) {
            System.out.println(arg);
        }
    }

    public static void main(String[] args) {
        FileReader fis = null;
        try {
            fis = new FileReader("temp.dat");
            char[] chars = new char[4];
            int countRead = 0;
            String loadedMap = "";
            while((countRead = fis.read(chars)) != -1){
                loadedMap = loadedMap + new String(chars,0,countRead);
            }
            Scanner scanner = new Scanner(loadedMap);

            Map map = new Map(7,3);
            map.setMap(scanner);
            map.printMap();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
