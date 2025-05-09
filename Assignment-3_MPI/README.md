# Develop a distributed system, to find sum of N elements in an array by distributing N/n elements to n number of processors MPI or OpenMP. Demonstrate by displaying the intermediate sums calculated at different processors.

## **1. Prerequisites**

Make sure you have the necessary dependencies installed on your machine to run MPI-based programs.

### **Install MPI (If Not Installed)**

Follow the commands below to install **MPICH** and **OpenMPI** on your system:

```bash
sudo apt update

# Install MPICH (Message Passing Interface)
sudo apt install mpich

# Install OpenMPI (another implementation of MPI)
sudo apt install openmpi-bin openmpi-common libopenmpi-dev
```

---

## **2. Program Files**

This distributed system consists of two main MPI programs:

* **sumOfArray.c**: Computes the sum of N elements in an array using MPI with `n` processors, and the results are collected and displayed.
* **sumAtProcc.c**: Displays the intermediate sums calculated at different processors while computing the total sum of the array.

### **Step 1: Compile the Files**

After installing the necessary packages, you need to compile the source code files to create executable binaries.

Run the following commands to compile each program:

```bash
# Compile the mpi_sum.c file
mpicc mpi_sum.c -o sum
```

---

## **3. Running the Programs**

### **Compute the Sum of Elements in the Array**

To run the first program (`mpi_sum.c`) and calculate the total sum, use the following command:

```bash
mpirun -np 4 ./sum
```

or

```bash
mpiexec -np 4 ./sum
```

Here, `-np 4` specifies that the program will use **4 processors**. You can change the number to use a different number of processors depending on your setup.

---

## **4. Notes:**

* You can change the number of processors by adjusting the `-np` parameter in the `mpirun` or `mpiexec` command. Just ensure that the number of processors you choose does not exceed the array size.

---