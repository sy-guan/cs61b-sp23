package timing;

import java.util.List;

public class TimingData {

    private List<Integer> Ns;  // The size of the data structure, or how many elements it contains.
    private List<Double> times; // The total time required for all operations, in seconds.
    private List<Integer> opCounts; // The number of operations made during the experiment.

    public TimingData(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        this.Ns = Ns;
        this.times = times;
        this.opCounts = opCounts;
    }

    public List<Integer> getNs() {
        return this.Ns;
    }

    public List<Double> getTimes() {
        return this.times;
    }

    public List<Integer> getOpCounts() {
        return this.opCounts;
    }
}
