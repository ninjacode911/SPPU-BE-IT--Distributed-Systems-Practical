# Implement Bully and Ring algorithm for leader election.


##  How to Run

### 1. Compile the Programs

```bash
javac RingAlgorithm.java
javac BullyAlgorithm.java
````

### 2. Run the Programs

#### Ring Algorithm

```bash
java RingAlgorithm
```

#### Bully Algorithm

```bash
java BullyAlgorithm
```

---

## 🧠 What They Do

### Ring Algorithm

* Arranges processes in a logical ring.
* Election circulates through all processes.
* Highest process ID becomes the leader.

### Bully Algorithm

* Processes know all others.
* An initiator contacts higher-ID processes.
* Highest responding ID becomes the leader.
---