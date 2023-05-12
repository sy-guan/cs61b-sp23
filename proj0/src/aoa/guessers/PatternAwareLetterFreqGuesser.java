package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character, Integer> freq = getFreqMapThatMatchesPattern(pattern);
        Map<Character, Integer> m = new TreeMap<>();

        if (freq.isEmpty()){
            return '?';
        }
        // Get largest frequency.
        int largest = 0;
        for (char w : freq.keySet()){
            int f = freq.get(w);
            if (!guesses.contains(w)){
                m.put(w,f);
                if (f>= largest) {
                    largest = f;
                }
            }
        }
        char letter = '\0';
        for (char w: m.keySet()){
            if (m.get(w) == largest){
                letter = w;
                break;
            }
        }
        return letter;
    }

    // Return a frequency map of words that matches pattern
    public Map<Character,Integer> getFreqMapThatMatchesPattern(String pattern){
        int l = pattern.length();
        List<String> wordsMatch = new ArrayList<>();

        // Get words that matches the pattern
        for (String word : words){
            Boolean match = true;
            if (word.length() != l) {
                match = false;
            }else{
                for (int i=0; i<l; i++){
                    if (pattern.charAt(i)!='-' && pattern.charAt(i)!=word.charAt(i)){
                        match = false;
                    }
                }
            }
            if (match == true){
                wordsMatch.add(word);
            }
        }
        //Get Frequency Map of words that matches the pattern
        Map<Character,Integer> mapMatches = new HashMap<>();
        for (String elem : wordsMatch){
            for (int i=0; i< l; i++){
                char c = elem.charAt(i);
                if (!mapMatches.containsKey(c)){
                    mapMatches.put(c,1);
                }else{
                    mapMatches.put(c,mapMatches.get(c)+1);
                }
            }
        }
        return mapMatches;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}