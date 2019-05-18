package com.mynym.LESampleDataPeopleOnly;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "code", "pop", "locs", "state" })
public class Postcode {
	public String code;
	public Integer pop;
	public List<String> locs = new ArrayList<>();
	public String state;
}
