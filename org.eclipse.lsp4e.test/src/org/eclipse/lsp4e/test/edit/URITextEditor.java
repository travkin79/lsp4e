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

import org.eclipse.ui.editors.text.TextEditor;

/**
 * A minimal {@link TextEditor} (which is an {@code ITextEditor}) that uses
 * {@link URIEditorDocumentProvider} — a document provider that does NOT
 * register documents with the {@code ITextFileBufferManager}.
 *
 * <p>This simulates editors like JDT's {@code ClassFileEditor} that manage
 * their own documents outside the buffer manager.</p>
 */
public class URITextEditor extends TextEditor {

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setDocumentProvider(new URIEditorDocumentProvider());
	}
}
