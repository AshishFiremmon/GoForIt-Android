package Util;

/**
 * Created by firemoonpc_11 on 30-06-2016.
 */
public class GameListModal {

    String packageName;
    String timeLeft;
    String score;
    String rank;
    String gameName;
    String logoUrl;


    String enddate;

 public   GameListModal(String pakName, String time, String score, String rank, String gameName, String logoUrl,String enddate){

        this.packageName=pakName;
        this.timeLeft=time;
        this.score=score;
        this.rank=rank;
        this.gameName=gameName;
        this.logoUrl=logoUrl;
     this.enddate=enddate;

    }

    public String getPackageName() {
        return packageName;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public String getScore() {
        return score;
    }

    public String getRank() {
        return rank;
    }

    public String getGameName() {
        return gameName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
    public String getEnddate() {
        return enddate;
    }


}
