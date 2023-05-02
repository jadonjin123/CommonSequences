import java.io.*;
import java.util.*;

public class CommonSequences {

    private static int MAX_FREQS = 100;
    public static void processInput(BufferedReader inputReader, Map<String, Integer> sequenceFrequencies) throws IOException{
        String line;
        while ((line = inputReader.readLine()) != null) {
            //remove any non-alphanumeric characters, excluding hyphens and spaces
            line = line.replaceAll("[^a-zA-Z0-9\\s-]+", "");
            //remove any hyphens that appear at the end of a line or paragraph and any whitespace that follows them
            line = line.replaceAll("(?i)-\\s*$", "");
            line = line.toLowerCase();
            //split lines by white space
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length - 2; i++) {
                String sequence = words[i] + " " + words[i+1] + " " + words[i+2];
                sequenceFrequencies.put(sequence, sequenceFrequencies.getOrDefault(sequence, 0) + 1);
            }
        }
    }

    public static int readFromInput(BufferedReader inputReader, Map<String, Integer> sequenceFrequencies) throws IOException {
        inputReader = new BufferedReader(new InputStreamReader(System.in));

        //if there is no stdin
        if (!inputReader.ready()) {
            System.out.println("Usage: java Main file1 [file2 ...]");
            System.out.println("Or: cat file1 [file2 ...] | java main.java");
            return 1;
        }
        processInput(inputReader, sequenceFrequencies);
        inputReader.close();
        return 0;
    }

    public static int readFromArgs(BufferedReader inputReader, Map<String, Integer> sequenceFrequencies, String[] args) throws IOException {
        //Process each file
        for(String arg : args) {
            File inputFile = new File(arg);
            if (!inputFile.exists()) {
                //If there are multiple files and at least one does not exist, the program will exit
                System.out.println("Error: Input file(s) does not exist.");
                return 1;
            }
            inputReader = new BufferedReader(new FileReader(arg));
            //Process the input line by line
            processInput(inputReader, sequenceFrequencies);
            inputReader.close();
        }
        return 0;
    }

    static class EntryComparator implements Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
            return b.getValue().compareTo(a.getValue());
        }
    }

    public static void main(String[] args) throws IOException {

        //Read the input
        BufferedReader inputReader = null;
        Map<String, Integer> sequenceFrequencies = new HashMap<>();
        int status = 0;
        if (args.length == 0) {
	        status = readFromInput(inputReader, sequenceFrequencies);
        }
        else {
            status = readFromArgs(inputReader, sequenceFrequencies, args);
        }
        if(status == 1) {
            System.exit(status);
        }
        
        //Sort the phrases by frequency
        List<Map.Entry<String, Integer>> frequencyList = new ArrayList<>(sequenceFrequencies.entrySet());
        Collections.sort(frequencyList, new EntryComparator());
        
        for (int i = 0; i < Math.min(MAX_FREQS, frequencyList.size()); i++) {
            System.out.println(frequencyList.get(i).getKey() + " - " + frequencyList.get(i).getValue());
        }
    }
}
