package com.trafik.buss;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.Gson;

public class Fetcher {
	private Set<String> listOfLines = new HashSet<>();
	private HashMap<String, String> stop = new HashMap<String, String>();
	private HashMap<String, Set<String>> jourMap = new HashMap<String, Set<String>>();
	private static final int NUMBER_OF_PRESENTED = 10;
	private static final String DIRECTION_CODE = "1";
	private Answer answerResult = new Answer();
	private static boolean verbose = false;
	private static final String URL = "https://api.sl.se/api2/lineData.json?key=xxxxxxxxxxxxxxxxx&DefaultTransportModeCode=BUS";

	public static void main(String[] args) throws Exception {
		Fetcher fetcher = new Fetcher();
		verbose = true;
		System.out.println(fetcher.getTopTenStation());
	}

	public String getTopTenStation() throws Exception {
		initiate();
		String urljour = URL + "&model=jour";
		getStops();

		Gson gson = new Gson();
		HttpRequest getReq = HttpRequest.newBuilder().uri(new URI(urljour)).build();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> getResp = httpClient.send(getReq, BodyHandlers.ofString());
		DataTable recievedData = new DataTable();
		recievedData = gson.fromJson(getResp.body(), DataTable.class);
		ArrayList<Result> arr = recievedData.getResponseData().getResult();
		moveToHashMap(arr);
		int NumberOfFound = 0;
		while (NumberOfFound < NUMBER_OF_PRESENTED) {
			getBiggest();
			NumberOfFound++;
		}

		if (verbose) {
			System.out.println("Antal Bus linje kvar är: " + jourMap.size());
			System.out.println("till slut Answer storlek " + answerResult.getBusLiner().size());
		}

		return formatResult();

	}

	/**
	 * an easy way to select the bus line which has biggest number of stops, select
	 * the biggest and return the rest as list
	 */
	private int getBiggest() {
		int biggestSize = 0;
		String storstaLine = "";
		Set<String> storstaSet = null;
		for (String key : jourMap.keySet()) {
			Set<String> v = jourMap.get(key);
			int storlek = v.size();
			if (storlek > biggestSize) {
				biggestSize = storlek;
				storstaLine = key;
				storstaSet = v;
			}
		}
		if (verbose) {
			System.out.println("Bus linje är " + storstaLine + " med antal " + biggestSize + "->" + storstaSet);
		}
		BusPresentation busPres = new BusPresentation();
		busPres.setBusNumber(storstaLine);
		busPres.setStationer(jourMap.get(storstaLine));
		answerResult.addBusPresntation(busPres);
		jourMap.remove(storstaLine);
		return biggestSize;
	}

	/**
	 * denna method används inte, tydligen behövs inte
	 * 
	 * @throws Exception
	 */
	public void checkBusLines() throws Exception {
		String urljour = URL + "&model=line";
		Gson gson = new Gson();
		HttpRequest getReq = HttpRequest.newBuilder().uri(new URI(urljour)).build();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> getResp = httpClient.send(getReq, BodyHandlers.ofString());
		DataTable helheten = new DataTable();
		helheten = gson.fromJson(getResp.body(), DataTable.class);
		ArrayList<Result> arr = helheten.getResponseData().getResult();
		for (int i = 0; i < arr.size(); i++) {
			Result trObj = arr.get(i);
			listOfLines.add(trObj.getLineNumber());
		}

	}

	public void getStops() throws Exception {
		String urljour = URL + "&model=stope";
		Gson gson = new Gson();
		HttpRequest getReq = HttpRequest.newBuilder().uri(new URI(urljour)).build();
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> getResp = httpClient.send(getReq, BodyHandlers.ofString());
		DataTable helheten = new DataTable();
		helheten = gson.fromJson(getResp.body(), DataTable.class);
		System.out.println("Antal object i Result för stop är:" + helheten.getResponseData().getResult().size());
		ArrayList<Result> arr = helheten.getResponseData().getResult();
		for (int i = 0; i < arr.size(); i++) {
			Result trObj = arr.get(i);
			stop.put(trObj.getStopPointNumber(), trObj.getStopPointName());
		}
	}

	/**
	 * kopierar Json-data till en lokal HashMap, lagra namnet på stop stationer och bussNumret som key
	 * 
	 * @param arr
	 */
	private void moveToHashMap(ArrayList<Result> arr) {
		for (int i = 0; i < arr.size(); i++) {
			Result trObj = arr.get(i);
			if (trObj.getDirectionCode().equals(DIRECTION_CODE)) {
				if (!jourMap.containsKey(trObj.getLineNumber())) {
					Set<String> v = new HashSet<>();
					v.add(stop.get(trObj.getJourneyPatternPointNumber()) + ", ");
					jourMap.put(trObj.getLineNumber(), v);
				} else {
					Set<String> innan = jourMap.get(trObj.getLineNumber());
					innan.add(stop.get(trObj.getJourneyPatternPointNumber()) + ", ");
					jourMap.put(trObj.getLineNumber(), innan);
				}
			}
		}
	}

	private void initiate() {
		answerResult = new Answer();
		stop = new HashMap<String, String>();
		jourMap = new HashMap<String, Set<String>>();
	}

	/**
	 * för att presentera Json dat som resultat,
	 * 
	 * @return
	 */
	private String formatResult() {
		Gson gson = new Gson();
		String gsonAnswer = gson.toJson(answerResult);
		// TODO: man borde kunna skriva bättre, använd korrekt Objekt definition
		gsonAnswer = gsonAnswer.substring(13, gsonAnswer.length());
		gsonAnswer = gsonAnswer.substring(0, gsonAnswer.length() - 2);

		return "[" + gsonAnswer + "]";
	}
}