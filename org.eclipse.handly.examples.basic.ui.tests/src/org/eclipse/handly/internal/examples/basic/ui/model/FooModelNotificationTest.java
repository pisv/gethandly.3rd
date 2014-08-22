/*******************************************************************************
 * Copyright (c) 2014 1C LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.internal.examples.basic.ui.model;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.Path;
import org.eclipse.handly.examples.basic.ui.model.FooModelCore;
import org.eclipse.handly.examples.basic.ui.model.IFooFile;
import org.eclipse.handly.examples.basic.ui.model.IFooModel;
import org.eclipse.handly.examples.basic.ui.model.IFooProject;
import org.eclipse.handly.junit.WorkspaceTestCase;
import org.eclipse.handly.model.IElementChangeEvent;
import org.eclipse.handly.model.IElementChangeListener;
import org.eclipse.handly.model.IHandleDelta;
import org.eclipse.handly.model.impl.HandleDelta;

/**
 * Foo element change notification tests.
 */
public class FooModelNotificationTest
    extends WorkspaceTestCase
{
    private IFooModel fooModel = FooModelCore.getFooModel();
    private FooModelListener listener = new FooModelListener();

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        setUpProject("Test001");
        fooModel.addElementChangeListener(listener);
    }

    @Override
    protected void tearDown() throws Exception
    {
        fooModel.removeElementChangeListener(listener);
        super.tearDown();
    }

    public void testFooModelNotification() throws Exception
    {
        IFooProject fooProject1 = fooModel.getFooProject("Test001");
        IFooProject fooProject2 = fooModel.getFooProject("Test002");

        setUpProject("Test002");
        assertEquality(newDelta().insertAdded(fooProject2), listener.delta);

        IFooFile fooFile1 = fooProject1.getFooFile("test.foo");
        fooFile1.getFile().touch(null);
        assertEquality(
            newDelta().insertChanged(fooFile1, HandleDelta.F_CONTENT),
            listener.delta);

        fooFile1.getFile().copy(new Path("/Test002/test1.foo"), true, null);
        assertEquality(
            newDelta().insertAdded(fooProject2.getFooFile("test1.foo")),
            listener.delta);

        fooFile1.getFile().delete(true, null);
        assertEquality(newDelta().insertRemoved(fooFile1), listener.delta);

        IFooFile fooFile2 = fooProject2.getFooFile("test.foo");
        IFooFile movedFooFile2 = fooProject1.getFooFile("test1.foo");
        fooFile2.getFile().move(new Path("/Test001/test1.foo"), true, null);
        assertEquality(
            newDelta().insertMovedTo(movedFooFile2, fooFile2).insertMovedFrom(
                fooFile2, movedFooFile2), listener.delta);

        fooProject2.getProject().close(null);
        assertEquality(
            newDelta().insertRemoved(fooProject2, HandleDelta.F_OPEN),
            listener.delta);

        fooProject2.getProject().open(null);
        assertEquality(newDelta().insertAdded(fooProject2, HandleDelta.F_OPEN),
            listener.delta);

        fooProject2.getProject().delete(true, null);
        assertEquality(newDelta().insertRemoved(fooProject2), listener.delta);

        IProjectDescription description =
            fooProject1.getProject().getDescription();
        String[] oldNatures = description.getNatureIds();
        description.setNatureIds(new String[0]);
        fooProject1.getProject().setDescription(description, null);
        assertEquality(
            newDelta().insertRemoved(fooProject1, HandleDelta.F_DESCRIPTION),
            listener.delta);

        description.setNatureIds(oldNatures);
        fooProject1.getProject().setDescription(description, null);
        assertEquality(
            newDelta().insertAdded(fooProject1, HandleDelta.F_DESCRIPTION),
            listener.delta);

        IFooProject movedFooProject1 = fooModel.getFooProject("Test");
        fooProject1.getProject().move(new Path("Test"), true, null);
        assertEquality(
            newDelta().insertMovedTo(movedFooProject1, fooProject1).insertMovedFrom(
                fooProject1, movedFooProject1), listener.delta);
    }

    private HandleDelta newDelta()
    {
        return new HandleDelta(fooModel);
    }

    private static void assertEquality(IHandleDelta expected,
        IHandleDelta actual)
    {
        if (expected == null)
        {
            assertNull(actual);
            return;
        }
        assertNotNull(actual);
        assertEquals(expected.getElement(), actual.getElement());
        assertEquals(expected.getKind(), actual.getKind());
        assertEquals(expected.getFlags(), actual.getFlags());
        assertEquals(expected.getMovedToElement(), actual.getMovedToElement());
        assertEquals(expected.getMovedFromElement(),
            actual.getMovedFromElement());
        IHandleDelta[] expectedChildren = expected.getAffectedChildren();
        IHandleDelta[] actualChildren = actual.getAffectedChildren();
        assertEquals(expectedChildren.length, actualChildren.length);
        for (int i = 0; i < expectedChildren.length; i++)
            assertEquality(expectedChildren[i], actualChildren[i]);
    }

    private static class FooModelListener
        implements IElementChangeListener
    {
        public HandleDelta delta;

        @Override
        public void elementChanged(IElementChangeEvent event)
        {
            delta = (HandleDelta)event.getDelta();
        }
    }
}
