package com.android.notebook;

public class userInfo{
    private int id;
    private String mytext;

    public userInfo(int id, String mytext){
        this.id=id;
        this.mytext=mytext;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMytext(String mytext) {
        this.mytext = mytext;
    }

    public String getMytext() {
        return mytext;
    }

    @Override
    public String toString() {
        String rester = "";
        String str = getMytext();
        if(str.length()<=20){
            rester = str;
        } else {
            for(int i = 0;i<=20;i++){
                rester += str.charAt(i);
            }
            rester += "...";
        }
        return "内容简介："+ rester;
    }
}

