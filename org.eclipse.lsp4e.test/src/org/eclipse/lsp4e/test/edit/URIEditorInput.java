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

import java.net.URI;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IURIEditorInput;

/**
 * An {@link IURIEditorInput} that carries a custom-scheme URI (e.g.
 * {@code jdt://}). Used for testing the editor-input URI fallback path
 * in {@code LSPEclipseUtils.toUri(IDocument)}.
 */
public class URIEditorInput implements IURIEditorInput {

	private final URI uri;
	private final String name;

	public URIEditorInput(URI uri) {
		this(uri, uri.toString());
	}

	public URIEditorInput(URI uri, String name) {
		this.uri = uri;
		this.name = name;
	}

	@Override
	public URI getURI() {
		return uri;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return name;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
}
