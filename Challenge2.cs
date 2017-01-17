using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

class Solution {
	static int findMutationDistance(string start, string end, string[] bank) {
        
         //will contain our final answer
         List<int> answer = new List<int>(1);
          
         //record the mutations we've already recursed into
         HashSet<string> visited = new HashSet<string>();
        
         //Depth first search recursively to find the correct number of mutations
         SearchValidMutations(start, end, bank, 0, ref answer, ref visited);
        
         //Is there an answer? if so thats our answer, if not DFS couldn't find one and return -1.
         return answer.Count() == 1 ? answer[0] : -1;
 
    }

    static int SearchValidMutations(string start, string end, string[] bank, int i, ref List<int> answer, ref HashSet<string> visited){
        visited.Add(start);
        
        //if we're at the desired state, our depth is our answer
        if(start == end && answer.Count() != 1){
            answer.Add(i);
            return i;
        }
        //if we already have an answer don't do more work
        if(answer.Count() > 0){
            return i;
        }
        
        //check every mutation in the bank
        foreach(string bankVal in bank){
            
            //find the number of differences between our starting string and the current bank value
            int numDiffs = 0;
            
            //record our number of characters progressing towards end
            //ie we want to be actively progressing towards the end mutation, if we're not then we shouldn't recurse
            int startProg = 0;
            int bankValProg = 0;
            for(int c = 0; c < start.Length; c++){
                //number of characters the start string is away from the desired result
                if(start[c] == end[c])
                    startProg++;
                
                //number of characters the current bank value is away from the desired result
                if(bankVal[c] == end[c])
                    bankValProg++;
                
                //record the number of differences between the current bank value and our start string
                if(start[c] != bankVal[c]){
                   numDiffs++; 
                }
                
            }
            
            
            //if the number of differences is one (ie value mutation)
            //and if we're actively progressing, we should recurse down and search for solutions from this bank value
            if(numDiffs == 1 && bankValProg > startProg){
                bool shouldRecurse = false;
                
                //if we've already visited this mutation state, don't explore it again
                //also if we already have an answer don't recurse again
                if(!visited.Contains(bankVal) && answer.Count() != 1)
                    shouldRecurse=true;
                
                if(shouldRecurse){
                    
                    //recurse and explore the bank value, incrementing the depth
                    SearchValidMutations(bankVal, end, bank, ++i, ref answer, ref visited);
                }
                
            }
        }
        
        return -1;
        
        
        
    }
	
	 static void Main(String[] args) {
        string fileName = System.Environment.GetEnvironmentVariable("OUTPUT_PATH");
        TextWriter tw = new StreamWriter(@fileName, true);
        int res;
        string _start;
        _start = Console.ReadLine();
        
        string _end;
        _end = Console.ReadLine();
        
        
        int _bank_size = 0;
        _bank_size = Convert.ToInt32(Console.ReadLine());
        string[] _bank = new string [_bank_size];
        string _bank_item;
        for(int _bank_i = 0; _bank_i < _bank_size; _bank_i++) {
            _bank_item = Console.ReadLine();
            _bank[_bank_i] = _bank_item;
        }
        
        res = findMutationDistance(_start, _end, _bank);
        tw.WriteLine(res);
        
        tw.Flush();
        tw.Close();
    }





}