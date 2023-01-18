# UDP_Chat_Application

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
