package com.matrix.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.matrix.utils.MBankUtil;
import com.mbank.entity.Account;
import com.mbank.entity.Activity;
import com.mbank.entity.MbankClient;
import com.mbank.entity.Properties;

@SuppressLint("SimpleDateFormat")
public class ClientAction {
	private static final ClientAction action = new ClientAction();

	private ClientAction() {
	}

	public static ClientAction getInstance() {
		return action;
	}

	public MbankClient logIn(String client_name, String password) {
		MbankClient client = null;
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/login?client_name="
				+ client_name.trim() + "&password=" + password.trim();
		String result = MBankUtil.get(url);
		if (result == null) {
			return null;
		} else {
			try {
				client = new MbankClient();
				JSONObject json = new JSONObject(result);
				client.setAddress(json.getString("address"));
				client.setClient_id(json.getLong("client_id"));
				client.setClient_name(json.getString("client_name"));
				client.setComment(json.getString("comment"));
				client.setEmail(json.getString("email"));
				client.setPassword(json.getString("password"));
				client.setPhone(json.getString("phone"));
				client.setType(json.getString("type"));
			} catch (JSONException e) {

				e.printStackTrace();
				return null;
			}
		}
		return client;
	}

	public String createToken() {
		String token = null;
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/login/gettoken/"
				+ MBankUtil.mbankclient.getClient_id();
		token = MBankUtil.get(url);

		return token;
	}

	public boolean removeToken() {
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/login/logout/"
				+ MBankUtil.token + "/" + MBankUtil.mbankclient.getClient_id();
		String result = MBankUtil.delete(url);
		if (result != null) {
			if (result.trim().equalsIgnoreCase("true"))
				return true;
			else
				return false;
		} else
			return false;
	}

	public boolean updateClient() {
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/updateclient/"
				+ MBankUtil.token;
		JSONObject json = new JSONObject();
		try {
			json.put("address", MBankUtil.mbankclient.getAddress());
			json.put("client_id", MBankUtil.mbankclient.getClient_id());
			json.put("client_name", MBankUtil.mbankclient.getClient_name());
			json.put("comment", MBankUtil.mbankclient.getComment());
			json.put("email", MBankUtil.mbankclient.getEmail());
			json.put("password", MBankUtil.mbankclient.getPassword());
			json.put("phone", MBankUtil.mbankclient.getPhone());
			json.put("type", MBankUtil.mbankclient.getType());
			String result = MBankUtil.put(url, json.toString());
			if (result == null) {
				return false;
			} else {
				if (result.trim().equalsIgnoreCase("true"))
					return true;
				else
					return false;
			}
		} catch (JSONException e) {

			e.printStackTrace();
			return false;
		}

	}

	public boolean withDraw(String balance) {
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/withdraw/"
				+ MBankUtil.token + "?client_id="
				+ MBankUtil.mbankclient.getClient_id() + "&balance=" + balance;
		String result = MBankUtil.put(url);
		if (result == null) {
			return false;
		} else {
			if (result.trim().equalsIgnoreCase("true"))
				return true;
			else
				return false;
		}

	}

	public boolean deposit() {
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/deposit/"
				+ MBankUtil.token;
		JSONObject json = new JSONObject();
		try {
			json.put("account_id", MBankUtil.account.getAccount_id());
			json.put("balance", MBankUtil.account.getBalance());
			json.put("client_id", MBankUtil.account.getClient_id());
			json.put("comment", MBankUtil.account.getComment());
			json.put("credit_limit", MBankUtil.account.getCredit_limit());
			String result = MBankUtil.put(url, json.toString());
			if (result == null) {
				return false;
			} else {
				if (result.trim().equalsIgnoreCase("true"))
					return true;
				else
					return false;
			}
		} catch (JSONException e) {

			e.printStackTrace();
			return false;

		}

	}

	public List<Activity> getALLActivity() {
		List<Activity> list = new ArrayList<Activity>();
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/getallactivity/"
				+ MBankUtil.token + "/" + MBankUtil.mbankclient.getClient_id();
		String result = MBankUtil.get(url);
		if (result == null || result.trim().equalsIgnoreCase("null")) {
			return null;
		} else {
			JSONObject json = null;
			try {
				json = new JSONObject(result);
				JSONArray array = json.getJSONArray("Activity");
				if (array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject ob = array.getJSONObject(i);
						Activity activity = new Activity();
						activity.setActivity_date(ob.getString("activity_date"));
						activity.setAmount(ob.getDouble("amount"));
						activity.setClient_id(ob.getLong("client_id"));
						activity.setCommission(ob.getDouble("commission"));
						activity.setDescription(ob.getString("description"));
						activity.setId(ob.getLong("id"));
						;
						list.add(activity);

					}
				}

			} catch (JSONException e) {
				JSONObject ob;
				try {
					ob = json.getJSONObject("Activity");

					Activity activity = new Activity();
					activity.setActivity_date(ob.getString("activity_date"));
					activity.setAmount(ob.getDouble("amount"));
					activity.setClient_id(ob.getLong("client_id"));
					activity.setCommission(ob.getDouble("commission"));
					activity.setDescription(ob.getString("description"));
					activity.setId(ob.getLong("id"));
					;
					list.add(activity);

				} catch (JSONException e1) {
					e1.printStackTrace();
					return null;
				}

			}
		}
		return list;
	}

	public boolean storeActivity(Double amount, String description,
			Double commission) {
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/storeactivity/"
				+ MBankUtil.token;
		JSONObject json = new JSONObject();
		try {
			json.put("activity_date", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			json.put("amount", amount);
			json.put("client_id", MBankUtil.mbankclient.getClient_id());

			json.put("commission", commission);
			json.put("description", description);
			json.put("id", 1);
			String result = MBankUtil.post(url, json.toString());
			if (result == null) {
				return false;
			} else {
				if (result.trim().equalsIgnoreCase("true"))
					return true;
				else
					return false;
			}

		} catch (JSONException e) {

			e.printStackTrace();
			return false;
		}

	}

	public List<Properties> getAllProperties() {
		List<Properties> list = new ArrayList<Properties>();
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/getallproperties/"
				+ MBankUtil.token;
		String result = MBankUtil.get(url);
		if (result == null || result.trim().equalsIgnoreCase("null")) {
			return null;
		} else {
			JSONObject json = null;
			try {
				json = new JSONObject(result);
				JSONArray array = json.getJSONArray("Properties");
				if (array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject ob = array.getJSONObject(i);
						Properties pro = new Properties();
						pro.setProp_key(ob.getString("prop_key"));
						pro.setProp_value(ob.getString("prop_value"));
						list.add(pro);

					}
				}

			} catch (JSONException e) {
				JSONObject ob;
				try {
					ob = json.getJSONObject("Properties");
					Properties pro = new Properties();
					pro.setProp_key(ob.getString("prop_key"));
					pro.setProp_value(ob.getString("prop_value"));
					list.add(pro);

				} catch (JSONException e1) {
					e1.printStackTrace();
					return null;
				}

			}
		}
		return list;
	}

	public Account getAccount() {
		Account account = null;
		String url = "http://192.168.1.82:8080/WS_MBANK/rest/action/getaccount/"
				+ MBankUtil.token + "/" + MBankUtil.mbankclient.getClient_id();
		String result = MBankUtil.get(url);
		if (result == null) {
			return null;
		} else {
			try {
				account = new Account();
				JSONObject json = new JSONObject(result);
                account.setAccount_id(json.getLong("account_id"));
                account.setBalance(json.getDouble("balance"));
                account.setClient_id(json.getLong("client_id"));
                account.setComment(json.getString("comment"));
                account.setCredit_limit(json.getDouble("credit_limit"));
			} catch (JSONException e) {

				e.printStackTrace();
				return null;
			}
		}
		return account;
	}
}
