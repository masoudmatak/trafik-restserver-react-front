package com.trafik.buss;

import java.util.ArrayList;

public class Answer {

	private ArrayList<BusPresentation> busLiner = new ArrayList<BusPresentation>();

	public ArrayList<BusPresentation> getBusLiner() {
		return busLiner;
	}

	public void setBusLiner(ArrayList<BusPresentation> busLiner) {
		this.busLiner = busLiner;
	}

	public void addBusPresntation(BusPresentation pres) {
		busLiner.add(pres);
	}
}
