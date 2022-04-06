import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] s1 = scanner.nextLine().split("=");
        StringBuilder s2 = new StringBuilder(s1[1]);
        for (int i=1 ; i<s2.length()-1 ; i++){
            if (Character.toString(s2.charAt(i)).compareTo("[") == 0){
                int j = 1;
                while (j <s2.length()-1){
                    if (Character.toString(s2.charAt(i+j)).matches(0-9)){

                    }
                }
            }
        }
        int[][] array = new int[][];
        int counter = 0;
        for (int i=0; i<array.length;i++){
            for (int j=0;j<array[i].length;j++){
                if (array[i][j]==1){
                    for (int k:array[i]){
                        if (k=1){
                            counter++;
                            break;
                        }
                    }
                }
            }
        }
    }
}

