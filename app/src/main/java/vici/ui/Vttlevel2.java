/* 

 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vici.ui;

//import com.example.android.apis.R;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




/**
 * Sample code that invokes the speech recognition intent API.
 */
public class Vttlevel2 extends Activity {
	public static String returnn;
	String aasd;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    AudioManager audMangr;
  
    
 

    /**
     * Called with the activity is first created.
     * @return 
     */
  
    public void onCreate(Bundle savedInstanceState) 
  {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.);
        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
           // speakButton.setOnClickListener(this);
        	 startVoiceRecognitionActivity();
        }
        else
        {Toast.makeText(Vttlevel2.this, "audio manager absent", Toast.LENGTH_SHORT).show();}
    }
   
	public class asdd
    {
    	String asd=aasd;
    }
    /**
     * Handle the click on the start recognition button.
     */
   

    /**
     * Fire an intent to start the speech recognition activity.
     */
   public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    
   }
    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            ListView lv = new ListView(this);
            lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
           setContentView(lv);
        	//String matches = data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
          //  aasd=matches.get(0);
        	Toast.makeText(Vttlevel2.this, matches.get(0), Toast.LENGTH_SHORT).show();
        /*    if(matches.get(0).equalsIgnoreCase("music")|| matches.get(0).equalsIgnoreCase("music player"))
            {
            	//Toast.makeText(Vtt.this, matches.get(0), Toast.LENGTH_SHORT).show();
            	//Intent musicp = new Intent("android.intent.action.MUSIC_PLAYER");
            	finish();
            	Intent musicp = new Intent(getApplicationContext(),Music.class);
            	startActivity(musicp);
            	
            }
         */
        returnn=matches.get(0);
        finish();
    	Intent myintent=new Intent( Vttlevel2.this, diary.class);

        startActivity(myintent);
		
            super.onActivityResult(requestCode, resultCode, data);
      //  finish();
        System.out.println(""+data);
    }
    
   
}
   
}
