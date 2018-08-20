package org.mscs.smartcard.controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.mscs.smartcard.beans.SmartCard;
import org.mscs.smartcard.service.SmartCardService;
import org.mscs.stations.Station;

public class SmartCardController {
	private SmartCardService smartCardService;

	public SmartCardController() {
		this.smartCardService = new SmartCardService();
	}

	private Integer fetchSmartCardNumber(Scanner scanner) {
		Integer smartCardNumber = null;
		System.out.print("Please enter smart card number: ");
		if (scanner.hasNextInt()) {
			smartCardNumber = scanner.nextInt();
		}
		scanner.nextLine();
		return smartCardNumber;
	}

	/**
	 * This method manages Swipe In operation of smart card
	 * 
	 * @param smartCard
	 *            {@link SmartCard} which will be used during entry
	 * @param stationCode
	 *            Station Code from where user will enter
	 * @return returns swipeIn status
	 */
	public Boolean swipeIn(SmartCard smartCard, Integer stationCode) {
		return smartCardService.swipeIn(smartCard, stationCode);
	}

	/**
	 * This method manages Swipe Out operation of smart card
	 * 
	 * @param smartCard
	 *            {@link SmartCard} which will be used during exit
	 * @param stationCode
	 *            Station Code from where user will exit
	 * @return returns swipeOut status
	 */
	public Boolean swipeOut(SmartCard smartCard, Integer stationCode) {
		return smartCardService.swipeOut(smartCard, stationCode);
	}

	public void inputhandler(Scanner scanner) {
		Integer smartCardNo = null;
		SmartCard smartCard = null;
		Integer stationCode = null;
		System.out.println("## Welcome to Metro Smart Card System ##");
		System.out.println("Please select operation from below options:");
		System.out.println("1. Generate new smart card");
		System.out.println("2. Recharge existing smart card");
		System.out.println("3. Check balance of existing smart card");
		System.out.println("4. Start journey");
		System.out.println("5. End journey");
		int selectedOption = scanner.nextInt();
		scanner.nextLine();
		switch (selectedOption) {
		case 1:
			smartCard = smartCardService.generateNewSmartCard();
			System.out.println("Smart card is generated successfully.");
			System.out.println("Smart Card details: ");
			System.out.println("  Card number  - " + smartCard.getSmartCardNumber());
			System.out.println("  Card balance - Rs. " + smartCard.getBalance());
			System.out.println("Please save card number for future use.");
			break;
		case 2:
			smartCardNo = fetchSmartCardNumber(scanner);
			smartCard = smartCardService.getSmartCard(smartCardNo);
			if (smartCard != null) {
				try {
					System.out.println("Please enter recharge amount: ");
					Double rechargeAmount = scanner.nextDouble();
					if (rechargeAmount != null && rechargeAmount.isNaN()) {
						System.out.println("Invalid recharge amount. Please retry with valid recharge amount value");
						break;
					}
					smartCard.setBalance(smartCard.getBalance() + rechargeAmount);
					System.out.println("Recharge of Rs. " + rechargeAmount + " is successful. Updated balance is Rs."
							+ smartCard.getBalance());
				} catch (InputMismatchException inputMismatchException) {
					System.out.println("Invalid Recharge value. Please retry with valid recharge value.");
				} catch (IllegalArgumentException illegalArgumentException) {
					System.out.println("Invalid Recharge value. Please retry with valid recharge value.");
				} catch (NoSuchElementException noSuchElementException) {
					System.out.println("Invalid Recharge value. Please retry with valid recharge value.");
				}
				scanner.nextLine();
			} else {
				System.out.println(
						"Invalid Smart card number. Please retry with valid smart card number or generate new one");
			}
			break;
		case 3:
			smartCardNo = fetchSmartCardNumber(scanner);
			smartCard = smartCardService.getSmartCard(smartCardNo);
			if (smartCard != null) {
				System.out.println("Available balance of smart card: Rs. " + smartCard.getBalance());
			} else {
				System.out.println("Invalid Smart card number. Please retry with valid smart card number");
			}
			break;
		case 4:
			smartCardNo = fetchSmartCardNumber(scanner);
			smartCard = smartCardService.getSmartCard(smartCardNo);
			if (smartCard == null) {
				System.out.println(
						"Invalid Smart card number. Please retry with valid smart card number or generate new one");
				break;
			} else if (smartCard.getEnteredStationCode() != null) {
				System.out.println("Unable to start journey as journey is already in progress from this card");
				break;
			}
			System.out.println("Enter Station Code(A1-A10) of start journey point");
			if (scanner.hasNextLine()) {
				try {
					stationCode = Station.valueOf(scanner.nextLine()).getStationCode();
					if (swipeIn(smartCard, stationCode)) {
						System.out.println(
								"Card swiped in successfully. Current balance is Rs. " + smartCard.getBalance());
					} else {
						System.out.println("Insufficient balance detected, please recharge to enter station.");
					}
				} catch (IllegalArgumentException illegalArgumentException) {
					System.out.println("Invalid Station Name. Please retry with valid Station Name.");
				} catch (NoSuchElementException noSuchElementException) {
					System.out.println("Invalid Station Name. Please retry with valid Station Name.");
				}

			} else {
				System.out.println("Invalid option selected. Please try choosing option from given choices");
			}
			break;
		case 5:
			smartCardNo = fetchSmartCardNumber(scanner);
			smartCard = smartCardService.getSmartCard(smartCardNo);
			if (smartCard == null) {
				System.out.println(
						"Invalid Smart card number. Please retry with valid smart card number or generate new one");
				break;
			} else if (smartCard.getEnteredStationCode() == null) {
				System.out.println("Unable to end journey as journey was not started from this card");
				break;
			}
			System.out.println("Enter Station Code(A1-A10) of end journey point");
			if (scanner.hasNext()) {
				try {
					stationCode = Station.valueOf(scanner.nextLine()).getStationCode();
					if (swipeOut(smartCard, stationCode)) {
						System.out.println(
								"Card swiped out successfully. Remaining balance is Rs. " + smartCard.getBalance());
					} else {
						System.out.println("Insufficient balance detected, please recharge to take exit.");
					}
				} catch (IllegalArgumentException illegalArgumentException) {
					System.out.println("Invalid Station Name. Please retry with valid Station Name.");
				} catch (NoSuchElementException noSuchElementException) {
					System.out.println("Invalid Station Name. Please retry with valid Station Name.");
				}
			} else {
				System.out.println("Invalid option selected. Please try choosing option from given choices");
			}
			break;
		default:
			System.out.println("Invalid option selected. Please try choosing option from given choices");
			break;
		}

	}

	public static void main(String[] args) {
		SmartCardController smartCardController = new SmartCardController();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				smartCardController.inputhandler(scanner);

				boolean incompleteOperation = true;
				while (true) {
					System.out.println("Wanna perform more operations?(Y/N)");
					if (scanner.hasNextLine()) {
						String continueOperation = scanner.nextLine();
						if (continueOperation.equalsIgnoreCase("Y")) {
							break;
						} else if (continueOperation.equalsIgnoreCase("N")) {
							System.out.println("Erasing all generated smart cards...");
							incompleteOperation = false;
							break;
						}
					}
				}
				if (!incompleteOperation) {
					break;
				}
			} catch (IllegalStateException exception) {
				System.out.println("Invalid Input captured. Please restart your process for further operations");
			}
		}
		scanner.close();
	}
}
