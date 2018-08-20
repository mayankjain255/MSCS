package org.mscs.smartcard.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.mscs.smartcard.beans.SmartCard;

public class SmartCardService {

	private HashMap<Integer, SmartCard> smartCards;
	private Random random;

	public SmartCardService() {
		this.smartCards = new HashMap<>();
		this.random = new Random();
	}

	public Boolean swipeIn(SmartCard smartCard, Integer stationCode) {
		Boolean swipeInStatus = false;
		if (smartCard.getBalance() >= 5.5 && smartCard.getEnteredStationCode() == null) {
			smartCard.setEnteredStationCode(stationCode);
			swipeInStatus = true;
		}
		return swipeInStatus;
	}

	private Double fareCalculator(Integer entryStationCode, Integer exitStationCode) {

		int stationCovered = Math.abs(entryStationCode - exitStationCode);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
			return stationCovered * 5.5;
		}
		return stationCovered * 7d;
	}

	public Boolean swipeOut(SmartCard smartCard, Integer exitStationCode) {
		Boolean swipeOutStatus = false;
		if (smartCard.getEnteredStationCode() != null) {
			Double totalFare = fareCalculator(smartCard.getEnteredStationCode(), exitStationCode);
			if (totalFare <= smartCard.getBalance()) {
				smartCard.setBalance(smartCard.getBalance() - totalFare);
				smartCard.setEnteredStationCode(null);
				return true;
			}
		}
		return swipeOutStatus;
	}

	public SmartCard generateNewSmartCard() {
		Integer newSmartCardNo = random.nextInt(Integer.MAX_VALUE);
		SmartCard smartCard = new SmartCard(newSmartCardNo, 50d);
		smartCards.put(newSmartCardNo, smartCard);
		return smartCard;
	}

	public boolean validateSmartCardNo(Integer smartCardNo) {
		return smartCards.get(smartCardNo) != null;
	}

	public SmartCard getSmartCard(Integer smartCardNo) {
		return smartCards.get(smartCardNo);
	}
}
