package com.solaxcloud.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * This class provides a model for retrieving data from solax cloud as well as
 * persist it using {@literal JPA}.
 * 
 * @author Maik Greubel <maikgreubel@gmail.com>
 */
@Entity
@XmlRootElement
@Table(name = "RealTimeStatus", uniqueConstraints = @UniqueConstraint(columnNames = "inverterSN"))
public class RealTimeStatus implements Serializable {

	private static final long serialVersionUID = -7170687699403829794L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 15, max = 15, message = "Exactly 15 characters")
	@Pattern(regexp = "[0-9A-Z]*", message = "Must contain numbers or uppercase letters")
	@Column(name = "InverterSerialNumber")
	private String inverterSN;

	@NotNull
	@Size(min = 10, max = 10, message = "Exactly 10 characters")
	@Pattern(regexp = "[A-Z]*", message = "Must contain uppercase letters")
	@Column(name = "CommunicationModuleSerialNumber")
	private String sn;

	@Column(name = "AcPower")
	private double acpower;

	@Column(name = "YieldToday")
	private double yieldtoday;

	@Column(name = "YieldTotal")
	private double yieldtotal;

	@Column(name = "FeedInPower")
	private double feedinpower;

	@Column(name = "FeedInEnergy")
	private double feedinenergy;

	@Column(name = "ConsumeEnergy")
	private double consumeenergy;

	@Column(name = "FeedInPowerM2")
	private double feedinpowerM2;

	@Column(name = "SOC")
	private double soc;

	@Column(name = "EPSPowerR")
	private double peps1;

	@Column(name = "EPSPowerS")
	private double peps2;

	@Column(name = "EPSPowerT")
	private double peps3;

	@Column(name = "InverterType")
	@Enumerated(EnumType.STRING)
	private InverterType inverterType;

	@Column(name = "InverterStatus")
	@Enumerated(EnumType.STRING)
	private InverterStatus inverterStatus;

	@Column(name = "LastUploadDateTime")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime uploadTime;

	@Column(name = "BatteryPower")
	private double batPower;

	@Column(name = "PowerDC1")
	private double powerdc1;

	@Column(name = "PowerDC2")
	private double powerdc2;

	@Column(name = "PowerDC3", nullable = true)
	private Double powerdc3;

	@Column(name = "PowerDC4", nullable = true)
	private Double powerdc4;

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the inverterSN
	 */
	public final String getInverterSN() {
		return inverterSN;
	}

	/**
	 * @param inverterSN the inverterSN to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setInverterSN(String inverterSN) {
		this.inverterSN = inverterSN;
		return this;
	}

	/**
	 * @return the sn
	 */
	public final String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setSn(String sn) {
		this.sn = sn;
		return this;
	}

	/**
	 * @return the acpower
	 */
	public final double getAcpower() {
		return acpower;
	}

	/**
	 * @param acpower the acpower to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setAcpower(double acpower) {
		this.acpower = acpower;
		return this;
	}

	/**
	 * @return the yieldtoday
	 */
	public final double getYieldtoday() {
		return yieldtoday;
	}

	/**
	 * @param yieldtoday the yieldtoday to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setYieldtoday(double yieldtoday) {
		this.yieldtoday = yieldtoday;
		return this;
	}

	/**
	 * @return the yieldtotal
	 */
	public final double getYieldtotal() {
		return yieldtotal;
	}

	/**
	 * @param yieldtotal the yieldtotal to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setYieldtotal(double yieldtotal) {
		this.yieldtotal = yieldtotal;
		return this;
	}

	/**
	 * @return the feedinpower
	 */
	public final double getFeedinpower() {
		return feedinpower;
	}

	/**
	 * @param feedinpower the feedinpower to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setFeedinpower(double feedinpower) {
		this.feedinpower = feedinpower;
		return this;
	}

	/**
	 * @return the feedinenergy
	 */
	public final double getFeedinenergy() {
		return feedinenergy;
	}

	/**
	 * @param feedinenergy the feedinenergy to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setFeedinenergy(double feedinenergy) {
		this.feedinenergy = feedinenergy;
		return this;
	}

	/**
	 * @return the consumeenergy
	 */
	public final double getConsumeenergy() {
		return consumeenergy;
	}

	/**
	 * @param consumeenergy the consumeenergy to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setConsumeenergy(double consumeenergy) {
		this.consumeenergy = consumeenergy;
		return this;
	}

	/**
	 * @return the feedinpowerM2
	 */
	public final double getFeedinpowerM2() {
		return feedinpowerM2;
	}

	/**
	 * @param feedinpowerM2 the feedinpowerM2 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setFeedinpowerM2(double feedinpowerM2) {
		this.feedinpowerM2 = feedinpowerM2;
		return this;
	}

	/**
	 * @return the soc
	 */
	public final double getSoc() {
		return soc;
	}

	/**
	 * @param soc the soc to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setSoc(double soc) {
		this.soc = soc;
		return this;
	}

	/**
	 * @return the peps1
	 */
	public final double getPeps1() {
		return peps1;
	}

	/**
	 * @param peps1 the peps1 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPeps1(double peps1) {
		this.peps1 = peps1;
		return this;
	}

	/**
	 * @return the peps2
	 */
	public final double getPeps2() {
		return peps2;
	}

	/**
	 * @param peps2 the peps2 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPeps2(double peps2) {
		this.peps2 = peps2;
		return this;
	}

	/**
	 * @return the peps3
	 */
	public final double getPeps3() {
		return peps3;
	}

	/**
	 * @param peps3 the peps3 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPeps3(double peps3) {
		this.peps3 = peps3;
		return this;
	}

	/**
	 * @return the inverterType
	 */
	public final InverterType getInverterType() {
		return inverterType;
	}

	/**
	 * @param inverterType the inverterType to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setInverterType(InverterType inverterType) {
		this.inverterType = inverterType;
		return this;
	}

	/**
	 * @return the inverterStatus
	 */
	public final InverterStatus getInverterStatus() {
		return inverterStatus;
	}

	/**
	 * @param inverterStatus the inverterStatus to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setInverterStatus(InverterStatus inverterStatus) {
		this.inverterStatus = inverterStatus;
		return this;
	}

	/**
	 * @return the uploadTime
	 */
	public final LocalDateTime getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
		return this;
	}

	/**
	 * @return the batPower
	 */
	public final double getBatPower() {
		return batPower;
	}

	/**
	 * @param batPower the batPower to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setBatPower(double batPower) {
		this.batPower = batPower;
		return this;
	}

	/**
	 * @return the powerdc1
	 */
	public final double getPowerdc1() {
		return powerdc1;
	}

	/**
	 * @param powerdc1 the powerdc1 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPowerdc1(double powerdc1) {
		this.powerdc1 = powerdc1;
		return this;
	}

	/**
	 * @return the powerdc2
	 */
	public final double getPowerdc2() {
		return powerdc2;
	}

	/**
	 * @param powerdc2 the powerdc2 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPowerdc2(double powerdc2) {
		this.powerdc2 = powerdc2;
		return this;
	}

	/**
	 * @return the powerdc3
	 */
	public final Double getPowerdc3() {
		return powerdc3;
	}

	/**
	 * @param powerdc3 the powerdc3 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPowerdc3(Double powerdc3) {
		this.powerdc3 = powerdc3;
		return this;
	}

	/**
	 * @return the powerdc4
	 */
	public final Double getPowerdc4() {
		return powerdc4;
	}

	/**
	 * @param powerdc4 the powerdc4 to set
	 * @return fluent interface
	 */
	public final RealTimeStatus setPowerdc4(Double powerdc4) {
		this.powerdc4 = powerdc4;
		return this;
	}
}
