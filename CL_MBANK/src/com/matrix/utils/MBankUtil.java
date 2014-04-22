package com.matrix.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.mbank.entity.Account;
import com.mbank.entity.MbankClient;
import com.mbank.entity.Properties;

public class MBankUtil {

	public static String token = null;
	public static MbankClient mbankclient = null;
	public static Account account = null;
	public static double deposit_commission = 0;
	public static double commission_rate = 0;
	public static double limit = 0;
	public static List<Properties> properties = null;

	public static void setProperties() {
		for (Properties p : properties) {
			if (p.getProp_key().trim().equalsIgnoreCase("commission_rate")) {
				commission_rate = Double.valueOf(p.getProp_value());
			} else if (p
					.getProp_key()
					.trim()
					.equalsIgnoreCase(
							mbankclient.getType().trim() + "_credit_limit")) {
				if (mbankclient.getType().trim().equalsIgnoreCase("platinum"))
					limit = 0;
				else
					limit = Double.valueOf(p.getProp_value());
			} else if (p
					.getProp_key()
					.trim()
					.equalsIgnoreCase(
							mbankclient.getType().trim() + "_deposit_commission")) {
				deposit_commission = Double.valueOf(p.getProp_value());
			}
		}
	}

	public static String get(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static String post(String url, String json) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			StringEntity se = new StringEntity(json.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			} else {
				return null;
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static String put(String url, String json) {
		HttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(url);
		try {
			StringEntity se = new StringEntity(json.toString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			put.setEntity(se);
			HttpResponse response = client.execute(put);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			} else {
				return null;
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}

	public static String put(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(url);
		try {
			HttpResponse response = client.execute(put);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			} else {
				return null;
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}

	public static String delete(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(url);
		try {

			HttpResponse response = client.execute(delete);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			} else {
				return null;
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {

				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static void reset() {
		token = null;
		mbankclient = null;
		account = null;
		deposit_commission = 0;
		commission_rate = 0;
		limit = 0;
		properties = null;
	}
}
