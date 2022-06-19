package com.pritul.quoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CopyListener{
    RecyclerView recycle_home;
    RequestManager manager;
    QuoteRecyclerAdapter adapter;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle_home=findViewById(R.id.recycle_home);

        manager =new RequestManager(this);
        manager.getAllQuotes(listener);
        dialog =new ProgressDialog(this);
        dialog.setTitle("loading....");
        dialog.show();

    }
    private final QuoteResponseListener listener =new QuoteResponseListener() {
        @Override
        public void didFetch(List<QuoteResponse> responses, String message) {
            showData(responses);
            dialog.dismiss();
        }

        @Override
        public void diderror(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(List<QuoteResponse> responses) {
        recycle_home.setHasFixedSize(true);
        recycle_home.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter =new QuoteRecyclerAdapter(MainActivity.this,responses,this);
        recycle_home.setAdapter(adapter);
    }

    @Override
    public void onCopyClicked(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data=ClipData.newPlainText("copies data",text);
        clipboardManager.setPrimaryClip(data);
        Toast.makeText(MainActivity.this, "copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}