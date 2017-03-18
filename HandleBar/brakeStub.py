import RPi.GPIO as GPIO

class brakeSensor(object):
	def __init__(self, port=22):
		self.port = port
		self.val = 0

	def getVal():
		return self.val

	def setVal(value):
		self.val = value
