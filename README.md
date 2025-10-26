# Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)

## 🎯 Objective
The goal of this assignment is to apply **Prim’s** and **Kruskal’s** algorithms to optimize a city’s transportation network by finding the minimum set of roads that connect all districts with the lowest total construction cost. The study also compares their efficiency and performance.

## 📂 Input Data Summary
Two graphs were analyzed using the file **`ass_3_input.json`**.
- **Graph ID**: Identifies each graph.
- **Vertices (V)**: Represents the districts.
- **Edges (E)**: Represents potential roads with associated costs.

| Graph ID | Vertices (V) | Edges (E) |
|----------|--------------|-----------|
| Graph 1  | 5 vertices   | 7 edges   |
| Graph 2  | 4 vertices   | 5 edges   |

## ⚙️ Algorithm Results

| Graph   | Algorithm | MST Cost | Operations | Execution Time (ms) |
|---------|-----------|----------|------------|---------------------|
| Graph 1 | Kruskal   | 16       | 37         | 1.28                |
| Graph 1 | Prim      | 16       | 42         | 1.52                |
| Graph 2 | Kruskal   | 6        | 31         | 0.92                |
| Graph 2 | Prim      | 6        | 29         | 0.87                |

### **Explanation:**
- **MST Cost**: The minimum cost of connecting all districts (roads).
- **Operations**: The number of operations (like comparisons and unions) performed by each algorithm.
- **Execution Time**: Time taken by each algorithm to compute the MST in milliseconds.

## 📊 Comparison Between Prim’s and Kruskal’s Algorithms
- Both algorithms produced **identical MST total costs** for each graph, as expected.
- **Kruskal** performed slightly fewer operations on Graph 1, while **Prim** was faster on Graph 2.
- This behavior matches theoretical expectations:
    - **Kruskal** is efficient for **sparse graphs** (few edges).
    - **Prim** performs better on **dense graphs** (many edges) or when **adjacency lists** are used.

## 🧩 Conclusions
- **Kruskal’s Algorithm** is preferable for:
    - **Sparse graphs**,
    - **Edge-list data representations**,
    - Simpler **sorting-based implementations**.

- **Prim’s Algorithm** is preferable for:
    - **Dense graphs**,
    - **Adjacency-list data representations**,
    - **Scenarios requiring incremental MST building**.

Both algorithms guarantee the **same MST cost** and **correctness**. The choice depends mainly on the **graph density** and **data structure** used.

## 📚 References
- Cormen, T.H., Leiserson, C.E., Rivest, R.L., & Stein, C. (2022). *Introduction to Algorithms* (4th ed.). MIT Press.
- GeeksforGeeks. *Difference Between Prim’s and Kruskal’s Algorithm.*

---

### Usage Instructions
To run the project, ensure you have the **input** and **output** files in the **`resources/`** folder.

1. **Build the project**:
    ```bash
    mvn clean package
    ```

2. **Run the JAR**:
    ```bash
    java -jar target/Assignment3MST-1.0.0.jar resources/ass_3_input.json resources/ass_3_output.json
    ```

This will generate the **output** in the `ass_3_output.json` file.

---
