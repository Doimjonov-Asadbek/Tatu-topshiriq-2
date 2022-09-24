package app.app.aplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductAdd extends AppCompatActivity {
    private DataBaseHelper sqlData;
    private ListView list_item;
    AdapterList adapter;
    String tit1 = "0", product_name,onedata;
    ArrayList<DataResponce> dataModalArrayList;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        sqlData = new DataBaseHelper(this);
        list_item = findViewById(R.id.list_item);
        SearchView searchView = findViewById(R.id.usersearchView);
        ImageView imageView = findViewById(R.id.imageView);

        dataModalArrayList = new ArrayList<>();
        adapter = new AdapterList(dataModalArrayList, this);
        list_item.setDivider(null);
        list_item.setDividerHeight(20);

        SharedPreferences sharedPreferences1 = this.getSharedPreferences("studentfile.text", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences1.edit();

        SharedPreferences filterdata = this.getSharedPreferences("studentfile.text",
                Context.MODE_PRIVATE);
        product_name = filterdata.getString("txt", "");

        SharedPreferences sharedPreferences = this.getSharedPreferences("studentfile.text", Context.MODE_PRIVATE);
        String tit = sharedPreferences.getString("txt", "");
        onedata = filterdata.getString("one", "");
        setTitle(tit);
        if (onedata.equals("")) {
            setTitle("All Product");
            sqlData.SqlInsert("book","100","0","2000-01-01","0","0","ALL");
            editor.putString("one","1");
            editor.putString("txt", getString(R.string.all));
            product_name = getString(R.string.all);
            editor.apply();
        }
        readsql();
        ccheckdata();

        searchView.setOnCloseListener(() -> {
            searchView.setQuery("", false);
            return false;
        });

        searchView.setOnClickListener(v -> searchView.setIconified(false));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    String text = query.substring(1);
                    adapter.getFilter().filter(text);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    String text = newText.substring(1);
                    adapter.getFilter().filter(text);
                }
                return true;
            }
        });

        imageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(ProductAdd.this, imageView);
            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.all:
                        setTitle("All Product");
                        editor.putString("txt", getString(R.string.all));
                        product_name = getString(R.string.all);
                        editor.apply();dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        break;
                    case R.id.food:
                        setTitle("Food");
                        editor.putString("txt", getString(R.string.food));
                        product_name = getString(R.string.food);
                        editor.apply();dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        break;
                    case R.id.technique:
                        setTitle("Technique");
                        editor.putString("txt", getString(R.string.technique));
                        product_name = getString(R.string.technique);
                        editor.apply();dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        break;
                    case R.id.clothing:
                        setTitle("Clothing");
                        editor.putString("txt", getString(R.string.clothing));
                        product_name = getString(R.string.clothing);
                        editor.apply();dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        break;
                    case R.id.raw_material:
                        setTitle("Raw Material");
                        editor.putString("txt", getString(R.string.raw_material));
                        product_name = getString(R.string.raw_material);
                        editor.apply();dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            Dialog dialog01 = new Dialog(this);
            dialog01.setContentView(R.layout.popupadd);
            dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog01.setCancelable(true);
            EditText edipopuptitle = dialog01.findViewById(R.id.edipopuptitle);
            EditText edipopupyouword = dialog01.findViewById(R.id.edipopupyouword);
            Button btnpopupsave = dialog01.findViewById(R.id.btnpopupsave);
            Button btnpopupmenu = dialog01.findViewById(R.id.btnpopupmenu);
            CheckBox checkBoxpopup = dialog01.findViewById(R.id.checkBoxpopup);

            btnpopupmenu.setOnClickListener(v1 -> {
                PopupMenu popupMenu = new PopupMenu(this, btnpopupmenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.all:
                            btnpopupmenu.setText(getString(R.string.all));
                            break;
                        case R.id.food:
                            btnpopupmenu.setText(getString(R.string.food));
                            break;
                        case R.id.technique:
                            btnpopupmenu.setText(getString(R.string.technique));
                            break;
                        case R.id.clothing:
                            btnpopupmenu.setText(getString(R.string.clothing));
                            break;
                        case R.id.raw_material:
                            btnpopupmenu.setText(getString(R.string.raw_material));
                            break;
                    }
                    return true;
                });
                popupMenu.show();
            });
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH/mm", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            btnpopupsave.setOnClickListener(v1 -> {
                String title = edipopuptitle.getText().toString();
                String youword = edipopupyouword.getText().toString();
                String wordprice = "0";
                String check = "0";
                String product = btnpopupmenu.getText().toString();
                String notes = "0";
                if (checkBoxpopup.isChecked()) {
                    check = "1";
                }
                else {
                    check = "0";
                }
                if (title.equals("") || youword.equals("")) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }else {
                    boolean con = sqlData.SqlInsert(title, youword, wordprice, currentDateandTime, notes,check,product);
                    if (con==true) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        setTitle("All Product");
                        editor.putString("txt", getString(R.string.all));
                        editor.apply();
                        product_name = getString(R.string.all);
                        dataModalArrayList.clear();
                        list_item.setAdapter(null);
                        readsql();
                        dialog01.dismiss();
                    }   else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog01.show();
        });
    }
    private void readsql() {
        Cursor cursor = sqlData.ReadSql2();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String youword = cursor.getString(2);
                String wordprice = cursor.getString(3);
                String meaning = cursor.getString(4);
                String notes = cursor.getString(5);
                String check = cursor.getString(6);
                String product = cursor.getString(7);
                if (product_name.equals("ALL")) {
                    dataModalArrayList.add(new DataResponce(id, title, youword, wordprice, meaning, notes, check, product));
                }else {
                    if (product_name.equals(product)) {
                        dataModalArrayList.add(new DataResponce(id, title, youword, wordprice, meaning, notes, check, product));
                    }
                }
            }
            list_item.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    private void ccheckdata(){
        SharedPreferences sharedPreferences2 = this.getSharedPreferences("studentfile1.text", Context.MODE_PRIVATE);
        tit1 = sharedPreferences2.getString("txt1", "");
        if (tit1.equals("")) {
            tit1 = "0";
        }
        if(tit1.equals("1")){
            tit1 = "0";
            SharedPreferences sharedPreferences1 = this.getSharedPreferences("studentfile1.text", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("txt1", "0");
            editor.apply();
            dataModalArrayList.clear();
            list_item.setAdapter(null);
            readsql();
            ccheckdata();

        }else {
            new android.os.Handler(Looper.getMainLooper()).postDelayed(this::ccheckdata,2000);
        }
    }
}