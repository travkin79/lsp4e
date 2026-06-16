/*******************************************************************************
 * Copyright (c) 2026 Advantest Europe GmbH and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Dietrich Travkin (Solunar GmbH) - initial implementation
 *******************************************************************************/
package org.eclipse.lsp4e.test.edit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;

/**
 * A document provider that creates plain {@link Document} instances without
 * registering them with the {@code ITextFileBufferManager}. This simulates
 * the behavior of JDT's {@code ClassFileDocumentProvider} or Xtext's
 * document provider.
 */
public class URIEditorDocumentProvider extends AbstractDocumentProvider {

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		return new Document("// test content");
	}

	@Override
	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		return null;
	}

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite)
			throws CoreException {
		// no-op — read-only test editor
	}

	@Override
	public boolean isModifiable(Object element) {
		return false;
	}

	@Override
	public boolean isReadOnly(Object element) {
		return true;
	}

	@Override
	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		return null;
	}

	/**
	 * Returns the document for the given editor input. This is a convenience
	 * method for test access.
	 */
	public IDocument getTestDocument(IEditorInput input) {
		return getDocument(input);
	}
}
