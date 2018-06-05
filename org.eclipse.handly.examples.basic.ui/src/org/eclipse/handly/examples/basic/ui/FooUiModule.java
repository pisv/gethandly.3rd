/*******************************************************************************
 * Copyright (c) 2014, 2018 1C-Soft LLC and others.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.examples.basic.ui;

import org.eclipse.handly.examples.basic.ui.internal.BasicActivator;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Use this class to register components to be used within the IDE.
 * <p>
 * Note: Xtext-generated {@link BasicActivator} implementation assumes 
 * that this class lives in this package. Don't rename/move this class.
 */
public class FooUiModule
    extends AbstractFooUiModule
{
    public FooUiModule(AbstractUIPlugin plugin)
    {
        super(plugin);
    }
}
