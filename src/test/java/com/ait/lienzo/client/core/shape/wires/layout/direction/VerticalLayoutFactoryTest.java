/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ait.lienzo.client.core.shape.wires.layout.direction;

import org.junit.Test;

import com.ait.lienzo.client.core.shape.wires.layout.direction.DirectionLayout.ReferencePosition;
import com.ait.lienzo.client.core.shape.wires.layout.direction.DirectionLayout.VerticalAlignment;

import static org.junit.Assert.assertTrue;

public class VerticalLayoutFactoryTest
{
    @Test
    public void getInside()
    {
        DirectionLayoutBuilder<VerticalAlignment> insideBuilder = VerticalLayoutFactory.get(ReferencePosition.INSIDE);
        assertTrue(insideBuilder instanceof VerticalLayoutFactory.InnerVerticalLayoutBuilder);
    }

    @Test
    public void getOutside()
    {
        DirectionLayoutBuilder<VerticalAlignment> insideBuilder = VerticalLayoutFactory.get(ReferencePosition.OUTSIDE);
        assertTrue(insideBuilder instanceof VerticalLayoutFactory.OuterVerticalLayoutBuilder);
    }
}