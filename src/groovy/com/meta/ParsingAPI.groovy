package com.meta

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.chunker.ChunkerME
import javax.servlet.ServletContext



class ParsingAPI {
	
	static Set nouns= []
	static Set verbs= []
	static Set adjs= []
	
	static def getKeyWords(sentence,url){
	
		InputStream modelIn = url.openStream()
		
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
		return nouns
		
	}
	public static void getPhrases(Parse p) {
		if (p.getType().equals("NN") || p.getType().equals("NNS") ||  p.getType().equals("NNP") || p.getType().equals("NNPS")) {
			nouns.add(p.getCoveredText())
		}

		if (p.getType().equals("JJ") || p.getType().equals("JJR") || p.getType().equals("JJS")) {
			adjs.add(p.getCoveredText())
		}

		if (p.getType().equals("VB") || p.getType().equals("VBP") || p.getType().equals("VBG")|| p.getType().equals("VBD") || p.getType().equals("VBN")) {
			verbs.add(p.getCoveredText())
		}

		for (Parse child : p.getChildren()) {
			getPhrases(child)
		}
	}
}
