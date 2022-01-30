package com.solaxcloud.api;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class provides a wrapper for retrieved inverter status from solax cloud
 * via API.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public enum InverterStatus {

	WAIT_MODE("100"), //
	CHECK_MODE("101"), //
	NORMAL_MODE("102"), //
	FAULT_MODE("103"), //
	PERMANENT_FAULT_MODE("104"), //
	UPDATE_MODE("105"), //
	EPS_CHECK_MODE("106"), //
	EPS_MODE("107"), //
	SELF_TEST_MODE("108"), //
	IDLE_MODE("109"), //
	STAND_BY_MODE("110"), //
	PV_WAKE_UP_BAT_MODE("111"), //
	GEN_CHECK_MODE("112"), //
	GEN_RUN_MODE("113");

	/**
	 * Provided by Solax_API.pdf Appendix Table 5
	 */
	private final String val;

	/**
	 * Package protected constructor
	 * 
	 * @param val the api value of type
	 */
	InverterStatus(final String val) {
		this.val = val;
	}

	/**
	 * Create enumeration from value
	 * 
	 * @param val the value to create enumeration of
	 * @return the enumeration which matches value or null
	 */
	@JsonCreator
	static InverterStatus fromValue(String val) {
		for (InverterStatus value : values()) {
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
