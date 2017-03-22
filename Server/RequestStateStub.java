package com.example.benearle.sebapp;
import java.util.HashMap;
public class RequestStateStub implements  RequestStateInterface{
	private HashMap<String, String> state;
	private Integer counter;
	RequestStateStub(){
		counter = 0;
		state = new HashMap<String, String>();
		state.put("turnL", "0");
		state.put("turnR", "0");
		state.put("brake", "0");
		state.put("Speed", "0");

	}
	//Returns the a stubbed state increasing the speed by one each time.
	public HashMap<String, String> getState(){
		counter ++;
		state.put("Speed", counter.toString());
		return state;
	}
}