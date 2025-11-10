# Assignment 4 — Smart City / Smart Campus Scheduling

This project implements:
- **SCC detection** (Tarjan), **condensation DAG** construction (edge-weight model, component edges aggregated by **min** weight),
- **Topological ordering** of the condensation DAG (Kahn, with cycle check),
- **Single-source shortest paths** and **critical (longest) path** on the DAG with **path reconstruction**,
- **Datasets** under `/data` (9 files: 3 small, 3 medium, 3 large),
- **JUnit tests** for SCC, Topo, DAG-SP,
- **Maven** build (`pom.xml`).

## How to run
```bash
mvn -q -e -DskipTests package
java -jar target/assignment4-smartcity-1.0.0.jar           # runs all datasets in ./data
# or
java -jar target/assignment4-smartcity-1.0.0.jar data/small1.json
```

## Weight model
- Using **edge weights** from input JSON (`"weight_model": "edge"`).
- When building the **condensation DAG**, if multiple original edges connect the same pair of components, we take the **minimum** edge weight (documented policy).

## Outputs
For each dataset, the program prints:
- SCCs (lists + sizes),
- Condensation DAG edges,
- Topological order of components and **derived order of original tasks**,
- Shortest distances (from the component of `source`) and an **example shortest path**,
- **Critical path** (longest path) and length.

## Datasets
All datasets are JSON with fields:
```json
{ "n": <int>, "edges": [{"u":0,"v":1,"w":2.5}, ...], "source": 0, "weight_model": "edge" }
```
Placed under `/data/`:
- `small1.json` … `small3.json` (6–10 nodes),
- `medium1.json` … `medium3.json` (10–20 nodes),
- `large1.json` … `large3.json` (20–50 nodes).
Each category includes **cyclic** and **acyclic** examples.

## Results & Analysis (fill in after running)
Create a table like:

| File | n | m | #SCC | DAG nodes | DAG edges | Shortest(ms) | Longest(ms) | Notes |
|------|---|---|------|-----------|-----------|--------------|-------------|-------|
| small1.json | | | | | | | | |
| … | | | | | | | | |

- Comment on **density vs performance** and **SCC sizes**.
- Note bottlenecks and practical recommendations.
