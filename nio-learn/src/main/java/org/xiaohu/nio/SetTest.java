package org.xiaohu.nio;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetTest {
    public static void main(String[] args) {
        Set<String> sets = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            sets.add(i + "");
        }
        Iterator<String> it = sets.iterator();
        while (it.hasNext()) {
            String nu = it.next();
            System.out.println("得到的数字：" + nu);
            it.remove();
        }
        System.out.println("count:" + sets.size());
    }
}
