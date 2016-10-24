package Util;

/**
 * Created by firemoonpc_11 on 30-06-2016.
 */
public class PrizeAmountListModal {


    public String getFirst_prize() {
        return first_prize;
    }

    public String getSecond_prize() {
        return second_prize;
    }

    public String getThird_prize() {
        return third_prize;
    }

    public String getFourth_prize() {
        return fourth_prize;
    }

    public String getFifth_prize() {
        return fifth_prize;
    }

    String first_prize;
    String second_prize;
    String third_prize;


    String fourth_prize;
    String fifth_prize;


 public PrizeAmountListModal(String first_prize, String second_prize, String third_prize, String fourth_prize,String fifth_prize){

        this.first_prize=first_prize;
        this.second_prize=second_prize;
        this.third_prize=third_prize;
        this.fourth_prize=fourth_prize;
     this.fifth_prize=fifth_prize;

    }



}
