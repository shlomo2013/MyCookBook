package com.MyCookBook.Utiltis;

import com.MyCookBook.Entities.PersonalSettings;
import com.example.mycookbook.mycookbook.Queries;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Andreas Thiele
 *
 *
 * An implementation of knn.
 * Uses Euclidean distance weighted by 1/distance
 *
 * Main method to classify if entry is male or female based on:
 * Height, weight
 */
public class NearestNeighbour{
    public static void Knn(){
        ArrayList<DataEntry> data = new ArrayList<NearestNeighbour.DataEntry>();
/*        data.add(new DataEntry(new double[]{1,10,4}, "soups"));
        data.add(new DataEntry(new double[]{193.5,110,4}, "fish"));
        data.add(new DataEntry(new double[]{183,92.8,2}, "Male"));
        data.add(new DataEntry(new double[]{160,60,6}, "Male"));
        data.add(new DataEntry(new double[]{177,73.1,8}, "Male"));
        data.add(new DataEntry(new double[]{175,80,9}, "Female"));
        data.add(new DataEntry(new double[]{150,55,8}, "Female"));
        data.add(new DataEntry(new double[]{159,63.2,88}, "Female"));
        data.add(new DataEntry(new double[]{180,70,33}, "Female"));
        data.add(new DataEntry(new double[]{163,110,44}, "Female"));*/

        data.add(new DataEntry(new double[]{20,0,0}, PersonalSettings.ANIN_TASTE));
        data.add(new DataEntry(new double[]{20,0,0}, PersonalSettings.ANIN_TASTE));

        data.add(new DataEntry(new double[]{0,20,0}, PersonalSettings.STANDARD_TASTE));
        data.add(new DataEntry(new double[]{0,20,0}, PersonalSettings.STANDARD_TASTE));

        data.add(new DataEntry(new double[]{0,0,20}, PersonalSettings.AMAMI_TASTE));
        data.add(new DataEntry(new double[]{0,0,20}, PersonalSettings.AMAMI_TASTE));

       /* ArrayList<PersonalSettings> settings = Queries.getClassified();
        for(PersonalSettings curr:settings){
            if(curr.getClassification()!=null)
                data.add(new DataEntry(new double[]{Double.parseDouble(curr.getString(PersonalSettings.Sweets)),
                                                    Double.parseDouble(curr.getString(PersonalSettings.Meat)),
                                                    Double.parseDouble(curr.getString(PersonalSettings.salads))}, curr.getClassification()));
        }*/

        NearestNeighbour nn = new NearestNeighbour(data, 3);//3 neighbours

        String sweets = "0";
        String meat = "0";
        String salads = "0";
        if(Queries.personalSettings!=null) {
            sweets = Queries.personalSettings.get(PersonalSettings.Sweets).toString();
            meat = Queries.personalSettings.get(PersonalSettings.Meat).toString();
            salads = Queries.personalSettings.get(PersonalSettings.salads).toString();
        }

        String result = nn.classify(new DataEntry(new double[]{Double.parseDouble(sweets),Double.parseDouble(meat),Double.parseDouble(salads)},"Ignore")).toString();
        System.out.println("Classified as: "+ PersonalSettings.convertTaste(result));
        //Queries.getMyUser().setClassification(result);

    }


    private int k;
    private ArrayList<Object> classes;
    private ArrayList<DataEntry> dataSet;
    /**
     *
     * @param dataSet The set
     * @param k The number of neighbours to use
     */
    public NearestNeighbour(ArrayList<DataEntry> dataSet, int k){
        this.classes = new ArrayList<Object>();
        this.k = k;
        this.dataSet = dataSet;

        //Load different classes
        for(DataEntry entry : dataSet){
            if(!classes.contains(entry.getY())) classes.add(entry.getY());
        }
    }

    private DataEntry[] getNearestNeighbourType(DataEntry x){
        DataEntry[] retur = new DataEntry[this.k];
        double fjernest = Double.MIN_VALUE;
        int index = 0;
        for(DataEntry tse : this.dataSet){
            double distance = distance(x,tse);
            if(retur[retur.length-1] == null){ //Hvis ikke fyldt
                int j = 0;
                while(j < retur.length){
                    if(retur[j] == null){
                        retur[j] = tse; break;
                    }
                    j++;
                }
                if(distance > fjernest){
                    index = j;
                    fjernest = distance;
                }
            }
            else{
                if(distance < fjernest){
                    retur[index] = tse;
                    double f = 0.0;
                    int ind = 0;
                    for(int j = 0; j < retur.length; j++){
                        double dt = distance(retur[j],x);
                        if(dt > f){
                            f = dt;
                            ind = j;
                        }
                    }
                    fjernest = f;
                    index = ind;
                }
            }
        }
        return retur;
    }

    private static double convertDistance(double d){
        return 1.0/d;
    }

    /**
     * Computes Euclidean distance
     * @param a From
     * @param b To
     * @return Distance
     */
    public static double distance(DataEntry a, DataEntry b){
        double distance = 0.0;
        int length = a.getX().length;
        for(int i = 0; i < length; i++){
            double t = a.getX()[i]-b.getX()[i];
            distance = distance+t*t;
        }
        return Math.sqrt(distance);
    }
    /**
     *
     * @param e Entry to be classifies
     * @return The class of the most probable class
     */
    public Object classify(DataEntry e){
        HashMap<Object,Double> classcount = new HashMap<Object,Double>();
        DataEntry[] de = this.getNearestNeighbourType(e);
        for(int i = 0; i < de.length; i++){
            double distance = NearestNeighbour.convertDistance(NearestNeighbour.distance(de[i], e));
            if(!classcount.containsKey(de[i].getY())){
                classcount.put(de[i].getY(), distance);
            }
            else{
                classcount.put(de[i].getY(), classcount.get(de[i].getY())+distance);
            }
        }
        //Find right choice
        Object o = null;
        double max = 0;
        for(Object ob : classcount.keySet()){
            if(classcount.get(ob) > max){
                max = classcount.get(ob);
                o = ob;
            }
        }

        return o;
    }

    public static class DataEntry{
        private double[] x;
        private Object y;

        public DataEntry(double[] x, Object y){
            this.x = x;
            this.y = y;
        }

        public double[] getX(){
            return this.x;
        }

        public Object getY(){
            return this.y;
        }
    }
}