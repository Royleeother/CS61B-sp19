package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private List<Point> points;
    private Map<Point, Node> pointToNode;
    private MyTrieSet trieSet;
    private Map<String, List<Node>> nameToNode; // smart strategy to avoid duplicates

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes();
        points = new ArrayList<>();
        pointToNode = new HashMap<>();
        trieSet = new MyTrieSet();
        nameToNode = new HashMap<>();

        for (Node node : nodes) {
            if (this.neighbors(node.id()).size() > 0) {
                Point p = new Point(node.lon(), node.lat());
                points.add(p);
                pointToNode.put(p, node);
            }

            if (node.name() != null) {
                String cleanName = cleanString(node.name());
                trieSet.add(cleanName);
                if (!nameToNode.containsKey(cleanName)) {
                    nameToNode.put(cleanName, new LinkedList<>());
                }
                nameToNode.get(cleanName).add(node);
                /*
                Map<String, List<String>> map = new HashMap<>();
                map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value1");
                map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value2");

                assertThat(map.get("key1").get(0)).isEqualTo("value1");
                assertThat(map.get("key1").get(1)).isEqualTo("value2");
                 */
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        /*
        ~遍历信息中的 node节点
        ~把有邻居的node添加到 一个<Point, Node> 的哈希表
        ~把有邻居的node添加到 一个<Point> 的列表
        ~用WeirdPointSet把列表转化为PointSet
        ~用WeirdPointSet.nearest(lon, lat) 得到最近点 P
        ~根据P在哈希表内找回相应的node
        ~返回node.id()
         */
        WeirdPointSet ps = new WeirdPointSet(points);
        Point p = ps.nearest(lon, lat);
        return pointToNode.get(p).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        LinkedList<String> fullName_result = new LinkedList<>();
        List<String> names = trieSet.keysWithPrefix(cleanString(prefix)); // this is an ArrayList
        for (String s : names) {
            for (Node node : nameToNode.get(s)) {
                if (!fullName_result.contains(node.name())) {
                    fullName_result.add(node.name());
                }
            }
            // fullName_result.add(s); this is wrong, would return the clean name (lowercase)
        }
        return fullName_result;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        LinkedList<Map<String, Object>> locations = new LinkedList<>();
        String cleanName = cleanString(locationName);

        if (nameToNode.containsKey(cleanName)) {
            List<Node> locations_nodes = nameToNode.get(cleanName);
            for (Node node : locations_nodes) {
                Map<String, Object> info = new HashMap<>();
                info.put("lon", node.lon());
                info.put("lat", node.lat());
                info.put("name", node.name());
                info.put("id", node.id());
                locations.add(info);
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
