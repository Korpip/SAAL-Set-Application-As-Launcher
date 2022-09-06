package com.saal.pip;

import android.content.ActivityNotFoundException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.net.Uri;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	SharedPreferences menaPref;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		menaPref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
		editor = menaPref.edit();
		String getselected = menaPref.getString("selectedItem", null); // getting String}
		if (getselected != null) {
			openOtherApp(getApplicationContext(), getselected);
		}
	}
	public static boolean openOtherApp(Context context, String packageName) {
    PackageManager manager = context.getPackageManager();
     try {
        Intent intent = manager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            //the app is not installed
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                //throw new ActivityNotFoundException();
                return false;
            }

         }
         intent.addCategory(Intent.CATEGORY_LAUNCHER);
         context.startActivity(intent);
         return true;
    } catch (ActivityNotFoundException e) {
        return false;
    }

}

}