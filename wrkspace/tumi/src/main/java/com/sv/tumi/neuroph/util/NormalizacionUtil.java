package com.sv.tumi.neuroph.util;

public class NormalizacionUtil {

	private final static Integer minCargoId = 1;
	private final static Integer maxCargoId = 3;
	private final static Integer minSubTemaId = 1;
	private final static Integer maxSubTemaId = 12;
	private final static Integer minNivelId = 1;
	private final static Integer maxNivelId = 3;

	private final static Integer minNivelDesempeñoId = 1;
	private final static Integer maxNivelDesempeñoId = 3;

	private final static Integer minNivelPersonalId = 1;
	private final static Integer maxNivelPersonalId = 3;

	public static void main(String[] args) {
		System.out.println(getCargoValue(2));
		System.out.println(getSubtemaValue(11));
		System.out.println(getNivelValue(2));
	}

	public static double getCargoValue(int cargoId) {
		Double value = (double) (cargoId - minCargoId)
				/ (maxCargoId - minCargoId);
		return value;
	}

	public static double getSubtemaValue(int subTemaId) {
		Double value = (double) (subTemaId - minSubTemaId)
				/ (maxSubTemaId - minSubTemaId);
		return value;
	}

	public static double getNivelValue(int nivelId) {
		Double value = (double) (nivelId - minNivelId)
				/ (maxNivelId - minNivelId);
		return value;
	}

	public static double getNivelDesempeñoValue(int nivelId) {
		Double value = (double) (nivelId - minNivelDesempeñoId)
				/ (maxNivelDesempeñoId - minNivelDesempeñoId);
		return value;
	}

	public static double getNivelPersonalValue(int nivelId) {
		Double value = (double) (nivelId - minNivelPersonalId)
				/ (maxNivelPersonalId - minNivelPersonalId);
		return value;
	}

}
