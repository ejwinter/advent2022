package winter.advent.advent2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TreetopHouseTest {

    private final TreetopHouse.Grove grove = TreetopHouse.Grove.fromLines(List.of(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390"
    ));

    final TreetopHouse.Grove largeGrove = TreetopHouse.Grove.fromStream(getClass().getResourceAsStream("/d8/grove.txt"));

    @Test
    public void parserTest(){
        assertEquals(5, grove.getTree(1,1).getHeight());
        assertEquals(0, grove.getTree(4,4).getHeight());
        assertEquals(7, grove.getTree(0,3).getHeight());
    }


    @Test
    public void findOutwardlyVisibleIndices(){
        assertEquals(5, grove.getTree(1,1).getHeight());
        assertEquals(0, grove.getTree(4,4).getHeight());
        assertEquals(7, grove.getTree(0,3).getHeight());
    }

    @Test
    public void pathToEdge(){

        var tree = grove.getTree(2,3);
        var paths = tree.pathsToEdges();

        assertEquals(4, paths.size());

        var up = paths.get(TreetopHouse.Direction.UP).map(TreetopHouse.Grove.Tree::getHeight).toList();
        assertEquals(Arrays.asList(3,1,7), up);

        var down = paths.get(TreetopHouse.Direction.DOWN).map(TreetopHouse.Grove.Tree::getHeight).toList();
        assertEquals(Arrays.asList(3,4,9), down);

        var left = paths.get(TreetopHouse.Direction.LEFT).map(TreetopHouse.Grove.Tree::getHeight).toList();
        assertEquals(Arrays.asList(3, 3, 5, 6), left);

        var right = paths.get(TreetopHouse.Direction.RIGHT).map(TreetopHouse.Grove.Tree::getHeight).toList();
        assertEquals(Arrays.asList(3, 2), right);

    }

    @Test
    public void findVisiblePaths() {

        var tree = grove.getTree(1, 1);

        var directionStreamMap = tree.outwardlyVisiblePath();
        assertEquals(Set.of(TreetopHouse.Direction.UP, TreetopHouse.Direction.LEFT), directionStreamMap.keySet());
    }

    @Test
    public void findVisiblePaths_edge() {

        var tree = grove.getTree(0, 1);

        var directionStreamMap = tree.outwardlyVisiblePath();
        assertEquals(Set.of(TreetopHouse.Direction.UP), directionStreamMap.keySet());
    }

    @Test
    public void visibleTrees() {
        assertEquals(21, grove.visibleTrees().count());
    }

    @Test
    public void calcScenicScore() {
        var tree = grove.getTree(3, 2);
        assertEquals(8, tree.calcScenicScore());
    }

    @Test
    public void visibleTrees_large(){
        assertEquals(1870, largeGrove.visibleTrees().count());
    }


    @Test
    public void mostScenicTree(){
        TreetopHouse.Grove.Tree mostScenicTree = grove.mostScenicTree();
        assertEquals(16, mostScenicTree.calcScenicScore());
    }

    @Test
    public void mostScenicTree_large(){
        TreetopHouse.Grove.Tree mostScenicTree = largeGrove.mostScenicTree();
        assertEquals(517_440, mostScenicTree.calcScenicScore());
    }

}