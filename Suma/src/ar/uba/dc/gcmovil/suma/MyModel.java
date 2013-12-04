package ar.uba.dc.gcmovil.suma;

import java.io.Serializable;

public class MyModel implements Serializable {
	
	private static final long serialVersionUID = 1187466234675583248L;
	
	private Integer value1;
	private Integer value2;
	private Boolean setValue1;
	
	public MyModel() {
		value1 = Integer.valueOf(0);
		value2 = Integer.valueOf(0);
		setValue1 = false;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
		this.setValue1 = true;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public Boolean getIsSetValue1() {
		return setValue1;
	}



}