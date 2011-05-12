/**
 * 
 */
package com.jsar.client.json;

import java.util.ArrayList;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.jsar.client.http.AbstractRequestCallback;
import com.jsar.client.http.HttpInterface;

/**
 * @author hyahn
 *
 **/
public class RestaurantJson extends AbstractJson{

	 public static final String NAME="name";
	  public static final String ADDRESS="address";
	  public static final String TELEPHONE_NUMBER= "telephoneNumber";
	  public static final String TYPE_OF_FOOD ="typeOfFood";
	  public static final String SEND_URL="/yapooldb/";
	  
	  public void setName(String name){
	    jsonObject.put(NAME, new JSONString(name));
	  }

	  public String getName(){
	    return jsonObject.get(NAME).isString().stringValue();
	  }
	  public void setAddress(String description){
	    jsonObject.put(ADDRESS, new JSONString(description));
	  }

	  public String getAddress(){
	    return jsonObject.get(ADDRESS).isString().stringValue();
	  }
	  public void setTelephoneNumber(String telephoneNumber){
	    jsonObject.put(TELEPHONE_NUMBER, new JSONString(telephoneNumber));
	    setId(telephoneNumber);
	  }

	  public void getTelephoneNumber(){
	    jsonObject.get(TELEPHONE_NUMBER).isString().stringValue();
	  }
	  public void setTypeOfFood(ArrayList typeOfFood)
	   {
		  JSONArray jsonArray=new JSONArray();
		   for(int i=0;i<typeOfFood.size();++i)
		  {
			  jsonArray.set(i, new JSONString((String)typeOfFood.get(i)));
		  }
		  jsonObject.put(TYPE_OF_FOOD,jsonArray);
	  }
	  public void getTypeOfFood(){
		    jsonObject.get(TYPE_OF_FOOD).isString().toString();
	}
	 /* yapoolDB?key=" "*/
	 public void postJson()
	 {
		 HttpInterface.doPostJson(SEND_URL,
				 			this,
				 			new RestaurantJsonRequestCallback());
	 }
}
class RestaurantJsonRequestCallback extends AbstractRequestCallback
{
    public void onResponseReceived(Request request, Response response) 
    {
      System.out.println(response.toString());
    }
}
