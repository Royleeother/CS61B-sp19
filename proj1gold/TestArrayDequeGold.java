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

    int a = -10;
    int b = 10;
    int loopVal = 10;
    int temp;
    int temp1;

    @Test  //StudentArrayDeque and ad
    public void testAddLast() {
        // initialize an Deque
        for (Integer i = 0; i < 10; i += 1) {
            int num = StdRandom.uniform(a, b) ;
            stud1.addFirst(num);
            solu.addFirst(num);
        }

        for (int i = 0; i < 10; i += 1) {
            if (!stud1.isEmpty() & !solu.isEmpty()) {
                assertEquals(stud1.removeFirst(), solu.removeFirst());
            }
        }

        for (Integer i = 0; i < 10; i += 1) {
            int num = StdRandom.uniform(a, b) ;
            stud1.addLast(num);
            solu.addLast(num);
        }

        for (int i = 0; i < 10; i += 1) {
            if (!stud1.isEmpty() & !solu.isEmpty()) {
                temp = solu.removeLast();
                temp1 = stud1.removeLast();
                assertEquals(temp, temp1);
                //assertEquals(solu.removeLast(), stud1.removeLast());
            }
        }
    }

    @Test
    public void RandomizedTesting() {
        for (int i = 0; i < loopVal; i++) {
            int item = StdRandom.uniform(10);
            stud1.addLast(item);
            solu.addLast(item);
        }

        String msg = helper(stud1);
        for (int i = 0; i < loopVal; i++) {
            Integer removed = stud1.removeLast();
            Integer removed1 = solu.removeLast();
            msg = helper1(msg);
            assertEquals(msg, removed1, removed);
        }
    }

    private String helper(StudentArrayDeque <Integer> array) {
        String msg = "";
        for (int i = 0; i < loopVal; i ++) {
            msg = msg + "addFirst(" + array.get(i).toString() + ")\n";
        }

        return msg;
    }

    private String helper1(String msg) {
        return msg + "removeLast()\n";
    }


}
