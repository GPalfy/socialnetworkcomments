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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//$example on:schema_merging$
//$example on:json_dataset$
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//$example off:json_dataset$
//$example off:schema_merging$
//$example off:basic_parquet_example$
import org.apache.spark.sql.SparkSession;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordFreqController {
	private static WordFreqView wf_view;

	public static boolean setWordFreqView(WordFreqView view){
		wf_view = view;
		return true;
	}

	public static void view(WordFreq model) {
		wf_view.view(model);
	}

	public static void list(ArrayList<WordFreq> in_list) {
		wf_view.list(in_list);
	}

	public static ArrayList<WordFreq> transformHTMLTableToList(Document document) {
		Pattern p = Pattern.compile("\\p{L}+", Pattern.UNICODE_CHARACTER_CLASS);
		ArrayList<WordFreq> list = new ArrayList<WordFreq>();
		Element table = document.select("table").get(0);
		Elements rows = table.select("tr");
		for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
			Element row = rows.get(i);
			Elements cols = row.select("td");
			String word = cols.get(1).text().trim();
			Matcher m = p.matcher(word);
			if (m.matches()) {
				int numero = Integer.parseInt(cols.get(0).text());
				int count = Integer.parseInt(cols.get(2).text());
				WordFreq tmp_obj_wf = new WordFreq(numero, word, count);
				list.add(tmp_obj_wf);
			}
		}
		return list;
	}

	public static Dataset<Row> transformHTMLTableToDataset(SparkSession spark, Document document) {
		ArrayList<WordFreq> list = transformHTMLTableToList(document);

		// Load the text into a Spark RDD, which is a distributed representation of each line of text
		final Dataset<Row> wfDS = spark.createDataFrame(list, WordFreq.class);	
		
		Dataset<Row> wfClean = NormTxtController.transformTxt(spark, wfDS, "word");
		return wfClean;
	}

	public static void transformHTMLTableToDatasetExample(SparkSession spark, Document document) {
		ArrayList<WordFreq> list = transformHTMLTableToList(document);

		// Load the text into a Spark RDD, which is a distributed representation of each line of text
		final Dataset<Row> wfDS = spark.createDataFrame(list, WordFreq.class);

		wfDS.printSchema();
		wfDS.createOrReplaceTempView("wordfrequencies");
		wfDS.show();


		Dataset<Row> result = spark.sql("SELECT no, lower(word) AS word, count FROM wordfrequencies");

		result.show(100);
		long n_words = result.count();
		System.out.println("Number of frequent words " + n_words);
	}
}
