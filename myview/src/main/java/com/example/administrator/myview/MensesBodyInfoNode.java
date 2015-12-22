package com.example.administrator.myview;

import org.json.JSONObject;

/**
 * 小秘密身体症状
 * Created by xupangen
 * on 2015/12/17.
 * e-mail: xupangen@ffrj.net
 */
public class MensesBodyInfoNode {

    private int id;
    private String name;
    private int iconResourceId;

    public MensesBodyInfoNode() {
    }

    public MensesBodyInfoNode(JSONObject jsonObject) {
        super();
        if (null == jsonObject) {
            return;
        }
        this.id = jsonObject.optInt("id");
        this.name = jsonObject.optString("name");
        this.iconResourceId = jsonObject.optInt("iconResourceId");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public static String TAG_ALL = "["
            + "{'id': '10101','name': '腰疼','iconResourceId':'0'},"
            + "{'id': '10102','name': '腹胀','iconResourceId':'1'},"
            + "{'id': '10103','name': '便秘','iconResourceId':'2'},"
            + "{'id': '10104','name': '贪甜','iconResourceId':'3'},"
            + "{'id': '10105','name': '痘痘','iconResourceId':'4'},"
            + "{'id': '10106','name': '头痛','iconResourceId':'5'},"
            + "{'id': '10107','name': '乳房胀痛','iconResourceId':'6'},"
            + "{'id': '10108','name': '颈痛','iconResourceId':'7'},"
            + "{'id': '10109','name': '易怒','iconResourceId':'8'},"
            + "{'id': '10110','name': '失眠','iconResourceId':'9'},"
            + "{'id': '10111','name': '恶心','iconResourceId':'10'},"
            + "{'id': '10112','name': '白带异常','iconResourceId':'10'}]";

    @Override
    public String toString() {
        return "{'id':'" + this.id + "'," + "'name':'" + this.name + "'," + "'iconResourceId':'" + this.iconResourceId + "'}";
    }

}
