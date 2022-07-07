package io.github.kahar;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        List<String> map = Arrays.asList(
                "####################################################",
                "#                 ####         ****              ###",
                "#   *  @  ##                 ########       OO    ##",
                "#   *    ##        O O                 ****       *#",
                "#       ##*                        ##########     *#",
                "#      ##***  *         ****                     **#",
                "#* **  #  *  ***      #########                  **#",
                "#* **  #      *               #   *              **#",
                "#     ##              #   O   #  ***          ######",
                "#*            @       #       #   *        O  #    #",
                "#*                    #  ######                 ** #",
                "###          ****          ***                  ** #",
                "#       O                        @         O       #",
                "#   *     ##  ##  ##  ##               ###      *  #",
                "#   **         #              *       #####  O     #",
                "##  **  O   O  #  #    ***  ***        ###      ** #",
                "###               #   *****                    ****#",
                "####################################################");

        World world = new World(map);

        Thread.sleep(2000);
        for (int i = 0; i < 30000; i++) {
            world.turn();
            if (i % 10 == 0) {
                System.out.print("\n\n\n\n\n---------------------\n\n\n\n\n");
                System.out.println(world);
            }
        }
    }
}
