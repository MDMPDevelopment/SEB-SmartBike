import RPi.GPIO as GPIO
import time
import sqlite3

conn = sqlite3.connect('seb.db')
c = conn.cursor()

GPIO.setmode(GPIO.BOARD)
GPIO.setup(24, GPIO.OUT)
GPIO.setup(26, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)
#GPIO.output(21, 1==1)
right = False
left = False
on1 = False
on2 = False
on3 = False

while(1):
	c.execute("SELECT * FROM SystemState WHERE Variable='turnR';")
	right = c.fetchone()[1] == '1'
	c.execute("SELECT * FROM SystemState WHERE Variable='turnL';")
	left = c.fetchone()[1] == '1'
	c.execute("SELECT * FROM SystemState WHERE Variable='brake';")
	brake = c.fetchone()[1] == '1' 
	if right:
		on1 = not on1
	else:
		on1 = False
	if left:
		on2 = not on2
	else:
		on2 = False
	if brake:
		on3 = True
	else:
		on3 = False
	
	GPIO.output(26, on1)
	GPIO.output(24, on2)
	GPIO.output(22, on3)
	time.sleep(1)
