package com.solaxcloud.api;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class provides a wrapper for retrieved inverter type from solax cloud
 * via API.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public enum InverterType {

	X1_LX("1"), //
	X_HYBRID("2"), //
	X1_HYBRID_FIT("3"), //
	X1_BOOST_AIR_MINI("4"), //
	X3_HYBRID_FIT("5"), //
	X3_20K_30K("6"), //
	X3_MIC_PRO("7"), //
	X1_SMART("8"), //
	X1_AC("9"), //
	A1_HYBRID("10"), //
	A1_FIT("11"), //
	A1_GRID("12"), //
	J1_ESS("13");

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
