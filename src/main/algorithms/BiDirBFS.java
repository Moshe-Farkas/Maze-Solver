package main.algorithms;

import main.MazeAlgorithm;
import main.MazeSolution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BiDirBFS extends MazeAlgorithm {

    private Set<Point> visitedFromStart;
    private Set<Point> visitedFromEnd;

    public BiDirBFS() {
        super();
        visitedFromEnd = new LinkedHashSet<>();
        visitedFromStart = new LinkedHashSet<>();
    }

    @Override
    public MazeSolution solve() {
        List<Point> shortestPath = bfs();
        List<Point> interleavedVisited = interleaveLists(visitedFromStart.stream().toList(), visitedFromEnd.stream().toList());
        interleavedVisited.remove(board.getEndPos());
        interleavedVisited.remove(board.getStartPos());
        return new MazeSolution(shortestPath, interleavedVisited);
    }

    private List<Point> bfs() {
        ArrayDeque<CustomNode> queueFromStart = new ArrayDeque<>();
        ArrayDeque<CustomNode> queueFromEnd = new ArrayDeque<>();

        queueFromStart.add(new CustomNode(board.getStartPos()));
        queueFromEnd.add(new CustomNode(board.getEndPos()));


        while (!queueFromStart.isEmpty() && !queueFromEnd.isEmpty()) {

            CustomNode currentFromStart = queueFromStart.pop();
            CustomNode currentFromEnd = queueFromEnd.pop();

            List<Point> neighborsFromStart = neighbors(currentFromStart.pos);
            for (Point neighborPos : neighborsFromStart) {
                if (validSpace(neighborPos, visitedFromStart)) {
                    visitedFromStart.add(neighborPos);
                    CustomNode node = new CustomNode(neighborPos);
                    node.parent = currentFromStart;
                    queueFromStart.add(node);
                }
                if (visitedFromEnd.contains(neighborPos)) {

                    // todo
                    List<Point> pathFromStart = constructShortestPath(queueFromStart.pop());
                    Collections.reverse(pathFromStart);
                    CustomNode endTemp;
                    for (endTemp = queueFromEnd.pop(); !endTemp.pos.equals(neighborPos); endTemp = queueFromEnd.pop()) {

                    }
                    List<Point> pathFromEnd = constructShortestPath(endTemp);
                    pathFromStart.addAll(pathFromEnd);
                    return pathFromStart;
                }
            }

            List<Point> neighborsFromEnd = neighbors(currentFromEnd.pos);
            for (Point neighborPos : neighborsFromEnd) {
                if (validSpace(neighborPos, visitedFromEnd)) {
                    visitedFromEnd.add(neighborPos);
                    CustomNode node = new CustomNode(neighborPos);
                    node.parent = currentFromEnd;
                    queueFromEnd.add(node);
                }
//                if (visitedFromStart.contains(neighborPos)) {
//                    List<Point> pathFromStart = constructShortestPath(queueFromStart.pop());
//                    List<Point> pathFromEnd = constructShortestPath(queueFromEnd.pop());
//                    pathFromStart.addAll(pathFromEnd);
//                    return pathFromStart;
//                }
            }

        }

        // if here then no solution
        visitedFromStart = new LinkedHashSet<>();
        visitedFromEnd = new LinkedHashSet<>();
        return new LinkedList<>();
    }

    private List<Point> constructShortestPath(CustomNode current) {

        List<Point> shortestPath = new LinkedList<>();
        while (current.parent != null) {
            shortestPath.add(current.pos);
            current = current.parent;
        }
        return shortestPath;
    }

    private boolean validSpace(Point pos, Set<Point> visited) {
        return
                !visited.contains(pos) &&
                !board.nodeAt(pos.x, pos.y).type.equals("wall");
    }

    private List<Point> interleaveLists(List<Point> list1, List<Point> list2) {
        List<Point> newList = new LinkedList<>();
        List<Point> shorterList;
        List<Point> longerList;
        if (list1.size() > list2.size()) {
            longerList = list1;
            shorterList = list2;
        }
        else {
            longerList = list2;
            shorterList = list1;
        }
        int i;
        for (i = 0; i < shorterList.size(); i++) {

            newList.add(longerList.get(i));
            newList.add(shorterList.get(i));
        }
        for ( ;i < longerList.size(); i++) {
            newList.add(longerList.get(i));
        }
        return newList;
    }


//    private boolean validSpace(int col, int row) {
//        return !outOfBounds(col, row)
//                && !visited.contains(new Point(col, row))
//                && !board.nodeAt(col, row).type.equals("wall");
//    }


//    private java.util.List<Point> bfs() {
//        Map<Point, Point> parents = new HashMap<>();
//        ArrayDeque<Point> queue = new ArrayDeque<>();
//        queue.add(board.getStartPos());
//        while (!queue.isEmpty()) {
//            Point currentPos = queue.pop();
//            if (isEndNode(currentPos)) {
//                return constructShortestPath(parents);
//            }
//            visited.add(currentPos);
//            List<Point> neighbors = neighbors(currentPos);
//            for (Point neighborPos : neighbors) {
//                if (validSpace(neighborPos.x, neighborPos.y)) {
//                    queue.add(neighborPos);
//                    visited.add(neighborPos);
//                    parents.put(neighborPos, currentPos);
//                }
//            }
//        }
//        // if here then no solution
//        visited = new LinkedHashSet<>();
//        return new LinkedList<>();
//    }
//

    private class CustomNode {
        public Point pos;
        public CustomNode parent;

        public CustomNode(Point pos) {
            this.pos = pos;
        }
    }
}
