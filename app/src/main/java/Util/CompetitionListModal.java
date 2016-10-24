package Util;

/**
 * Created by firemoonpc_11 on 30-06-2016.
 */
public class CompetitionListModal {


    String compId;
    String timeLeft;
    String score;
    String rank;
    String gameName;
    String logoUrl;

    public String getDownloadLink() {
        return downloadLink;
    }

    String downloadLink;

 public CompetitionListModal(String compId, String time, String gameName, String logoUrl,String downloadLink){

        this.timeLeft=time;
        this.compId=compId;
        this.gameName=gameName;
        this.logoUrl=logoUrl;
     this.downloadLink=downloadLink;

    }


    public String getTimeLeft() {
        return timeLeft;
    }



    public String getGameName() {
        return gameName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getCompId() {
        return compId;
    }

}
