# CCTV Android Client

This program is part of my home CCTV system. Full list of programs in the system:
* [Home CCTV Server (Main program)](https://github.com/SorroZ/homecctvserver)
* [Motion detector](https://github.com/SorroZ/motiondetector)
* [CCTV HTTP](https://github.com/SorroZ/cctvHTTP)
* [CCTV Android Client](https://github.com/SorroZ/cctvAndroidClient)

#### System functionality
At the moment the main system (Home CCTV Server, Motiondetector and CCTV HTTP) is run on a Raspberry Pi B+ (Raspian). 
The Raspberry Pi also runs a web server (NginX) which provides the HTTP-API for getting pictures and picture data for either a web browser or the android client program. 
The motion detector can however run on another Raspberry Pi within the network after just a little tweak in the configuration file. The motion detector is the only part of the system that uses the Rasp's pins. 
All the other server programs can run on a regular computer.

The Home CCTV server is the program that takes the picture, so it waits for connections from the motiondetector or the Android client to get instructions. 
The Android Client can change the setting for the server to listen or not listen to the motion detectors instructions. 
So if the motion detector setting is off and the motion detector program registers a movement, no picture will be taken.
The Android Client is also able to send information to make the server take a snapshot.
The pictures is stored in one of the server's file catalogs and data about the picture is stored in a MySQL database.

The pictures can then be accessed with the HTTP-API from a webbrowser or the Android Client. 
A search is done in the database with the specifications in the URL and the data or the picture is aquired.

##### Required softwares
* NginX (web server program)
* MySQL (database)
* fswebcam (The server uses this program to take the picture with the webcam)

#### Some information about this program
