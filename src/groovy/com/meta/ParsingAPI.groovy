package com.meta

import javax.servlet.ServletContext
import edu.stanford.nlp.tagger.maxent.MaxentTagger



class ParsingAPI {
	
	static def getKeyWords(sentence,url){
		url = url.trim()
		MaxentTagger tagger = new MaxentTagger(url);
		String tagged = tagger.tagString("Python is a widely used high-level, general-purpose, interpreted, dynamic programming language.[22][23] Its design philosophy emphasizes code readability, and its syntax allows programmers to express concepts in fewer lines of code than would be possible in languages such as C++ or Java.[24][25] The language provides constructs intended to enable clear programs on both a small and large scale.[26]");
		Set keywords = []

		tagged.split(" ").each {

			if(it.endsWith("/NNP") || it.endsWith("/NN") || it.endsWith("/NNPS")){
				keywords<<it.substring(0,it.indexOf("/"))
			}
		}
		println "keywords**" + keywords
		return keywords
		/*InputStream modelIn = url.openStream()
		keywords = []
		try {
			ParserModel model = new ParserModel(modelIn)
			Parser parser = ParserFactory.create(model)
			Parse[] topParsers = ParserTool.parseLine(sentence, parser, 1)
			topParsers.each {p->
				getPhrases(p)
			}
		}
		catch (Exception e) {
			e.printStackTrace()
		}
		finally {
			if (modelIn != null) {
				try {
					modelIn.close()
				}
				catch (IOException e) {
				}
			}
		}
		return nouns*/
		
	}
	
}
