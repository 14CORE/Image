import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class Main {
	static int width;
	static int height;
	static double alpha = 2;
	static double beta = 50;

	public static void loadLib() {		
		System.load(new File("/usr/local/share/OpenCV/java/libopencv_java2410.dylib").getAbsolutePath());
	}

	public static void main( String[] args )
	{
		loadLib();
//		setOfColor();
		blackOnly();
		System.out.println("end");
	}
	
	public static void printArray(double[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + "/");
		}
		System.out.println();
	}
	
	public static void printArray(Double[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + "/");
		}
		System.out.println();
	}
	
	public static Set<Pixel> cache = new HashSet<Pixel>();
	
	public static Pixel convert(double[] d) {
		return new Pixel(d);
	}
	
	public static void blackOnly() {
		String file = "oopp";
		String type = ".jpg";
		
		Mat source = Highgui.imread("/Users/mapfap/Desktop/" + file + type, Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = source.clone();
		double[] white = new double[]{255, 255 ,255};
		for (int w = 0; w < source.size().width; w++) {
			for (int h = 0; h < source.size().height; h++) {
				double[] d = source.get(h, w);
				if (d[0] == d[1] && d[1] == d[2]) {
				} else {
					dest.put(h, w, white);
				}
			}
		}
		Highgui.imwrite("/Users/mapfap/Desktop/" + file + "x"+type, dest);
		
		
//		Mat destination=new Mat(source.rows(),source.cols(), source.type());
//		System.out.println(source.get(3, 2)[0] + "," + source.get(3, 2)[1] + "," + source.get(3, 2)[2]);
//		source.convertTo(destination, -1, i, beta);
	}

	public static void setOfColor() {
		Mat source = Highgui.imread("/Users/mapfap/Desktop/a.png", Highgui.CV_LOAD_IMAGE_COLOR);
	
		for (int w = 0; w < source.size().width; w++) {
			for (int h = 0; h < source.size().height; h++) {
				double[] d = source.get(w, h);
				cache.add(convert(d));
			}
		}
		
		for(Pixel p : cache) {
			System.out.println(p);
		}
		
//		Mat destination=new Mat(source.rows(),source.cols(), source.type());
		
//		System.out.println(source.get(3, 2)[0] + "," + source.get(3, 2)[1] + "," + source.get(3, 2)[2]);
//		source.convertTo(destination, -1, i, beta);
//		Highgui.imwrite("/Users/mapfap/Desktop/out/" + i + ".jpg", destination);
	}
}