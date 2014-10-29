
public class Pixel {
	double r;
	double g;
	double b;
	
	public Pixel(double[] d) {
		this.r = d[0];
		this.g = d[1];
		this.b = d[2];
	}
	
	

	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == this.hashCode();
	}



	@Override
	public int hashCode() {
		return (int) (r*7 + g*9 + b*21);
	}

	@Override
	public String toString() {
		return r + ", " + g + ", " + b; 
	}
	
	
}
