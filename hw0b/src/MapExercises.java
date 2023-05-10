import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character,Integer> m = new HashMap<>();
        int i = 1;
        for (char c = 'a'; c <='z'; ++c){
            m.put(c,i);
            i+=1;
        }
        return m;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer,Integer> m = new HashMap<>();
        for (int elem : nums){
            m.put(elem, (int) Math.pow(elem,2));
        }
        return m;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> m = new HashMap<>();
        for (String elem:words){
            if (!m.keySet().contains(elem)) {
                m.put(elem, 1);
            }else{
                m.put(elem,m.get(elem)+1);
            }
        }
        return m;
    }
}
