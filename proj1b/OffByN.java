public class OffByN implements CharacterComparator {
    private int gap;
    // public void OffByN(int N)  consturctor should never have void
    public OffByN(int N) {
        gap = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == gap);
    }
}
