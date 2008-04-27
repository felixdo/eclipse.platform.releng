/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.releng.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.osgi.util.NLS;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.core.resources.IFile;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;


/**
 * @author droberts
 */
public abstract class SourceFile {
	
	IFile file;
	List comments = new ArrayList();
	StringWriter contents = new StringWriter();
	private ITextFileBufferManager textFileBufferManager;


	public SourceFile(IFile file) {
		super();
		this.file = file;
		initialize();
	}

	public abstract String getCommentStart();
	public abstract String getCommentEnd();
	

	private void initialize() {
		textFileBufferManager= FileBuffers.createTextFileBufferManager();
		try {
			ITextFileBuffer fileBuffer= getFileBuffer();
			if (fileBuffer == null)
				return;
			
			IDocument document;
			try {
				document= fileBuffer.getDocument();
			} finally {
				try {
					textFileBufferManager.disconnect(file.getFullPath(), LocationKind.IFILE, null);
				} catch (CoreException e) {
					e.printStackTrace();
					// continue as we were able to get the file buffer and its document
				}
			}
			
			String newLine= TextUtilities.getDefaultLineDelimiter(document);
			BufferedReader aReader = new BufferedReader(new StringReader(document.get()));
			String aLine = aReader.readLine();
			String comment = ""; //$NON-NLS-1$
			BufferedWriter contentsWriter = new BufferedWriter(contents);
			int lineNumber = 0;
			int commentStart = 0;
			int commentEnd = 0;
			boolean inComment = false;
			
			while (aLine != null) {
				contentsWriter.write(aLine);
				contentsWriter.newLine();
				if (!inComment && aLine.trim().startsWith(getCommentStart())) {
					// start saving comment
					inComment = true;
					commentStart = lineNumber;
				}
				
				if (inComment) {
					comment = comment + aLine + newLine;
								
					if (aLine.trim().endsWith(getCommentEnd()) && commentStart != lineNumber) {
						// stop saving comment
						inComment = false;
						commentEnd = lineNumber;
						BlockComment aComment = new BlockComment(commentStart, commentEnd, comment.toString(), getCommentStart(), getCommentEnd());
						comments.add(aComment);
						comment = ""; //$NON-NLS-1$
						commentStart = 0;
						commentEnd = 0;
					}
				}
				
				aLine = aReader.readLine();
				lineNumber++;
			}
			
			aReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return BlockComment
	 */
	public BlockComment firstBlockComment() {
		if (comments.isEmpty()) {
			return null;
		} else {
			return (BlockComment) comments.get(0);
		}
	}

	private ITextFileBuffer getFileBuffer() {
		try {
			textFileBufferManager.connect(file.getFullPath(), LocationKind.IFILE, null);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}

		ITextFileBuffer fileBuffer= textFileBufferManager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
		if (fileBuffer != null)
			return fileBuffer;

		System.err.println(NLS.bind(Messages.getString("SourceFile.0"), file.getFullPath())); //$NON-NLS-1$
			return null;
	}
	
	/**
	 * @param string
	 */
	public void insert(String string) {
		ITextFileBuffer fileBuffer= getFileBuffer();
		if (fileBuffer == null)
			return;

		try {
			IDocument document= fileBuffer.getDocument();
			document.replace(0, 0, string);
			fileBuffer.commit(null, false);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			try {
				textFileBufferManager.disconnect(file.getFullPath(), LocationKind.IFILE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return BlockComment
	 */
	public BlockComment firstCopyrightComment() {
		Iterator anIterator = comments.iterator();
		while (anIterator.hasNext()) {
			BlockComment aComment = (BlockComment) anIterator.next();
			if (aComment.isCopyright()) {
				return aComment;
			}
		}
		
		return null;
	}

	/**
	 * @param firstCopyrightComment
	 * @param string
	 */
	public void replace(BlockComment aComment, String string) {
		
	
		try {
			ITextFileBuffer fileBuffer= getFileBuffer();
			if (fileBuffer == null)
				return;
			
			IDocument document= fileBuffer.getDocument();

			IRegion startLine= document.getLineInformation(aComment.start);
			IRegion endLine= document.getLineInformation(aComment.end + 1);
			document.replace(startLine.getOffset(), endLine.getOffset() - startLine.getOffset(), string);
			
			fileBuffer.commit(null, false);
		
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} finally  {
			try {
				FileBuffers.getTextFileBufferManager().disconnect(file.getFullPath(), LocationKind.IFILE, null);
			} catch (CoreException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	/**
	 * @return boolean
	 */
	public boolean hasMultipleCopyrights() {
		int count = 0;
		Iterator anIterator = comments.iterator();
		while (anIterator.hasNext()) {
			BlockComment aComment = (BlockComment) anIterator.next();
			if (aComment.isCopyright()) {
				count++;
			}
			if (count > 1) {
				return true;
			}
		}
		return false;
	}
}