package solution.strategies;

public class gitStrategyInfo {
    public StrategyInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    private String name;

    private long time;

    private int uses;

}
