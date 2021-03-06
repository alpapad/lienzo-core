/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.lienzo.client.core.shape.wires;

import java.util.Collections;
import java.util.Iterator;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.tooling.common.api.types.Activatible;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;

public final class ControlHandleList extends Activatible implements IControlHandleList
{
    private Layer                                m_layer;

    private final NFastArrayList<IControlHandle> m_chlist = new NFastArrayList<IControlHandle>();

    public ControlHandleList()
    {
    }

    @Override
    public final int size()
    {
        return m_chlist.size();
    }

    @Override
    public final boolean isEmpty()
    {
        return (0 == m_chlist.size());
    }

    @Override
    public final boolean contains(final IControlHandle handle)
    {
        if ((null != handle) && (m_chlist.size() > 0))
        {
            return m_chlist.contains(handle);
        }
        return false;
    }

    @Override
    public final void add(final IControlHandle handle)
    {
        if (false == contains(handle))
        {
            m_chlist.add(handle);
        }
    }

    @Override
    public final void remove(final IControlHandle handle)
    {
        if (contains(handle))
        {
            m_chlist.remove(handle);
        }
    }

    @Override
    public void destroy()
    {
        final int size = size();

        for (int i = 0; i < size; i++)
        {
            final IControlHandle handle = m_chlist.get(i);

            if (null != handle)
            {
                handle.destroy();
            }
        }
        m_chlist.clear();

        if (null != m_layer)
        {
            m_layer.batch();

            m_layer = null;
        }
    }

    @Override
    public void display(final Layer layer)
    {
        if (null != layer)
        {
            int totl = 0;

            final int size = size();

            for (int i = 0; i < size; i++)
            {
                final IControlHandle handle = m_chlist.get(i);

                if (null != handle)
                {
                    IPrimitive<?> prim = handle.getControl();

                    if (null != prim)
                    {
                        layer.add(prim);

                        totl++;
                    }
                }
            }
            if (totl > 0)
            {
                m_layer = layer;

                m_layer.batch();
            }
        }
    }

    @Override
    public final Iterator<IControlHandle> iterator()
    {
        return Collections.unmodifiableList(m_chlist.toList()).iterator();
    }
}
