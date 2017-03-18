import RPi.GPIO as GPIO
import time
import sqlite3

conn = sqlite3.connect('SEB.db')
c = conn.cursor()

GPIO.setmode(GPIO.BOARD)
GPIO.setup(21, GPIO.OUT)
GPIO.setup(23, GPIO.OUT)
right = 0
left = 0
on1 = 0
led1 = 0
on2 = 0
led2 = 0
while(1):
	
	
	
        if right :
                on1 = not on1
                led1 = on1
                GPIO.output(23, on1)
                
        if left :
                on2 = not on2
                led2 = on2
                GPIO.output(24, on2)        
                
        if on1:
                led1 = not led1
                GPIO.output(23, led1)
                time.sleep(0.5)
        if on2:
                led2 = not led2
                GPIO.output(21, led2)
                time.sleep(0.5)
