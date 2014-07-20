package com.liang.java8.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Briliang
 * @date 2014/7/18
 * Description(java.util.Stream示例)
 */
public class Streams {
    private enum Status {OPEN, CLOSED}

    ;

    private static final class Task {
        private final Status status;
        private final Integer points;

        private Task(Status status, Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return String.format("[%s][%d]", status, points);
        }
    }

    public static void main(String[] args) {
        final Collection<Task> tasks = Arrays.asList(new Task(Status.OPEN, 5),
                new Task(Status.OPEN, 13), new Task(Status.CLOSED, 8));
        final long totalPointsOfOpenTasks = tasks.stream().filter(task -> task.getStatus() == Status.OPEN).mapToInt(Task::getPoints).sum();
        System.out.println("Total points:" + totalPointsOfOpenTasks);
        final double totalPoint = tasks.stream().parallel().map(task -> task.getPoints()).reduce(0, Integer::sum);
        System.out.println("Total points (all tasks): " + totalPoint);
        /**
         * sort by condition with stream
         */
        final Map<Status, List<Task>> map = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
        System.out.println(map);
        /**
         * 计算整个集合中每个task分数（或权重）的平均值
         */
       /* final Collection<String> result = tasks.stream().parallel()
                .mapToInt(Task::getPoints)
                .parallel()
                .asLongStream()
                .mapToDouble(points -> points / totalPoint)
                .boxed()
                .mapToLong(weigth->(long)(weigth*100))
                .mapToObj(percentage->percentage+"%")
                .collect(Collectors.toList());*/
        final Collection<String> result1 = tasks.stream().parallel()
                .mapToInt(Task::getPoints)
                .mapToDouble(points -> (points / totalPoint) * 100)
                .mapToObj(percentage -> percentage + "%")
                .collect(Collectors.toList());

        System.out.println("task分数权重:"+result1);
    }

}
