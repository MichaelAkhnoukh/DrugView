/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WebScrapper{

    Medicine medicine;
    Context context;
    String ScanResult;

    public WebScrapper(String ScanResult) {
        String DrugName = ScanResult.replaceAll("[^a-zA-Z]", " ").replaceAll("(^|\\s+)[a-zA-Z](\\s+|$)", " ").replaceAll(" ", "");

        final String url =
                "http://www.drugeye.pharorg.com/htmlv/1.aspx?q=" + DrugName;

        medicine = new Medicine();
        medicine.drugName = DrugName;


        try {
            final Document document = Jsoup.connect(url).get();
            Log.d("docc", document.toString());

            //table tr:eq(1) > td"
            for (Element row : document.select(
                    "table tr:eq(1) > td")) {
                if (row.select("td:nth-of-type(1)").text().equals("")) {
                    continue;
                } else {
                    final String GenericName =
                            row.select("td:nth-of-type(1)").text();


                    medicine.Generic = GenericName;
                    break;
                }

            }
            for (Element row : document.select(
                    "table tr:eq(3) > td")) {
                if (row.select("td:nth-of-type(1)").text().equals("")) {
                    continue;
                } else {
                    final String Price
                            = row.select("td:nth-of-type(1)").text();

                    medicine.GenericPrice = Price;
                    break;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final String GenericUrl =
                "http://www.drugeye.pharorg.com/htmlv/similars.aspx?q=" + medicine.Generic;

        try {
            final Document document = Jsoup.connect(GenericUrl).get();

            for (Element row : document.select(
                    "table tr:eq(1) > td")) {
                if (row.select("td:nth-of-type(1)").text().equals("")) {
                    continue;
                } else {
                    final String TradeName2 =
                            row.select("td:nth-of-type(1)").text();
                    medicine.SimilarsPrices.add(TradeName2);


                }
            }
            for (Element row : document.select(
                    "table tr:eq(0) > td")) {
                if (row.select("td:nth-of-type(1)").text().equals("")) {
                    continue;
                } else {
                    final String TradeName2 =
                            row.select("td:nth-of-type(1)").text();
                    medicine.Similars.add(TradeName2);


                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Log.d("Drugs", "Drug: "+ScanResult+ " Price: "+medicine.GenericPrice);
        Log.d("Drugs", "Generic: " + medicine.Generic);
        Log.d("Drugs", "Similars: " + medicine.Similars);
    }

    public Medicine getMedicine (){
        return this.medicine;
    }
    
}
