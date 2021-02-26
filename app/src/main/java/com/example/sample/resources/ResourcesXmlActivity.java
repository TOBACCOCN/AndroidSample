package com.example.sample.resources;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.example.sample.R;
import com.example.sample.util.ErrorPrintUtil;

import androidx.appcompat.app.AppCompatActivity;

public class ResourcesXmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_xml);

        XmlResourceParser xml = getResources().getXml(R.xml.book);
        try {
            while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xml.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xml.getName();
                    if (name.equals("book")) {
                        String price = xml.getAttributeValue(null, "price");
                        String date = xml.getAttributeValue(1);
                        String bookName = xml.nextText();
                        XLog.d("PRICE: [%s], DATE: [%s], BOOK_NAME: [%s]", price, date, bookName);
                        Toast.makeText(this, "PRICE: " + price + ", DATE: " + date + ", BOOK_NAME: " + bookName, Toast.LENGTH_LONG).show();
                    }
                }
                xml.next();
            }
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(e);
        }
    }
}