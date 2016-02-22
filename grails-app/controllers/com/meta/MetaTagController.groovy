package com.meta

import grails.converters.JSON

class MetaTagController {

    def index() { 

    	render(view:"index", model:[:])
    }

    def suggestMetaKeyWords(){

    	Map hm =[:]
    	String content = params.content
    	List paragraphs =  content.split("\n")
    	List contentList  = content.split("\\.")
    	int size = contentList.size()
		
		double score
		double[][] scores = new double[size][size]
		for(int i=0;i<size;i++){
			score = 0.0
			for(int j=0;j<size;j++){
				if(i == j){
					continue
				}
				score = score + Summarize.getEdgeScore(contentList.get(i),contentList.get(j))
				hm.put(contentList.get(i), score)
			}
		}
		String sentence = ""
		String summary = ""
		paragraphs.each{paragraph->
			
			sentence = Summarize.getSentence(paragraph, hm);
			if(sentence.trim().length()>0){
				summary = summary + "\n" + sentence;
			}
			
		}
		log.info  "summary : " + summary
		def keywords = []
		def url = request.getServletContext().getResource("/WEB-INF/en-parser-chunking.bin")
		summary.split("\n").each{
			if(it.trim()!=""){
				keywords << ParsingAPI.getKeyWords(it.replaceAll(',',''),url)
			}
			
		}
		
		keywords =  keywords.flatten().unique().join(',')
		
		log.info "keywords : " + keywords
		def result = [summary:summary,keywords:keywords]
    	render result as JSON
    }
}
