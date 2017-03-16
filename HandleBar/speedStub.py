class speedStub(object):
	#Speed sensor stub.  Stores its value in a variable instead of getting it from 
	#the sensor.
	def __init__(self):
		self.value = 0

	#Returns the value the same way as the actual sensor.
	def sensorVal():
		return self.value

	#Allows the value to be set to any arbitrary value.  Not authenticating the value
	#allows testing for garbage values (something is wrong if value == 37).
	def setVal(val):
		self.value = val
