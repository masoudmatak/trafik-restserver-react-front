package com.trafik.buss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BusPresentation {

	private String busNumber;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Set<String> Stationer = new HashSet<>();

	public Set<String> getStationer() {
		return Stationer;
	}

	public void setStationer(Set<String> stationer) {
		Stationer = stationer;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNummer) {
		this.busNumber = busNummer;
	}

}
