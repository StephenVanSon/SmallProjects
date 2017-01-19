import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String args[] ) throws Exception {
        //get all the inputs
        Scanner input = new Scanner(System.in);
        String dateRange =  input.nextLine();
        
        //skip empty line
        input.nextLine();
        
        //get all actual data
        List<String> data = new ArrayList<String>();
        while(input.hasNextLine()){
            data.add(input.nextLine());
        }
        

        //remove whitespace from daterange 
        dateRange = dateRange.replaceAll("\\s","");
        String[] range = dateRange.split(",");
        
        //set date format to given format
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        Date startDate = format.parse(range[0]);
        Date endDate = format.parse(range[1]);
        
        //put data into buckets according to date and data type
        HashMap<Date, TreeMap<String, Integer>> dataMappedToDates = PutDataIntoBuckets(startDate, endDate, format, data);
        
        //sort by date and create output
        CreateOutputInDesiredOrder(dataMappedToDates, format);     
    }
    
    //helper function to put the data into buckets by date and within those buckets put them into buckets by data type
    public static HashMap<Date, TreeMap<String, Integer>> PutDataIntoBuckets(Date startDate, Date endDate, DateFormat format, List<String> data) throws ParseException{
        
        //our final bucketed data
        HashMap<Date, TreeMap<String, Integer>> dataMappedToDates = new HashMap<Date, TreeMap<String, Integer>>();
       
        
        //put the data in the buckets
        for(int i=0; i < data.size(); i++){
            //current piece of data (needing to be parsed)
            String current = data.get(i);
            
            //remove whitespace
            current = current.replaceAll("\\s", "");
            
            //split into parts
            String[] currParts = current.split(",");
            
            //date is first part
            String date = currParts[0];
            
            //data type is second part
            String dataKey = currParts[1];
            
            //value is third part
            Integer number = Integer.parseInt(currParts[2]);
            
            
            //split the date up into year, month, day
            String[] dateParts = date.split("-");
            //get the year
            String dateYear = dateParts[0];
            //get the month
            String dateMonth = dateParts[1];
            //parse as a date with desired format
            Date ourDate = format.parse(dateYear + "-" + dateMonth);
            
            //is the date within our desired range?
            if(ourDate.before(endDate) && !ourDate.before(startDate)){
                //do we already have a bucket for this date? if so put its data in that bucket
                if(dataMappedToDates.containsKey(ourDate)){
                   TreeMap<String, Integer> dataForDate = dataMappedToDates.get(ourDate); 
                    
                   //do we already have a bucket for this data? if so update the value
                   if(dataForDate.containsKey(dataKey)){
                       dataForDate.put(dataKey, dataForDate.get(dataKey) + number);
                   }
                   //otherwise add it as a new bucket
                   else{
                       dataForDate.put(dataKey, number);
                   }
                }
                //if we don't have a bucket, create one
                else{
                    //create a new data bucket representing this date (treemap because sorted alphabetically)
                    TreeMap<String, Integer> dataBucketsForDate = new TreeMap<String, Integer>();
                    //put the number in the bucket corresponding to the data type
                    dataBucketsForDate.put(dataKey, number);
                    
                    //put the date bucket into our data array
                    dataMappedToDates.put(ourDate, dataBucketsForDate);
                }
            }
            
        }
        
        return dataMappedToDates;
        
        
    }
    
    
    //helper function to sort the data entries by date and output them
    public static void CreateOutputInDesiredOrder(HashMap<Date, TreeMap<String, Integer>> dataMappedToDates, DateFormat format) {
        
        //get all our dates in descending order
        ArrayList<Date> sortedByDateDesc = new ArrayList<Date>(dataMappedToDates.size());
        sortedByDateDesc.addAll(dataMappedToDates.keySet());
        Collections.sort(sortedByDateDesc, Collections.reverseOrder());
        
        
        //create the output line for each date bucket
        for(int i =0; i < sortedByDateDesc.size(); i++){
            Date currDate = sortedByDateDesc.get(i);
            //get the current date bucket
            TreeMap<String, Integer> ourDateData = dataMappedToDates.get(currDate);
            //start the output with the date of the bucket
            String strToPrint = format.format(currDate);
            
            //iterate over the data types for this date
            Set<String> keys = ourDateData.keySet();
            for(String key : keys){
                Integer num = ourDateData.get(key);
                //add the data type with the value of it
                strToPrint += ", " + key + ", " + num;
            }
            
            System.out.println(strToPrint);
        }
    }
    
    
    
    
    
    
    
    
}