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
