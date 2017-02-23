import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(26, GPIO.OUT)
GPIO.setup(24, GPIO.OUT)
GPIO.setup(21, GPIO.IN)
GPIO.setup(23, GPIO.IN)
on1 =0
led1=0
on2 =0
led2=0
while(1):
        if GPIO.input(21):
                on1 = not on1
                led1 = on1
                GPIO.output(26, on1)
                while GPIO.input(21):
                        time.sleep(0.5)
        if GPIO.input(23):
                on2 = not on2
                led2 = on2
                GPIO.output(24, on2)        
                while GPIO.input(23):
                        time.sleep(0.5)
        if on1:
                led1 = not led1
                GPIO.output(26, led1)
                time.sleep(0.5)
        if on2:
                led2 = not led2
                GPIO.output(24, led2)
                time.sleep(0.5)
