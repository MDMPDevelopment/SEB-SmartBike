import RPi.GPIO as GPIO

class speedSensor(object):
	#Initialize the GPIO.
	def __init__(self):
		GPIO.setmode(GPIO.BOARD)
	`	GPIO.setup(22, GPIO.IN) #speed

	#Returns the value of the sensor connected to port 22.
	def sensorVal():
		return GPIO.input(22)
