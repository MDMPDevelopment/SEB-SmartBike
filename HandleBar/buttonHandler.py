import RPi.GPIO as GPIO
import socket, sys, time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(26, GPIO.IN) #right input
GPIO.setup(24, GPIO.IN) #left input

host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

brake =0
rightlight = 0
leftlight = 0 
while 1:
    data = ""
    if GPIO.input(26):
        rightlight = not rightlight
        data = "turnR:%d" %rightlight
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(26):
            time.sleep(0.5)
    if GPIO.input(24):
        leftlight = not leftlight
        data = "turnL:%d" %leftlight
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(24):
            time.sleep(0.5)
s.shutdown(1)
