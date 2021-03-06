package vici.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.ui.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vici.adapters.ResponsesAdapter;
import vici.interfaces.GetTextFromVoiceCallback;
import vici.ui.model.Commands;


public class HomeScreen extends Activity implements GetTextFromVoiceCallback {


	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	private String LOG_TAG= HomeScreen.class.getSimpleName();
	private ImageView btn,instructions;
	private TextToSpeech t1;
	private ArrayList<Commands> viciesponses= new ArrayList<>();
	private ListView listVieW;
	private ResponsesAdapter adapter;
	AudioManager audMangr;



	@Override
	 public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.main);

			btn= (ImageView)findViewById(R.id.talk);
			listVieW=(ListView)findViewById(R.id.list1);
			instructions=(ImageView)findViewById(R.id.instructions);


			adapter=new ResponsesAdapter( viciesponses, getApplicationContext() );
			listVieW.setAdapter(adapter);

			t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
				@Override
				public void onInit(int status) {
					if(status != TextToSpeech.ERROR) {
						t1.setLanguage(Locale.UK);
					}
				}
			});





			btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					promptSpeechInput();

				}
			});

			instructions.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(HomeScreen.this, "Say the following commands after tapping the speak button\nto activate the features", Toast.LENGTH_LONG).show();
					Toast.makeText(HomeScreen.this, "Contacts\n\nMusic\n\nDiary\n\nSilent\n\nNormal\n\n", Toast.LENGTH_LONG).show();
					Toast.makeText(HomeScreen.this, "Now press the mic below to get started", Toast.LENGTH_LONG).show();

					open();
				}
			});
	        
	        
	    }




	@Override
	public void onTextReceived(String resultText) {

		Toast.makeText(HomeScreen.this, resultText+"", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();


			Log.i(LOG_TAG, "destroy");




	}









	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case VOICE_RECOGNITION_REQUEST_CODE: {



				if (resultCode == RESULT_OK && null != data) {

					ArrayList<String> result = data
							.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					///start receiver
					String resultText = result.get(0);
					viciesponses.add(new Commands(true, resultText + ""));
					adapter.notifyDataSetChanged();


					if(resultText.equalsIgnoreCase("music")|| resultText.equalsIgnoreCase("music player"))
					{
						//Toast.makeText(Vtt.this, matches.get(0), Toast.LENGTH_SHORT).show();
						//Intent musicp = new Intent("android.intent.action.MUSIC_PLAYER");
						//finish();
						Intent musicp = new Intent(getApplicationContext(),MusicActivity.class);
						startActivity(musicp);

					}

					else if(resultText.startsWith("call"))
					{//int index=aasd.indexOf("call");
						Pattern p = Pattern.compile("(?<=\\bcall\\s)(\\w+)");
						Matcher m = p.matcher(resultText);
						while (m.find())
						{
							System.out.println(m.group(1));

						}
						if(resultText.length()>4)
						{String qwe= resultText.substring(5);
							Toast.makeText(this, qwe, Toast.LENGTH_LONG).show();
						}
						else
						{ Toast.makeText(this,"say the name", Toast.LENGTH_LONG).show();}
					}
					else if(resultText.equalsIgnoreCase("contact")|| resultText.equalsIgnoreCase("contacts"))
					{
						//finish();
						Intent contactss = new Intent(getApplicationContext(),ContactActivity.class);
						startActivity(contactss);
					}

					else if(resultText.equalsIgnoreCase("diary")||resultText.equalsIgnoreCase("diary"))
					{
						//finish();
						Intent contactss = new Intent(getApplicationContext(),DiaryActivity.class);
						startActivity(contactss);
					}

					else if(resultText.equalsIgnoreCase("quit")|| resultText.equalsIgnoreCase("exit"))
					{
						finish();
						finish();
					}
					else if(resultText.equalsIgnoreCase("hi")|| resultText.equalsIgnoreCase("hello"))
					{


						t1.speak("Hi, Iam VICKI, your personal assistant", TextToSpeech.QUEUE_FLUSH, null);
						viciesponses.add(new Commands(false,"Hi, Iam VICI, your personal assistant"));
						adapter.notifyDataSetChanged();



					}

					else if(resultText.equalsIgnoreCase("silent")|| resultText.equalsIgnoreCase("silence"))
					{
						//finish();

						audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

						//For Normal mode
						//  audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

						//For Silent mode
						//  audMangr.setRingerMode(AudioManager.RINGER_MODE_SILENT);

						//For Vibrate mode
						audMangr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					}
					else if(resultText.equalsIgnoreCase("normal")|| resultText.equalsIgnoreCase("normal mode"))
					{
						//finish();
						audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

						//For Normal mode
						audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					}
					else if(resultText.equalsIgnoreCase("help") || resultText.contains("command"))
					{

						viciesponses.add(new Commands(false,"Contacts\n\nMusic\n\nDiary\n\nSilent\n\nNormal\n\n"));
						adapter.notifyDataSetChanged();
						t1.speak("use one of the following commands", TextToSpeech.QUEUE_FLUSH, null);
					}

					else
					{
						viciesponses.add(new Commands(false,"no such command, please try again"));
						adapter.notifyDataSetChanged();
						t1.speak("no such command, please try again", TextToSpeech.QUEUE_FLUSH, null);
						//Toast.makeText(this, "no such command", Toast.LENGTH_LONG).show();
						//finish();

					}
				}
				break;
			}

		}
	}



	public void open(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Project Developed by\n" +
				"Sunder\n" +
				"Umair\n" +
				"varsha\n" +
				"amruta");



		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
