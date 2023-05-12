package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> wordsPattern = wordsMatchesPattern(pattern);
        /* First condition: char c in guess is contained in pattern
        char c cannot appear else where than pattern
        remove those that contains additional char at other position.
        */
        // Split character in guess into contained in pattern and not contained in pattern.
        List<Character> has = new ArrayList<>();
        List<Character> notHave = new ArrayList<>();
        for (char c:guesses){
            Boolean hasLetter= false;
            for (int i = 0; i<pattern.length(); i++){
                if (pattern.charAt(i)==c) {
                    hasLetter=true;
                    break;
                }
            }
            if (hasLetter){
                has.add(c);
            }else{
                notHave.add(c);
            }
        }
        //Find words that fit the condition of not having guessed wrong letter and no additional guessed right letter.
        List<String> wordsFit = new ArrayList<>();
        for (String word: wordsPattern){
            Boolean letterContainNoFalse = true;
            Boolean noAdditionalLetter = true;
            // If words contains guessed wrong letter, then it should not be included
            for (Character c: notHave){
                for (int i=0; i<word.length();i++){
                    if (word.charAt(i)==c){
                        letterContainNoFalse = false;
                        break;
                    }
                }
            }
            // If words contains guessed right letter in other position, it should not be included.
            for (Character cInHas: has){
                for (int i = 0; i<word.length();i++){
                    char cInWord = word.charAt(i);
                    char cInPattern = pattern.charAt(i);
                    if (cInHas==cInWord && cInPattern != cInWord){
                        noAdditionalLetter = false;
                        break;
                    }
                }
            }
            if (letterContainNoFalse && noAdditionalLetter){
                wordsFit.add(word);
            }
        }
        // Compute frequency map.
        Map<Character, Integer> pagaFreqMap = freqMap(wordsFit);
        Character letter = getCharacter(pagaFreqMap, guesses);
        return letter;
    }

    // Returns the letter with the largest frequency
    public char getCharacter(Map<Character, Integer> freq, List<Character> guesses) {
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

    // Get words that matches the pattern
    public List<String> wordsMatchesPattern(String pattern){
        int l = pattern.length();
        List<String> wordsMatch = new ArrayList<>();

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
        return wordsMatch;
    }

    // Get frequency map of a list of words.
    public Map<Character, Integer> freqMap(List<String> words){
        Map<Character,Integer> wordFreqMap = new HashMap<>();
        for (String elem : words){
            for (int i=0; i< elem.length(); i++){
                char c = elem.charAt(i);
                if (!wordFreqMap.containsKey(c)){
                    wordFreqMap.put(c,1);
                }else{
                    wordFreqMap.put(c,wordFreqMap.get(c)+1);
                }
            }
        }
        return wordFreqMap;
    }
    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
