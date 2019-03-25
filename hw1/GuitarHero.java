import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] guitarString = new GuitarString[37];

        for (int i = 0; i < 37; i++) {
            double frequency = CONCERT_A * Math.pow(2, (i - 25) / 12);
            guitarString[i] = new GuitarString(frequency);
        }

        GuitarString string = guitarString[0];

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    string = guitarString[index];
                    string.pluck();
                }
            }
            /* compute the superposition of samples */
            double sample = string.sample();

            StdAudio.play(sample);
            System.out.println((sample));

            string.tic();
        }
    }
}
