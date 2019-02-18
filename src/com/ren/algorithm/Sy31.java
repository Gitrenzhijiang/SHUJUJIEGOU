package com.ren.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 会场安排问题
 * 假设在足够多的会场里安排一批活动(N个活动), 每个活动先给定活动的开始时间和
 * 结束时间, 使用贪心算法求出最少需要用多少会场, 并求出每个活动安排在第几个会场
 * 
 * @author renzhijiang
 *
 */
public class Sy31 {

    public static void main(String[] args) {
        Meeting[] ms = {new Meeting(1, 23), new Meeting(12, 28),
                new Meeting(25, 35), new Meeting(27, 80), new Meeting(36, 50)};
        Sy31 sy31 = new Sy31();
        System.out.println("需要:" + sy31.getMinCost(ms) + "个会场.");
        for (int i = 0;i < ms.length;i++) {
            System.out.println(ms[i]);
        }
    }
    public int getMinCost(Meeting[] ms) {
        int max = 1;
        int startIndex = 0, endIndex = 0; //正在进行的活动
        ms[0].no = 1; // 第一个活动在第一个会场
        List<Integer> arr = new ArrayList<>();
        for (int i = 1;i < ms.length;i++) {
            // 开始第i个会议时, 计算有多少个会议处于进行状态
            for (int m = startIndex;m <= endIndex;m++) {
                if (ms[m].endTime <= ms[i].startTime) {
                    startIndex = m+1;
                    arr.add(ms[m].no);
                }
            }
            endIndex++;
            if (ms[i].startTime < ms[endIndex].endTime) {
                if (!arr.isEmpty()) {
                    ms[i].no = arr.remove(arr.size()-1);
                }else {
                    ms[i].no = ++max;
                }
            }else {
                ms[i].no = ms[i-1].no;
            }
        }
        return max;
    }
    // 会议的结构
    public static class Meeting{
        int startTime;
        int endTime;
        int no;
        public Meeting(int st, int et) {
            startTime = st;
            endTime = et;
        }
        @Override
        public String toString() {
            return "Meeting [startTime=" + startTime + ", endTime=" + endTime + ", no=" + no + "]";
        }
        
    }
}
