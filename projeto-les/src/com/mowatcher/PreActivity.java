package com.mowatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.mowatcher.activity.ListagemActivity;

public class PreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		Button novoTI = (Button)findViewById(R.id.novoTI_bt);
		novoTI.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PreActivity.this, MainActivity.class);
				startActivity(i);
			}
		});
		
		
	    Button acompanhamento = (Button)findViewById(R.id.acompanhamento_bt);
	    acompanhamento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PreActivity.this, ListagemActivity.class);
				startActivity(i);
			}
		});
	    Button historico = (Button)findViewById(R.id.historico_bt);
	    
	    return true;
	}

	
}
