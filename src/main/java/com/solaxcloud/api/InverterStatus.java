package com.solaxcloud.api;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class provides a wrapper for retrieved inverter status from solax cloud
 * via API.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
public enum InverterStatus {

	WaitMode("100"), //
	CheckMode("101"), //
	NormalMode("102"), //
	FaultMode("103"), //
	PermanentFaultMode("104"), //
	UpdateMode("105"), //
	EPSCheckMode("106"), //
	EPSMode("107"), //
	SelfTestMode("108"), //
	IdleMode("109"), //
	StandByMode("110"), //
	PvWakeUpBatMode("111"), //
	GenCheckMode("112"), //
	GenRunMode("113");

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
