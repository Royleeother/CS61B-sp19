package es.datastructur.synthesizer;
import edu.princeton.cs.introcs.StdAudio;
import java.nio.Buffer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        double capacity = SR / frequency;
        int kp = (int) Math.round(capacity);
        buffer = new ArrayRingBuffer<>(kp);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other.
        // (int)(Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
        int size = buffer.fillCount();
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        double res = 0;
        double num = 0;
        for (int i = 0; i < buffer.capacity(); i += 1) {
            num = Math.random() - 0.5;
            while (res == num) {
                num = Math.random() - 0.5;
            }
            res = num;
            buffer.enqueue(num);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double front = buffer.dequeue();
        double newDouble = (front + buffer.peek()) * 0.5 * DECAY;
        buffer.enqueue(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.peek();
    }
}
    // TODO: Remove all comments that say TODO when you're done.
