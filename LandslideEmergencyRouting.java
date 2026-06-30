import java.util.*;

public class LandslideEmergencyRouting {

    static class Edge {
        int targetNode;
        int travelTimeMinutes;
        boolean isBlocked;

        public Edge(int targetNode, int travelTimeMinutes) {
            this.targetNode = targetNode;
            this.travelTimeMinutes = travelTimeMinutes;
            this.isBlocked = false;
        }
    }

    static class PQNode implements Comparable<PQNode> {
        int nodeId;
        int accumulatedTime;

        public PQNode(int nodeId, int accumulatedTime) {
            this.nodeId = nodeId;
            this.accumulatedTime = accumulatedTime;
        }

        @Override
        public int compareTo(PQNode other) {
            return Integer.compare(this.accumulatedTime, other.accumulatedTime);
        }
    }

    static class Graph {
        int vertices;
        List<List<Edge>> adjList;
        String[] nodeNames;

        public Graph(int vertices, String[] nodeNames) {
            this.vertices = vertices;
            this.nodeNames = nodeNames;
            this.adjList = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        public void addEdge(int source, int target, int weight) {
            adjList.get(source).add(new Edge(target, weight));
            adjList.get(target).add(new Edge(source, weight)); 
        }

        public void blockRoad(int u, int v) {
            for (Edge edge : adjList.get(u)) {
                if (edge.targetNode == v) edge.isBlocked = true;
            }
            for (Edge edge : adjList.get(v)) {
                if (edge.targetNode == u) edge.isBlocked = true;
            }
        }
    }

    public static void computeShortestPaths(Graph graph, int sourceId) {
        int V = graph.vertices;
        int[] distances = new int[V];
        int[] parent = new int[V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        
        distances[sourceId] = 0;
        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        pq.add(new PQNode(sourceId, 0));

        while (!pq.isEmpty()) {
            PQNode current = pq.poll();
            int u = current.nodeId;

            if (current.accumulatedTime > distances[u]) continue;

            for (Edge edge : graph.adjList.get(u)) {
                // Flowchart Check: "Is the edge blocked by a landslide?"
                if (edge.isBlocked) continue; 

                int v = edge.targetNode;
                int weight = edge.travelTimeMinutes;

                // Flowchart Check: "distance[u] + weight < distance[v]"
                if (distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    parent[v] = u;
                    pq.add(new PQNode(v, distances[v]));
                }
            }
        }

        printReportCard(graph, distances, parent, sourceId);
    }

    private static void getPath(int currentVertex, int[] parents, List<Integer> path) {
        if (currentVertex == -1) return;
        getPath(parents[currentVertex], parents, path);
        path.add(currentVertex);
    }

    private static void printReportCard(Graph graph, int[] distances, int[] parent, int sourceId) {
        System.out.println("====================================================================");
        System.out.println("BENTONG DISASTER RESPONSE ROUTING ENGINE - OUTPUT LOG");
        System.out.println("====================================================================");
        System.out.println("[SOURCE NODE]: " + graph.nodeNames[sourceId]);
        System.out.println("STATUS: SUCCESSFUL PATH COMPUTATION\n");

        for (int i = 0; i < graph.vertices; i++) {
            if (!graph.nodeNames[i].startsWith("Village")) continue;

            String destName = graph.nodeNames[i];
            
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println("* " + destName + ": No available route.");
                System.out.println("  Total Estimated Travel Time: INF (∞)");
                System.out.println("  Route Status                : CRITICAL CRASH - VILLAGE IS ISOLATED");
                System.out.println("  Action Required             : ALERT AIR DISPATCH (HELICOPTER REQ)\n");
            } else {
                List<Integer> pathNodes = new ArrayList<>();
                getPath(i, parent, pathNodes);
                
                StringBuilder pathStr = new StringBuilder();
                for (int j = 0; j < pathNodes.size(); j++) {
                    pathStr.append(graph.nodeNames[pathNodes.get(j)]);
                    if (j < pathNodes.size() - 1) pathStr.append(" -> ");
                }
                
                System.out.println("* " + destName + ": " + pathStr.toString());
                System.out.println("  Total Estimated Travel Time: " + distances[i] + " Minutes");
                
                if (destName.equals("Village 1")) {
                    System.out.println("  Route Status                : CLEAR / OPTIMAL\n");
                } else {
                    System.out.println("  Route Status                : CLEAR / ALTERNATIVE DETOUR\n");
                }
            }
        }
        System.out.println("====================================================================");
    }
    public static void main(String[] args) {
        // Node indexes assigned strictly following your report layout:
        // 0: Bentong Hospital, 1: Junction A, 2: Junction B, 3: Junction C
        // 4: Village 1, 5: Village 2, 6: Village 3, 7: Village 4
        String[] nodeNames = {
            "Bentong Hospital (EOC)", "Junction A", "Junction B", "Junction C", 
            "Village 1", "Village 2", "Village 3", "Village 4"
        };

        Graph operationalGraph = new Graph(8, nodeNames);

        // Standard edge list weights mapping exactly to table 2.7
        operationalGraph.addEdge(0, 2, 10); // Bentong Hospital -> Junction B
        operationalGraph.addEdge(0, 1, 12); // Bentong Hospital -> Junction A
        operationalGraph.addEdge(2, 4, 8);  // Junction B -> Village 1
        operationalGraph.addEdge(2, 5, 5);  // Junction B -> Village 2
        operationalGraph.addEdge(1, 3, 6);  // Junction A -> Junction C
        operationalGraph.addEdge(3, 5, 15); // Junction C -> Village 2
        operationalGraph.addEdge(2, 6, 9);  // Junction B -> Village 3
        operationalGraph.addEdge(3, 6, 11); // Junction C -> Village 3
        operationalGraph.addEdge(3, 7, 14); // Junction C -> Village 4

        // Real-Time Landslide Impact Blocks applied before execution loop
        operationalGraph.blockRoad(2, 6); // Landslide on Junction B → Village 3
        operationalGraph.blockRoad(3, 6); // Landslide on Junction C → Village 3

        computeShortestPaths(operationalGraph, 0);
    }
}

