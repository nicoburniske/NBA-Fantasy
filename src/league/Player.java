package league;

public class Player {
    private String Name;
    //private int Season;
    private String Team;
    private int Age;
    private int Games_Played;
    private double MPG;
    private double Minutes_Percentage;
    private double Usage_Percentage;
    private double Turnover_Ratio;
    private int FTA;
    private double FT_Percentage;
    private int TwoPoint_Attempts;
    private double TwoPoint_Percentage;
    private int ThreePoint_Attempts;
    private double ThreePoint_Percentage;
    private double EFG_Percentage;
    private double TS_Percentage;
    private double PPG;
    private double RPG;
    private double APG;
    private double SPG;
    private double BPG;
    private double TOPG;
    private double ORTG;
    private double DRTG;


    public Player(String name, String team, int age, int games_Played, double MPG, double minutes_Percentage, double usage_Percentage, double turnover_Ratio, int FTA, double FT_Percentage, int twoPoint_Attempts, double twoPoint_Percentage, int threePoint_Attempts, double threePoint_Percentage, double EFG_Percentage, double TS_Percentage, double PPG, double RPG, double APG, double SPG, double BPG, double TOPG, double ORTG, double DRTG) {
        Name = name;
        Team = team;
        Age = age;
        Games_Played = games_Played;
        this.MPG = MPG;
        Minutes_Percentage = minutes_Percentage;
        Usage_Percentage = usage_Percentage;
        Turnover_Ratio = turnover_Ratio;
        this.FTA = FTA;
        this.FT_Percentage = FT_Percentage;
        TwoPoint_Attempts = twoPoint_Attempts;
        TwoPoint_Percentage = twoPoint_Percentage;
        ThreePoint_Attempts = threePoint_Attempts;
        ThreePoint_Percentage = threePoint_Percentage;
        this.EFG_Percentage = EFG_Percentage;
        this.TS_Percentage = TS_Percentage;
        this.PPG = PPG;
        this.RPG = RPG;
        this.APG = APG;
        this.SPG = SPG;
        this.BPG = BPG;
        this.TOPG = TOPG;
        this.ORTG = ORTG;
        this.DRTG = DRTG;
    }

    public String getName() {
        return Name;
    }

    /*public int getSeason() {
        return Season;
    }*/

    public String getTeam() {
        return Team;
    }

    public int getAge() {
        return Age;
    }

    public int getGames_Played() {
        return Games_Played;
    }

    public double getMPG() {
        return MPG;
    }

    public double getMinutes_Percentage() {
        return Minutes_Percentage;
    }

    public double getUsage_Percentage() {
        return Usage_Percentage;
    }

    public double getTurnover_Ratio() {
        return Turnover_Ratio;
    }

    public int getFTA() {
        return FTA;
    }

    public double getFT_Percentage() {
        return FT_Percentage;
    }

    public int getTwoPoint_Attempts() {
        return TwoPoint_Attempts;
    }

    public double getTwoPoint_Percentage() {
        return TwoPoint_Percentage;
    }

    public int getThreePoint_Attempts() {
        return ThreePoint_Attempts;
    }

    public double getThreePoint_Percentage() {
        return ThreePoint_Percentage;
    }

    public double getEFG_Percentage() {
        return EFG_Percentage;
    }

    public double getTS_Percentage() {
        return TS_Percentage;
    }

    public double getPPG() {
        return PPG;
    }

    public double getRPG() {
        return RPG;
    }

    public double getAPG() {
        return APG;
    }

    public double getSPG() {
        return SPG;
    }

    public double getBPG() {
        return BPG;
    }

    public double getTOPG() {
        return TOPG;
    }

    public double getORTG() {
        return ORTG;
    }

    public double getDRTG() {
        return DRTG;
    }
}
