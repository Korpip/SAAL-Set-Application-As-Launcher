package com.saal.pip;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

	ListView listView;
	TextView text;
	TextView textView2;
	SharedPreferences menaPref;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menaPref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
		editor = menaPref.edit();
		String getselected = menaPref.getString("selectedItem", null); // getting String}

		// initialise layout
		listView = findViewById(R.id.listview);
		text = findViewById(R.id.totalapp);
		textView2 = findViewById(R.id.textview);
		if (getselected != null) {
			textView2.setText("You have selected : " + getselected);
		}
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selectedItem = (String) parent.getItemAtPosition(position);
				editor.putString("selectedItem", selectedItem);
				editor.apply();
				String getselected = menaPref.getString("selectedItem", null); // getting String

				textView2.setText("You have selected : " + getselected);
			}
		});
	}

	public void getallapps(View view) {
		// get list of all the apps installed
		List<ApplicationInfo> infos = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
		// create a list with size of total number of apps
		String[] apps = new String[infos.size()];
		int i = 0;
		// add all the app name in string list
		for (ApplicationInfo info : infos) {
			apps[i] = info.packageName;
			i++;
		}
		// set all the apps name in list view
		listView.setAdapter(new ArrayAdapter<String>(SettingActivity.this, android.R.layout.simple_list_item_1, apps));
		// write total count of apps available.
		text.setText(infos.size() + " Apps are installed");
	}

	@Override
	protected void onStart() {
		super.onStart();

	}
}