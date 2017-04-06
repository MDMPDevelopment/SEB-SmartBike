import RPi.GPIO as GPIO
import socket, sys, time

PIN = 22

GPIO.setmode(GPIO.BOARD)
GPIO.setup(PIN, GPIO.IN) #brake

host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

brake = 0
while 1:
    data = ""
    if GPIO.input(PIN) != brake:
        data = "brake:%d" %brake
        s.sendto(data.encode('utf-8'), server_address)
        brake = not brake
        while GPIO.input(PIN):
            time.sleep(0.5)
s.shutdown(1)
