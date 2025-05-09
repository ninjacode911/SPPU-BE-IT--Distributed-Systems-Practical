#include <mpi.h>
#include <stdio.h>

#define n 10 // Size of the array

// Input array
int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

int main(int argc, char* argv[]) {
    int pid, np;
    int elements_per_process, n_elements_received;
    MPI_Status status;

    // Initialize the MPI environment
    MPI_Init(&argc, &argv);

    // Get the rank of the process
    MPI_Comm_rank(MPI_COMM_WORLD, &pid);
    // Get the total number of processes
    MPI_Comm_size(MPI_COMM_WORLD, &np);

    if (np > n) {
        if (pid == 0)
            printf("Number of processes should not exceed %d\n", n);
        MPI_Finalize();
        return 1;
    }

    // Master process (rank 0)
    if (pid == 0) {
        // Calculate how many elements each process should handle
        elements_per_process = n / np;

        // Distribute parts of the array to worker processes
        if (np > 1) {
            for (int i = 1; i < np - 1; i++) {
                int index = i * elements_per_process;
                MPI_Send(&elements_per_process, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
                MPI_Send(&a[index], elements_per_process, MPI_INT, i, 0, MPI_COMM_WORLD);
            }

            // Last process gets the remaining elements
            int index = (np - 1) * elements_per_process;
            int elements_left = n - index;

            MPI_Send(&elements_left, 1, MPI_INT, np - 1, 0, MPI_COMM_WORLD);
            MPI_Send(&a[index], elements_left, MPI_INT, np - 1, 0, MPI_COMM_WORLD);
        }

        // Master computes its portion of the sum (first segment)
        int sum = 0;
        for (int i = 0; i < elements_per_process; i++)
            sum += a[i];

        // Print partial sum of master before receiving others
        printf("Partial sum at rank %d is >> %d\n", pid, sum);

        // Receive and add partial sums from worker processes
        int tmp;
        for (int i = 1; i < np; i++) {
            MPI_Recv(&tmp, 1, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
            printf("Received partial sum from rank %d >> %d\n", status.MPI_SOURCE, tmp);
            sum += tmp;
        }

        // Print the final total sum
        printf("Sum of array is >> %d\n", sum);
    } 
    // Worker processes
    else {
        // Receive number of elements to process
        MPI_Recv(&n_elements_received, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        // Receive array segment
        int a2[n_elements_received];
        MPI_Recv(a2, n_elements_received, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        // Compute partial sum
        int partial_sum = 0;
        for (int i = 0; i < n_elements_received; i++)
            partial_sum += a2[i];

        // Print partial sum before sending
        printf("Partial sum at rank %d is >> %d\n", pid, partial_sum);

        // Send partial sum to master
        MPI_Send(&partial_sum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    // Finalize the MPI environment
    MPI_Finalize();
    return 0;
}