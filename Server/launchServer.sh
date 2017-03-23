#!/bin/bash

serverIP="10.0.0.18"
handleIP="10.0.0.11"
helmetIP="10.0.0.14"
port=13375


args=$(getopt -o "s:a:e:p:h" -- "$@")

eval set -- "$args"

while [ $# -ge 1 ]; do
        case "$1" in
                --)
                    # No more options left.
                    shift
                    break
                   ;;
                -s)
			serverIP="$2"
                        shift
                        ;;
		-a)
			handleIP="$2"
			shift
			;;
                -e)
                        helmetIP="$2"
                        shift
                        ;;
                -p)
                        port=$2
                        shift
                        ;;
                -h)
                        echo "Usage: launchServer.sh -s serverIP -a handleBarIP -e helmet IP -p port"
                        exit 0
                        ;;
        esac

        shift
done

echo "serverIP = $serverIP"
echo "helmetIP = $helmetIP"
echo "handleIP = $handleIP"
echo "port     = $port" 
echo 
echo "Starting the server..."
echo 

#javac ./*.java
#java -ea TestServer
java -cp ".:sqlite-jdbc-3.16.1.jar" Server &

python3 signalHandler.py &

echo "Server Running"
