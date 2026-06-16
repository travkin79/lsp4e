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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.lsp4e.test.utils.AbstractTestWithProject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the editor-input URI fallback in
 * {@code LSPEclipseUtils.toUri(IDocument)} and the fix to
 * {@code LSPEclipseUtils.toUri(IEditorInput)} for non-file URI schemes.
 */
class LSPEclipseUtilsEditorInputFallbackTest extends AbstractTestWithProject {

	private final List<IEditorPart> openedEditors = new ArrayList<>();

	@AfterEach
	void closeEditors() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		for (IEditorPart editor : openedEditors) {
			page.closeEditor(editor, false);
		}
		openedEditors.clear();
	}

	/**
	 * Verifies that {@code toUri(IDocument)} resolves the URI from an open
	 * editor's {@code IURIEditorInput} when the document is not managed by
	 * the buffer manager. Opens two editors so that the first one becomes
	 * inactive — both must resolve correctly regardless of active state.
	 */
	@Test
	void testToUri_FindsUriForDocumentUsingUriEditorInput() throws Exception {
		URI jdtUri1 = URI.create("jdt://contents/rt.jar/java.lang/String.class");
		URIEditorInput input1 = new URIEditorInput(jdtUri1, "String.class");

		URI jdtUri2 = URI.create("jdt://contents/rt.jar/java.util/List.class");
		URIEditorInput input2 = new URIEditorInput(jdtUri2, "List.class");

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String editorId = "org.eclipse.lsp4e.test.edit.uriTextEditor";

		// Open first editor — it will become inactive when the second one opens
		IEditorPart editor1 = page.openEditor(input1, editorId);
		assertNotNull(editor1, "First editor should have opened");
		openedEditors.add(editor1);

		// Open second editor — this one becomes the active editor
		IEditorPart editor2 = page.openEditor(input2, editorId);
		assertNotNull(editor2, "Second editor should have opened");
		openedEditors.add(editor2);

		// Verify the first (now inactive) editor's document resolves
		ITextEditor textEditor1 = (ITextEditor) editor1;
		IDocument document1 = textEditor1.getDocumentProvider().getDocument(input1);
		assertNotNull(document1, "Document should be available from the first editor");

		URI resolved1 = LSPEclipseUtils.toUri(document1);
		assertEquals(jdtUri1, resolved1,
				"toUri(IDocument) should resolve the jdt:// URI from the inactive editor");

		// Verify the second (active) editor's document resolves
		ITextEditor textEditor2 = (ITextEditor) editor2;
		IDocument document2 = textEditor2.getDocumentProvider().getDocument(input2);
		assertNotNull(document2, "Document should be available from the second editor");

		URI resolved2 = LSPEclipseUtils.toUri(document2);
		assertEquals(jdtUri2, resolved2,
				"toUri(IDocument) should resolve the jdt:// URI from the active editor");
	}

}
