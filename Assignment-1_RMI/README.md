# Multi-Threaded Client/Server Communication using RMI

## Steps to Run on Linux

### 1. Install Java (if not installed)
Before running the server and client, ensure that Java is installed on your machine.

```bash
sudo apt update
sudo apt install default-jdk
java -version
````

### 2. Compile Java Files

Compile the Java source files by navigating to the directory where the `.java` files are located, and run:

```bash
javac *.java
```

### 3. Start RMI Registry

RMI requires the RMI Registry to be running for the client and server to communicate. Start the RMI Registry with the following command:

```bash
rmiregistry &
```

If the above command fails, you can kill any lingering `rmiregistry` processes:

```bash
ps
kill <PID>
rmiregistry &
```

### 4. Run the Server (Terminal 1)

Now, you can run the server in the first terminal. Navigate to the directory containing the server code and run:

```bash
java Server
```

### 5. Run the Client (Terminal 2)

Finally, open a second terminal and run the client. Make sure the client code is in the same directory as the compiled files.

```bash
java Client
```

The server and client will now communicate over RMI, and you can observe the multi-threaded communication in action.