package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    private TimeSeries countsTS;
    private HashMap<String, TimeSeries> wordsHM;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordsHM = new HashMap();
        countsTS = new TimeSeries();

        readWordFile(wordsFilename);
        readCountsFile(countsFilename);
    }


    private void readWordFile(String wordFileName) {
        In wordFile = new In(wordFileName);

        while (wordFile.hasNextLine()) {
            String word = wordFile.readString();
            Integer year = wordFile.readInt();
            Double count = wordFile.readDouble();

            // Initialize a TimeSeries if the word does not exist yet.
            wordsHM.putIfAbsent(word, new TimeSeries());
            wordsHM.get(word).put(year, count);

            wordFile.readLine();
        }

        wordFile.close();
    }

    private void readCountsFile(String CountsFileName) {
        In countsFile = new In(CountsFileName);

        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            // Split the line into fields
            String[] fields = line.split(",");

            Integer year = Integer.valueOf(fields[0]);
            Double count = Double.valueOf(fields[1]);

            countsTS.put(year, count);
        }

        countsFile.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries historyTS = new TimeSeries(wordsHM.get(word), startYear, endYear);

        return historyTS;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries historyTS = new TimeSeries();
        // Copy TimeSeries for word in words HashMap into historyTS.
        historyTS.putAll(wordsHM.get(word));

        return historyTS;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries totalCountTS = new TimeSeries();
        // Copy Counts TimeSeries.
        totalCountTS.putAll(countsTS);

        return totalCountTS;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // Divide the count by total counts found in counts TimeSeries.
        TimeSeries weightHistoryTS = countHistory(word, startYear, endYear).dividedBy(countsTS);

        return weightHistoryTS;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // Get weightHistory by setting start year to MIN_YEAR, end year to MAX_YEAR.
        TimeSeries weightHistoryTS = weightHistory(word, MIN_YEAR, MAX_YEAR);

        return weightHistoryTS;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeightTS = new TimeSeries();

        // For word in words, sum every weight history of the word.
        for (String word : words) {
            TimeSeries temp = weightHistory(word, startYear, endYear);
            summedWeightTS = summedWeightTS.plus(temp);
        }

        return summedWeightTS;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedWeightTS = summedWeightHistory(words, MIN_YEAR, MAX_YEAR);

        return summedWeightTS;
    }
}
