package ar.uba.dc.gcmovil.gdc;

import java.io.Serializable;

public class MyModel implements Serializable {
	
	private static final long serialVersionUID = 1187466234675583248L;
	
	private Integer nro1;
	private Integer nro2;	
	
	public MyModel() {
		nro1 = Integer.valueOf(0);
		nro2 = Integer.valueOf(0);
	}

	public Integer getNro1() {
		return nro1;
	}

	public void setNro1(Integer nro1) {
		this.nro1 = nro1;
	}

	public Integer getNro2() {
		return nro2;
	}

	public void setNro2(Integer nro2) {
		this.nro2 = nro2;
	}
}