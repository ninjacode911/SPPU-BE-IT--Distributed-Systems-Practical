from functools import reduce
from dateutil import parser
import threading
import socket
import time
import datetime

# Data structure used to store client address and clock data
client_data = {}

# Nested thread function used to receive clock time from a connected client
def startReceivingClockTime(connector, address):
    while True:
        # Receive clock time
        clock_time_string = connector.recv(1024).decode()
        clock_time = parser.parse(clock_time_string)
        clock_time_diff = datetime.datetime.now() - clock_time
        client_data[address] = {
            "clock_time": clock_time,
            "time_difference": clock_time_diff,
            "connector": connector
        }
        print("Client data updated with:", address)
        time.sleep(5)

# Master thread function used to open a portal for accepting clients over a given port
def startConnecting(master_server):
    # Fetch clock time at slaves/clients
    while True:
        master_slave_connector, address = master_server.accept()
        slave_address = f"{address[0]}:{address[1]}"
        print(f"{slave_address} got connected successfully")
        current_thread = threading.Thread(
            target=startReceivingClockTime,
            args=(master_slave_connector, slave_address)
        )
        current_thread.start()

# Subroutine function used to fetch the average clock difference
def getAverageClockdiff():
    if not client_data:
        return datetime.timedelta(0)  # Return zero if no clients are connected
    time_difference_list = [client['time_difference'] for client in client_data.values()]
    sum_of_clock_difference = reduce(
        lambda x, y: x + y, time_difference_list, datetime.timedelta(0)
    )
    average_clock_difference = sum_of_clock_difference / len(client_data)
    return average_clock_difference

# Master sync thread function used to generate cycles of clock synchronization in the network
def synchronizeAllClocks():
    while True:
        print("New synchronization cycle started")
        print("Number of clients to be synchronized:", len(client_data))
        if client_data:
            average_clock_difference = getAverageClockdiff()
            for client_address, client in client_data.items():
                print("Synchronizing:", client_address, client)
                try:
                    synchronized_time = datetime.datetime.now() + average_clock_difference
                    client['connector'].send(str(synchronized_time).encode())
                except Exception as e:
                    print(f"Error sending synchronized time to {client_address}: {e}")
        else:
            print("No client data, synchronization not applicable")
        print("\n")
        time.sleep(5)

# Function used to initiate the clock server/master node
def initiateClockServer(port=8080):
    master_server = socket.socket()
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    print("Socket at master node created successfully\n")
    master_server.bind(('', port))

    # Start listening for requests
    master_server.listen(10)
    print("Clock server started...\n")

    # Start making connections
    print("Starting to make connections...\n")
    master_thread = threading.Thread(
        target=startConnecting,
        args=(master_server,)
    )
    master_thread.start()

    # Start synchronization
    print("Starting synchronization in parallel...\n")
    sync_thread = threading.Thread(
        target=synchronizeAllClocks
    )
    sync_thread.start()

if __name__ == "__main__":
    initiateClockServer(port=8080)