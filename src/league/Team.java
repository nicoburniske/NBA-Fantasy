package league;

/**
 * Wrapper class for Team data.
 */
public class Team {
    private String Name;
    private double Age;
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

    public Team(String name, double age,  int FTA, double FT_Percentage, int twoPoint_Attempts, double twoPoint_Percentage, int threePoint_Attempts, double threePoint_Percentage, double EFG_Percentage, double TS_Percentage, double PPG, double RPG, double APG, double SPG, double BPG, double TOPG) {
        Name = name;
        Age = age;
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
    }

    public String getName() {
        return Name;
    }

    public double getAge() {
        return Age;
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
}
