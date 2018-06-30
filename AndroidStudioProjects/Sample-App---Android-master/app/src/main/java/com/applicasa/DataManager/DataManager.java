package com.applicasa.DataManager;

import android.database.Cursor;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDataManagerGetArrayCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDataManagerGetByIDCallback;
import com.applicasa.ApplicasaManager.LiManager.LiObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.communication.LiObjRequest;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.communication.LiRequestConst.RequestCallback;
import applicasa.LiCore.communication.LiUtility;
import applicasa.LiCore.sqlDB.database.LiCoreDBmanager;
import applicasa.LiCore.sqlDB.database.LiDbObject;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;



public class DataManager extends DataManagerData {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////							SAVE                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Saves the Object to Applicasa's Servers
	 * The method saves all of the item's value to the server
	 * If the Object has an ID the operation will update existing object in applicasa's server; otherwise an add operation will be called
	 * 
	 * In Order to Update a specific field Use the method saveFields
	 * @param actionCallback
	 * @return
	 * @throws LiErrorHandler
	 */
	public void save(LiCallbackAction liCallbackAction)  
	{
		LiObjRequest request = new LiObjRequest();
		
		// If Id is of hex representation and not 0, then the itemId is Mongo id
		if (DataManagerID!= "0" && (LiUtility.isHex(DataManagerID)|| DataManagerID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(DataManagerID);
			request.setIncrementedFields(incrementedFields);			
		}
		else
		{
			request.setAction(RequestAction.ADD_ACTION);
			request.setAddedObject(this);
		}
		
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setEnableOffline(EnableOffline);
		
		setActionCallback(liCallbackAction,request.requestID);
		// add the Values of the Object Item to the Request
		try{
			request.setParametersArrayValue(dictionaryRepresentation(false));
		}catch (LiErrorHandler e) {
			if (liCallbackAction != null)
				liCallbackAction.onFailure(e);
			else 
				return;
		}
		
		request.startASync();

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////						  DELETE                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void delete(LiCallbackAction liCallbackAction) 
	{
		// Verifies Item isn't null
		if (DataManagerID == null || DataManagerID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(DataManagerID);
		
		setActionCallback(liCallbackAction,request.requestID);
		request.setEnableOffline(EnableOffline);
		
		request.startASync();

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////							  						 /////////////////////////////////////////
//////////////////////////////////						   GET                       /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	    * A- Synchronized function which returns an object from server by ID
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getByID(String Id, QueryKind queryKind, LiDataManagerGetByIDCallback liDataManagerGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldDataManager.DataManagerID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liDataManagerGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiDataManagerGetArrayCallback liDataManagerGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liDataManagerGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiDataManagerGetArrayCallback liDataManagerGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liDataManagerGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<DataManager> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
		 {
			 LiObjRequest request = new LiObjRequest();
			 request.setClassName(kClassName);
			 request.setAction(RequestAction.GET_ARRAY);
			 request.setGet(queryKind);
			 request.setQueryToRequest(query);
			 LiObjResponse response = request.startSync();
			 
			 if (response.LiRespType.equals(ApplicasaResponse.RESPONSE_SUCCESSFUL))
			 {
			  Cursor cursor = request.getCursor();
			  return buildDataManagerFromCursor(request.requestID, cursor);
			 }
			 
			 return null;
		 }

	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////					    Upload File                  /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	* Method to Upload file 
	* @param dataManagerField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param dataManagerActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldDataManager liFieldDataManager, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(DataManager.kClassName);
		request.setRecordID(DataManagerID);
		
		request.setFileFieldName(liFieldDataManager);
		request.setFilePath(filePath);
		request.setAddedObject(this);
		request.setCallback(callbackHandler);
		setActionCallback(liCallbackAction,request.requestID);
		
		
		request.startASync();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////						Callback                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
static RequestCallback callbackHandler = new RequestCallback() {
		
	public void onCompleteGet(String requestID, Cursor cursor) {
		// TODO Auto-generated method stub
		List<DataManager> returnList = new ArrayList<DataManager>();

		returnList = buildDataManagerFromCursor(requestID ,cursor);

		Object callback = dataManagerCallbacks.get(requestID);
		if (callback != null && callback instanceof LiDataManagerGetArrayCallback)
		{
			dataManagerCallbacks.remove(requestID);
			((LiDataManagerGetArrayCallback)callback).onGetDataManagerComplete(returnList);
		}
		if (callback != null && callback instanceof LiDataManagerGetByIDCallback )
		{
			dataManagerCallbacks.remove(requestID);
			((LiDataManagerGetByIDCallback)callback).onGetDataManagerComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = dataManagerCallbacks.get(requestID);
		if (callback != null && callback instanceof LiDataManagerGetArrayCallback )
		{
			dataManagerCallbacks.remove(requestID);
			((LiDataManagerGetArrayCallback)callback).onGetDataManagerFailure(ex);
		}		
		else if (callback != null && callback instanceof LiDataManagerGetByIDCallback )
		{
			dataManagerCallbacks.remove(requestID);
			((LiDataManagerGetByIDCallback)callback).onGetDataManagerFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			dataManagerCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = dataManagerCallbacks.get(requestID);
			if (callback != null )
			{
				dataManagerCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((DataManager)response.addedObject).DataManagerID = response.newObjID;
					
				if (response.action == RequestAction.UPLOAD_FILE)
				{
					((DataManager)response.addedObject).setDataManagerFieldbySortType((LiFieldDataManager)response.field, response.newObjID);
					if (response.actionResponseList.get(0).objId != null && response.actionResponseList.get(0).requestID == requestID )
						((DataManager)response.addedObject).DataManagerID = response.actionResponseList.get(0).objId;
				}
								
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildDataManagerFromCursor
	 * @return
	 */
	@Deprecated
	public static List<DataManager> BuildDataManagerFromCursor(String requestID, Cursor cursor)
	{
		return buildDataManagerFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<DataManager> buildDataManagerFromCursor(String requestID ,Cursor cursor)
	{
		List<DataManager> returnList = new ArrayList<DataManager>();
		if (cursor == null || cursor.getCount() == 0 ) {return returnList; }// nothing received
		else
		{
			cursor.moveToFirst();
			ArrayList<String> idsList = LiObjRequest.IdsMap.get(requestID);
            ArrayList<String> idsToDelete = new ArrayList<String>();
            
            String id;
            while (!cursor.isAfterLast())
            {
                id = cursor.getString(0);
                if (idsList == null || idsList.contains(id))
                {
                    returnList.add(new DataManager(cursor));                    
                }
                else
                {
                    idsToDelete.add(id);
                }
				cursor.moveToNext();
            }
            if (!idsToDelete.isEmpty())
			{
				LiObjRequest.DeleteUnlistedIds(kClassName,requestID, idsToDelete);
			}
			idsList = null;
			idsToDelete = null;			
		}
		
		cursor.close();
	
	
		return returnList;
		
	}
	
	
	private static void setGetCallback(LiDataManagerGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		dataManagerCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiDataManagerGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		dataManagerCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		dataManagerCallbacks.put(reqID, actionCallback);
	}
	
	
	 /** Synchronized Method that updates local storage according to request
	 * @return the item Count, if count of 1500 is the max number of values returned by the server.
	 * @throws LiErrorHandler
	 */
	 public static int updateLocalStorage(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
	 {
		 int recordsCount = 0;
		 LiObjRequest request = new LiObjRequest();
		 request.setClassName(kClassName);
		 request.setAction(RequestAction.GET_ARRAY);
		 request.setGet(queryKind);
		 request.setQueryToRequest(query);
		 LiObjResponse response = request.startSync();
		 
		 if (response.LiRespType.equals(ApplicasaResponse.RESPONSE_SUCCESSFUL))
		 {
			 Cursor cursor = request.getCursor();
			 if(cursor == null)
				return 0;
		
			 if (queryKind.compareTo(QueryKind.PAGER) != 0)
			 {
				 deleteItems(request.requestID,cursor);
			 }
			 
			 recordsCount = cursor.getCount();
			 cursor.close();
			 cursor = null;
		 }
		 
		 return recordsCount;
	 }
	 
	 public static void deleteItems(final String requestID ,final Cursor cursor)
	 {
		 new Thread(new Runnable() {
			
			public void run() {
					// TODO Auto-generated method stub
					if (cursor == null || cursor.getCount() == 0 ) {}// nothing received
					else
					{
						cursor.moveToFirst();
						ArrayList<String> idsList = LiObjRequest.IdsMap.get(requestID);
						ArrayList<String> idsToDelete = new ArrayList<String>();
						
						String id;
						while (!cursor.isAfterLast())
						{
							id = cursor.getString(0);
							if (idsList != null && !idsList.contains(id))
							{
								idsToDelete.add(id);
							}
							cursor.moveToNext();
						}
						if (!idsToDelete.isEmpty())
						{
							LiObjRequest.DeleteUnlistedIds(kClassName,requestID, idsToDelete);
						}
						idsList = null;
						idsToDelete = null;			
					}
				}
			}).run();
		}
 /** End of Basic SDK **/ 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////                                                   ////////////////////////////////////////
///////////////////////////////////                    Init Method                    ////////////////////////////////////////
///////////////////////////////////                    Don't ALTER                    ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public DataManager()
	{
		this.DataManagerID = "0";
		(this.DataManagerLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.DataManagerAaa = 0;
		this.DataManagerName = "";
	}

	public DataManager(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public DataManager(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public DataManager(String DataManagerID)
	{
		this.DataManagerID = DataManagerID;
	}

	public DataManager(DataManager item)
	{
		initWithObject(item);
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public DataManager initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public DataManager initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldDataManager.DataManagerID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DataManagerID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDataManager.DataManagerLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.DataManagerLastUpdate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDataManager.DataManagerAaa.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DataManagerAaa = cursor.getInt(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDataManager.DataManagerName.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DataManagerName = cursor.getString(columnIndex);
		
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(DataManager item)
	{
		this.DataManagerID			= item.DataManagerID;
		this.DataManagerLastUpdate			= item.DataManagerLastUpdate;
		this.DataManagerAaa			= item.DataManagerAaa;
		this.DataManagerName			= item.DataManagerName;
	
		return DataManagerID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with DataManager item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldDataManager.DataManagerID, DataManagerID);
	
		dictionary.put(LiFieldDataManager.DataManagerLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(DataManagerLastUpdate));
	
		dictionary.put(LiFieldDataManager.DataManagerAaa, DataManagerAaa);
	
		dictionary.put(LiFieldDataManager.DataManagerName, DataManagerName);
	
		return dictionary;
		}
		catch (LiJSONException ex)
		{
			throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, ex.getMessage());
		}
	}
	
	public static LiDbObject createDB() throws LiJSONException{
		LiDbObject dbObject = new LiDbObject();
		dbObject.put("LiClassName", kClassName);
		dbObject.put(LiFieldDataManager.DataManagerID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldDataManager.DataManagerLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldDataManager.DataManagerAaa, LiCoreDBmanager.INTEGER,0);
		dbObject.put(LiFieldDataManager.DataManagerName, LiCoreDBmanager.TEXT,"");
	return dbObject;
}
	public void increment(LiFieldDataManager liFieldDataManager) throws LiErrorHandler
	{
		increment(liFieldDataManager, 1);
	}
		 
	public void increment(LiFieldDataManager liFieldDataManager, Object value) throws LiErrorHandler
	{
		String key = liFieldDataManager.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getDataManagerFieldbySortType(liFieldDataManager);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setDataManagerFieldbySortType(liFieldDataManager, total);
				if (incrementedFields.has(liFieldDataManager.toString()))
					oldValueInt = (Integer)incrementedFields.remove(key);
	
				incrementedFields.put(key, (oldValueInt+incInt));
			}
			else if (incrementedField instanceof Float)
			{
				float incFloat;
				 if (value instanceof Float)
					incFloat = (Float)value;
				 else
					incFloat = Float.valueOf((Integer)value);
				float total = (Float)incrementedField+incFloat;
				setDataManagerFieldbySortType(liFieldDataManager, total);
					if (incrementedFields.has(liFieldDataManager.toString()))
						oldValueFloat = (Float)incrementedFields.remove(key);
				incrementedFields.put(key, (oldValueFloat+incFloat));
			}
			else
				throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR,"Can't increase, Specified field is not Int or Float");
		} catch (LiJSONException e) {
			// TODO Auto-generated catch block
			throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR,"Can't increase, Recheck inserted Values");
		}
	}
		 
	private void resetIncrementedFields() {
		// TODO Auto-generated method stub
		incrementedFields = new LiJSONObject();
	}
	

}
