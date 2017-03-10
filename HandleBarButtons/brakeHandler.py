import RPi.GPIO as GPIO
import socket, sys, time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(22, GPIO.IN) #brake

host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
port = int(textport)
server_address = (host, port)

brake =0
light = 0
while 1:
    data = ""
    if not GPIO.input(22) == brake:
        data = "Brake:%d" %brake
        s.sendto(data.encode('utf-8'), server_address)
        brake = not brake
        while GPIO.input(22):
            time.sleep(0.5)
s.shutdown(1)
