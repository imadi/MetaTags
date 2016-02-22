package com.meta

import org.apache.commons.logging.LogFactory

class Summarize {

	private static final log = LogFactory.getLog(this)

	static main(args) {
		
		Scanner sc = new Scanner(new File("H:\\excel\\Summarization\\inp.txt"))
		Map hm =[:]
		List contentList = []
		List paragraphs = []
		while(sc.hasNextLine()){
			String paragraph  = sc.nextLine()
			paragraphs << paragraph
			contentList.addAll(paragraph.split("\\."))
		}
		int size = contentList.size()
		
		double score
		double[][] scores = new double[size][size]
		for(int i=0;i<size;i++){
			score = 0.0
			for(int j=0;j<size;j++){
				if(i == j){
					continue
				}
				score = score + getEdgeScore(contentList.get(i),contentList.get(j))
				hm.put(contentList.get(i), score)
			}
		}
		String sentence = ""
		String summary = ""
		paragraphs.each{paragraph->
			
			sentence = getSentence(paragraph, hm);
			if(sentence.trim().length()>0){
				summary = summary + "\n" + sentence;
			}
			
		}
		println "summary" + summary
		log.info "summary " + summary

		/*def keywords = []
		summary.split("\n").each{
			if(it.trim()!=""){
				keywords << ParsingAPI.getKeyWords(it.replaceAll(',',''))
			}
			
		}
		
		keywords =  keywords.flatten().unique().join(',')
		
		println "keywords : " + keywords*/
		
		
	}
	public static double getEdgeScore(String str1,String str2){
		List<String> sen1List = new ArrayList<String>();
		sen1List.addAll(Arrays.asList(str1.split(" ")));
		int sen1Len = sen1List.size();
		
		List<String> sen2List =  new ArrayList<String>();
		sen2List.addAll(Arrays.asList(str2.split(" ")));
		
		int sen2Len = sen2List.size();
		
		sen1List =  sen1List.intersect(sen2List)
		
		double score = (double) sen1List.size() / (double) ((sen1Len+sen2Len)/2);
		return score ;
	}
	public static String getSentence(String paragraph,Map<String, Double> hm){
		String[] sentences = paragraph.split("\\.|\\. ")
		String bestSentence = ""
		double maxval = 0.0;
		if(sentences.length == 1){
			bestSentence = sentences[0]
			return bestSentence
		}
		sentences.each {sentence->
			if(sentence.trim().length()>0){
				if(hm.get(sentence) > maxval){
					maxval = hm.get(sentence);
					bestSentence = sentence;
				}
			}
		}
		return bestSentence;
	}

}
