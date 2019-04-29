package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 45;

    private static final long SEED = 19990724; // MY B-DAY
    private static final Random RANDOM = new Random(SEED);

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param x the bottom left coordinate of the hexagon by x coordinate
     * @param y the bottom left coordinate of the hexagon by y coordinate
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, int x,int y, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        singleHexagon(world, x, y, s, t);
    }

    // draw a character repetitively sth time
    private static void drawChar(TETile[][] world, int x, int y, int s, TETile t) {
        if (s > 0) {
            world[x][y] = t;
            drawChar(world, x += 1, y, s -= 1, t);
        }
    }

    // consider the space
    private static void drawLine(TETile[][] world, int x,int y, int s, int space_fac, TETile t) {
        drawChar(world, x + space_fac, y, s, t);
    }

    private static void singleHexagon(TETile[][] world, int x,int y, int s,
                                      TETile t) {
        int half = s;
        int length = s * 2;
        int space_factor = s - 1;

        for (int i = 1; i <= length; i += 1) {
            drawLine(world, x, y, s, space_factor, t);
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

    /**
     * @param s size of each hexagons
     *
     * @attention: ~ You are not allow the set the size of the hexagon as large as you want,
     *  otherwise you will encounter a out of length exception, since it is constrained by the
     *  width and height of the grid.
     *  ~ Perhaps I should add a exception condition at the front,
     *  but it's kind of tricky to calculate the factor via WIDTH,HEIGHT,SIZE...(I AM LAZY...and STUPID)
     *  Or add a method that could detect the size that user input, then resize the grid
     *  accordingly, that is.
     *  I am proud of myself, hhhhhaaaaa.
     */
    private static void generateHexagon(TETile[][] world, int s) {
        // I want to display it as middle as possible
        int x = world.length / 6;
        int y = world[0].length / 4;

        // by column, 3-4-5-4-3, respectively
        for (int i = 3; i <= 5; i++) {
            drawRandomVerticalHexes(world, s, x, y, i);
            if (i == 5) {
                x = RightNeighbor_X(x, s);
                y = topRightNeighbor_Y(y, s); // adjust the position, prepare for next round
                break;
            }
            x = RightNeighbor_X(x, s);
            y = bottomRightNeighbor_Y(y, s);
        }
        for (int i = 4; i > 2; i--) {
            drawRandomVerticalHexes(world, s, x, y, i);
            x = RightNeighbor_X(x, s);
            y = topRightNeighbor_Y(y, s);
        }

    }

    private static void drawRandomVerticalHexes(TETile[][] world, int s, int x, int y, int num) {
        int upward_factor = s * 2;
        for (int i = 0; i < num; i++) {
            TETile t = randomTile();
            addHexagon(world, x, y, s, t);
            y += upward_factor;
        }
    }

    private static int RightNeighbor_X(int x, int s) {
        int span = s - 1; // span is the number of stair shapes ,你懂得.
        x += s + span;
        return x;
    }
    private static int topRightNeighbor_Y(int y, int s) {
        y += s;
        return y;
    }
    private static int bottomRightNeighbor_Y(int y, int s) {
        y -= s;
        return y;
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile background = Tileset.FLOOR;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = background;
            }
        }
        //drawChar(world, 5, 2, 5, Tileset.WATER);
        //drawLine(world, 0, 0, 2, 1, Tileset.WATER);
        //singleHexagon(world, 0, 0, 4, Tileset.WATER);
        //singleHexagon(world, 0, 8, 4, Tileset.WATER);
        //singleHexagon(world, 10 - 3, 4, 4, Tileset.FLOWER);
        //drawRandomVerticalHexes(world, 3, 0, 0, 3);
        generateHexagon(world, 4);
        // by column, 3-4-5-4-3, respectively

        ter.renderFrame(world);
    }
}
