package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void addHexagon(int s, int head_x, int head_y) {

    }
    private static void singleHexagon(int size) {
        int space_factor = size - 1;
        int half = size;

        for (int i = 1; i <= size * 2; i += 1) {
            printLine(size, space_factor, 'a', ' ');
            if (i < half) {
                size += 2;
                space_factor -= 1;
            } else if (i > half) {
                size -= 2;
                space_factor += 1;
            }
            System.out.println(' ');
        }
    }

    private static void printChar(int size, char C){
        if(size > 0){
            System.out.print(C);
            printChar(size-1, C);
        }
    }
    private static void printLine(int size, int sp_fac, char main_char, char buff) {
        printChar(sp_fac, ' ');
        printChar(size, 'a');
        printChar(sp_fac, ' ');
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.FLOWER;
            }
        }

        singleHexagon(5);
    }
}
