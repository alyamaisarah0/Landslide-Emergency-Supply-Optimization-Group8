# Landslide-Emergency-Supply-Optimization-Group8
CSC4202 Design and Analysis of Algorithms - Group Project Portfolio.

# 🌍 Bentong District Landslide Emergency Supply Routing Optimization
**Course:** CSC4202 Design and Analysis of Algorithms  
**Group:** Group 2 (Semester II 2025/2026)  
**Lecturer:** Dr. Nur Arzilawati Md Yunus  
**Institution:** Faculty of Computer Science and Information Technology, Universiti Putra Malaysia (UPM)

---

## 1. Problem Illustration & Scenario Development

### The Disaster Scenario
During prolonged heavy rainfall, the mountainous region of Bentong District suffers severe landslide incidents. Critical road infrastructure segments are instantly buried under debris, blocking multiple paths and leaving interior villages completely isolated. 

Emergency operations must originate from a singular headquarters—**Bentong Hospital (Emergency Operations Center - EOC)**—and navigate through a network of junctions to safely deliver medicine and relief supplies to affected locations during the critical "Golden Hour".

### Network Graph Model
The physical map topology is modeled as a weighted, undirected graph $G = (V, E)$, consisting of **8 core operational nodes**:
* `V0`: Bentong Hospital (EOC) — *The Source Node*
* `V1`: Junction A
* `V2`: Junction B
* `V3`: Junction C
* `V4`: Village 1
* `V5`: Village 2
* `V6`: Village 3
* `V7`: Village 4

The travel time between locations represents edge weights (in minutes). 

### The Core Challenge & Optimization Importance
When a crisis hits, rescue dispatchers experience high cognitive overload. Human guesswork often routes vehicles toward blocked paths or highly congested detours. 

This project aims to implement a systematic optimization program that dynamically drops blocked paths, isolates inaccessible regions immediately, and computes the mathematically shortest travel path to all accessible targets.

---

## 2. Algorithm Paradigm & Design

### Selected Paradigm: Greedy / Graph Traversal (Dijkstra's Algorithm)
We selected **Dijkstra's Single-Source Shortest Path (SSSP)** algorithm utilizing a **Min-Priority Queue**. 
* **Why it fits:** Unlike Sorting or Divide & Conquer paradigms which cannot inherently map graph topologies, graph algorithms natively process structural linkages. 
* **Greedy Strategy:** At each step, the algorithm greedily extracts the unvisited vertex with the absolute lowest tentative travel time from the source. It relaxes neighboring edges immediately, ensuring optimal sub-problems form the global shortest route path.

### Algorithm Pseudocode

```text
Algorithm: DijkstraLandslideRouting(Graph, Source)
Input: A Graph with vertices V, edges E, edge weights, and landslide statuses; Source vertex.
Output: Shortest distance array and path tracking pointer array.

Initialize distance array dist[] to Infinity for all nodes
Initialize parent array parent[] to -1 for all nodes
dist[Source] = 0

Create an empty Min-PriorityQueue PQ
Insert (Source, 0) into PQ

While PQ is not empty:
    u = Node with minimum accumulated travel time extracted from PQ
    
    For each neighbor v of u:
        If Edge(u, v) is Blocked by Landslide:
            Continue // Skip this dangerous pathway entirely (Flowchart Branch Check)
            
        weight = travel time minutes from u to v
        
        If dist[u] + weight < dist[v]:
            dist[v] = dist[u] + weight
            parent[v] = u
            Insert (v, dist[v]) into PQ

Print Report Card tracking path strings using recursive backward parent lookups
```

## 3. Program Demonstration & Output Analysis

### Implementation Environment
* **Language:** Java (JDK 17)
* **Code Architecture:** Object-oriented modeling split cleanly into `Edge`, `PQNode`, and `Graph` classes. It isolates physical map configurations from dynamic real-time road blockage interventions.

### Source Code
The complete Java source file can be viewed directly in our repository files above: `LandslideEmergencyRouting.java`.

### Simulated Landslide Interventions
To mimic actual road failure events, the following road linkages were flagged as closed prior to algorithm execution:
* Junction B → Village 3 (Blocked)
* Junction C → Village 3 (Blocked)

### System Execution Results
When executed, the program processes the network and outputs the final rescue logistics log:

```text
==========================================================================================
BENTONG DISASTER RESPONSE ROUTING ENGINE - OUTPUT LOG
==========================================================================================
[SOURCE NODE]: Bentong Hospital (EOC)
STATUS: SUCCESSFUL PATH COMPUTATION

▶ Village 1: Bentong Hospital (EOC) → Junction B → Village 1
  Total Estimated Travel Time: 18 Minutes
  Route Status                : CLEAR / OPTIMAL

▶ Village 2: Bentong Hospital (EOC) → Junction B → Village 2
  Total Estimated Travel Time: 15 Minutes
  Route Status                : CLEAR / OPTIMAL

▶ Village 3: No available route.
  Total Estimated Travel Time: INF (∞)
  Route Status                : CRITICAL CRASH - VILLAGE IS ISOLATED
  Action Required             : ALERT AIR DISPATCH (HELICOPTER REQ)

▶ Village 4: Bentong Hospital (EOC) → Junction A → Junction C → Village 4
  Total Estimated Travel Time: 32 Minutes
  Route Status                : CLEAR / ALTERNATIVE DETOUR

==========================================================================================
```

## 4. Algorithm Analysis

### Time Complexity: $O((V + E) \log V)$
* **Extraction Operations:** Extracting the minimum element from the binary heap-based `PriorityQueue` takes $O(\log V)$ time. This step occurs once per vertex, resulting in a total cost of $O(V \log V)$ for all vertices.
* **Relaxation Operations:** In the worst-case scenario, every single edge in the network is examined. For each edge checked, updating the priority queue element requires an up-heap or down-heap rebalancing process that costs $O(\log V)$ time. Across all edges, this takes $O(E \log V)$ time.
* **Total Bounds:** Combined, the global performance bounds match $O(V \log V + E \log V) = O((V + E) \log V)$. This high efficiency guarantees that the application runs within milliseconds, satisfying real-time deployment needs during an actual landslide emergency.

### Space Complexity: $O(V + E)$
* **Adjacency List:** The graph layout is efficiently stored using an Adjacency List. This requires $O(V + E)$ memory allocation to map the 8 vertices and their active, unblocked edge segments.
* **Tracking Matrices:** The system sets up auxiliary tracking arrays (`distances[]` and `parent[]`) to manage path history data. These tracking metrics scale strictly to linear space $O(V)$. 
* **Total Bounds:** Combining the adjacency list with the tracking matrices results in an overall space complexity boundary of $O(V + E)$, which fits easily into standard computer memory profiles.

---

## 5. Group Portfolio Contributors

* **Rasyidah Liana Binti Abd Halim** (Matric: 222239)
* **Nur Sofea Binti Muhalis** (Matric: 224704)
* **Nurul Syazana Binti Zaidi** (Matric: 225552)
* **Nurul Aini Binti Ismail** (Matric: 225068)
* **Alya Maisarah Hannani Binti Ahmad Sukri** (Matric: 226080)

## 6. Presentation Slides

You can interactively view our project presentation slides directly via Canva below:
👉 [Click Here to View Our Project Presentation Slides](https://canva.link/zmyom4oijm6fp40)
