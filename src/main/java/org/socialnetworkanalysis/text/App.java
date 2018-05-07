/*
MIT License

Copyright (c) 2017-2018 Juraj Palfy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.socialnetworkanalysis.text;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.sql.SparkSession;


public class App 
{
	private static void runWordFreqDataset(SparkSession spark) {
    	// String freq_word = "data/words.html";
		// File words.html contains duplicate
		// words e.g. na - Na, v - V, etc.
		String freq_word = "data/lemmas.html";
    	TextResourceController.loadWordFreqToDataset(spark, freq_word);		
	}
	
    public static void main( String[] args )
    {	// Open the Spark Context
        final SparkSession spark = SparkSession
      	      .builder()
      	      .appName("Text Cleaning")
      	      .master("local")
      	      .getOrCreate();
        
        // Set logger level WARN
        Logger.getLogger("org").setLevel(Level.WARN);
        Logger.getLogger("akka").setLevel(Level.WARN);
        Logger.getLogger("spark").setLevel(Level.WARN);
        LogManager.getRootLogger().setLevel(Level.WARN);
        
    	runWordFreqDataset(spark);
    	
		String file_freqword = "data/lemmas.html";
		String file_comments = "data/dev-test-exploratory-analysis/224564804326967_facebook_comments.csv";

		// Comments preprocessing (formatting, cleaning, normalization is left out).
		
        String file_sentpos = "data/sentiment-lexicons/positive_sk.txt";
        String file_sentneg = "data/sentiment-lexicons/negative_sk.txt";
		// Save the formatted, cleaned and normalized dataset to use along 
        // exploratory data analysis and machine learning tasks.
        // TextResourceController.saveNormDatasets(spark, file_comments, file_freqword, morp_word, file_sentpos, file_sentneg);
		
        // Close the Spark Context
        spark.stop();
    }
}
