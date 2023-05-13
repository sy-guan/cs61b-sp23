package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.io.File;
import java.util.List;


public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        if (wordLength < 1){
            throw new IllegalArgumentException("Word length illegal!");
        }

        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (words.isEmpty()){
            throw new IllegalStateException("No words found of length!");
        }

        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int occur = 0;
        for (int i=0; i<chosenWord.length();i++){
            if (chosenWord.charAt(i) == letter){
                occur++;
                pattern = pattern.substring(0,i)+letter+pattern.substring(i+1);
            }
        }
        return occur;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
