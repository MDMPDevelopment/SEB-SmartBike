import RPi.GPIO as GPIO

class brakeSensor(object):
	def __init__(self, port=22):
		self.port = port
		GPIO.setmode(GPIO.BOARD)
		GPIO.setup(port, GPIO.IN)

	def getVal():
		return GPIO.input(port)
