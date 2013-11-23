package ar.uba.dc.gcmovil.triangle;

import java.io.Serializable;

public class MyModel implements Serializable {
	
	private static final long serialVersionUID = 1187466234675583248L;
	
	private Integer side1;
	private Integer side2;
	private Integer side3;
	
	public MyModel() {
		side1 = Integer.valueOf(0);
		side2 = Integer.valueOf(0);
		side3 = Integer.valueOf(0);
	}

	public Integer getSide1() {
		return side1;
	}

	public void setSide1(Integer side1) {
		this.side1 = side1;
	}

	public Integer getSide2() {
		return side2;
	}

	public void setSide2(Integer side2) {
		this.side2 = side2;
	}

	public Integer getSide3() {
		return side3;
	}

	public void setSide3(Integer side3) {
		this.side3 = side3;
	}

}