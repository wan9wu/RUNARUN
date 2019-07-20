package org.elastos.dma.dmademo.tool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.elastos.dma.dmademo.config.SystemConfig;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class NativeDataService {
	
	private static NativeDataService nativeDataService;
	
	private NativeDataService(){}
	
	public static NativeDataService getInstance(){
		if(nativeDataService==null){
			nativeDataService = new NativeDataService();
		}
		return nativeDataService;
	}



	/**
	 * 保存钱包相关
	 * @param context
	 * @param map
     * @return
     */
	public boolean saveWallet(Context context,Map<String,String> map){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){
            Set<String> tagSet = new LinkedHashSet<String>();
			Editor editor = preferences.edit();
			if(map.get("keyStore")!=null){
				editor.putString("keyStore",map.get("keyStore"));
			}
			if(map.get("privateKey")!=null){
				editor.putString("privateKey",map.get("privateKey"));
			}
			if(map.get("elaPrivateKey")!=null){
				editor.putString("elaPrivateKey",map.get("elaPrivateKey"));
			}
			if(map.get("publicKey")!=null){
				editor.putString("publicKey",map.get("publicKey"));
			}
			if(map.get("did")!=null){
				editor.putString("did",map.get("did"));
                tagSet.add(map.get("did"));
			}
			if(map.get("address")!=null){
				editor.putString("address",map.get("address"));
                tagSet.add(map.get("address"));
			}
			if(map.get("ethAddress")!=null){
				editor.putString("ethAddress",map.get("ethAddress"));
                tagSet.add(map.get("ethAddress").replace("0x",""));
			}
			if(map.get("mnemonic")!=null){
				editor.putString("mnemonic",map.get("mnemonic"));
			}
			if(map.get("privateKeyPwd")!=null){
				editor.putString("privateKeyPwd",map.get("privateKeyPwd"));
			}
			return editor.commit();
		}
		return false;
	}
	/**
	 * 删除钱包相关
	 * @param context
	 * @return
	 */
	public boolean deleteWallet(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.remove("address");
			editor.remove("walletname");
			return editor.commit();
		}
		return false;
	}
	/**
	 * 加载钱包
	 * @param context
	 * @return
	 */
	public Map<String,String> loadWallet(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		Map<String,String> map=new HashMap<String, String>();
		if(preferences!=null){
			map.put("keyStore", preferences.getString("keyStore",""));
			map.put("publicKey", preferences.getString("publicKey",""));
			map.put("address", preferences.getString("address",""));
			map.put("ethAddress", preferences.getString("ethAddress",""));
			map.put("mnemonic", preferences.getString("mnemonic",""));
			map.put("privateKey", preferences.getString("privateKey",""));
			map.put("elaPrivateKey", preferences.getString("elaPrivateKey",""));
			map.put("privateKeyPwd", preferences.getString("privateKeyPwd",""));
			map.put("did", preferences.getString("did",""));
			map.put("walletname", preferences.getString("walletname",""));
			map.put("openFinger", preferences.getInt("openFinger",-2)+"");//-2表示没设置

			return map;
		}
		return map;
	}
	public boolean saveWalletNmae(Context context,String walletname){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
		editor.putString("walletname",walletname);
		return editor.commit();
	}
		return false;
	}
	public boolean saveFingerVerify(Context context,int state){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_WALLET, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putInt("openFinger",state);
			return editor.commit();
		}
		return false;
	}

	public boolean saveLanguage(Context context,int language){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putInt("language",language);
			return editor.commit();
		}
		return false;
	}
	//获取语言设置
	public int loadLanguage(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			return preferences.getInt("language", 0);
		}
		return 0;
	}




	//保存上一次发起交易的时间
	public boolean saveTransferTime(Context context){
		SharedPreferences preferences = context.getSharedPreferences(SystemConfig.Keys.NATIVE_INFO_USER_PRIVATE, Activity.MODE_PRIVATE);
		if(preferences!=null){
			Editor editor = preferences.edit();
			editor.putLong("transfertime",System.currentTimeMillis());
			return editor.commit();
		}
		return false;
	}
}
