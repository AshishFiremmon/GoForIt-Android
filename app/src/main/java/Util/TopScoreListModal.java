package Util;

/**
 * Created by firemoonpc_11 on 30-06-2016.
 */
public class TopScoreListModal {


    String compId;
    String timeLeft;
    String gameName;


    String avtarUrl;

    public String getAvtarname() {
        return avtarname;
    }

    String avtarname;
    String rank;
    String score;

 public TopScoreListModal(String avtarname, String rank, String score, String logoUrl){

        this.rank=rank;
        this.avtarname=avtarname;
        this.score=score;
        this.avtarUrl=logoUrl;

    }

    public String getAvtarUrl() {
        return avtarUrl;
    }

    public String getRank() {
        return rank;
    }

    public String getScore() {
        return score;
    }

    public String getTimeLeft() {
        return timeLeft;
    }



    public String getGameName() {
        return gameName;
    }


    public String getCompId() {
        return compId;
    }

}
