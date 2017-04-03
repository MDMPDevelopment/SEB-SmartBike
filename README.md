SEB - SmartBike

SYSC3010 - Carleton Engineering, 3rd year project

Contributors:
  Kyle Bjornson,
  Shane Corrigan,
  Ben Earle,
  Matthew Penner.

Another branch exists to show the changes made to our code after the test review it is called 'Implemented-CodeReview-Recomendations'. The reason we have done this is that all of our pi's are currently mounted on the bike and have changes on them that have not yet been pushed to master. Once we unmount the Pi's from the bike we will push the changes we made after our integration testing. 

Description of folders:

Android/SEBapp: Contains the source code for our andriod app. The android app sends and receives UDP packets to the Server to start new rides, set the wheel radius, set the maximum speed, and get the current values of the system state table. 

   -Manifest files can be found in SEB-SmartBike/Android/SEBapp/app/src/main/
   -Activity XML files can be found in SEB-SmartBike/Android/SEBapp/app/src/main/res/layout/
   -Java source code can be found in SEB-SmartBike/Android/SEBapp/app/src/main/java/com/example/benearle/sebapp/, the following is a discription of the java classes that can be found there:
      -MainActivity.java, this class is for the main activity, it is launched at startup. It has 3 buttons, two open new activites (Settings and System State) and the third sends a new ride signal to the server.  
      -RequestStateInterface.java, this is an interface used to stub out the server while developing and testing the app.
      -RequestStateStub.java, this is the class that we used to stub out the server while developing and testing the app.
      -RequestState.java, this class is used to request the server state, it is called by the StateActivity and starts a new thread running RunnableGetState.
      -RunnableGetState.java, this class implements runnable and is used to start the UDP receiver in a seperate thread from the UI manager.
      -SettingsActivity.java, this is an activity with settings such as max speed and wheel radius, when either button is pressed the app sends the number in the apropriate text field to the server.
      -StateActivity.java, this activity uses RequestState to get the servers state and displays it.
      -UDPSend.java, this is a UDP sender class that implements runnable in order to send UDP packets in a non UI thread.
    
   
   
GUI: Contians the source code for our Java GUI, much like the app it displays the current values of the system state table.

  -See above description of the set of RequestState classes.
  -SebGui.java, this class contains the view and the contorler for our GUI.
  -SebModel.java, this is the class that handles the model for our GUI, it has the button handlers.
  -bike.png, is the beautiful bike clip art used by the GUI.
  
  
  
HandleBar: Conatians all of the python scripts that are run on the handle bar pi, some of these scripts were only used to test our hardware and will not be described here.
  
  -brakeHandler, this script takes a bit stream from either brakeSensor or brakeStub, processes it and then sends a UDP packet to the server. It will send a packet whenever the rider pulls the break or releases it.
  -brakeSensor, this object records the input on the GPIO pin used for the brake magnet and outputs it to brakeHandler.
  -buttonHandler, this script waits for a button to be pressed adn then sends the apropriate packet to the server. This will update the system state and start the turning lights
  -speedHandler.py, this script takes a bit stream from either speedSensor or speedStub, calculates the speed then sends it to the server
  -speedSensor.py, this object records the input on the GPIO pin used for the speed magnet and outputs it to speedHandler.
  -speedingLight.py, this script waits to get a UDP packet stating that the rider exceeding the maximum speed that they set.


