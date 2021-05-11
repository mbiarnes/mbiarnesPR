/*
 *
 *    Copyright (c) 2017 Ahome' Innovation Technologies. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ait.lienzo.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.ait.tooling.nativetools.client.NObjectJSO;

/**
 * This test just shows how Lienzo's overlay type methods can be mocked by using this library, if you need so.
 *
 * @author Roger Martinez
 * @since 1.0
 *
 */
@RunWith(LienzoMockitoTestRunner.class)
public class JSOMockTest
{
    @Mock
    NObjectJSO objectJSO;

    @Before
    public void setup()
    {
        when(objectJSO.getAsString(anyString())).thenReturn("My Custom #getAsString");
    }

    @Test
    public void test()
    {
        final String s = objectJSO.getAsString("Lienzo rocks ;)");

        Assert.assertEquals("My Custom #getAsString", s);
    }
}
