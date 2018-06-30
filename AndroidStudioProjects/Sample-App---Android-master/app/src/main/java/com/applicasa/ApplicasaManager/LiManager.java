package com.applicasa.ApplicasaManager;

import android.content.Context;

import com.applicasa.Achievments.Achievments;
import com.applicasa.Chat.Chat;
import com.applicasa.Colors.Colors;
import com.applicasa.DataManString.DataManString;
import com.applicasa.DataManager.DataManager;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Foo.Foo;
import com.applicasa.GameV.GameV;
import com.applicasa.Languages.Languages;
import com.applicasa.Levels.Levels;
import com.applicasa.ScoreB.ScoreB;
import com.applicasa.User.User;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.applicasa.VirtualGood.VirtualGood;
import com.applicasa.VirtualGoodCategory.VirtualGoodCategory;

import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.Applicasa;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiJson.LiJSONArray;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.IAP.Callbacks.LiCallbackIAPInitialize;

/**
 * Applicasa imports
 **/

public class LiManager {
	
	/**
	* Initialize applicasa's Framework Using application context and application Key
	* @param context
	* @param callback a callback to indicate when the initialize operation has Finished.
	* if initialize is Successful
	*/
	public static void initialize(Context context,LiCallbackInitialize liCallbackInitialize)
	{		
		Applicasa.initializeApplicasa(context,LiConfig.getApplicationKey(),liCallbackInitialize);	
	}
	
	/**
	* Initialize applicasa's Framework Using application context and application Key
	* @param context
	* @param liCallbackInitialize
	* a callback to indicate when the initialize operation has Finished.
	* if initialize is Successful
	* @param liCallbackIAPInitialize
	* a callback to indicate when the In App billing initialize operation has Finished.
	* if initialize is Successful
	*/
	public static void initialize(Context context,LiCallbackInitialize liCallbackInitialize, LiCallbackIAPInitialize liCallbackIAPInitialize) 
	{
		Applicasa.initializeApplicasa(context,LiConfig.getApplicationKey(),liCallbackInitialize, liCallbackIAPInitialize);	
	}
	
	// GCM
		
	/**
	* Register for push GCM service
	* 
	* @param senderID 
	*  GCM - project number from Google API "https://code.google.com/apis/console/#project:xxxxxxxxx"
	*  C2DM - sender Email address
	*/
		
	public static void registerToGCM()
	{
		Applicasa.registerToGCM();
	}
	
	/**
	* Unregister from push GCM service
	*/
	public static void unRegisterFromGCM()
	{
		Applicasa.unRegisterFromGCM();
	}
	
	public static boolean isRegisteredToGCM()
	{
		return Applicasa.isRegisterToGCM();
	}
	public static LiJSONArray getDataBaseCreation() throws LiJSONException
	{
		LiJSONArray array = new LiJSONArray();
		array.put(User.createDB());
		array.put(VirtualCurrency.createDB());
		array.put(VirtualGoodCategory.createDB());
		array.put(Dynamic.createDB());
		array.put(Chat.createDB());
		array.put(Achievments.createDB());
		array.put(Foo.createDB());
		array.put(GameV.createDB());
		array.put(DataManager.createDB());
		array.put(DataManString.createDB());
		array.put(ScoreB.createDB());
		array.put(Levels.createDB());
		array.put(Colors.createDB());
		array.put(Languages.createDB());
		array.put(VirtualGood.createDB());
		
		return array;
	}
	protected static Map<String, LiObject> stringMap = new HashMap<String, LiObject>();
		public static enum LiObject{
		User,
		VirtualCurrency,
		VirtualGoodCategory,
		Dynamic,
		Chat,
		Achievments,
		Foo,
		GameV,
		DataManager,
		DataManString,
		ScoreB,
		Levels,
		Colors,
		Languages,
		VirtualGood;
			private LiObject() {
				stringMap.put(this.toString(), this);
			}
		public static LiObject getLiObject(String key) {
			return stringMap.get(key);
		}
	};
	/**
	* 
	* @return true if Applicasa service is initialized, else false
	*/
	public static boolean isInitialized()
	{
		return Applicasa.isInitialized();
	}
}

