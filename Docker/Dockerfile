#Specify parent image
FROM ubuntu
LABEL maintainer="JadonJin"

#Set working directory
WORKDIR /app

#Copy files from host to current working directory
COPY CommonSequences.class /app
COPY short.txt /app
RUN apt-get update
RUN apt install -y default-jdk
RUN apt install -y default-jre

#Run the application
CMD ["java", "CommonSequences", "short.txt"]
