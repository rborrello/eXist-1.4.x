/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-04 The eXist Project
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
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.storage.index;

import java.nio.ByteBuffer;

import org.exist.storage.DBBroker;
import org.exist.storage.journal.LogException;
import org.exist.storage.txn.Txn;
import org.exist.util.ByteArray;
import org.exist.util.FixedByteArray;

/**
 * @author wolf
 *
 */
public class OverflowAppendLoggable extends AbstractBFileLoggable {

    protected long pageNum;
    protected ByteArray data;
    protected int chunkSize;
    protected int startOffset;
    
    /**
     * 
     * 
     * @param page 
     * @param chunk 
     * @param startOffset 
     * @param chunkSize 
     * @param fileId 
     * @param transaction 
     */
    public OverflowAppendLoggable(byte fileId, Txn transaction, long page, 
			ByteArray chunk, int startOffset, int chunkSize) {
        super(BFile.LOG_OVERFLOW_APPEND, fileId, transaction);
        this.pageNum = page;
        this.data = chunk;
        this.chunkSize = chunkSize;
        this.startOffset = startOffset;
    }

    /**
     * @param broker
     * @param transactionId
     */
    public OverflowAppendLoggable(DBBroker broker, long transactionId) {
        super(broker, transactionId);
    }

    public void write(ByteBuffer out) {
        super.write(out);
        out.putInt((int) pageNum);
        out.putInt(chunkSize);
        data.copyTo(startOffset, out, chunkSize);
    }
	
	public void read(ByteBuffer in) {
		super.read(in);
		pageNum = in.getInt();
		chunkSize = in.getInt();
		byte b[] = new byte[chunkSize];
		in.get(b);
		data = new FixedByteArray(b);
	}
	
	public int getLogSize() {
		return super.getLogSize() + 8 + chunkSize;
	}
	
	public void redo() throws LogException {
		getIndexFile().redoAppendOverflow(this);
	}
	
    public void undo() throws LogException {
        getIndexFile().undoAppendOverflow(this);
    }
    
	public String dump() {
		return super.dump() + " - append to overflow page " + pageNum;
	}
}