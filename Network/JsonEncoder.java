import org.json.simple.JSONObject;


class JsonEncoder {
	JSONObject obj;
	JsonEncoder(String value, String type){
		obj = new JSONObject();
		obj.put("value", value);
		obj.put("type", type);
	}
	
	public JSONObject getJSONObject(){
		return obj;
	} 
	public void setValue(String value){
		obj.put("value", value);
	}
	public void setType(String type){
		obj.put("value", type);
	}
	
    public static void main(String[] args){
		JsonEncoder je = new JsonEncoder("34", "Speed");
		System.out.println(je.getJSONObject());		
   }
}
