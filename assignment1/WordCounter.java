import java.io.*;
import java.util.*;

public class WordCounter {
  private Map<String,Integer> wordCounts;
  private Map<String,Integer> fileWords;
  private int fileCount;

  public WordCounter() {
    wordCounts = new TreeMap<>();
  }
  public int getFileCount(){
    return this.fileCount;
  }
  public void processFile(File file) throws IOException {
    System.out.println("Processing " + file.getAbsolutePath() + "...");
    if (file.isDirectory()) {
      // process all the files in that directory
      File[] contents = file.listFiles();
      for (File current: contents) {
        processFile(current);
      }
    } else if (file.exists()) {
      // count the words in this file
      fileCount++;
      Scanner scanner = new Scanner(file);
      fileWords=new TreeMap<>();
      scanner.useDelimiter("'|\\s|\"|,|:|!|\\?|\\.|-|\'");
      while (scanner.hasNext()) {
        String word = scanner.next().toLowerCase();
        if (isWord(word)) {
          wordAdd(word);
        }
      }
    }
  }

  private boolean isWord(String word) {
    String pattern = "^[a-zA-Z]+$";
    if (word.matches(pattern)) {
      return true;
    } else {
      return false;
    }
  }

  private void wordAdd(String word){
    if (!fileWords.containsKey(word)){
      fileWords.put(word,1);
      if (wordCounts.containsKey(word)) {
        int oldCount = wordCounts.get(word);
        wordCounts.put(word, oldCount+1);
      } else {
        wordCounts.put(word, 1);
      }
    }
  }

  public void outputWordCounts(int minCount, File outFile)
                              throws IOException {
    System.out.println("Saving word counts to " + outFile.getAbsolutePath());
    System.out.println("# of words: " + wordCounts.keySet().size());
    if (!outFile.exists()) {
      outFile.createNewFile();
      if (outFile.canWrite()) {
        PrintWriter fileOut = new PrintWriter(outFile);

        Set<String> keys = wordCounts.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while (keyIterator.hasNext()) {
          String key = keyIterator.next();
          int count = wordCounts.get(key);

          fileOut.println(key + ": " + count);
        }

        fileOut.close();
      } else {
        System.err.println("Error:  Cannot write to file: " + outFile.getAbsolutePath());
      }
    } else {
      System.err.println("Error:  File already exists: " + outFile.getAbsolutePath());
      System.out.println("outFile.exists(): " + outFile.exists());
      System.out.println("outFile.canWrite(): " + outFile.canWrite());
    }
  }

  public static void main(String[] args) throws IOException{
    if (args.length < 2) {
      System.err.println("Usage: java WordCounter <dir> <outfile>");
      System.exit(0);
    }

    WordCounter hamTrain = new WordCounter();
    WordCounter spamTrain=new WordCounter();
    //File dataDir = new File(args[0]);
    //File outFile = new File(args[1]);
    File hamOut=new File("hamOut");
    File spamOut=new File("spamOuts");
    File hamDir=new File("data/train/ham");
    File spamDir=new File("data/train/spam");
    try {
      hamTrain.processFile(hamDir);
      spamTrain.processFile(spamDir);
      hamTrain.outputWordCounts(2, hamOut);
      spamTrain.outputWordCounts(2,spamOut);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String,Float> spamProb=new TreeMap<>();
    Set<String> spamKeys = spamTrain.wordCounts.keySet();
    Iterator<String> spamKeyIterator = spamKeys.iterator();
    //calculating word spam
    while (spamKeyIterator.hasNext()) {
      String key = spamKeyIterator.next();
      int occur = spamTrain.wordCounts.get(key);
      if(hamTrain.wordCounts.containsKey(key)){
        float probAppearSpam=((float)occur/spamTrain.getFileCount());
        float probAppearHam=((float)hamTrain.wordCounts.get(key)/hamTrain.getFileCount());
        float probSpamWord=probAppearSpam/(probAppearHam+probAppearSpam);
        System.out.println(probSpamWord);
        spamProb.put(key,probSpamWord);
        System.out.println(key);
      }
      //fileOut.println(key + ": " + count);
    }

    File testDir=new File("data/test/spam");
    File[] contents = testDir.listFiles();
    for (File current: contents) {
      ArrayList<String> wordsInFile = new ArrayList<String>();
      int spamChance=0;
      int totalSize=0;
      Scanner scanner = new Scanner(current);
      scanner.useDelimiter("'|\\s|\"|,|:|!|\\?|\\.|-|\'");
      while (scanner.hasNext()) {
        String word = scanner.next().toLowerCase();
        if (spamProb.containsKey(word)) {
          wordsInFile.add(word);
          totalSize++;
          //System.out.println(word);
        }
      }

      System.out.println(current);
      System.out.println(totalSize);

    }
    //System.out.println(totalSize);
  }
}
