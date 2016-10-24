package com.moon.fire.goapp;

/**
 * Created by firemoonpc_11 on 12-07-2016.
 */
public class BookedModal {

    String doctor_id;



    String clinic_add;
    String doctor_name;
    String doctor_img_url;





 public BookedModal(String doctor_id, String doctor_name, String clinic_add, String doctor_img_url)
    {
        this.doctor_name=doctor_name;
        this.clinic_add=clinic_add;
        this.doctor_img_url=doctor_img_url;
    }

    public String getClinic_add() {
        return clinic_add;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getDoctor_img_url() {
        return doctor_img_url;
    }
}
