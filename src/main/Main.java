package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvKNearest;

import NL.DrawWin;
import NL.ImageUtils;
import NL.NeuralNetwork;
import NL.RecognitionWin;
import NL.Shared;

public class Main {
	static int width;
	static int height;
	static double alpha = 2;
	static double beta = 50;
	
	public static boolean[][] share;

	public static void loadLib() {		
		System.load(new File("/usr/local/share/OpenCV/java/libopencv_java2410.dylib").getAbsolutePath());
	}

	public static void main( String[] args )
	{
		try {
		loadLib();
		// setOfColor();
//		blackOnly();
//		loopBlackOnly();
		booleanArray();
		
		
//		train();
		
		
		
		
		
		System.out.println("end");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void train() {
		 Mat temp1 = new Mat();
	        Mat temp2 = new Mat();
	        Mat temp3 = new Mat();
	        Mat train_responses = new Mat(1, 10, CvType.CV_32SC1);
	        double myint[] = new double[10];
	        Mat train_samples = new Mat();

	        
	        for(int ii = 0; ii < 10; ii++) {
	            temp1 = Highgui.imread("/Users/mapfap/Desktop/test/save" + ii + ".jpg");
	            Imgproc.resize(temp1, temp2, new Size(10,10)); 
	            temp2.convertTo(temp3, CvType.CV_32FC1);
	            train_samples.push_back(temp3.reshape(1, 1));
	            myint[ii] = ii;
	        }

	        train_responses.put(0, 0, myint);

	        CvKNearest knn = new CvKNearest();

	        knn.train(train_samples, train_responses);
	        System.out.println(train_responses);
	        System.out.println("train OK");
	        
//	        knn.find_nearest(samples, k, results, neighborResponses, dists)
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
		String file = "opx";
		String type = ".jpg";

		Mat source = Highgui.imread("/Users/mapfap/Desktop/" + file + type, Highgui.CV_LOAD_IMAGE_COLOR);
		Mat dest = source.clone();
		double[] white = new double[]{255, 255 ,255};
		for (int w = 0; w < source.size().width; w++) {
			for (int h = 0; h < source.size().height; h++) {
				double[] d = source.get(h, w);
				if (d[0] < 30 && d[0] == d[1] && d[1] == d[2]) {
				} else {
					dest.put(h, w, white);
				}
			}
		}
		Highgui.imwrite("/Users/mapfap/Desktop/" + file + "x"+type, dest);

	}

	/**
	 * Best for now
	 */
	public static void loopBlackOnly() {
		double[] white = new double[]{255, 255 ,255};

		for(int i = 0; i < 20; i++) {
			Mat source = Highgui.imread("/Users/mapfap/Desktop/test/save" + i + ".jpg", Highgui.CV_LOAD_IMAGE_COLOR);
			Mat dest = source.clone();
			for (int w = 0; w < source.size().width; w++) {
				for (int h = 0; h < source.size().height; h++) {
					double[] d = source.get(h, w);
					if (d[0] < 30 && d[0] == d[1] && d[1] == d[2]) {
						
					} else {
						dest.put(h, w, white);
					}
				}
			}
			Highgui.imwrite("/Users/mapfap/Desktop/testx/save" + i + ".jpg", dest);
			System.out.println(i);
		}

	}
	
	public static void booleanArray() {
		double[] white = new double[]{255, 255 ,255};
		Mat source = Highgui.imread("/Users/mapfap/Desktop/test/savea.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		//	Mat dest = source.clone();
		int width = (int) source.size().width - 180; // ด้านสูง
		int height = (int) source.size().height - 10; // ด้านยาว
		System.out.println(width + ", " + height);
		
		boolean[][] array = new boolean[height][width];
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				double[] d = source.get(h, w);
				if (d[0] < 30 && d[0] == d[1] && d[1] == d[2]) {
//					System.out.print("|");
					array[w][h] = true;
				} else {
//					System.out.print(" ");
				}
			}
//			System.out.println();
			
		}
		
		try {
			Shared.neuralNet = new NeuralNetwork("res/nn_weights.txt", 0.3, 100, 25, 10);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.share = array;
		
		boolean[][] data = Main.share;
		BufferedImage image = ImageUtils.getImage(data);
		int[] rectCoords = ImageUtils.getSquare(data);
		boolean[][] bits = ImageUtils.getBits(rectCoords, data);
		
//		boolean[][] booleanBits = Shared.recognitionWin.getImageBits();
		int[] intBits = new int[100];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				intBits[10*j + i] = (bits[i][j])? 1 : 0;
		int y = Shared.neuralNet.eval(intBits);
		Shared.recognitionWin = new RecognitionWin();
		System.out.println(" ***RESULT = '" + y + "' ****");
		
		
		// Show GUI
		Shared.recognitionWin = new RecognitionWin();
		Shared.drawWin = new DrawWin();
		
		
		
		//	Highgui.imwrite("/Users/mapfap/Desktop/testx/save" + i + ".jpg", dest);
		//	System.out.println(i);

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