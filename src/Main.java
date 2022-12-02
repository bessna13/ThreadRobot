import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                int count = 0;
                String letters = generateRoute("RLRFR", 100);
                System.out.println(letters);
                for (int r = 0; r < 100; r++) {

                    if (letters.charAt(r) == 'R') {
                        count++;
                    }
                }
                System.out.println(count);
                addToMap(count);
            }

            );
            threads.add(thread);
            thread.start();
            thread.join();
        }
        int moreKey = sizeToFreq.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();

        int moreVal = sizeToFreq.get(moreKey);
        System.out.println("Самое частое количество повторений " + moreKey + " встретилось " + moreVal);
        System.out.println("Другие размеры: ");
        sizeToFreq.remove(moreKey);
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз);");
        }
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void addToMap(int count) {
        synchronized (Main.class) {
            if (sizeToFreq.containsKey(count)) {
                sizeToFreq.put(count, sizeToFreq.get(count) + 1);
            } else sizeToFreq.put(count, 1);
        }
    }
}