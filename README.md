# CommonSequences Project

This project folder contains all of the files needed for a program that takes in text and then outputs the 100 most frequent three word phrases in descending order. Here is how to build and run it.
Note: To make the setup easier, the junit.jar file is included in this project directory.
Here is the Github link: https://github.com/jadonjin123/CommonSequences

1. Make sure you have java, javac, and junit(optional) installed on your machine. You can run these commands from the terminal to make sure you have installed them.
```
java -version
openjdk version "20" 2023-03-21
OpenJDK Runtime Environment Homebrew (build 20)
OpenJDK 64-Bit Server VM Homebrew (build 20, mixed mode, sharing)
```

```
javac -version
javac 20
```

```
echo $CLASSPATH | grep junit.jar
::/opt/local/share/java/junit.jar
```

2. If you chose to install junit yourself, do this step. Make sure you have the CLASSPATH environment variable set up correctly in your ~/.bashrc or ~/.zshrc file. Make sure it points to wherever you installed junit.

Example:
```
export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"
export JUNIT_HOME="$CLASSPATH:/opt/local/share/java"
export CLASSPATH="$CLASSPATH:$JUNIT_HOME/junit.jar"
```

3. Navigate to the src folder in this directory and run the following commands to compile the Java source files:
```
javac CommonSequences.java
```

If you have junit installed and the CLASSPATH variable set, run this:
```
javac SequenceTest.java
```
If you don't have junit installed, you can also pass in junit.jar in the lib folder manually when compiling SequenceTest.java like:
```
javac -cp ../lib/junit-4.13.2.jar:. SequenceTest.java
```
This classpath includes both the junit.jar package and the src files.

If the compilation was successful, you should see the CommonSequences.class and SequenceTest.class files.

4. To run the project executable, you can input the file names as arguments or input them to stdin like the following:
```
java CommonSequences ../texts/moby-dick.txt
```
```
java CommonSequences ../texts/moby-dick.txt ../texts/brothers-karamazov.txt
```
```
cat ../texts/*.txt | java CommonSequences
```

Make sure you have the correct file path to the input file from the current directory.

5. To run the JUnit tests for the project and if you have the CLASSPATH variable set, run this:
```
java SequenceTest
```

If you don't have junit installed, you can also pass in junit.jar in the lib folder manually when running SequenceTest like:
```
java -cp ../lib/junit-4.13.2.jar:. SequenceTest
```
6. (Note: this only works on Linux) To run the program in Docker, go to the Docker directory, and run
```
docker build -t commonsequences .
```
and then run
```
docker run -t commonsequences
```

If I was given more time, I would edit the processInput method so that it can also create and record three word phrases that start from the end of the first line and end at the beginning of the next line. Currently, it only processes one line at a time and does not take into account the phrases that span between lines. I would also write some more tests that cover some more edge cases if I was given more time to think of the cases and write tests for them. Also, these instructions only work on the Mac and Linux operating systems, and I would have made it work for Windows too if I had more time. If would also made it so that the Docker commands work on Mac too if given more time.

One bug that I am aware of is that the program does not work properly if you input files from stdin and as arguments at the same time. If I had more time I would do something to account for this condition.
