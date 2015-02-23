package mok.localization.utils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import weka.core.Instances;

public class Classifier {
	public final static String PATH="/resources/";
	Properties properties;

	public Classifier(Properties properties) {
		this.properties = properties;
	}
	String prediction="";
	public String eval(String ask,ServletContext ctx){
		try{
			InputStream isHead=ctx.getResourceAsStream("/resources/"+properties.getProperty("head"));
			Instances test = null;

			//			DataSource source = new DataSource(path+"test2.arff");
			//			test = source.getDataSet();
			//			test.setClassIndex(28);
			//			ask="00:1f:45:4b:9e:29,-39,00:1f:45:4b:9e:28,-40,00:1f:45:4b:98:e8,-50,00:1f:45:4b:98:e9,-56,00:1f:45:4b:9e:20,-57,00:1f:45:4b:9e:21,-58,00:1f:45:4b:a1:98,-59,00:1f:45:4b:a1:99,-60,00:1f:45:4b:98:e0,-65,00:1f:45:4b:94:28,-66,00:1a:70:31:ab:30,-69,00:1f:45:4b:a1:91,-71,00:1f:45:4b:a1:90,-72,00:1f:45:4b:a0:f9,-77,?";
			InstancesBuilder ib=new InstancesBuilder(ask,isHead);
			test=ib.getInstances();

			Integer numberOfClassifiers=Integer.valueOf(properties.getProperty("number_of_classifiers"));
			InputStream is;
			StringBuilder sb=new StringBuilder();
			for(int i=1;i<=numberOfClassifiers;i++){
				is=ctx.getResourceAsStream("/resources/"+properties.getProperty("classifier"+i));
				weka.classifiers.Classifier cl1 = null;
				try {
					ObjectInputStream in = new ObjectInputStream(is);
					Object tmp = in.readObject();
					cl1 = (weka.classifiers.Classifier) tmp;
					in.close();
				}
				catch (Exception e) {
					System.out.println("Problem found when reading: ");
				}
				try {
					double pred = cl1.classifyInstance(test.instance(0));
					System.out.println("===== Classified instance =====");
					prediction = test.classAttribute().value((int) pred);
					System.out.println("Class predicted: " + prediction);
					sb.append(properties.getProperty("classifier_name"+i));
					sb.append(":");
					sb.append(prediction);
					sb.append(";");
				}catch (Exception e) {
					System.out.println("Problem found when classifying the text");
				}
			}
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return prediction;
	}
}
