package aoa.choosers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in this constructor.
        if (wordLength < 1){
            throw new IllegalArgumentException("Word length illegal!");
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.isEmpty()){
            throw new IllegalStateException("No words found of length!");
        }
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        // Step 1: Find pattern of letter for each word, if does not match pattern existed, add to dict.
        Map<String, List<String>> patternMap = new TreeMap<>();
        for (String word : wordPool){
            String wordPattern = pattern;
            for (int i = 0; i<word.length(); i++){
                char w = word.charAt(i);
                if (w == letter && w != pattern.charAt(i)){
                    wordPattern = wordPattern.substring(0,i)+letter+wordPattern.substring(i+1);
                }
            }
            if (!patternMap.containsKey(wordPattern)){
                List<String> wordsOfAPattern = new ArrayList<>();
                wordsOfAPattern.add(word);
                patternMap.put(wordPattern, wordsOfAPattern);
            }else{
               patternMap.get(wordPattern).add(word);
            }
        }
        
        // Step 2: With the dict, find the pattern(s) with the largest family size.
        String largestPattern = "\0";
        int largestSize = 0;
        List<String> largestFamily = new ArrayList<>();
        for (String p:patternMap.keySet()){
            if (patternMap.get(p).size()>largestSize){
                largestPattern = p;
                largestSize = patternMap.get(p).size();
                largestFamily = patternMap.get(p);
            }
        }
        // Step 3: Update WordPool and pattern.
        pattern = largestPattern;
        wordPool = largestFamily;
        // Step 4: Return the occurrence of the letter in the new pattern.
        int occur = 0;
        for (int i=0; i<largestPattern.length();i++){
            if (letter == largestPattern.charAt(i)){
                occur+=1;
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
        String word = "\0";
        if (wordPool.size() == 1){
            word = wordPool.get(0);
        }else{
            int randomlyChosenWordNumber = StdRandom.uniform(1);
            word = wordPool.get(randomlyChosenWordNumber);
        }
        return word;
    }
}
