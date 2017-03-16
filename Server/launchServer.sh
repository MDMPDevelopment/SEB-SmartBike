#!/bin/bash



args=$(getopt -l "searchpath:" -o "s:h" -- "$@")

eval set -- "$args"

while [ $# -ge 1 ]; do
        case "$1" in
                --)
                    # No more options left.
                    shift
                    break
                   ;;
                -sip)
                        searchpath="$2"
                        shift
                        ;;
		-hbip)
			
			shift
			;;
                -hlmip)
                        searchpath="$2"
                        shift
                        ;;
                -p)
                        searchpath="$2"
                        shift
                        ;;
                -h)
                        echo "Usage: launchServer.sh -sip serverIP -hbip handleBarIP -hlmip helmet IP -p port"
                        exit 0
                        ;;
        esac

        shift
done


echo "Starting the server..."

