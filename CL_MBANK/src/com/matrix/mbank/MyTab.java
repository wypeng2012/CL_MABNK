package com.matrix.mbank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.action.ClientAction;
import com.matrix.utils.MBankUtil;
import com.mbank.entity.Activity;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MyTab extends TabActivity implements OnTabChangeListener {
	private ListView listview;
	private double rate = 0;
	private double deposit_money = 0;
	private double withdraw_money = 0;
	private TextView username;
	private TextView balance;
	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	private static final int myMenuResources[] = { R.menu.update,
			R.menu.default_mune };
	public static final int DEPOSIT = 1;
	public static final int WITHDRAW = 2;
	public static final int UPDATECLIENT = 3;
	public static final int GETACTIVITY = 4;
	private EditText deposit;
	private Button deposit_button;
	private Button withdraw_button;
	private EditText withdraw;
	private EditText clientname;
	private EditText type;
	private EditText address;
	private EditText phone;
	private EditText email;
	private EditText comment;
	private Button update_button;
	private TextView account_balance;
	private TextView account_limit;
	private TextView account_comment;
	private List<Activity> activity = null;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DEPOSIT:
				boolean b = (Boolean) msg.obj;
				if (b) {
					Toast.makeText(MyTab.this,
							getString(R.string.deposit_success),
							Toast.LENGTH_SHORT).show();
					balance.setText(MBankUtil.account.getBalance() + "");
					account_balance
							.setText(MBankUtil.account.getBalance() + "");
					deposit.setText("");
					withdraw.setText("");
					new Thread(
							new StoreActivity(deposit_money, "deposit", rate))
							.start();
				} else {
					Toast.makeText(MyTab.this,
							getString(R.string.deposit_failure),
							Toast.LENGTH_SHORT).show();
				}
				break;
			case WITHDRAW:
				boolean b1 = (Boolean) msg.obj;
				if (b1) {
					Toast.makeText(MyTab.this,
							getString(R.string.withdraw_success),
							Toast.LENGTH_SHORT).show();
					balance.setText(MBankUtil.account.getBalance() + "");
					account_balance
							.setText(MBankUtil.account.getBalance() + "");
					deposit.setText("");
					withdraw.setText("");
					new Thread(new StoreActivity(withdraw_money, "withdraw",
							rate)).start();
				} else {
					Toast.makeText(MyTab.this,
							getString(R.string.withdraw_failure),
							Toast.LENGTH_SHORT).show();
				}
				break;
			case UPDATECLIENT:
				boolean b2 = (Boolean) msg.obj;
				if (b2) {
					Toast.makeText(MyTab.this,
							getString(R.string.updateclient_success),
							Toast.LENGTH_SHORT).show();
					address.setEnabled(false);
					phone.setEnabled(false);
					email.setEnabled(false);
					comment.setEnabled(false);
					update_button.setAlpha(0.0f);
				}
				break;
			case GETACTIVITY:
				if (activity != null) {
					SimpleAdapter adapter = new SimpleAdapter(
							getApplicationContext(), getData(), R.layout.item,
							new String[] { "amout", "date", "commission",
									"description" }, new int[] {
									R.id.activity_amout, R.id.activity_date,
									R.id.activity_commission,
									R.id.activity_description });
				listview.setAdapter(adapter);
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
		LoginActivity.instance.finish();
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		myTabhost = this.getTabHost();

		LayoutInflater.from(this).inflate(R.layout.home,
				myTabhost.getTabContentView(), true);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));

		myTabhost
				.addTab(myTabhost
						.newTabSpec("One")
						// make a new Tab
						.setIndicator("",
								getResources().getDrawable(R.drawable.action))
						// set the Title and Icon
						.setContent(R.id.widget_layout_Action));
		// set the layout

		myTabhost.addTab(myTabhost
				.newTabSpec("Two")
				// make a new Tab
				.setIndicator("",
						getResources().getDrawable(R.drawable.client_info))
				// set the Title and Icon
				.setContent(R.id.widget_layout_ClientInfo));
		// set the layout

		myTabhost.addTab(myTabhost
				.newTabSpec("Three")
				// make a new Tab
				.setIndicator("",
						getResources().getDrawable(R.drawable.account_info))
				// set the Title and Icon
				.setContent(R.id.widget_layout_AccountInfo));
		// set the layout

		myTabhost.addTab(myTabhost
				.newTabSpec("Four")
				// make a new Tab
				.setIndicator("",
						getResources().getDrawable(R.drawable.history))
				// set the Title and Icon
				.setContent(R.id.widget_layout_History));
		// set the layout

		myTabhost.setOnTabChangedListener(this);
		listview=(ListView)findViewById(R.id.listview);
		username = (TextView) findViewById(R.id.homename);
		balance = (TextView) findViewById(R.id.balance);
		username.setText(MBankUtil.mbankclient.getClient_name());
		balance.setText(MBankUtil.account.getBalance() + "");
		deposit = (EditText) findViewById(R.id.edit_deposit);
		withdraw = (EditText) findViewById(R.id.edit_withdraw);
		deposit_button = (Button) findViewById(R.id.deposit);
		withdraw_button = (Button) findViewById(R.id.withdraw);
		clientname = (EditText) findViewById(R.id.clientname);
		type = (EditText) findViewById(R.id.type);
		address = (EditText) findViewById(R.id.address);
		phone = (EditText) findViewById(R.id.phone);
		email = (EditText) findViewById(R.id.clientemail);
		comment = (EditText) findViewById(R.id.comment);
		clientname.setText(MBankUtil.mbankclient.getClient_name());
		type.setText(MBankUtil.mbankclient.getType());
		address.setText(MBankUtil.mbankclient.getAddress());
		phone.setText(MBankUtil.mbankclient.getPhone());
		email.setText(MBankUtil.mbankclient.getEmail());
		comment.setText(MBankUtil.mbankclient.getComment());
		account_balance = (TextView) findViewById(R.id.account_balance);
		account_limit = (TextView) findViewById(R.id.account_limit);
		account_comment = (TextView) findViewById(R.id.account_comment);
		account_balance.setText(MBankUtil.account.getBalance() + "");
		account_limit.setText(MBankUtil.account.getCredit_limit() + "");
		account_comment.setText(MBankUtil.account.getComment());
		update_button = (Button) findViewById(R.id.updateBtn);
		update_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (address.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (email.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (phone.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (comment.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				MBankUtil.mbankclient.setAddress(address.getText().toString()
						.trim());
				MBankUtil.mbankclient.setPhone(phone.getText().toString()
						.trim());
				MBankUtil.mbankclient.setEmail(email.getText().toString()
						.trim());
				MBankUtil.mbankclient.setComment(comment.getText().toString()
						.trim());
				new Thread(new UpdateClient()).start();

			}
		});
		withdraw_button.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				if (withdraw.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				withdraw_money = Double.valueOf(withdraw.getText().toString()
						.trim());
				rate = MBankUtil.commission_rate;
				if (!MBankUtil.mbankclient.getType().trim()
						.equalsIgnoreCase("platinum")) {
					if (withdraw_money > (MBankUtil.account.getBalance() + MBankUtil.limit)) {
						Toast.makeText(getApplicationContext(),
								getString(R.string.bigdraw), Toast.LENGTH_SHORT);
						return;
					}
				}
				AlertDialog.Builder builder = new Builder(MyTab.this);
				builder.setMessage(getString(R.string.withdraw_rate) + rate);
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
								new Thread(new WithDraw()).start();
							}
						});
				builder.show();

			}
		});
		deposit_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (deposit.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.input), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				deposit_money = Double.valueOf(deposit.getText().toString()
						.trim());
				rate = MBankUtil.deposit_commission * deposit_money
						+ MBankUtil.commission_rate;
				AlertDialog.Builder builder = new Builder(MyTab.this);
				builder.setMessage(getString(R.string.deposit_rate) + rate);
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
								new Thread(new DepositToAccount()).start();
							}
						});
				builder.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		myMenu = menu;
		myMenu.clear();
		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();

		switch (myMenuSettingTag) {
		case 2:
			inflater.inflate(myMenuResources[0], menu);

			break;
		default:
			inflater.inflate(myMenuResources[1], menu);
			break;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabChanged(String tagString) {

		if (tagString.equals("One")) {
			myMenuSettingTag = 1;
		}
		if (tagString.equals("Two")) {
			myMenuSettingTag = 2;
		}
		if (tagString.equals("Three")) {
			myMenuSettingTag = 3;
		}
		if (tagString.equals("Four")) {
			myMenuSettingTag = 4;
			new Thread(new GetActivity()).start();
		}
		if (myMenu != null) {
			onCreateOptionsMenu(myMenu);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.edit:
			// clientname.setEnabled(true);
			// type.setEnabled(true);
			address.setEnabled(true);
			phone.setEnabled(true);
			email.setEnabled(true);
			comment.setEnabled(true);
			update_button.setAlpha(1.0f);
			break;

		case R.id.logout:
			AlertDialog.Builder builder = new Builder(MyTab.this);
			builder.setMessage(getString(R.string.sureout));
			builder.setTitle(getString(R.string.prompt));
			builder.setNegativeButton(getString(R.string.no),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							return;
						}
					});
			builder.setPositiveButton(getString(R.string.yes),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							MBankUtil.reset();
							startActivity(new Intent(MyTab.this,
									LoginActivity.class));
							finish();
						}
					});
			builder.show();
			break;
		}

		return true;

	}

	class DepositToAccount implements Runnable {

		@Override
		public void run() {

			MBankUtil.account.setBalance(MBankUtil.account.getBalance() - rate
					+ deposit_money);
			boolean b = ClientAction.getInstance().deposit();
			Message message = new Message();
			message.obj = b;
			message.what = DEPOSIT;
			handler.sendMessage(message);

		}
	}

	class WithDraw implements Runnable {

		@Override
		public void run() {
			MBankUtil.account.setBalance(MBankUtil.account.getBalance() - rate
					- withdraw_money);
			boolean b = ClientAction.getInstance().deposit();
			Message message = new Message();
			message.obj = b;
			message.what = WITHDRAW;
			handler.sendMessage(message);
		}

	}

	class StoreActivity implements Runnable {
		private double amount = 0;
		private String description;
		private double commission;

		public StoreActivity() {
			super();
		}

		public StoreActivity(double amount, String description,
				double commission) {
			super();
			this.amount = amount;
			this.description = description;
			this.commission = commission;
		}

		@Override
		public void run() {
			ClientAction.getInstance().storeActivity(amount, description,
					commission);

		}

	}

	class UpdateClient implements Runnable {

		@Override
		public void run() {
			boolean b = ClientAction.getInstance().updateClient();
			Message message = new Message();
			message.obj = b;
			message.what = UPDATECLIENT;
			handler.sendMessage(message);
		}

	}

	class GetActivity implements Runnable {

		@Override
		public void run() {
			activity = ClientAction.getInstance().getALLActivity();
			Message message = new Message();

			message.what = GETACTIVITY;
			handler.sendMessage(message);
		}

	}

	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Activity a : activity) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("amout", a.getAmount());
			map.put("date", a.getActivity_date());
			map.put("commission", a.getCommission());
			map.put("description", a.getDescription());
			list.add(map);
		}
		return list;
	}
}
