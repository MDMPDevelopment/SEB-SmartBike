import socket, sys, time
import RPi.GPIO as GPIO

host = sys.argv[1]
textport = sys.argv[2]
#board_type = sys.argv[-1]

#io.setmode(io.BCM))

#io.output(22, 1)
#time.sleep(0.5)
#io.output(22, 0)
PIN = 23
GPIO.setmode(GPIO.BOARD)
GPIO.setup(PIN, GPIO.OUT)
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)
s.bind(server_address)

while True:

   # print ("Waiting to receive on port %d : press Ctrl-C or Ctrl-Break to stop " % port)

    buf, address = s.recvfrom(port)
    if not len(buf):
        break
	
    GPIO.output(PIN, buf[0]-48)
    print ("Received %s bytes from %s: %d " % (len(buf), address, buf[0] ))

s.shutdown(1)
