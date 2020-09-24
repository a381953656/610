package simhash;

import java.text.DecimalFormat;

public class Formula {
	static public double getSimliar(int x) {
		double number1 = Math.sqrt(2 * Math.PI * 0.16);
		double number2 = Math.pow(0.01 * x - 0.01, 2);
		double number3 = 2 * 0.0459 * 0.0459 * (-1.0);
		double form1 = 1 / number1;
		double form2 = Math.exp(number2 / number3);
		double result = form1 * form2;
		result = (double)Math.round(result*100)/100;
		return result;
	}
}
