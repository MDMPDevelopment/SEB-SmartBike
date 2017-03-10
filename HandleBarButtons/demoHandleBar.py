import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(21, GPIO.OUT)
GPIO.setup(23, GPIO.OUT)
GPIO.setup(26, GPIO.IN)
GPIO.setup(24, GPIO.IN)
on1 =0
led1=0
on2 =0
led2=0
while(1):
        if GPIO.input(26):
                on1 = not on1
                led1 = on1
                GPIO.output(23, on1)
                while GPIO.input(23):
                        time.sleep(0.5)
        if GPIO.input(24):
                on2 = not on2
                led2 = on2
                GPIO.output(24, on2)        
                while GPIO.input(24):
                        time.sleep(0.5)
        if on1:
                led1 = not led1
                GPIO.output(23, led1)
                time.sleep(0.5)
        if on2:
                led2 = not led2
                GPIO.output(21, led2)
                time.sleep(0.5)
