package my.edu.unikl.gotravel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {

    private WebView aboutWebView, developerWebView, supervisorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // get action bar
        ActionBar actionBar = getSupportActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);


        aboutWebView = (WebView) findViewById(R.id.about);
        aboutWebView.getSettings().setJavaScriptEnabled(true);
        developerWebView = (WebView) findViewById(R.id.developer);
        developerWebView.getSettings().setJavaScriptEnabled(true);
        supervisorView = (WebView) findViewById(R.id.supervisor);
        supervisorView.getSettings().setJavaScriptEnabled(true);

        aboutWebView.setBackgroundColor(0);
        developerWebView.setBackgroundColor(0);
        supervisorView.setBackgroundColor(0);

        String about = getResources().getString(R.string.about);
        aboutWebView.loadData(about, "text/html", "UTF-8");

        String developer = getResources().getString(R.string.developer);
        developerWebView.loadData(developer, "text/html", "UTF-8");

        String supervisor = getResources().getString(R.string.supervisor);
        supervisorView.loadData(supervisor, "text/html", "UTF-8");
    }

}
