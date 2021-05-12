/*
   Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.

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

package com.ait.lienzo.client.core.event;

import com.ait.tooling.nativetools.client.collection.NFastStringSet;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public final class ImmediateAttributesChangedBatcher implements IAttributesChangedBatcher
{
    public static final ImmediateAttributesChangedBatcher INSTANCE = new ImmediateAttributesChangedBatcher();

    public ImmediateAttributesChangedBatcher()
    {
    }

    @Override
    public void bufferAttributeWithManager(final String name, final AttributesChangedManager manager)
    {
        final long time = System.currentTimeMillis();

        Scheduler.get().scheduleFixedDelay(new RepeatingCommand()
        {
            @Override
            public boolean execute()
            {
                manager.fireChanged(new NFastStringSet(name), time, time);

                return false;
            }
        }, 0);
    }

    @Override
    public final void cancelAttributesChangedBatcher()
    {
    }

    @Override
    public final IAttributesChangedBatcher copy()
    {
        return new ImmediateAttributesChangedBatcher();
    }

    @Override
    public final String getName()
    {
        return "ImmediateAttributesChangedBatcher()";
    }
}
