package com.mowatcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.mowatcher.MainActivity;
import com.mowatcher.R;

public class BaseActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.ver_listagem:
				Intent listagem = new Intent(this, ListagemActivity.class);
				startActivity(listagem);
				break;
			case R.id.ver_relatorio:
	
			default:
				Intent main = new Intent(this, MainActivity.class);
				startActivity(main);
				break;
		}
		return true;
	}
}