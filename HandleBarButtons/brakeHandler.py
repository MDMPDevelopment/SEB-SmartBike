import RPi.GPIO as GPIO
import socket, sys, time

#GPIO SETUP
GPIO.setmode(GPIO.BOARD)
GPIO.setup(22, GPIO.IN) #brake uses PORT 22

#UDP SETUP
host = sys.argv[1] #can modify host/port
textport = sys.argv[2]
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)


brake =0 #used to compare if lights are already on
while 1:
    if not GPIO.input(22) == brake: #compares with current light state
        brake = not brake #change light state
        #send packet
		data = "brake:%d" %brake
        s.sendto(data.encode('utf-8'), server_address)
        
		#debouncing
		while GPIO.input(22):
            time.sleep(0.5)
s.shutdown(1)
