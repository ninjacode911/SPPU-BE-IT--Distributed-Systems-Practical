# CORBA-Based Calculator Application (Object Brokering Demo)

## 🔧 Prerequisites

- Java JDK 8 installed
- CORBA tools (`idlj`, `tnameserv`) available from Java 8

## 🛠 Setup & Execution Steps

### 1. Switch to Java 8 (if multiple versions installed)

```bash
sudo update-alternatives --config java
sudo update-alternatives --config javac
````

> ⚠️ Make sure both `java` and `javac` point to JDK 8.


### 2. Compile the IDL File

```bash
idlj -fall Calculator.idl
```

This generates the `CalculatorApp/` folder with necessary stubs and skeletons.

---

### 3. Compile Java Source Files

```bash
javac CalculatorApp/*.java *.java
```

---

### 4. Start the CORBA Naming Service

Open **Terminal 1**:

```bash
tnameserv -ORBInitialPort 1050
```

Leave it running. This provides the registry for the CORBA object references.

---

### 5. Run the Server

Open **Terminal 2**:

```bash
java CalculatorServer -ORBInitialPort 1050
```

---

### 6. Run the Client

Open **Terminal 3**:

```bash
java CalculatorClient -ORBInitialPort 1050
```

---

## 📌 Notes

* All remote method calls are handled via the CORBA ORB and naming service.
* Division by zero will throw a runtime exception from the server.
* Port `1050` is used for the naming service; you may change it if needed.

---