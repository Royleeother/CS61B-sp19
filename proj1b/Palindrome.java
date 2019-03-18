public class Palindrome {
    public Deque wordToDeque(String word) {
        LinkedListDeque<String> dq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            dq.addLast(Character.toString(word.charAt(i)));
        }
        return dq;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) { return true; }
        Deque dq =  wordToDeque(word);
        LinkedListDeque<String> reverse = new LinkedListDeque<>();
        for (int i = word.length(); i > 0; i -= 1) {
            reverse.addLast(Character.toString(word.charAt(i - 1)));
        }
        for (int i = 0; i < dq.size(); i++) {
            if (!dq.get(i).equals( reverse.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deque<Character> worddeque = wordToDeque(word);
     *
     *         if (worddeque.size() == 0 || worddeque.size() == 1) {
     *             return true;
     *         } else {
     *
     *             if (worddeque.removeFirst() == worddeque.removeLast()) {
     *                 return isPalindrome(DequeToString(worddeque));
     *             } else {
     *                 return false;
     *             }
     *         }
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque dq = wordToDeque(word);
        int len = dq.size();
        for (int i = 0; i < len / 2; i++) {
            char a = ((dq.get(i)).toString()).charAt(0);
            char b = ((dq.get(len - i - 1)).toString()).charAt(0);
            if (!cc.equalChars(a, b)) {
                return false;
            }
        }
        return true;
    }
}

