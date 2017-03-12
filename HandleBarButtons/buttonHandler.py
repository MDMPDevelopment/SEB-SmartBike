import RPi.GPIO as GPIO
import socket, sys, time

#GPIO Setup
GPIO.setmode(GPIO.BOARD)
GPIO.setup(26, GPIO.IN) #right input
GPIO.setup(24, GPIO.IN) #left input

#UDP setup
host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

rightlight = 0
leftlight = 0 
while 1:
    if GPIO.input(26): #wait for right button press
        rightlight = not rightlight #change light state
        #send packet
		data = "turnR:%d" %rightlight
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(26):
            time.sleep(0.5)
    if GPIO.input(24): # wait for left button
        leftlight = not leftlight #change light state
        #send packet
		data = "turnL:%d" %leftlight
        s.sendto(data.encode('utf-8'), server_address)
        while GPIO.input(24):
            time.sleep(0.5)
s.shutdown(1)
