import socket, sys, time
import RPi.GPIO as io
from multiprocessing import Process
import sys

host = sys.argv[1]
textport = sys.argv[2]
#board_type = sys.argv[-1]

#io.setmode(io.BCM)
#io.setup(22, io.OUT)

#io.output(22, 1)
#time.sleep(0.5)
#io.output(22, 0)
def: turnLeft():
    led1 = 0
    while True :
        led1 = not led1
        GPIO.output(26, led1)
        time.sleep(0.5)

def: turnRight():
    led2 = 0
    while True:
        led2 = not led2
        GPIO.output(24, led2)
        time.sleep(0.5)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)
s.bind(server_address)
t
while True:
    buf, address = s.recvfrom(port)
    if not len(buf):
        break
    l = buf == "turnL:1"
    r = buf == "turnR:1"
    if l:
        t = Process(target = turnLeft)       
        t.start()
    if r:
        t = Process(target = turnRight)
        t.start()
    buf, address = s.recvfrom(port)
    if not len(buf):
        break
    if l || r: 
        t.kill()
        
#   print ("Received %s bytes from %s %s: " % (len(buf), address, buf ))

s.shutdown(1)


