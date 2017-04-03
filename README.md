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



Server: This folder contains the server and all the other scripts that need to be run on the Server pi. While running the server receives requests on port 13375 in the following format: "Request:Value". Below is a list of all the current requests it can handle.
               "Speed:Km/h"
               "turnL:0|1"
               "turnR:0|1"
               "brake:0|1" 
               "newRide:1"
               "getState:port"
The first four will make changes to our system state table, new ride will start a new ride and get state will send the state in a UDP packet to the address it received from and the port in the request. 

  -DatabaseManager.java, this class handles all of our database comunications. It is mostly used for holding the current state of the system and loggign all the speed entries. If we had time to add more biometric sensors we would also log them here.
  -DatabaseManagerInterface.java, this interface was used for our mock class, TestServer.
  -DatabaseManagerTest.java, this is our JUnit test for the DatabaseManager class, it uses the sebTEST.db file with preset known values.
  -README.md contains information on compiling and running our server.
  -Server.java, see the explination above. This is the class that sends and receceives the UDP packets.
  -TestServer.jaca, this is the previously menitoned mock. It passes itself to the server in the place of DatabaseManager and sends UDP packets to the server and monitor it's method calls.
  -launchServer.sh is a bash script that we were going to use to start the server. The plan for it was to take all the info requried for starting the server as parameters. We didn't add this support to the java classes yet and for this reason it does not currently work.
  -make.sh this script just compiles all the java code in this folder with the apropriate class path, I got tired of typing it out so I thought this  could save some time.
  -seb.db, this is a SQLite3 database, this is a flat file that contains all the tables, coloumns and rows from our db. It's pretty slick!
  -signalHandler.py, this is the python script that runs the lights a the back of the bike. It doesn't use the RequestState java class, since it is on the same pi as the database we just use SQLlite python libraries to querry the system state table without the server being involved.

