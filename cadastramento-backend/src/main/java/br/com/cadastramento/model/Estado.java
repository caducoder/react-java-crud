package br.com.cadastramento.model;

public enum Estado {
	AC("Acre"),
	AL("Alagoas"),
	AP("Amap?"),
	AM("Amazonas"),
	BA("Bahia"),
	DF("Bras?lia"),
	CE("Cear?"),
	ES("Esp?rito Santo"),
	GO("Goi?s"),
	MA("Maranh?o"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Par?"),
	PB("Para?ba"),
	PR("Paran?"),
	PE("Pernambuco"),
	PI("Piau?"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	RO("Rond?nia"),
	RR("Roraima"),
	SC("Santa Catarina"),
	SP("S?o Paulo"),
	SE("Sergipe"),
	TO("Tocantins");
	
	public final String label;
	
	private Estado(String label) {
		this.label = label;
	}
}
