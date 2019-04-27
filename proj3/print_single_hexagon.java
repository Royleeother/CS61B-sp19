public class print_single_hexagon {
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
        singleHexagon(5);
        singleHexagon(3);
        singleHexagon(2);
    }
}     
    