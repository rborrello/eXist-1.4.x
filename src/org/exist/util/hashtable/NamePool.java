/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-06 Wolfgang M. Meier
 *  wolfgang@exist-db.org
 *  http://exist.sourceforge.net
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
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.util.hashtable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.exist.dom.QName;

/**
 * @author Pieter Deelen
 */
public class NamePool {

    private ConcurrentMap pool;

    public NamePool() {
        pool = new ConcurrentHashMap();
    }
    
    public QName getSharedName(QName name) {
        QName sharedName = (QName)pool.putIfAbsent(name, name);
        if (sharedName == null) {
            // The name was not in the pool, return the name just added.
            return name;
        } else {
            // The name was in the pool, return the shared name.
            return sharedName;
        }
    }
    
}
