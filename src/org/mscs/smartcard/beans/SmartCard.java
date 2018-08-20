package org.mscs.smartcard.beans;

public class SmartCard {
	private Integer smartCardNumber;
	private Double balance;
	private Integer enteredStationCode;

	public SmartCard(Integer smartCardNumber, Double balance) {
		this.smartCardNumber = smartCardNumber;
		this.balance = balance;
		this.enteredStationCode = null;
	}

	public Integer getSmartCardNumber() {
		return smartCardNumber;
	}

	public void setSmartCardNumber(Integer smartCardNumber) {
		this.smartCardNumber = smartCardNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getEnteredStationCode() {
		return enteredStationCode;
	}

	public void setEnteredStationCode(Integer enteredStationCode) {
		this.enteredStationCode = enteredStationCode;
	}

}
