import java.util.*;

//答案用的好像是 Map？？
//不知道
//肚子好饿

public class MyTrieSet implements TrieSet61B {
    private static final int R = 256;
    private Node root;
    //private HashMap map;

    private static class Node{
        char ch;
        private boolean isKey;
        //private  Node[] next = new Node[R]; //DataIndexArray
        private HashMap map;

        private Node(char c, boolean b) {
            this.ch = c;
            this.isKey = b;
            //this.next = new Node[R];
            this.map = new HashMap(R);
        }
    }

    public MyTrieSet() {
        root = new Node('0', true);
        clear();
    }

    @Override
    public void clear() {
        root.ch = '~';
        root.isKey = true;
        root.map = new HashMap(R);
    }
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            } else {
                curr = (Node) curr.map.get(c);
            }
        }
        return curr.isKey;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = (Node) curr.map.get(c);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null || prefix.length() < 1) {
            return null;
        }
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (curr.map.containsKey(c)) {
                curr = (Node) curr.map.get(c);
            } else {
                return null;
            }
        }
        prefix_helper(curr, prefix);
        return haha;
    }
    private List<String> haha = new ArrayList<String>();
    private void prefix_helper(Node curr, String prefix) {
        if (!curr.map.isEmpty()) {
            for (Object i : curr.map.keySet()) {
                Node jj = (Node) curr.map.get(i);  //要换一个写法！！！
                if (jj.isKey) {
                    haha.add(prefix + jj.ch);
                }
                prefix_helper(jj, prefix + jj.ch);
            }
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
