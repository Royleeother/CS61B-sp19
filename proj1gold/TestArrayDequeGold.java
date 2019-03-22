import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    /**
     * Your test should randomly call StudentArrayDeque
     * and ArrayDequeSolution methods
     * until they disagree on an output.
     */
    StudentArrayDeque<Integer> stud1 = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> solu = new ArrayDequeSolution<>();

    @Test  //StudentArrayDeque and ad
    public void testAddLast() {
        // initialize an Deque
        int a = -10;
        int b = 10;
        Integer tempt;
        Integer tempt1;
        for (Integer i = 0; i < 5; i += 1) {
            //int numberBetweenZeroAndOne = StdRandom.uniform(a, b) ;
            stud1.addFirst(i);
            solu.addFirst(i);
        }

        for (int i = 0; i < 5; i += 1) {
            if (!stud1.isEmpty() & !solu.isEmpty()) {
                Integer num1 = StdRandom.uniform(a, b);
                Integer num2 = StdRandom.uniform(a, b);

                stud1.addFirst(num1);
                solu.addFirst(num1);

                assertEquals(stud1.removeFirst(), solu.removeFirst());

                stud1.addLast(num2);
                solu.addLast(num2);

                assertEquals(solu.removeLast(), stud1.removeLast());
            }
        }
    }
}
