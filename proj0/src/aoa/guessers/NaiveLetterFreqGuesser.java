package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // Finished: Fill in this method.
        Map<Character, Integer> m = new HashMap<>();
        for (String w : words){
            for (int i = 0; i < w.length(); i++){
                char c = w.charAt(i);
                if (!m.containsKey(c)){
                    m.put(c,1);
                }else{
                    m.put(c,m.get(c)+1);
                }
            }
        }
        return m;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        // Tree map stores keys in sorted order
        Map<Character, Integer> m = new TreeMap<>();
        Map<Character, Integer> freq = getFrequencyMap();
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

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
