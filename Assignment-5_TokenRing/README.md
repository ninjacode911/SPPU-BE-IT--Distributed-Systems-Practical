# Token Ring Based Mutual Exclusion Algorithm

## Description:

This program implements a simple **Token Ring** based mutual exclusion algorithm where nodes in a ring pass a token to ensure that only one node can access its critical section at a time. Nodes send data only when they hold the token. The program simulates token passing and data sending between nodes.

## Steps to Run:

1. **Compile the program**:

   ```
   javac TokenRing.java
   ```

2. **Run the program**:

   ```
   java TokenRing
   ```

## Functionality:

* The program prompts you to enter the number of nodes in the token ring.
* You can send data from one node to another, but only the node holding the token can send data.
* After sending data, the token is passed to the next node in the ring.

