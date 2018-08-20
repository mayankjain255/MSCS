package org.mscs.stations;

public enum Station {

	A1(1), A2(2), A3(3), A4(4), A5(5), A6(6), A7(7), A8(8), A9(9), A10(10);

	private final Integer stationCode;

	private Station(Integer stationCode) {
		this.stationCode = stationCode;
	}

	public Integer getStationCode() {
		return stationCode;
	}
}
