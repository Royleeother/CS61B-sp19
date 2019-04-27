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

    /**
     * @source: yngz
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param x the bottom left coordinate of the hexagon
     * @param y
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, int x,int y, int s, TETile t, TETile buff) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
    }

    private static void drawChar(TETile[][] world, int x, int y, int s, TETile t) {
        if (s > 0) {
            world[x][y] = t;
            drawChar(world, x += 1, y, s -= 1, t);
        }
    }

    private static void drawLine(TETile[][] world, int x,int y, int s, int spac_fac,
                                 TETile t, TETile buff) {
        drawChar(world, x, y, spac_fac, buff);
        drawChar(world, x + spac_fac, y, s, t);
        drawChar(world, x + spac_fac + s, y, spac_fac, buff);
    }

    private static void singleHexagon(TETile[][] world, int x,int y, int s,
                                      TETile t, TETile buff) {
        int half = s;
        int length = s * 2;
        int space_factor = s - 1;

        for (int i = 1; i <= length; i += 1) {
            drawLine(world, x, y, s, space_factor, t, buff);
            if (i < half) {
                s += 2;
                space_factor -= 1;
            } else if (i > half) {
                s -= 2;
                space_factor += 1;
            }
            y += 1;

        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile background = Tileset.WALL;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = background;
            }
        }
        //drawChar(world, 5, 2, 5, Tileset.WATER);
        //drawLine(world, 0, 0, 2, 1, Tileset.WATER, Tileset.NOTHING);
        singleHexagon(world, 0, 0, 4, Tileset.WATER, Tileset.WALL);
        singleHexagon(world, 0, 8, 4, Tileset.WATER, Tileset.WALL);
        singleHexagon(world, 10 - 3, 4, 4, Tileset.FLOWER, Tileset.WATER);
        ter.renderFrame(world);
    }
}
