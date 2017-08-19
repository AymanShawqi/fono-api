package com.example.android.fono;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JSONArrayRequestListener {
    Button searchBtn;
    Button refreshBtn;
    RadioGroup optionRadioGb;
    RadioButton samsungRadioBtn;
    RecyclerView mobileRecycleView;
    ProgressBar mprogressBar;
    LinearLayout searchOptionLayout;
    Toolbar mToolbar;
    RecyclerView.LayoutManager mlayoutManager;
    EditText numEditText;
    List<Mobile> mobilesList;
    MobileAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }
    //initialize component
    void initUI(){
        mToolbar=(Toolbar) findViewById(R.id.toolbarId);
        optionRadioGb=(RadioGroup)findViewById(R.id.optionGroupId);
        samsungRadioBtn=(RadioButton)findViewById(R.id.samsungId) ;
        numEditText=(EditText)findViewById(R.id.editTextId);
        searchBtn=(RadioButton)findViewById(R.id.searchId);
        refreshBtn=(RadioButton)findViewById(R.id.refreshId);
        mobileRecycleView=(RecyclerView)findViewById(R.id.recyclerId);
        mprogressBar=(ProgressBar)findViewById(R.id.progressId);
        searchOptionLayout=(LinearLayout) findViewById(R.id.searchOptionId);
        mlayoutManager=new LinearLayoutManager(this);
        //
        setSupportActionBar(mToolbar);
        //
       // numEditText.setOnFocusChangeListener();
    }

    //menue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchId:
                handleSearch();
                return true;
            case R.id.refreshId:
                handleRefresh();
                        return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String handleRadioBtn(){
        RadioButton selectedBtn;
        String selectedBrand;
        int selectedId= optionRadioGb.getCheckedRadioButtonId();
        if(selectedId==R.id.anyBrandId){
           // Toast.makeText(this,"AnyBrand",Toast.LENGTH_LONG).show();
            return "";}
        selectedBtn=(RadioButton)findViewById(selectedId);
        selectedBrand=selectedBtn.getText().toString();
        return selectedBrand;
    }

    int handleEditText(){
        int n;
        String s=numEditText.getText().toString();
        if(s.equals(null)||s.equals(""))
            return -1;
        else {
            n=Integer.parseInt(s);
            if(n<1||n>100){
                Toast.makeText(this,"Enter number between 1 and 100",Toast.LENGTH_SHORT).show();
                numEditText.setText("");
            }
            return n;
        }
    }
   // menu option
    void handleSearch(){
        int num=handleEditText();
        String devicenumbers="100";
        boolean send=true;

        if(num>=1 && num<=100)
            devicenumbers=Integer.toString(num);
        else if(num!=-1)
            send=false;

        if(send){
            String selectedBrand = handleRadioBtn();
            hideSearchOption();
            hideList();
            showProgressBar();
            connectToFonoApi(selectedBrand,devicenumbers);
        }
    }

    void handleRefresh(){
        hideList();
        hideProgressBar();
        samsungRadioBtn.setChecked(true);
        numEditText.setText("");
        showSearchOption();
    }

//using internet to connect to fhono Api
    void connectToFonoApi(String selectedBrand,String devicenumbers){
        AndroidNetworking.get("https://fonoapi.freshpixl.com/v1/{option}")
                .addPathParameter("option","getlatest")
                .addQueryParameter("token","e88465adf2cb08dcb1245653b883772f9e39483bc2932c67")
                .addQueryParameter("brand",selectedBrand)
                .addQueryParameter("limit",devicenumbers)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(this);

    }

//override method of JSONArrayRequestListener which called from connectToFonoApi
    @Override
    public void onResponse(JSONArray response) {
        mobilesList=new ArrayList<>();
        for (int i=0;i<response.length();i++){
            String name="";
            try {
                JSONObject object=response.getJSONObject(i);
                name=object.getString("DeviceName");
                String brand=object.getString("Brand");
                String size=object.getString("size");
                String colors=object.getString("colors");
                Mobile mobile=new Mobile();
                mobile.setDeviceName(name)
                        .setBrand(brand)
                        .setSize(size)
                        .setColors(colors);
                mobilesList.add(mobile);
            } catch (JSONException e) {
                //e.printStackTrace();
                //Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(this,name+"has no brand ",Toast.LENGTH_SHORT).show();
            }
        }

        adapter=new MobileAdapter(this,mobilesList);
        mobileRecycleView.setLayoutManager(mlayoutManager);
        hideProgressBar();
        showList();
        mobileRecycleView.setAdapter(adapter);
    }

    @Override
    public void onError(ANError anError) {
        Toast.makeText(this,anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
    }

    //show/hide progressbar,search option ,recyclar view
    private void showProgressBar(){
        mprogressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mprogressBar.setVisibility(View.INVISIBLE);
    }
    private void showList(){
        mobileRecycleView.setVisibility(View.VISIBLE);}
    private void hideList(){mobileRecycleView.setVisibility(View.INVISIBLE);
    }
    private void showSearchOption(){ searchOptionLayout.setVisibility(View.VISIBLE);
    }
    private void hideSearchOption(){ searchOptionLayout.setVisibility(View.GONE);
    }








}
