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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.spark.sql.SparkSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

public class TextResourceController {
	
	public static boolean strIsEmpty( final String s ) {
		  // Null string & empty string
		  return s == null || s.trim().isEmpty();
	}
	
	public static void errFileNameEmpty () {
		System.err.println("File name is empty.");
		System.err.println("Program exits with error 1.");
		System.exit(1);
	}
	
    public static ArrayList<WordFreq> loadWordFreq(String fname) {
    	ArrayList<WordFreq> arr_wf = null;
        try {// We are sure that the file name is not empty?
        	if (!strIsEmpty(fname)) {
	            File file = new File(fname.trim());
	            Document document = Jsoup.parse(file, "UTF-8", "");
	        	WordFreqView WFV = new WordFreqView();
	        	WordFreqController.setWordFreqView(WFV);
	        	arr_wf = WordFreqController.transformHTMLTableToList(document);
        	} else {
        		errFileNameEmpty();
        	}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return arr_wf;
    }
       
    public static Dataset<Row> loadWordFreqToDataset(SparkSession spark, String fname) {
    	Dataset<Row> ds = null;
        try {// We are sure that the file name is not empty?
        	if (!strIsEmpty(fname)) {
	            File file = new File(fname.trim());
	            Document document = Jsoup.parse(file, "UTF-8", "");
	        	WordFreqView WFV = new WordFreqView();
	        	WordFreqController.setWordFreqView(WFV);
	        	ds = WordFreqController.transformHTMLTableToDataset(spark, document);
        	} else {
        		errFileNameEmpty();
        	}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ds;
    }
    
    public static void listWordFreq(ArrayList<WordFreq> in_lst) {
    	WordFreqView WFV = new WordFreqView();
    	WordFreqController.setWordFreqView(WFV);
    	WordFreqController.list(in_lst);
    }
    
	public static void errNotFile () {
		System.err.println("File does not exists.");
		System.err.println("Program exits with error 1.");
		System.exit(1);
	}
	 
    
    public static void saveNormDatasets(SparkSession spark, String file_comments, String file_stopwords, String file_lexeme, String file_sentiment_pos, String file_sentiment_neg) {	
    	Dataset<Row> ds_sentiment_pos = loadSentiment(spark, file_sentiment_pos);
    	Dataset<Row> ds_sentiment_neg = loadSentiment(spark, file_sentiment_neg);
    	
		String fname_sentiment_pos = "data/dev-test-exploratory-analysis/sentiment_pos_norm";
		String fname_sentiment_neg = "data/dev-test-exploratory-analysis/sentiment_neg_norm";
		System.out.println("Save normalized positive sentiments to" + fname_sentiment_pos);
		ds_sentiment_pos
		.write()
    	.format("text")
    	.mode(SaveMode.Overwrite)
    	.save(fname_sentiment_pos);

		System.out.println("Save normalized negative sentiments to" + fname_sentiment_neg);
		ds_sentiment_neg
		.write()
    	.format("text")
    	.mode(SaveMode.Overwrite)
    	.save(fname_sentiment_neg);
    	// CommentsController.saveNormalizedDatasets(spark, ds_text, ds_stopwords, ds_lexeme);
    }
}
