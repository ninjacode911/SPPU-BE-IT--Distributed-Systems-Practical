# Berkeley Algorithm for Clock Synchronization

This project implements the Berkeley algorithm for clock synchronization between multiple clients and a server.

## Steps to Run

1. **Make `server.py` executable:**
   ```bash
   chmod +x server.py
   ```

2. **Run the server:**

   ```bash
   python3 server.py
   ```

3. **Run the client(s):**

   ```bash
   python3 client.py
   ```



## Description

* The server acts as a time coordinator.
* Clients request the time from the server.
* The server calculates the average time and sends it back to clients for synchronization.

## Requirements

* Python 3.x
* Ensure that `server.py` is executable and both the server and clients can communicate via the specified ports.