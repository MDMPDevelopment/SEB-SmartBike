import RPi.GPIO as GPIO
import socket, sys, time
import brakeSensor, brakeStub

#Allows program to be run with either the sensor or the stub.  Conditional lets program
#run without the testing argument.  Defaults to sensor if testing argument not set.
test = False

if len(sys.argv) > 3:
        test = (sys.argv[3][0] == 't' or sys.argv[3][0] == 'T')

brake_sensor = brakeStub() if test else brakeSensor()

#UDP SETUP
host = sys.argv[1] #can modify host/port
textport = sys.argv[2]
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)


brake =0 #used to compare if lights are already on
while 1:
    if not brake_sensor.getVal() == brake: #compares with current light state
        brake = not brake #change light state
        #send packet
		data = "brake:%d" %brake
        s.sendto(data.encode('utf-8'), server_address)
        
		#debouncing
		while GPIO.input(22):
            time.sleep(0.5)
s.shutdown(1)
