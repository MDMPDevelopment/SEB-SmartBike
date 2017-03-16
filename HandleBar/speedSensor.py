import RPi.GPIO as GPIO

class speedSensor(object):
	def __init__(self):
		GPIO.setmode(GPIO.BOARD)
	`	GPIO.setup(22, GPIO.IN) #speed

	def sensorVal():
		return GPIO.input(22)
