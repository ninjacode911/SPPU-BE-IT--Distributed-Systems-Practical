from timeit import default_timer as timer
from dateutil import parser
import threading
import datetime
import socket
import time


# Client thread function used to send time at the client side
def startSendingTime(slave_client):
    while True:
        try:
            # Provide server with clock time at the client
            slave_client.send(str(datetime.datetime.now()).encode())
            print("Recent time sent successfully")
            time.sleep(5)
        except Exception as e:
            print(f"Error sending time to server: {e}")
            break


# Client thread function used to receive synchronized time
def startReceivingTime(slave_client):
    while True:
        try:
            # Receive data from the server
            synchronized_time = parser.parse(slave_client.recv(1024).decode())
            print("Synchronized time at the client is:", synchronized_time)
        except Exception as e:
            print(f"Error receiving synchronized time: {e}")
            break


# Function used to synchronize client process time
def initiateSlaveClient(port=8080):
    slave_client = socket.socket()
    try:
        # Connect to the clock server on local computer
        slave_client.connect(('127.0.0.1', port))
        print("Connected to the clock server.")

        # Start sending time to the server
        print("Starting to send time to the server...\n")
        send_time_thread = threading.Thread(
            target=startSendingTime,
            args=(slave_client,)
        )
        send_time_thread.start()

        # Start receiving synchronized time from server
        print("Starting to receive synchronized time from server...\n")
        receive_time_thread = threading.Thread(
            target=startReceivingTime,
            args=(slave_client,)
        )
        receive_time_thread.start()

    except Exception as e:
        print(f"Connection failed: {e}")
        slave_client.close()


if __name__ == '__main__':
    initiateSlaveClient(port=8080)