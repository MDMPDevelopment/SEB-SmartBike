import socket, sys, time
import RPi.GPIO as GPIO

#UDP setup
host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)
s.bind(server_address)

GPIO.setmode(GPIO.BOARD)
GPIO.setup(19, GPIO.OUT)

string1 = "slowRider:1"
string2 = "slowRider:0"
while True:

    print ("Waiting to receive on port %d : press Ctrl-C or Ctrl-Break to stop " % port)

    buf, address = s.recvfrom(13375)
    if not len(buf):
        break
    if(buf.compare(string1)):
		GPIO.output(19,1)
		print("speeding")
	if(buf.compare(string2)):
		GPIO.output(19,0)
		print("not speeding")
	
s.shutdown(1)
