# Proje_2_UDP_Chat 
Design with respect to to host action as server client at the same time one listening to port 5000 the other listening port 5001 and return responces vice versa. Created according to the minimum requirements that are as follows 
  -Make a chat applicaiion that uses UDP protocol
  -Allow two Users to communicate each other from two different PC's
  -Incoming messages needs to inlude time recieved stamp in "HH:mm:ss" format

# UDP_Chat_Application
Improved version of the Proje_2 regarding below description

#App Description

A Desktop application that allows users to chat with other online users
A server listening at port 5000 to receive and forward the messages that contains information regarding whose message to who and the time recieved at the server
Messages can only be sent to other online users

# Design Desicions
Layered architecture pattern is preferred considering clients as entities layered access to entities
UDP Protocol is used for network comunications
PostgreSQL is used as initial database selection to store client information
Hibernate is used to ease possible database migration with code first approach 

# Maven Dependencies

Apache Commons-Codec is used for encryption of user password with sha256 method
Hibernate Core Relocation
PostgreSQL JDBC Driver

# Requirement

Java 8 is minimum required java version since client controller layer includes some operations using Java Stream API

![UDP_Chat_Multiple_Client](https://user-images.githubusercontent.com/113839940/213135418-fe4d1d26-4028-4f7a-8bde-d50edc6c8984.JPG)
