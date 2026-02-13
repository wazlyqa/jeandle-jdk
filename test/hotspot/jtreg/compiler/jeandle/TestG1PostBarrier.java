/*
 * Copyright (c) 2026, the Jeandle-JDK Authors. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

/**
 * @test
 * @requires vm.gc.G1
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm
 *   -Xbootclasspath/a:.
 *   -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *   -XX:+UseG1GC -XX:CompileCommand=compileonly,TestG1PostBarrier::test
 *   -Xcomp -XX:-TieredCompilation -XX:+UseJeandleCompiler -XX:+JeandleDumpIR
 *   TestG1PostBarrier
 */

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;
import java.lang.ref.WeakReference;

public class TestG1PostBarrier {

    static void test() {
        String[] stringArray = new String[10];
        stringArray[0] = "123";
        Asserts.assertEquals(stringArray.length, 10);
        Asserts.assertEquals(stringArray[0], "123");
        
        Integer[] integerArray = new Integer[3];
        integerArray[1] = 1;
        Asserts.assertEquals(integerArray.length, 3);
        Asserts.assertEquals(integerArray[1], 1);
    }

    public static void main(String[] args) throws Exception {
        for(int i=0; i<100000; i++) {
            test();
        }
    }
}