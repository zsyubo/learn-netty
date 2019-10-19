package org.xiaohu.protobuf;

public class MainTets {
    public static void main(String[] args) {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("SB").setAge(19).setAddress("迪迦").build();
        System.out.println(student.toString());
    }
}
