import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SequenceTest {
    @Test
    public void testProcessInput() throws FileNotFoundException, IOException{
        System.out.println("Running testProcessInput.");
        BufferedReader inputReader = new BufferedReader(new FileReader("../texts/short.txt"));
        Map<String, Integer> sequenceFrequencies = new HashMap<>();
        CommonSequences.processInput(inputReader, sequenceFrequencies);
        assertTrue(sequenceFrequencies.containsKey("brave new world"));
        assertTrue(sequenceFrequencies.containsKey("pride and prejudice"));
        assertTrue(sequenceFrequencies.containsKey("the great gatsby"));
        assertTrue(sequenceFrequencies.containsValue(4));
        assertTrue(sequenceFrequencies.containsValue(3));
        assertTrue(sequenceFrequencies.containsValue(2));
        assertEquals(sequenceFrequencies.get("brave new world"), (Integer)4);
        assertEquals(sequenceFrequencies.get("pride and prejudice"), (Integer)3);
        assertEquals(sequenceFrequencies.get("the great gatsby"), (Integer)2);
        inputReader.close();
        System.out.println("Test testProcessInput done!");
    }

    @Test
    public void testReadFromInput() throws IOException {
        System.out.println("Running testReadFromInput.");
        BufferedReader inputReader = null;
        Map<String, Integer> sequenceFrequencies = new HashMap<>();

        //Capture stdout to compare to a string
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        //test when input reader is null
        int status = CommonSequences.readFromInput(inputReader, sequenceFrequencies);
        System.setOut(originalOut);
        String expected = "Usage: java Main file1 [file2 ...]\nOr: cat file1 [file2 ...] | java main.java\n";
        String actual = baos.toString();
        assertEquals(expected, actual);
        assertEquals(status, 1);

        //test reading from stdin
        String files = "`cat ../texts/*.txt";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(files.getBytes()));
            status = CommonSequences.readFromInput(inputReader, sequenceFrequencies);
        } finally {
            System.setIn(stdin);
        }
        assertEquals(status, 0);
        System.out.println("Test testReadFromInput done!");
    }

    @Test
    public void testReadFromArgs() throws IOException {
        System.out.println("Running testReadFromArgs.");
        BufferedReader inputReader = null;
        Map<String, Integer> sequenceFrequencies = new HashMap<>();

        //Capture stdout to compare to a string
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        //test when files are not found
        String[] args1 = {"does_not_exist.txt"};
        int status = CommonSequences.readFromArgs(inputReader, sequenceFrequencies, args1);
        System.setOut(originalOut);
        String expected = "Error: Input file(s) does not exist.\n";
        String actual = baos.toString();
        assertEquals(expected, actual);
        assertEquals(status, 1);

        baos.reset();
        System.setOut(new PrintStream(baos));
        String[] args2 = {"../texts/short.txt", "does_not_exist.txt"};
        status = CommonSequences.readFromArgs(inputReader, sequenceFrequencies, args2);
        System.setOut(originalOut);
        expected = "Error: Input file(s) does not exist.\n";
        actual = baos.toString();
        assertEquals(expected, actual);
        assertEquals(status, 1);

        //test when files are found
        String[] args3 = {"../texts/short.txt", "../texts/moby-dick.txt"};
        status = CommonSequences.readFromArgs(inputReader, sequenceFrequencies, args3);
        assertEquals(status, 0);

        System.out.println("Test testReadFromArgs done!");
    }

    @Test
    public void testMain() throws IOException{
        System.out.println("Running testMain.");

        //Capture stdout to compare to a string
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        String[] arguments = {"../texts/short.txt"};
        CommonSequences.main(arguments);
        System.setOut(originalOut);

        String expected = "brave new world - 4\npride and prejudice - 3\nthe great gatsby - 2\n";
        String actual = baos.toString();
        assertEquals(expected, actual);
        System.out.println("Test testMain done!");
    }

    public static void main(String[] args) throws IOException {
        SequenceTest t = new SequenceTest();
        t.testProcessInput();
        t.testReadFromInput();
        t.testReadFromArgs();
        t.testMain();
        System.out.println("All tests passed!");
    }
}
