package mok.localization.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class InstancesBuilder {

	public static final String propBssids="bssids";
	public static final String propClasses="class";
	public static final String propNumber="number";
	
	Integer numberOfPair=0;
	Integer classIndex=0;
	private Instances instances;
	FastVector bssidValues;
	FastVector classValues;
	String ask=null;
	InputStream isHead;


	public InstancesBuilder(String ask,InputStream head) {
		this.ask=ask;
		isHead=head;
	}
	/**
	 * read from a file the possibly values of the bssid and the rooms
	 * file like
	 * bssid1,bssid2,..,bssidn
	 * room1,room2,..,roomn
	 * EOF
	 * @return
	 */
	private void setBssidAndRoomsValues() {
		bssidValues=new FastVector();
		classValues=new FastVector();
		try {
			Properties p=new Properties();
			p.load(isHead);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(isHead));
			String line=null;
			if ((line = p.getProperty(propBssids)) != null) {
				String[] bssidss=line.split(",");
				for (String bs : bssidss) {
					bssidValues.addElement(bs);
				}
			}
			if ((line = p.getProperty(propClasses)) != null) {
				String[] roomss=line.split(",");
				for (String room : roomss) {
					classValues.addElement(room);
				}
			}
			numberOfPair=Integer.valueOf(p.getProperty(propNumber));
			classIndex=numberOfPair*2;
		}
		catch (IOException e) {
		}
	}

	private void makeInstance() throws Exception {
		try{
			if(ask==null)
				throw new Exception("ask is null");
			// Create the attributes, class and text
			setBssidAndRoomsValues();
			System.out.println("here");
			//		Attribute attribute1 = new Attribute("_b1_", bssidValues);
			//		Attribute attribute2 = new Attribute("_s1_");
			ArrayList<Attribute>listOfAttribute=getListOfAttribute();
			// Create list of instances with one element
			FastVector fvWekaAttributes = new FastVector();
			for (Attribute attribute : listOfAttribute) {
				fvWekaAttributes.addElement(attribute);
			}
			instances = new Instances("Test relation", fvWekaAttributes, 1);
			// Set class index
			instances.setClassIndex(classIndex);
			// Create and add the instance
			Instance instance = new Instance(classIndex+1);
			String []values=ask.split(",");
			for (int i = 0; i < listOfAttribute.size()-1; i++) {
				String v="";
				if(i%2==0){
					if(values.length<=i)
						v="-";
					else
						v=values[i];
					instance.setValue(listOfAttribute.get(i), v);
				}
				else{
					if(values.length<=i)
						v="0";
					else
						v=values[i];
					instance.setValue(listOfAttribute.get(i), Integer.valueOf(v));
				}
			}
			// Another way to do it:
			// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
			instances.add(instance);
			System.out.println(instances);
		}catch(Exception e){
			throw new Exception("Error "+e.getMessage());
		}
	}

	private ArrayList<Attribute> getListOfAttribute() {
		ArrayList<Attribute> res=new ArrayList<Attribute>();
		for(int i=0;i< numberOfPair;i++){
			res.add(new Attribute("_b"+(i+1)+"_",bssidValues));
			res.add(new Attribute("_s"+(i+1)+"_"));
		}
		res.add(new Attribute("class",classValues));
		return res;
	}
	public Instances getInstances() throws Exception{
		makeInstance();
		return instances;
	}

}
