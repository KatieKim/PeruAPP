package com.app.phr.peru.peruphr_app.JAVA;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.phr.peru.peruphr_app.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FragmentPHR extends Fragment {
    private PHR phr;
    private  boolean getPHR = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private XmlParser parser;
    private String mID = "";
    private String mKey = "";
    private requestPHRTask requestTask = null;
    private boolean flag = false;
    private String Phr;
    TableLayout tb1, tb2;
    public FragmentPHR() {
        parser = new XmlParser();
    }

    private void requestPHR() {  //request phr data to server
        Log.d("frag", "request");
        if (requestTask != null) {
            return;
        }
        mID = preferences.getString(PreferencePutter.PREF_ID, "error");
        mKey = preferences.getString(PreferencePutter.PREF_KEY, "error");
        requestTask = new requestPHRTask();
        requestTask.execute((Void) null);
    }

    private void parse_putPHR(String result) {  //parse and put phr data in activirty through using xml parser
        phr = parser.getPhrObject(result);
    }


    public class requestPHRTask extends AsyncTask<Void, Void, String> {  //thread to connect to server
        private HTTPClient client;
        private XmlWriter writer;

        @Override
        protected void onPreExecute() {

            writer = new XmlWriter();
            client = new HTTPClient();
            Log.d("http send", writer.getForPhr(mID, mKey).toString());
            client.setDoc(writer.getForPhr(mID, mKey));
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            // Simulate network access.
            result = client.connect();
            result = "<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<Response StatusCode=\"202\">\n" +
                    "  <PatientSummary>\n" +
                    "    <Allergys>\n" +
                    "      <Allergy>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>home dust allergy</Value>\n" +
                    "      </Allergy>\n" +
                    "      <Allergy>\n" +
                    "        <Date>2016-08-10</Date>\n" +
                    "        <Value>nose allergy</Value>\n" +
                    "      </Allergy>\n" +
                    "    </Allergys>\n" +
                    "    <AdverseReactions>\n" +
                    "      <AdverseReaction>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>타이레놀 못먹음</Value>\n" +
                    "      </AdverseReaction>\n" +
                    "    </AdverseReactions>\n" +
                    "    <PastHistorys>\n" +
                    "      <PatientHistory>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>출산자녀수: 1</Value>\n" +
                    "      </PatientHistory>\n" +
                    "    </PastHistorys>\n" +
                    "    <FamilyHistorys>\n" +
                    "      <FamilyHistory>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>Alcohol 여부: Yes</Value>\n" +
                    "      </FamilyHistory>\n" +
                    "    </FamilyHistorys>\n" +
                    "    <SosicalHistorys>\n" +
                    "      <SosicalHistory>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>Alcohol 여부: Yes Alcohol 여부: Yes</Value>\n" +
                    "      </SosicalHistory>\n" +
                    "    </SosicalHistorys>\n" +
                    "    <PhysicalMeasurement>\n" +
                    "      <Height>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>173.0</Value>\n" +
                    "      </Height>\n" +
                    "      <Weight>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>78.0</Value>\n" +
                    "      </Weight>\n" +
                    "      <BloodPressure>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>88-122</Value>\n" +
                    "      </BloodPressure>\n" +
                    "      <Pulse>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>81</Value>\n" +
                    "      </Pulse>\n" +
                    "      <BloodType>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>AB</Value>\n" +
                    "      </BloodType>\n" +
                    "    </PhysicalMeasurement>\n" +
                    "    <Medications>\n" +
                    "      <Medication>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Code>k-1234</Code>\n" +
                    "        <Value>비타500</Value>\n" +
                    "      </Medication>\n" +
                    "    </Medications>\n" +
                    "    <TeleMedicines>\n" +
                    "      <TeleMedicine>\n" +
                    "        <Date>2016-07-31</Date>\n" +
                    "        <Value>알레르기 진찰 후 비타500 투약 완료.</Value>\n" +
                    "      </TeleMedicine>\n" +
                    "    </TeleMedicines>\n" +
                    "  </PatientSummary>\n" +
                    "  <!--Patient Example 1-->\n" +
                    "</Response>";

            if (result.equals("connection error")) {
                setLayoutWithout_Net();
            } else {
                int response = parser.checkResponse(result);
                //save result in object and show to activity.
                if (response == 100) {
                    Log.d("phr", "response msg : 100");
                    //layout = R.layout.fragment_phr;
                    editor = preferences.edit();
                    editor.putString(PreferencePutter.PHR, result);
                    editor.commit();
                    Phr = result;
                    getPHR = true;
                } else if (response == 202) {
                    //show 진료된 기록이 없습니다.
//               Log.d("phr", "response msg : 100");
                    editor = preferences.edit();
                    editor.putString(PreferencePutter.PHR, "non_record");
                    editor.commit();
                    getPHR = false;

                } else {
                    Log.d("phr","response 500");
                    setLayoutWithout_Net();
                }
            }
            flag = true;
            requestTask = null;
            return "";

        }

        @Override
        protected void onCancelled() {
            flag = true;
            requestTask = null;
            //showProgress(false);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);

        if (NetworkUtil.getConnectivityStatusBoolean((getActivity()))) {
            requestPHR();

            while (!flag) {
            }
            flag = false;
// 데이터 receive 성공 유무 판단 !!
            Log.d("frag", "get phr");
        } else {
            setLayoutWithout_Net();
        }
    }
    private void setLayoutWithout_Net(){
        Log.d("frag", "networkless");
        Phr = preferences.getString(PreferencePutter.PHR, "null");
        if (Phr.equals("null")) {
            //show need to networ for receiving data
            Log.d("frag", "null phr");
            getPHR = false;
        } else if (Phr.equals("non_record")) {
            getPHR = false;
        } else {
            //parsing result & show
            parse_putPHR(Phr);  //insert phr into layout
            Log.d("non net", Phr);
            getPHR = true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView;
        if(getPHR) {
            rootView = inflater.inflate(R.layout.phr_view_layout, container, false);

            phr = new PHR();
            tb1 = (TableLayout) rootView.findViewById(R.id.table1);
            tb1.setStretchAllColumns(true);
            tb2 = (TableLayout) rootView.findViewById(R.id.table2);
            tb2.setStretchAllColumns(true);

            setTb1Title();
            Parsing();
            Parsing();
            Parsing();
            setTb1Title();
            Parsing();
            Parsing();
            Parsing();
            setTb1Title();
            Parsing();
            Parsing();
            Parsing();
            setTb1Title();
            Parsing();
            Parsing();
            Parsing();

        }
        else{
            rootView = inflater.inflate(R.layout.non_phr, container, false);
        }
        return rootView;

    }

    public void failTb() {

        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tb1_t.setText("fail");
    }

    public void Parsing() {
        try {

            String xml = Phr;

            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //노드 읽어오기
            Element order = doc.getDocumentElement();
            //노드 선언
            NodeList allergys = order.getElementsByTagName("Allergy");
            //노드 length

            for (int i = 0; i < allergys.getLength(); i++) {
                NodeList allergys_date = order.getElementsByTagName("Date");
                NodeList allergys_value = order.getElementsByTagName("Value");
                Node a_date = allergys_date.item(i);
                Node a_value = allergys_value.item(i);
                Node value = a_value.getFirstChild();
                Node date = a_date.getFirstChild();
                //string에 값 넣기
                phr.setAllergy_v(value.getNodeValue());
                phr.setAllergy_d(date.getNodeValue());

                setTb1();

            }
        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setTb1Title() {
        TableRow tr_ti = new TableRow(getActivity());

        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.argb(255,122,178,212));
        tb1_t.setPadding(40,10,0,10);
        tb1_t.setText("Allergy");
        tb1_t.setTextColor(Color.WHITE);

        tb1_t.setTextSize(22);
        tr_ti.addView(tb1_t);
        tb1.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TableRow tr_head = new TableRow(getActivity());
        tr_head.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView date = new TextView(getActivity());
        date.setText("Date");
        date.setTextColor(Color.BLACK);
        date.setTextSize(18);
        date.setPadding(40, 10, 0, 10);
        tr_head.addView(date);
        TextView value = new TextView(getActivity());
        value.setText("Value");
        value.setTextColor(Color.BLACK);
        value.setPadding(40, 10, 0, 10);
        value.setTextSize(18);
        tr_head.addView(value);
        tb1.addView(tr_head, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setTb1() {
        TextView dateView = new TextView(getActivity());
        dateView.setText(phr.getAllergy_d());
        dateView.setPadding(40,0,0,0);
        TextView valueView = new TextView(getActivity());
        valueView.setText(phr.getAllergy_v());
        valueView.setPadding(40, 0, 0, 0);
        TableRow tr = new TableRow(getActivity());
        tr.setPadding(0,10,0,10);
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.addView(dateView);
        tr.addView(valueView);
        tb1.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
