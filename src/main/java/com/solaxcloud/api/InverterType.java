package com.solaxcloud.api;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class provides a wrapper for retrieved inverter type from solax cloud
 * via API.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public enum InverterType {

	X1LX("1"), //
	XHybrid("2"), //
	X1HybridFit("3"), //
	X1BoostAirMini("4"), //
	X3HybridFit("5"), //
	X320K30K("6"), //
	X3MicPro("7"), //
	X1Smart("8"), //
	X1AC("9"), //
	A1Hybrid("10"), //
	A1Fit("11"), //
	A1Grid("12"), //
	J1ESS("13");

	/**
	 * Provided by Solax_API.pdf Appendix Table 4
	 */
	private final String val;

	/**
	 * Package protected constructor
	 * 
	 * @param val the api value of type
	 */
	InverterType(final String val) {
		this.val = val;
	}

	/**
	 * Create enumeration from value
	 * 
	 * @param val the value to create enumeration of
	 * @return the enumeration which matches value or null
	 */
	@JsonCreator
	static InverterType fromValue(String val) {
		for (InverterType value : values()) {
			if (value.val.equals(val)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Returns the internal value of enumeration
	 * 
	 * @return the internal value
	 */
	public String value() {
		return val;
	}

	/**
	 * Returns the value
	 */
	@Override
	public String toString() {
		return val;
	}
}
