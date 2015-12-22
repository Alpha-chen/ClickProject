package com.example.administrator.myview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xupangen
 * on 2015/12/17.
 * e-mail: xupangen@ffrj.net
 */
public class MensesBodyInfoNodes {

    private ArrayList<MensesBodyInfoNode> basketItemNodes = new ArrayList<MensesBodyInfoNode>();

    public MensesBodyInfoNodes() {
        super();
    }

    public ArrayList<MensesBodyInfoNode> getBasketItemNodes() {
        return basketItemNodes;
    }

    public void setBasketItemNodes(ArrayList<MensesBodyInfoNode> basketItemNodes) {
        this.basketItemNodes = basketItemNodes;
    }

    public MensesBodyInfoNodes(String json) {
        super();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json);
            if (null != jsonArray) {
                basketItemNodes = new ArrayList<MensesBodyInfoNode>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if (null != jsonObject) {
                        MensesBodyInfoNode basketItemNode = new MensesBodyInfoNode(jsonObject);
                        basketItemNodes.add(basketItemNode);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
