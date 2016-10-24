package Util;

/**
 * Created by firemoonpc_11 on 30-06-2016.
 */
public class LeaderboardGameListModal {



    String bundle_id;
    String game_name;
    String game_image;

    public String getIs_country_split() {
        return is_country_split;
    }

    String is_country_split;




 public LeaderboardGameListModal(String bundle_id, String game_name, String game_image, String is_country_split){

        this.bundle_id=bundle_id;
        this.game_name=game_name;
        this.game_image=game_image;
this.  is_country_split=is_country_split;

    }

    public String getBundle_id() {
        return bundle_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public String getGame_image() {
        return game_image;
    }

}
