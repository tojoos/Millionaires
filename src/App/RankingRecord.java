package App;

public class RankingRecord {
    private String name;
    private String winning;
    private String date;
    private int points;
    private String script;

    public RankingRecord(String winning, int points, String date, String name, String script) {
        this.name = name;
        this.winning = winning;
        this.date = date;
        this.points = points;
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWinning() {
        return winning;
    }

    public void setWinning(String winning) {
        this.winning = winning;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
