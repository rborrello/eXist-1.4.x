/*
 * eXist Open Source Native XML Database
 * Copyright (C) 2006-2009 The eXist Project
 * http://exist-db.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *  
 *  $Id$
 */
package org.exist.performance.xquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.exist.dom.DefaultDocumentSet;
import org.exist.dom.MutableDocumentSet;
import org.exist.dom.QName;
import org.exist.indexing.lucene.LuceneIndex;
import org.exist.indexing.lucene.LuceneIndexWorker;
import org.exist.security.PermissionDeniedException;
import org.exist.storage.TextSearchEngine;
import org.exist.util.Occurrences;
import org.exist.xquery.*;
import org.exist.xquery.value.*;

public class RandomText extends BasicFunction {

    public final static FunctionSignature signature = new FunctionSignature(
            new QName("random-text", PerfTestModule.NAMESPACE_URI, PerfTestModule.PREFIX),
                "This function generates a string of random words.",
                new SequenceType[] {
                    new FunctionParameterSequenceType("max-words", Type.INT, Cardinality.EXACTLY_ONE, "The maximum number of random words to generate.")
                },
            new SequenceType(Type.STRING, Cardinality.EXACTLY_ONE));

    private static String[] words = null;

    private Random random = new Random();

    public RandomText(XQueryContext context) {
        super(context, signature);
    }

    public Sequence eval(Sequence[] args, Sequence contextSequence) throws XPathException {
        if (words == null)
            generateWordList();
        int max = ((IntegerValue)args[0].itemAt(0)).getInt();
        max = random.nextInt(max) + 1;
        StringBuffer text = new StringBuffer();
        for (int i = 0; i < max; i++) {
            if (text.length() > 0)
                text.append(' ');
            
            if(words.length>0){
                text.append(words[random.nextInt(words.length)]);
            }
        }
        return new StringValue(text.toString());
    }

    private void generateWordList() throws XPathException {
		MutableDocumentSet docs = new DefaultDocumentSet();
		docs = context.getBroker().getAllXMLResources(docs);
		LuceneIndexWorker index = (LuceneIndexWorker) context.getBroker()
		        .getIndexController().getWorkerByIndexId(LuceneIndex.ID);
		if (index == null)
			throw new XPathException(this, "pt:random-text requires lucene index to be enabled");
		
		Occurrences[] occurrences = index.scanIndex(context, docs, null, null);
		
		List list = new ArrayList();
        list.add("database");
        list.add("xquery");
		for (int i = 0; i < occurrences.length; i++) {
		    list.add(occurrences[i].getTerm().toString());
		}
		words = new String[list.size()];
		list.toArray(words);
    }
}
