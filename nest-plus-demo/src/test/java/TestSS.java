import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestSS {
    public static void main(String[] args) {
        List<Integer> sources = new ArrayList<>();
        sources.add(12);
        sources.add(13);
        sources.add(15);
        sources.add(19);
        sources.add(18);
        sources.add(17);
        sources.add(22);
        sources.add(10);
        sources.add(4);
        sources.add(55);
        sources.add(56);
        sources.add(57);
        sources.add(3);
        sources.add(11);
        sources.add(16);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("缩写后：");
        List<Integer> tempList = new ArrayList<>();
        sources.stream().sorted().forEach(p -> {
            if (tempList.size() == 0) {
                tempList.add(p);
                return;
            }

            Integer lastOne = tempList.get(tempList.size() - 1);
            if (lastOne + 1 == p) {
                tempList.add(p);
                return;
            }

            // 获取当前数组的头和属，用短线连接
            if (tempList.size() == 1) {
                stringBuilder.append(lastOne + ",");
            } else {
                int first = tempList.get(0);
                stringBuilder.append(first + "-" + lastOne + ",");
            }

            //清空临时List，重新开始
            tempList.clear();
            tempList.add(p);

        });

        System.out.println(stringBuilder.toString());

    }
}