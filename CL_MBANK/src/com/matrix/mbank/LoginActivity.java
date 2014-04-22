package com.matrix.mbank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matrix.action.ClientAction;
import com.matrix.utils.MBankUtil;

public class LoginActivity extends Activity {
	public static final String MBANK="MBANK";
	private Button login = null;
	private EditText username;
	private EditText password;
	private static final int LOGIN = 1;
	private static final int GETACCOUNT = 2;
	public static final int GETTOKEN = 3;
	public static final int GETPROPERTIES = 4;
	public static LoginActivity instance;
    @SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
    	@SuppressLint("ShowToast")
		@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case LOGIN:
				Log.i(MBANK, "1111");
				if(MBankUtil.mbankclient!=null){
				new Thread(new GetToken()).start();
				}else{
					Log.i(MBANK, "222");
					Toast.makeText(LoginActivity.this, getString(R.string.loginerro), Toast.LENGTH_SHORT).show();
				}
				break;
			case GETTOKEN:
				if(MBankUtil.token!=null){
					new Thread(new GetAccount()).start();
				}else{
					Toast.makeText(LoginActivity.this, getString(R.string.loginmessage), Toast.LENGTH_SHORT).show();
				}
				break;
			case GETACCOUNT:
				if(MBankUtil.account!=null){
					new Thread(new GetProperties()).start();
				}else{
					Toast.makeText(LoginActivity.this, getString(R.string.loginmessage), Toast.LENGTH_SHORT).show();
				}
				break;
			case GETPROPERTIES:
				if(MBankUtil.properties!=null){
					MBankUtil.setProperties();
					startActivity(new Intent(LoginActivity.this,
							 MyTab.class));
				}else{
					Toast.makeText(LoginActivity.this, getString(R.string.loginmessage), Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}
    	};
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		instance = this;
		MainActivity.instance.finish();
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (username.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(LoginActivity.this,
							getString(R.string.usernameisnull),
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (password.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(LoginActivity.this,
							getString(R.string.passwordisnull),
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!MBankUtil.isNetworkConnected(LoginActivity.this)) {

					AlertDialog.Builder builder = new Builder(
							LoginActivity.this);
					builder.setMessage(getString(R.string.setnet));
					builder.setTitle(getString(R.string.prompt));
					builder.setNegativeButton(getString(R.string.no),
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									return;
								}
							});
					builder.setPositiveButton(getString(R.string.yes),
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
								}
							});
					builder.show();

				}else{
					new Thread(new Login()).start();
				}

				/*startActivity(new Intent(LoginActivity.this,
				 MyTab.class));*/
			}
		});
	}
	 class  Login implements Runnable{

	

		@Override
		public void run() {
			MBankUtil.mbankclient=ClientAction.getInstance().logIn(username.getText().toString().trim(), password.getText().toString().trim());
			Message message=new Message();
			message.what=LOGIN;
			handler.sendMessage(message);
		}
			
	 }
	 class GetAccount implements Runnable{

		

		@Override
		public void run() {
			MBankUtil.account=ClientAction.getInstance().getAccount();
			Message message=new Message();
			message.what=GETACCOUNT;
			handler.sendMessage(message);
		}
		 
	 }
	 class GetToken implements Runnable{

		@Override
		public void run() {
			MBankUtil.token=ClientAction.getInstance().createToken();
			Message message=new Message();
			message.what=GETTOKEN;
			handler.sendMessage(message);
		}
		 
	 }
	 class GetProperties implements Runnable{

		@Override
		public void run() {
			MBankUtil.properties=ClientAction.getInstance().getAllProperties();
			Message message=new Message();
			message.what=GETPROPERTIES;
			handler.sendMessage(message);
		}
		 
	 }
}
