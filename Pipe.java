
//Pipe Data Structure
class Pipe {
    String start=null;
    String end=null;
    int length=0;
    int offPeriod=0;
    int[][] offPeriodTimes;
    
    public Pipe(String start, String end, int length, int offPeriod,int[][] offPeriodTimes){
        this.start=start;
        this.end=end;
        this.length=length;
        this.offPeriod=offPeriod;
        this.offPeriodTimes=new int[this.offPeriod][2];
        for(int i=0;i<offPeriod;i++){
            for(int j=0;j<2;j++){
                this.offPeriodTimes[i][j]=offPeriodTimes[i][j];
            }
        }
    }
    
    //Return Length of Pipe
    public int length(){
        return this.length;
    }
    
    //Return Start node of Pipe
    public String from(){
        return this.start;
    }
    
    //Return End node of Pipe
    public String to(){
        return this.end;
    }
    
    //Return off Periods of pipe
    public int[][] offPeriodTimes(){
        return this.offPeriodTimes;
    }
}