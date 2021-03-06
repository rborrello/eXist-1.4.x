/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-07 The eXist Project
 *  http://exist-db.org
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * $Id: TextExtractor.java 12946 2010-10-17 19:36:20Z wolfgang_m $
 */
package org.exist.indexing.lucene;

import org.exist.dom.QName;
import org.exist.util.XMLString;

/**
 * Extract text from an XML fragment to be indexed with Lucene.
 * This interface provides an additional abstraction to handle whitespace
 * between elements or ignore certain elements.
 */
public interface TextExtractor {

    public void configure(LuceneConfig config, LuceneIndexConfig idxConfig);

    public int startElement(QName name);

    public int endElement(QName name);

    public int beforeCharacters();
    
    public int characters(XMLString value);

    public LuceneIndexConfig getIndexConfig();
    
    public XMLString getText();
}