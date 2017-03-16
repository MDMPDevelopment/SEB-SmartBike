import RPi.GPIO as GPIO
import socket, sys, time
import speedSensor
import speedStub

test = False

if len(sys.argv) > 3:
	test = (sys.argv[3][0] == 't' or sys.argv[3][0] == 'T')

speed_sensor = speedStub() if test else speedSensor()

#UDP setup
host = sys.argv[1]
textport = sys.argv[2]
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

#data for calculations
radius = 0.29 #radius of bike wheel
pi = 3.14159 
count = 0
rotations = 5 #number of rotations
wait = 0.1 #wait used for debouncing
start = time.time() #timer

while 1:  
  if not speed_sensor.sensorVal():
    time.sleep(wait) # wait so only one reading occurs
    if count == rotations: #enter if max rotation
      t = time.time() - start - wait #time for 5 rotations
      speed = (2*pi*radius*rotations)/t #speed calc
      #send packet
      data = "Speed:%f" %speed
      s.sendto(data.encode('utf-8'), server_address)
      #reset count and time
      start = time.time()
      count = 0
    else:
      count = count + 1
  #reset if bike has stopped for 10 secs
  #if time.time() - start > 10:
    #start = time.time()
    #count = 0 
s.shutdown(1)
