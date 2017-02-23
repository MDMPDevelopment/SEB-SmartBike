import RPi.GPIO as GPIO
import socket, sys, time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(21, GPIO.IN)
GPIO.setup(23, GPIO.IN)

host = sys.argv[1]
textport = sys.argv[2]
 
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

while 1:
    data = ""
    if GPIO.input(21):
        data = "turnR:1"
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(21):
            time.sleep(0.5)
    if GPIO.input(23):
        data = "turnL:1"
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(23):
            time.sleep(0.5)

s.shutdown(1)
